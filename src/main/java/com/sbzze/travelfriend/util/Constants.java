package com.sbzze.travelfriend.util;

public interface Constants {
    // Redis
    String FILE_MD5_KEY = "FILE_MD5:";
    String FILE_UPLOAD_STATUS = "FILE_UPLOAD_STATUS";

    // image_constants表
    String IMAGE = "image";
    String VIDEO = "video";

    // FFmpeg
    String FFMPEG_PATH = "";



    // 文件类型
    enum FileType {
        IMAGE,VIDEO
    }
    // 相册类型
    enum AlbumType {
        USER,TEAM
    }

}
