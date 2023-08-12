package com.garage.garage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;

@SpringBootApplication
@RestController
@EntityScan("com/garage/garage/model")
public class GarageApplication {

	public static void main(String[] args) {

		SpringApplication.run(GarageApplication.class, args);
	}

}
