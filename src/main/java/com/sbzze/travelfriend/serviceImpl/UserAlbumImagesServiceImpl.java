package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.entity.TeamAlbumImages;
import com.sbzze.travelfriend.entity.UserAlbumImages;
import com.sbzze.travelfriend.entity.UserAlbum;
import com.sbzze.travelfriend.mapper.UserAlbumImagesMapper;
import com.sbzze.travelfriend.service.UserAlbumImagesService;
import com.sbzze.travelfriend.service.UserAlbumService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.Constants;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class UserAlbumImagesServiceImpl extends BaseServiceImpl<UserAlbumImagesMapper, UserAlbumImages> implements UserAlbumImagesService {

    @Autowired
    UserService userService;

    @Autowired
    UserAlbumService userAlbumService;

    @Override
    public UserAlbumImages findImagesById(String id) {
        return baseMapper.selectById(id);
    }

    /**
     * 新增照片或视频
     * @param albumId
     * @param imageType
     * @param address
     * @param compressAddress
     * @return id / null
     */
    @Override
    public String insert(String albumId, String imageType, String address, String compressAddress) {

        String id = UUIDUtil.getUUID();

        UserAlbumImages userAlbumImages = new UserAlbumImages();
        userAlbumImages.setId(id);
        userAlbumImages.setAlbumId(albumId);
        userAlbumImages.setImageType(imageType);
        userAlbumImages.setAddress(address);
        userAlbumImages.setCompressAddress(compressAddress);

        if ( baseMapper.insert(userAlbumImages) > 0 ) {
            return id;
        } else {
            return null;
        }
    }

    // 更新
    @Override
    public int update(String id, String imageType, String address, String compressAddress) {
        UserAlbumImages userAlbumImages = findImagesById(id);
        userAlbumImages.setImageType(imageType);
        userAlbumImages.setAddress(address);
        userAlbumImages.setCompressAddress(compressAddress);

        return baseMapper.updateById(userAlbumImages);
    }

    /**
     * 分片上传照片或视频
     * @param fileChunkDto
     * @return id / null
     */
    @Override
    public String uploadImagesOrVideos( FileChunkDto fileChunkDto ) {
        String fileId = null;

        // 图片/视频保存地址：/ROOT_PATH/SON_PATH/user/album/${username}/${albumid}/fileType
        String signName = USER + "/" + ALBUM;
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
