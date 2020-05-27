package com.sbzze.travelfriend.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("album_images")
public class AlbumImages {

    @TableId
    private String id;

    @TableField("album_id")
    private String albumId;

    @TableField("image")
    private String image;

    @TableField("compress_image")
    private String compressImage;

    @TableField("video")
    private String video;


}