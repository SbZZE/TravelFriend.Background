package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.entity.UserAlbumImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserAlbumImagesService extends IService<UserAlbumImages> {
    List<UserAlbumImages> findImagesByAlbumId(String albumId);

    int updateUserAlbum(String username, MultipartFile[] files, String albumname);
}
