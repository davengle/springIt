package com.example.springIt;

import com.example.springIt.config.SpringitProperties;
import com.example.springIt.domain.Comment;
import com.example.springIt.domain.Link;
import com.example.springIt.repository.CommentRepository;
import com.example.springIt.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.TimeZone;

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

    //@Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){
        return args -> {
            Link link = new Link("Getting started with Spring Boot 2", "https://therealdanvega.com/spring-boot-2");
            linkRepository.save(link);

            Comment comment = new Comment("This Spring Boot 2 link is awesome!", link);
            commentRepository.save(comment);
            link.addComment(comment);

            link.setTitle("Continuing with Spring Boot2");
            linkRepository.save(link);

        };

    }
}
