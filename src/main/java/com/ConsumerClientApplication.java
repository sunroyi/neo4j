package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableCircuitBreaker
//@EnableDiscoveryClient
@SpringBootApplication
//或者用下面这个标签代替
//@SpringCloudApplication
@ServletComponentScan
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})	// 启动时不需要数据库
public class ConsumerClientApplication extends WebMvcConfigurerAdapter {

	/**
	 * 跨域设置
	 * 
	 * 
	 **/
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**").allowCredentials(true).allowedHeaders("*")
				.allowedOrigins("*").allowedMethods("*");

	}	
	
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerClientApplication.class, args);
    }
}
