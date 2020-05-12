package com.sbzze.travelfriend.service;


import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.entity.User;

public interface UserService extends IService<User> {
    User findUserByName( String username );
    User findUserById( String id );
}
