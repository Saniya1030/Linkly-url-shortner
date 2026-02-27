package com.shortner.springboot_url_shortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication

@ConfigurationPropertiesScan//this will scan all the packages and subpackages
public class SpringbootUrlShortnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootUrlShortnerApplication.class, args);
	}

}