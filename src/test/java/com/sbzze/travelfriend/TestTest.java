package com.sbzze.travelfriend;


import com.sbzze.travelfriend.mapper.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class TestTest {

    @Autowired
    private TestMapper testMapper;

    @Test
    public void test() {
        System.out.println("----  test ----");
        List<com.sbzze.travelfriend.entity.Test> testList = testMapper.selectList(null);

        testList.forEach(System.out::println);

    }
}
