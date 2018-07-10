package com.bridgeit.fundooNote.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket documentaionapi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgeit.fundooNote"))
				.paths(PathSelectors.ant("/user/*"))
				.build().apiInfo(apiInfo());
	}
	private ApiInfo apiInfo()
	{
		return new ApiInfo(
				 "My REST API", 
			       "Some custom description of API.", 
			       "API TOS", 
			       "Terms of service", 
			       new Contact("Pushpa Navik", "swaggerexample", "pushpanavik17@gmail.com"), 
			       "License of API", "API license URL", Collections.emptyList());
				
	}
}
