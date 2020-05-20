package com.sbzze.travelfriend.dto;

import lombok.Data;


public class UserDto {

    @Data
    public static class UserRegisterDto {
        private String username;
        private String password;
        private String nickname;
        private String signature;
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


}
