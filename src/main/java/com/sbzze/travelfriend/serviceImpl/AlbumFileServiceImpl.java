package com.sbzze.travelfriend.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.entity.AlbumFile;
import com.sbzze.travelfriend.mapper.AlbumFileMapper;
import com.sbzze.travelfriend.service.AlbumFileService;
import com.sbzze.travelfriend.util.Constants;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class AlbumFileServiceImpl extends BaseServiceImpl<AlbumFileMapper, AlbumFile> implements AlbumFileService {

    /**
     * 获取相册文件列表
     * @param albumId
     * @return
     */
    @Override
    public List<AlbumFile> getAlbumFileList( String albumId ) {
        Wrapper<AlbumFile> wrapper = new EntityWrapper<>();
        wrapper.eq("album_id", albumId);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 获取文件缩略图
     * @param fileid
     * @param width
     * @param height
     * @return
     */
    @Override
    public byte[] getCompressFile( String fileid,  String width,  String height) {
        AlbumFile albumFile = baseMapper.selectById(fileid);
        if ( null == albumFile || null == albumFile.getCompressAddress() ) {
            return null;
        }
        String coverUrl = albumFile.getCompressAddress();

        byte[] fileBytes = FileUtil.downloadFileBytesByWidthAndHeight(coverUrl, Integer.valueOf(width), Integer.valueOf(height));
        return fileBytes;
    }

    /**
     * 新增照片或视频
     * @param albumId
     * @param type
     * @param address
     * @param compressAddress
     * @return id / null
     */
    @Override
    public String insert(String albumId, String type, String address, String compressAddress) {

        String id = UUIDUtil.getUUID();

        AlbumFile albumFile = new AlbumFile();
        albumFile.setId(id);
        albumFile.setAlbumId(albumId);
        albumFile.setType(type);
        albumFile.setAddress(address);
        albumFile.setCompressAddress(compressAddress);

        if ( baseMapper.insert(albumFile) > 0 ) {
            return id;
        } else {
            log.error(albumId + "相册图片/视频插入失败");
            return null;
        }
    }

    /**
     * 更新文件信息
     * @param id
     * @param type
     * @param address
     * @param compressAddress
     * @return
     */
    @Override
    public int update(String id, String type, String address, String compressAddress) {
        AlbumFile albumFile = baseMapper.selectById(id);
        albumFile.setType(type);
        albumFile.setAddress(address);
        albumFile.setCompressAddress(compressAddress);

        return baseMapper.updateById(albumFile);
    }

    /**
     * 分片上传照片或视频
     * @param fileChunkDto
     * @return id / null
     */
    @Override
    public Pair<Integer, String> uploadImagesOrVideos(FileChunkDto fileChunkDto) {
        String fileId = null;
        String signName = null;
        String fileType = null;
        int chunkNumber;

        // 图片/视频保存地址：/ROOT_PATH/SON_PATH/${albumType}/album/${targetid}/${albumid}/fileType
        if (fileChunkDto.getAlbumtype() == Constants.USER_INT) {
            signName = Constants.USER + "/" + Constants.ALBUM;
        }
        if (fileChunkDto.getAlbumtype() == Constants.TEAM_INT) {
            signName = Constants.TEAM + "/" + Constants.ALBUM;
        }
        if (fileChunkDto.getFiletype() == Constants.IMAGE_INT) {
            fileType = Constants.IMAGE;
        }
        if (fileChunkDto.getFiletype() == Constants.VIDEO_INT) {
            fileType = Constants.VIDEO;
        }
        String tagName = fileChunkDto.getTargetid() + "/" + fileChunkDto.getAlbumid() + "/" + fileType;
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName, tagName);

        // 获得临时文件
        Pair<Boolean, File> tempPair = FileUtil.sliceUpload(filePath, fileChunkDto);
        boolean isChunkUpload = tempPair.getLeft();
        File tempFile = tempPair.getRight();
        // 分片上传成功
        if (isChunkUpload) {
            chunkNumber = fileChunkDto.getChunknumber();
        } else {
            chunkNumber = fileChunkDto.getChunknumber()-1;
        }
        // 文件上传成功
        if (tempFile != null) {
            //生成url保存至数据库
            String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName, tagName);
            //文件原名保存
            String insertFileName = urlPath + fileChunkDto.getFilename();
            fileId = insert(fileChunkDto.getAlbumid(), fileType, insertFileName, null);

            //生成压缩图
            MultipartFile multipartFile = FileUtil.transferFileToMultipartFile(tempFile);
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
        Pair<Integer, String> pair =  new ImmutablePair<>(chunkNumber, fileId);
        return pair;
    }
}
