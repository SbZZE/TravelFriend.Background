package com.sbzze.travelfriend.service;


import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.dto.UserDto.UserInfoWithOutAvatarDto;
import com.sbzze.travelfriend.entity.User;
import org.springframework.web.multipart.MultipartFile;


public interface UserService extends IService<User> {
    int insertUser(String username, String password, String nickname);

    User findUserByName(String username);

    User findUserById(String id);

    UserDto.UserInfoWithOutAvatarDto findUserInfoByName(String username);

    int updateUserInfoWithOutAvatar(UserInfoWithOutAvatarDto updateDto);

    int updateUserAvatar(String username, MultipartFile file);

    byte[] getAvatar(String username);

    byte[] getCompressAvatar(String username);

    int deleteUser(String username);
}
