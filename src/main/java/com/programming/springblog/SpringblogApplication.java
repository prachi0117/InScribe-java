package com.programming.springblog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringblogApplication.class, args);
		System.out.println("hello");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}

