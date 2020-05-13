package com.sbzze.travelfriend.service;


import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.dto.UserDto.UserInfoWithOutAvatarDto;
import com.sbzze.travelfriend.entity.User;

public interface UserService extends IService<User> {
    int insertUser( String username, String password, String nickname, String signature );
    User findUserByName( String username );
    User findUserById( String id );
    UserDto.UserInfoDto findUserInfoByName( String username );
    int updateUserInfoWithOutAvatar(UserInfoWithOutAvatarDto updateDto);
}
