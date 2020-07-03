package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.filter.PassToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@PassToken
@RequestMapping("/testlogging")
public class LoggingTestController {

    @GetMapping("/hello")
    public String hello() {
        return "My First SpringBoot Application";
    }
}
