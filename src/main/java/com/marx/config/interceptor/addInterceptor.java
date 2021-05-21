package com.marx.config.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class addInterceptor implements WebMvcConfigurer {

  @Autowired
  private MyInterceptor myInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration interceptorRegistration = registry.addInterceptor(myInterceptor);
    //添加你要拦截的请求
    interceptorRegistration.addPathPatterns("/**");
    //添加你不想被拦截的请求
    interceptorRegistration.excludePathPatterns("/login","/logout").
            excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/**")
            .excludePathPatterns("/upload/**");
  }
}
