package com.jsp.ums.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumention {

	
	Contact contact() {
		return new Contact().email("shanmukhashani2009@gmail.com")
				.url("xyz.abc.in")
				.email("shanmukha")
				.name("Shanmukha");
	}
	
	Info info() {
		return new Info().title("User Management System API")
				.version("1.0v")
				.description("User Mangement System is a RESTful API built using"
						+"Spring Boot and MySQL database")
				.contact(contact());
	}
	
	@Bean
	OpenAPI openApI() {
		return new OpenAPI().info(info());
	}
	
}
