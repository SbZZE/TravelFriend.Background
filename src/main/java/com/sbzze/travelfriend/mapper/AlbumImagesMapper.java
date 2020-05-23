package com.sbzze.travelfriend.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.AlbumImages;
import org.springframework.data.repository.query.Param;

public interface AlbumImagesMapper extends BaseMapper<AlbumImages> {

    AlbumImages findImagesByAlbumId(@Param("albumId") String albumId);
}