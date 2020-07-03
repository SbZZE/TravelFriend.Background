package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.entity.TeamAlbumImages;
import com.sbzze.travelfriend.mapper.TeamAlbumImagesMapper;
import com.sbzze.travelfriend.service.TeamAlbumImagesService;
import com.sbzze.travelfriend.util.Constants;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class TeamAlbumImagesServiceImpl extends BaseServiceImpl<TeamAlbumImagesMapper, TeamAlbumImages> implements TeamAlbumImagesService {


    /**
     * 新增照片或视频
     * @param albumId
     * @param imageType
     * @param address
     * @param compressAddress
     * @return id / null
     */
    @Override
    public String insert( String albumId, String imageType, String address, String compressAddress ) {

        String id = UUIDUtil.getUUID();

        TeamAlbumImages teamAlbumImages = new TeamAlbumImages();
        teamAlbumImages.setId(id);
        teamAlbumImages.setAlbumId(albumId);
        teamAlbumImages.setImageType(imageType);
        teamAlbumImages.setAddress(address);
        teamAlbumImages.setCompressAddress(compressAddress);

        if ( baseMapper.insert(teamAlbumImages) > 0 ) {
            return id;
        } else {
            log.error("相册图片/视频插入失败");
            return null;
        }
    }

    // 更新
    @Override
    public int update(String id, String imageType, String address, String compressAddress) {
        TeamAlbumImages teamAlbumImages = findImagesById(id);
        teamAlbumImages.setImageType(imageType);
        teamAlbumImages.setAddress(address);
        teamAlbumImages.setCompressAddress(compressAddress);

        return baseMapper.updateById(teamAlbumImages);
    }

    // 查找所有类型的照片或视频
    @Override
    public List<TeamAlbumImages> findImagesByAlbumId( String albumId ) {
        return baseMapper.findImagesByAlbumId(albumId);
    }

    // 查找指定照片或视频
    @Override
    public TeamAlbumImages findImagesById(String id) {
        return baseMapper.selectById(id);
    }

    // 查找某类照片或视频
    @Override
    public List<TeamAlbumImages> findImagesByAlbumIdAndImageType(String albumId, String imageType) {
        return baseMapper.findImagesByAlbumIdAndImageType(albumId, imageType);
    }

    /**
     * 分片上传照片或视频
     * @param fileChunkDto
     * @return id / null
     */
    @Override
    public String uploadImagesOrVideos( FileChunkDto fileChunkDto ) {
        String fileId = null;

        // 图片/视频保存地址：/ROOT_PATH/SON_PATH/team/album/${teamid}/${albumid}/fileType/
        String signName = TEAM + "/" + ALBUM;
        String fileType = null;

        if (fileChunkDto.getFiletype() == Constants.IMAGE_INT) {
            fileType = Constants.IMAGE;
        }
        if (fileChunkDto.getFiletype() == Constants.VIDEO_INT) {
            fileType = Constants.VIDEO;
        }
        String tagName = fileChunkDto.getTargetid() + "/" + fileChunkDto.getAlbumid() + "/" + fileType;
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName, tagName);

        // 获得临时文件
        File tempFile = FileUtil.sliceUpload(filePath, fileChunkDto);
        // 上传成功
        if (tempFile != null) {
            //生成url保存至数据库
            String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName, tagName);
            //文件原名保存
            String insertFileName = urlPath + fileChunkDto.getFilename();
            fileId = insert(fileChunkDto.getAlbumid(), fileType, insertFileName, null);

            //生成压缩图
            MultipartFile multipartFile = FileUtil.transferMultipartFileToFile(tempFile);
            if (fileType.equals(Constants.IMAGE)) {
                FileUtil.compressFile(multipartFile, filePath, fileChunkDto.getFilename(), PREFIX, 1f, 0.2f);

            }
            if (fileType.equals(Constants.VIDEO)) {
                FileUtil.compressVideoToImage(filePath + fileChunkDto.getFilename(), Constants.FFMPEG_PATH, "320", "200", PREFIX);

            }
            String insertCompressFileName = urlPath + PREFIX + fileChunkDto.getFilename();
            //更新文件表
            update(fileId, fileType, insertFileName, insertCompressFileName);
        }
        return fileId;
    }
}
