package com.sbzze.travelfriend.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MybatisPlusConfig {

	
		@Bean
	    public PerformanceInterceptor performanceInterceptor(){
	        return new PerformanceInterceptor();
	    }

	    @Bean
	    public PaginationInterceptor paginationInterceptor() {
	    	PaginationInterceptor page =  new PaginationInterceptor();
//	    	page.setDialectType("mysql");
	        // 打开PageHelper localPage 模式
	        page.setLocalPage(false);
	        return page;
	    }
	    
	
}
