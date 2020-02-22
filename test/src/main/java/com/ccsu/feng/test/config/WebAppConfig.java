package com.ccsu.feng.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * @author admin
 * @create 2020-02-09-13:11
 */
@Configuration
@Slf4j
public class WebAppConfig  implements WebMvcConfigurer {


    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
        factory.setMaxFileSize("10MB");
        //设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController("/").setViewName("/page/index");
        registry.addViewController("/page/xi/person").setViewName("/page/xi/person");
        registry.addViewController("/page/xi/weapon").setViewName("/page/xi/weapon");
        registry.addViewController("/page/xi/place").setViewName("/page/xi/place");
        registry.addViewController("/page/xi/deeds").setViewName("/page/xi/deeds");
        registry.addViewController("/page/home").setViewName("/page/home");
    }


}
