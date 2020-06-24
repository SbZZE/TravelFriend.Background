package com.sbzze.travelfriend.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.TeamAlbumImages;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface TeamAlbumImagesMapper extends BaseMapper<TeamAlbumImages> {

    List<TeamAlbumImages> findImagesByAlbumId(@Param("albumId") String albumId);

    List<TeamAlbumImages> findImagesByAlbumIdAndImageType(@Param("albumId") String albumId, @Param("imageType") String imageType);
}