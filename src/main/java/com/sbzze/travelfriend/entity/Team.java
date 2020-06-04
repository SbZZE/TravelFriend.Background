package com.sbzze.travelfriend.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:15:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_team")
public class Team {
    @TableId
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("team_name")
    private String teamName;

    @TableField("avatar")
    private String avatar;

    @TableField("compress_avatar")
    private String compressAvatar;

    @TableField("introduction")
    private String introduction;

    @TableField("member")
    private char member[];


}
