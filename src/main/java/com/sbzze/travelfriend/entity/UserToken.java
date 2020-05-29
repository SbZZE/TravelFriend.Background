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
@TableName("user_token")
public class UserToken {

    @TableId
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("token")
    private String token;

}