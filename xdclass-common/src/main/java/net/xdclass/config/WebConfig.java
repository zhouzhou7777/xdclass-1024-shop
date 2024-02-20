package net.xdclass.config;

import net.xdclass.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注入自定义拦截器 .excludePathPatterns放行注册及登入
        registry.addInterceptor(loginInterceptor).excludePathPatterns("user/login","user/register");
    }

}
