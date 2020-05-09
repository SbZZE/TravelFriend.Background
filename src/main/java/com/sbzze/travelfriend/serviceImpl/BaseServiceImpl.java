package com.sbzze.travelfriend.serviceImpl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.sbzze.travelfriend.service.BaseService;

public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements BaseService<T> {


    @Override
    public PageInfo<T> findPageList(Wrapper<T> wrappe, Integer pageNum, Integer pageSize) {
        return null;
    }
}
