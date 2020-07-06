package com.sbzze.travelfriend.util;

public interface Constants {
    // Redis
    String FILE_MD5_KEY = "FILE_MD5:";
    String FILE_UPLOAD_STATUS = "FILE_UPLOAD_STATUS";

    // constants表
    String IMAGE = "image";
    String VIDEO = "video";
    String USER = "user";
    String TEAM = "team";

    // FFmpeg
    //Linux
    String FFMPEG_PATH = "/root/ffmpeg/ffmpeg-4.2.3/bin/./ffmpeg";
    //windows
    //String FFMPEG_PATH = "";



    // 文件类型
    int IMAGE_INT = 0;
    int VIDEO_INT = 1;
    // 相册类型
    int USER_INT = 0;
    int TEAM_INT = 1;

    String ALBUM = "album";
    String COVER = "cover";

}
