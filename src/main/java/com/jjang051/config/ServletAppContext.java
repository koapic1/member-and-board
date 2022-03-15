package com.jjang051.config;

import java.util.Properties; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.jjang051.controller")
public class ServletAppContext implements WebMvcConfigurer {
	
	//jsp viewResolver 처리
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/",".jsp");
	}
	
	// 정적 파일 찾기...
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/galleryImage/**").addResourceLocations("file:///Users/klow_on/Desktop/TIS/galleryImage/");
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
//	@Bean
//	public JavaMailSender javaMailSender() {
//		Properties properties = new Properties(); 
//		properties.put("mail.smtp.auth", true);
//		properties.put("mail.transport.protocol", "smtp");
//		properties.put("mail.smtp.starttls.enable", true);
//		properties.put("mail.smtp.starttls.required", true);
//		properties.put("mail.debug", true);
//		
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.gmail.com");
//		mailSender.setPort(465);
//		mailSender.setUsername("jjang051");
//		mailSender.setPassword("ulciaixoxwqavfsx");
//		mailSender.setDefaultEncoding("utf-8");
//		mailSender.setJavaMailProperties(properties);
//		
//		return mailSender;
//		
//	}
}
