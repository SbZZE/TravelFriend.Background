package com.sbzze.travelfriend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：tj
 * @description: swagger的配置文件
 * @date:9:45 2020/05/08
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //public static final String URL = "http://2p277534k9.iok.la:58718/jc/common";
    
    @Bean
    public Docket productPlanApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("测试")
                .apiInfo(apiInfo("测试","v1.0",""))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sbzze.travelfriend.controller.TestController"))
                .paths(PathSelectors.any())
                .build();
                //.globalOperationParameters(getToken(null));
    }

    
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("全部接口")
                .apiInfo(apiInfo("全部","v1.0",""))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sbzze.travelfriend.controller"))
                .paths(PathSelectors.any())
                .build();
                //.globalOperationParameters(getToken(null));
                
    }
    

	private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey("token", "token", "header"));
        return apiKeyList;
    }
	
	
    private ApiInfo apiInfo(String title, String version, String description) {
        return new ApiInfoBuilder()
                .title(title)                 //标题
                .description(description)     //说明
                .version(version)             //版本号
                .build();
    }
    
    private List<Parameter> getToken(List<Parameter> pars) {
    	ParameterBuilder tokenPar = new ParameterBuilder();
    	if(pars==null) {
    		pars = new ArrayList<>();
    	}
        tokenPar.name("token").description("令牌").
        modelRef(new ModelRef("string")).
        parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

    
  
	


}
