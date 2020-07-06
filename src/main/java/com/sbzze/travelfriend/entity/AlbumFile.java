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
@TableName("album_file")
public class AlbumFile {
    @TableId
    private String id;

    @TableField("album_id")
    private String albumId;

    @TableField("type")
    private String type;

    @TableField("address")
    private String address;

    @TableField("compress_address")
    private String compressAddress;
}