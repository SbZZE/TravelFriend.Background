package com.sbzze.travelfriend.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.IPage;
import com.sbzze.travelfriend.entity.User;

public interface UserService extends IService<User> {
    int insertUser( User user );
    int updateUser( User user );
    int deleteUser( User user );
    User findUserByName( String userName );
    IPage getUserPage(Page page, User user );
}
