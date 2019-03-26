package com.mao;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMVCConfig  extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/bgm/")
                .addResourceLocations("classpath:/")
                .addResourceLocations("file:F:/mini_video_dev/");
              //  .addResourceLocations("classpath:/resources/public/");
//        .addResourceLocations("classpath:/META-INF/resources/")
//        .addResourceLocations("classpath:/resources/bgm/")
//        .addResourceLocations("classpath:/resources/")


    }


}
