package com.ccsu.feng.test.config;

import com.ccsu.feng.test.interceptor.AdminUserLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

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


    @Bean
    public HandlerInterceptor getAdminUserLoginInterceptor(){
        return new AdminUserLoginInterceptor();
    }

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController("/admin").setViewName("/admin/index");
        registry.addViewController("/admin/index").setViewName("/admin/index");
        registry.addViewController("/admin/login").setViewName("/admin/login");
        registry.addViewController("/admin/xi/person").setViewName("/admin/xi/person");
        registry.addViewController("/admin/xi/weapon").setViewName("/admin/xi/weapon");
        registry.addViewController("/admin/xi/place").setViewName("/admin/xi/place");
        registry.addViewController("/admin/xi/deeds").setViewName("/admin/xi/deeds");
        registry.addViewController("/admin/xi/relation").setViewName("/admin/xi/relation");
        registry.addViewController("/admin/home").setViewName("/admin/home");
        registry.addViewController("/login").setViewName("/page/login");
        registry.addViewController("/").setViewName("/page/login");
        registry.addViewController("/register").setViewName("/page/register");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration loginRegistry = registry.addInterceptor(getAdminUserLoginInterceptor());
        // 拦截路径
        loginRegistry.addPathPatterns("/admin/**");
        loginRegistry.excludePathPatterns("/admin/login");
        loginRegistry.excludePathPatterns("/admin/loginOut");

//        // 排除路径
//        loginRegistry.excludePathPatterns("/");
//        loginRegistry.excludePathPatterns("/login");
//        loginRegistry.excludePathPatterns("/register");
//        loginRegistry.excludePathPatterns("/loginout");
//        // 排除资源请求
//        loginRegistry.excludePathPatterns("/common/**");
//        loginRegistry.excludePathPatterns("/admin/**");
    }



}
