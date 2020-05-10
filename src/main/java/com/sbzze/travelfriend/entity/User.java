package com.sbzze.travelfriend.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;


@Data
@TableName("user")
public class User {

    @TableId
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

}