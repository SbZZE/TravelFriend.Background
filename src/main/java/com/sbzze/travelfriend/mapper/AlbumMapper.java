package com.sbzze.travelfriend.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.Album;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlbumMapper extends BaseMapper<Album> {
    Album findAlbumByTypeIdAndAlbumName(@Param("typeId") String typeId, @Param("albumname") String albumname);
}