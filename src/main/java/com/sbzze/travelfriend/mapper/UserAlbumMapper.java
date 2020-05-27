package com.sbzze.travelfriend.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.UserAlbum;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAlbumMapper extends BaseMapper<UserAlbum> {
    UserAlbum findAlbumByUserIdAndAlbumName(@Param("userId") String userId, @Param("albumname") String albumname);
    List<UserAlbum> findAlbumByUserId(@Param("userId") String userId);
}