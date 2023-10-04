package com.lmg.digitization.digital.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.lmg.digitization"} )
@EntityScan("com.lmg.digitization")
@EnableJpaRepositories("com.lmg.digitization")
@SpringBootApplication
public class DigitalWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalWalletApplication.class, args);
	}
	
}