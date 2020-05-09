package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

public interface BaseService<T> extends IService<T> {


    /**
     * 分页查询
     * @param wrappe
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<T> findPageList(Wrapper<T> wrappe, Integer pageNum, Integer pageSize );
}
