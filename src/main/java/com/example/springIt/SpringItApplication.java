package com.example.springIt;

import com.example.springIt.config.SpringitProperties;
import com.example.springIt.repository.CommentRepository;
import com.example.springIt.repository.LinkRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(SpringitProperties.class)
@EnableJpaAuditing
public class SpringItApplication {


    @Autowired
    private SpringitProperties springitProperties;

    private static final Logger log = LoggerFactory.getLogger(SpringItApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringItApplication.class, args);
        System.out.println("How are ya?");
    }

    @Bean
    PrettyTime prettyTime(){
        return new PrettyTime();
    }

    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){
        return args -> {

            System.out.println(springitProperties.getYmlMsg());
            System.out.println(springitProperties.getEnvSpecificMsg());
            System.out.println(springitProperties.getPropertiesMsg());
            System.out.println(springitProperties.getDefaultMsg());

        };
    }


}
