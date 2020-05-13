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
    private final Logger logger = LogManager.getLogger(this.getClass());
    @GetMapping("/hello")
    public String hello() {
        for (int i=0; i<100; i++) {
            logger.info("info execute index method");
            logger.warn("warn execute index method");
            logger.error("error execute index method");
        }
        System.out.println(System.getProperty("user.home"));
        return "My First SpringBoot Application";
    }
}
