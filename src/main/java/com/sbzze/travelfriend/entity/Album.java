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
@TableName("album")
public class Album {
    @TableId
    private String id;

    @TableField("type")
    private String type;

    @TableField("type_id")
    private String typeId;

    @TableField("albumname")
    private String albumname;

    @TableField("introduction")
    private String introduction;

    @TableField("count")
    private Integer count;

    @TableField("cover")
    private String cover;
}