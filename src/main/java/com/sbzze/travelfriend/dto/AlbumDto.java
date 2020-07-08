package com.sbzze.travelfriend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class AlbumDto {
    @Data
    public static class AlbumInfoDto {
        private String albumid;
        private String albumname;
        private String introduction;
    }

    @Data
    public static class AlbumFileInfoDto {
        private String fileid;
        private int type;
    }

    @Data
    public static class AlbumAddDto {
        private String targetid;
        private String target;
        private String albumname;
        private String introduction;
        private MultipartFile cover;
    }

}
