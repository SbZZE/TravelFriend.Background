package com.sbzze.travelfriend.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.IPage;
import com.sbzze.travelfriend.entity.Test;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.TestMapper;
import com.sbzze.travelfriend.service.TestService;
import org.springframework.stereotype.Service;


/**
 * Test
 * @author TJ
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public int testInsert(Test tst) {
        return baseMapper.insert(tst);
    }


}
