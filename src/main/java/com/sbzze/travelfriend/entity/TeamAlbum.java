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
 * @Time:15:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("team_album")
public class TeamAlbum {
    @TableId
    private String id;

    @TableField("team_id")
    private String teamid;

    @TableField("cover")
    private String cover;

    @TableField("compress_cover")
    private String compressCover;

    @TableField("albumname")
    private String albumname;

    @TableField("introduction")
    private String introduction;

    @TableField("count")
    private String count;
}
