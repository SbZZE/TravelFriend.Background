package com.sbzze.travelfriend.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.UserAlbumImages;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface UserAlbumImagesMapper extends BaseMapper<UserAlbumImages> {

    List<UserAlbumImages> findImagesByAlbumId(@Param("albumId") String albumId);
}