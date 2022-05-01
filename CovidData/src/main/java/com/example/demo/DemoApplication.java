package com.example.demo;

import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public APIrequest apireq(){
		return new APIrequest();
	}
}
