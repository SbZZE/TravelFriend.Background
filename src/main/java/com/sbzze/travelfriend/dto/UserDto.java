package com.sbzze.travelfriend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


public class UserDto {

    @Data
    public static class UserRegisterDto {
        private String username;
        private String password;
        private String nickname;
    }

    @Data
    public static class UserInfoDto {
        private String username;
        private String nickname;
        private String signature;
        private String avatar;
        private String gender;
        private String birthday;
        private String address;
    }

    @Data
    public static class UserInfoWithOutAvatarDto {
        private String username;
        private String nickname;
        private String signature;
        private String gender;
        private String birthday;
        private String address;
    }

    @Data
    public static class UserAlbumDto {
        private String username;
        private String albumname;
        private String introduction;
        private MultipartFile cover;
    }

    @Data
    public static class UserAlbumInfoDto {
        private String albumid;
        private String albumname;
        private String introduction;
    }


}
