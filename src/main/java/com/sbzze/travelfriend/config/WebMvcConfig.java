package com.sbzze.travelfriend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //图片存放根路径
    @Value("${file.rootPath}")
    private String ROOT_PATH;
    //图片存放根目录下的子目录
    @Value("${file.sonPath}")
    private String SON_PATH;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        // linux
        // file:/root/travelfriend/user/image/avatar/
        String filePath = "file:" + ROOT_PATH + SON_PATH + "/";
        //指向外部目录
        registry.addResourceHandler("/user/image/**").addResourceLocations(filePath);

        // windows
        // file:D:/image/
        //String filePath = "file:" + ROOT_PATH + SON_PATH + "/";
        //registry.addResourceHandler("/image/**").addResourceLocations(filePath);
    }
}
