package com.sbzze.travelfriend.serviceImpl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.sbzze.travelfriend.service.BaseService;
import org.springframework.beans.factory.annotation.Value;

public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements BaseService<T> {

    //图片存放根路径 /root/travelfriend
    @Value("${file.rootPath}")
    public String ROOT_PATH;

    //图片存放根目录下的子目录 /image
    @Value("${file.sonPath}")
    public String SON_PATH;

    //获取主机端口
    @Value("${server.port}")
    public String POST;

    //前缀 COMPRESS_
    @Value("${file.prefix}")
    public String PREFIX;

    public String MAN = "1";
    public String WOMAN = "0";
    public String AVATAR = "avatar";
    public String ALBUM = "album";
    public String COVER = "cover";
    public String TEAM = "team";
    public String USER = "user";

    @Override
    public PageInfo<T> findPageList(Wrapper<T> wrappe, Integer pageNum, Integer pageSize) {
        return null;
    }
}
