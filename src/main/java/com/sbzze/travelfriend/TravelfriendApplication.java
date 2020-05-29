package com.sbzze.travelfriend;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan({"com.sbzze.travelfriend.mapper"})
@ComponentScan(basePackages = {"com.sbzze.travelfriend.*"})
@ServletComponentScan
public class TravelfriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelfriendApplication.class, args);
    }

}
