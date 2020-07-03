package com.sbzze.travelfriend.dto;

import lombok.Data;

public class AlbumDto {
    @Data
    public static class AlbumInfoDto {
        private String albumid;
        private String target;
        private String albumname;
        private String introduction;
    }
}
