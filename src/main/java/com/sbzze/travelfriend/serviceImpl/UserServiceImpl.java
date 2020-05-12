package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.UserMapper;
import com.sbzze.travelfriend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    // 查
    @Override
    public User findUserByName( String username ) { return baseMapper.findUserByName( username ); }

    @Override
    public User findUserById( String id ) {
        return baseMapper.findUserById( id );
    }

}
