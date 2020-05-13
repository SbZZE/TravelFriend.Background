package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.UserMapper;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    // 增
    @Override
    public int insertUser( String username, String password, String nickname, String signature ) {
        User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setUsername(username);
        user.setPassword(password);
        if (null == nickname) {
            user.setNickname(username);
        } else {
            user.setNickname(nickname);
        }
        user.setSignature(signature);

        return baseMapper.insert( user );
    }

    // 查
    @Override
    public User findUserByName( String username ) { return baseMapper.findUserByName( username ); }

    @Override
    public User findUserById( String id ) {
        return baseMapper.findUserById( id );
    }

    @Override
    public UserDto.UserInfoDto findUserInfoByName( String username ) {
        User user = baseMapper.findUserByName( username );
        UserDto.UserInfoDto userInfo = new UserDto.UserInfoDto();

        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setSignature(user.getSignature());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setGender(user.getGender());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setAddress(user.getAddress());

        return userInfo;
    }

    // 改
    @Override
    public int updateUserInfoWithOutAvatar( UserDto.UserInfoWithOutAvatarDto updateDto ) {
        User user = baseMapper.findUserByName( updateDto.getUsername() );

        user.setNickname(updateDto.getNickname());
        user.setGender(updateDto.getGender());
        user.setSignature(updateDto.getSignature());
        user.setBirthday(updateDto.getBirthday());
        user.setAddress(updateDto.getAddress());

        return baseMapper.updateById(user);
    }

}
