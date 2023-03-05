package com.waoi.waoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class WaoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaoiApplication.class, args);
	}

}
