package org.seckill.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Created by touch on 2016/8/28 0028.
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.seckill.web")
public class WebConfig extends WebMvcConfigurerAdapter {

   @Bean
   public ViewResolver viewResolver(){
       InternalResourceViewResolver resourceViewResolver=new InternalResourceViewResolver();
       resourceViewResolver.setPrefix("/WEB-INF/views/");
       resourceViewResolver.setSuffix(".jsp");
       resourceViewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
       return resourceViewResolver;
   }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }



}
