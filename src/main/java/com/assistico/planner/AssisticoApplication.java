package com.assistico.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AssisticoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssisticoApplication.class, args);
	}

}
