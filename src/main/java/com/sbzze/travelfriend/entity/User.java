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
@TableName("user")
public class User {

    @TableId
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("signature")
    private String signature;

    @TableField("avatar")
    private String avatar;

    @TableField("gender")
    private String gender;

    @TableField("birthday")
    private String birthday;

    @TableField("address")
    private String address;

    @TableField("compress_avatar")
    private String compressAvatar;

}