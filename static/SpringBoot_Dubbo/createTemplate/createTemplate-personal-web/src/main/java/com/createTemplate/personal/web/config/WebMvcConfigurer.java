/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.personal.web.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.dubbo.config.ApplicationConfig;

@EnableWebMvc
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * 自定义拦截器
	 * 
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new
		// BootInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	/**
	 * 跨域处理 映射所有路径 允许所有来源 以下方法请求
	 * 
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE",
				"PATCH");
	}

	/**
	 * 初始页面
	 * 
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
          registry.addResourceHandler("/webjars/**")
                 .addResourceLocations("classpath:/META-INF/resources/webjars/");
          registry.addResourceHandler("401.html")
          .addResourceLocations("classpath:/static/");
          registry.addResourceHandler("404.html")
          .addResourceLocations("classpath:/static/");
          registry.addResourceHandler("500.html")
          .addResourceLocations("classpath:/static/");

    }
	
    
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
       return new EmbeddedServletContainerCustomizer() {
          @Override
          public void customize(ConfigurableEmbeddedServletContainer container) {
             ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
             ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
             ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
             container.addErrorPages(error401Page, error404Page, error500Page);
          }
       };
    }
    
//    @Bean
//    public ApplicationConfig applicationConfig(){
//    	ApplicationConfig config = new ApplicationConfig();
//    	config.setName("personal_web");
//    	config.setQosAcceptForeignIp(false);
//    	config.setQosEnable(false);
//    	config.setQosPort(21221);
//    	return config;
//    }
}
