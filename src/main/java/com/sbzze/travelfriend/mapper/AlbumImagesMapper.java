package com.sbzze.travelfriend.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.AlbumImages;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface AlbumImagesMapper extends BaseMapper<AlbumImages> {

    AlbumImages findImagesByAlbumId(@Param("albumId") String albumId);
}