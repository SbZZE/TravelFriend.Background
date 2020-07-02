package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.entity.UserAlbumImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserAlbumImagesService extends IService<UserAlbumImages> {

    UserAlbumImages findImagesById(String id);

    String insert(String albumId, String imageType, String address, String compressAddress);

    // 更新
    int update(String id, String imageType, String address, String compressAddress);

    String uploadImagesOrVideos(FileChunkDto fileChunkDto);
}
