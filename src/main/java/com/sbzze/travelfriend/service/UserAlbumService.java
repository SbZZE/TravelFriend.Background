package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.UserAlbum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserAlbumService extends IService<UserAlbum> {
    UserAlbum findAlbumByUserIdAndAlbumName( String userId, String albumname );


    List<UserAlbum> findAlbumByUserId(String userId);

    boolean isAlbumExist(String username, String albumname);

    String addAlbum(UserDto.UserAlbumDto userAlbumDto);

    List<Object> getAlbumInfo(String username);

    byte[] getAlbumCover(String albumid);
}
