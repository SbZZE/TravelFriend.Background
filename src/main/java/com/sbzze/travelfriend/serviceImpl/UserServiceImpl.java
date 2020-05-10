package com.sbzze.travelfriend.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.IPage;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.UserMapper;
import com.sbzze.travelfriend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    // 增
    @Override
    public int insertUser(User user) {
        return baseMapper.insert( user );
    }

    // 改
    @Override
    public int updateUser(User user) {
        return baseMapper.updateById( user );
    }

    // 删
    @Override
    public int deleteUser(User user) {
        return baseMapper.deleteById( user.getId() );
    }

    //TODO
    // 查
    @Override
    public User findUserByName( String userName ) {
        User user = new User();
        //return baseMapper.getUserByName( userName );
        return user;
    }

    @Override
    public IPage getUserPage(Page page, User user) {
        return null;
    }
}
