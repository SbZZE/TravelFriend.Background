package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.entity.Test;
import com.sbzze.travelfriend.mapper.TestMapper;
import com.sbzze.travelfriend.service.TestService;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Test
 * @author TJ
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    public String testInsert(Test tst) {
        int flag;
        flag = testMapper.insertSelective(tst);
        if (flag > 0) {
            System.out.println("insert success");
            return "SUCCESS";
        }
        else {
            System.out.println("insert error");
            return "ERROR";
        }
    }

}
