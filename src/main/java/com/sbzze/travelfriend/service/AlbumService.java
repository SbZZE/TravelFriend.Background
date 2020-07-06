package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.AlbumDto;
import com.sbzze.travelfriend.entity.Album;


public interface AlbumService extends IService<Album> {
    /**
     * 判断相册是否存在
     * @param targetId
     * @param albumName
     * @return
     */
    boolean isAlbumExist(String targetId, String albumName);

    /**
     * 判断相册是否存在
     * @param id
     * @return
     */
    boolean isAlbumExist(String id);

    /**
     * 新建相册
     * @param albumAddDto
     * @return
     */
    String addAlbum(AlbumDto.AlbumAddDto albumAddDto);

    /**
     * 根据相册ID获取指定宽高相册封面
     * @param albumid
     * @param width
     * @param height
     * @return
     */
    byte[] getAlbumCover(String albumid, String width, String height);

    /**
     * 修改相册信息(不含相册封面)
     * @param id
     * @param albumname
     * @param introduction
     * @return
     */
    int updateAlbumInfo(String id, String albumname, String introduction);

}
