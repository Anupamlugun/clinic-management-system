package com.clinic.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan(
		basePackages = "com.clinic.cms.config.properties"
)
public class ClinicManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicManagementSystemApplication.class, args);
	}

}
