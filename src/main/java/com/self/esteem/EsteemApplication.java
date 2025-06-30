package com.self.esteem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class EsteemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsteemApplication.class, args);
	}

}
