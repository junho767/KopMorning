package com.junho.Kopmorning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class KopmorningApplication {

	public static void main(String[] args) {
		SpringApplication.run(KopmorningApplication.class, args);
	}

}
