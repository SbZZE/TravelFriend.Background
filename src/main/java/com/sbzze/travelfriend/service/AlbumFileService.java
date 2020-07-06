package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.entity.AlbumFile;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface AlbumFileService extends IService<AlbumFile> {
    /**
     * 获取相册文件列表
     * @param albumId
     * @return
     */
    List<AlbumFile> getAlbumFileList(String albumId);

    /**
     * 获取文件缩略图
     * @param fileid
     * @param width
     * @param height
     * @return
     */
    byte[] getCompressFile(String fileid, String width, String height);

    /**
     * 新增照片或视频
     * @param albumId
     * @param type
     * @param address
     * @param compressAddress
     * @return id / null
     */
    String insert(String albumId, String type, String address, String compressAddress);

    /**
     * 更新文件信息
     * @param id
     * @param type
     * @param address
     * @param compressAddress
     * @return
     */
    int update(String id, String type, String address, String compressAddress);

    /**
     * 分片上传照片或视频
     * @param fileChunkDto
     * @return id / null
     */
    Pair<Integer, String> uploadImagesOrVideos(FileChunkDto fileChunkDto);
}
