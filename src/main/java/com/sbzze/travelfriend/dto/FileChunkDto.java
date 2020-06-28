package com.sbzze.travelfriend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileChunkDto {
    // 用户名或团队ID
    private String targetid;
    // 相册ID
    private String albumid;
    // 相册类型(用户-USER,团队-TEAM)
    private int albumtype;
    // 文件名
    private String filename;
    // 文件类型(图片-IMAGE,视频-VIDEO)
    private int filetype;
    // 文件唯一标识(md5)
    private String identifier;
    // 文件总大小
    private int  totalsize;
    // 分片数量
    private int totalchunks;
    // 分片序号
    private int chunknumber;
    // 分片大小
    private int chunksize;
    // 当前分片大小
    private int currentchunksize;
    // 分片文件
    private MultipartFile filechunk;
}
