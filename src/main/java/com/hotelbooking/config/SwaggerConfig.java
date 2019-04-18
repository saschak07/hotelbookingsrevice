package com.hotelbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*@Configuration
@EnableSwagger2*/
public class SwaggerConfig extends WebMvcConfigurationSupport {
	/*
	 * 
	 * 
	 * @Bean public Docket api() { return new
	 * Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()) .select()
	 * .apis(RequestHandlerSelectors.any()) .paths(PathSelectors.ant("/api/*"))
	 * .build(); }
	 * 
	 * private ApiInfo apiInfo() { return new
	 * ApiInfoBuilder().title("HotelPresentationService APIs")
	 * .description("HotelPresentationService API reference for developers")
	 * .termsOfServiceUrl("https://hotelservice1.herokuapp.com")
	 * .licenseUrl("saschak07@gmail.com").version("1.0").build(); }
	 * 
	 * @Override protected void addResourceHandlers(ResourceHandlerRegistry
	 * registry) { registry.addResourceHandler("swagger-ui.html")
	 * .addResourceLocations("classpath:/META-INF/resources/");
	 * 
	 * registry.addResourceHandler("/webjars/**")
	 * .addResourceLocations("classpath:/META-INF/resources/webjars/"); }
	 */}
