package com.xp.test.common.config;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


@RequiredArgsConstructor
@EnableOpenApi
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		// DocumentationType.SWAGGER_2
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				.enable(true)
				.select()
				// 加了ApiOperation注解的类，才生成接口文档
				.apis(RequestHandlerSelectors
						.withClassAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.globalResponses(HttpMethod.GET, getGlobalResponseMessage())
				.globalResponses(HttpMethod.POST, getGlobalResponseMessage())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(
						Arrays.asList(new ApiKey("token", "token",
								SecurityScheme.In.HEADER.name())));

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("springboot-swagger-demo")
				.description("接口文档").termsOfServiceUrl("http://127.0.0.1:8090")
				.version("3.0").build();
	}

	/**
	 * 生成通用响应信息
	 */
	private List<Response> getGlobalResponseMessage() {
		List<Response> responseList = new ArrayList<>();
		responseList.add(new ResponseBuilder().code("404").description("找不到资源")
				.build());
		return responseList;
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
		// .forPaths(PathSelectors.regex("/*.*"))
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(
				"global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(new SecurityReference("token",
				authorizationScopes));
	}

}