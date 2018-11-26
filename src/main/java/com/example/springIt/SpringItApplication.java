package com.example.springIt;

import com.example.springIt.config.SpringitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableConfigurationProperties(SpringitProperties.class)
public class SpringItApplication {


	@Autowired
	private SpringitProperties springitProperties;

	private static final Logger log = LoggerFactory.getLogger(SpringItApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringItApplication.class, args);
		System.out.println("Welcome to Spring IT, take 2");
	}

	@Bean
	@Profile("dev")
	CommandLineRunner runner(){
		return args -> {
			System.out.println("Welcome message: " +  springitProperties.getWelcomeMsg());
			System.out.println("Test message: " +  springitProperties.getTestMsg());
			System.out.println("This is something that we would only do in dev");
			log.error("CommandLineRunner.run();");
			log.warn("CommandLineRunner.run();");
			log.info("CommandLineRunner.run();");
			log.debug("CommandLineRunner.run();");
			log.trace("CommandLineRunner.run();");
		};
	}
}
