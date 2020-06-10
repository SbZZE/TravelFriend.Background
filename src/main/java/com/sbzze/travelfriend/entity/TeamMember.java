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
 * @Date:2020/6/10
 * @Time:17:18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("team_member")
public class TeamMember {
    @TableId
    private String id;

    @TableField("team_id")
    private String teamid;

    @TableField("member_name")
    private String membername;

    @TableField("member_nickname")
    private String membernickname;

    @TableField("isleader")
    private boolean isleader;

}
