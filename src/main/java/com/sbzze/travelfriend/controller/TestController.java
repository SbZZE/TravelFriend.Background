package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.HttpResult;
import com.sbzze.travelfriend.entity.Test;
import com.sbzze.travelfriend.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation("insert")
    @PostMapping("/insertTest")
    public String insertData(@RequestBody Test tst) {
      return testService.testInsert(tst);
    }


}
