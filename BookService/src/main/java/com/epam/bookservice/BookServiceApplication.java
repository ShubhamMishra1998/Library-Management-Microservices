package com.epam.bookservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableEurekaClient
public class BookServiceApplication {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}



	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}
