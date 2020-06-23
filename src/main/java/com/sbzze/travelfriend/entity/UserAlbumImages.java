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
@TableName("user_album_images")
public class UserAlbumImages {

    @TableId
    private String id;

    @TableField("album_id")
    private String albumId;

    @TableField("image_type")
    private String imageType;

    @TableField("address")
    private String address;

    @TableField("compress_address")
    private String compressAddress;

}