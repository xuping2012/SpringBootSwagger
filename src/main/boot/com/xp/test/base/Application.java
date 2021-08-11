package com.xp.test.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
// @ComponentScan(basePackages = { "com.xp.test.controller",
// "com.xp.test.handler", "com.xp.test.param"})
// 上下两个写法差不太多
@SpringBootApplication(scanBasePackages = { "com.xp.test.controller",
		"com.xp.test.handler" })
// @EnableSwagger2
// @EnableKnife4j
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}