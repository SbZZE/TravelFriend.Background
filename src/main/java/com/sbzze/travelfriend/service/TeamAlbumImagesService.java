package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.entity.TeamAlbumImages;

import java.util.List;


public interface TeamAlbumImagesService extends IService<TeamAlbumImages> {

    // 新增照片或视频
    String insert(String albumId, String imageType, String address, String compressAddress);

    // 更新
    int update(String id, String imageType, String address, String compressAddress);

    // 查找所有类型照片或视频
    List<TeamAlbumImages> findImagesByAlbumId(String albumId);

    // 查找指定照片或视频
    TeamAlbumImages findImagesById(String id);

    // 查找某类照片或视频
    List<TeamAlbumImages> findImagesByAlbumIdAndImageType(String albumId, String imageType);

    // 上传照片或视频
    String uploadImagesOrVideos(FileChunkDto fileChunkDto);
}
