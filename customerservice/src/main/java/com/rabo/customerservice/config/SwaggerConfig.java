package com.rabo.customerservice.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.rabo"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("CustomerService", "Manages the customer information.", "1.0", "Terms of service",
				new springfox.documentation.service.Contact("Goparaju Akula", "Infosys",
						"customerserviice@infosys.com"),
				"License of API", "API license URL", Collections.emptyList());
	}

}
