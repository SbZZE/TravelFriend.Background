package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.entity.AlbumImages;
import org.springframework.web.multipart.MultipartFile;


public interface AlbumImagesService extends IService<AlbumImages> {
    AlbumImages findImagesByAlbumId(String albumId);

    int updateUserAlbum(String username, MultipartFile[] files, String albumname);
}
