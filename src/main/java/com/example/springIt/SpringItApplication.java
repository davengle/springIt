package com.example.springIt;

import com.example.springIt.config.SpringitProperties;
import com.example.springIt.domain.Comment;
import com.example.springIt.domain.Link;
import com.example.springIt.repository.CommentRepository;
import com.example.springIt.repository.LinkRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(SpringitProperties.class)
public class SpringItApplication {


    @Autowired
    private SpringitProperties springitProperties;

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    private static final Logger log = LoggerFactory.getLogger(SpringItApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringItApplication.class, args);
        System.out.println("How are ya?");

    }


    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){
        return args -> {

            System.out.println(springitProperties.getYmlMsg());
            System.out.println(springitProperties.getEnvSpecificMsg());
            System.out.println(springitProperties.getPropertiesMsg());
            System.out.println(springitProperties.getDefaultMsg());


//            Link link = new Link("Getting started with Spring Boot 2", "https://therealdanvega.com/spring-boot-2");
//            linkRepository.save(link);
//
//            Comment comment = new Comment("This Spring Boot 2 link is awesome!", link);
//            commentRepository.save(comment);
//            link.addComment(comment);
//
//            link.setTitle("Continuing with Spring Boot2");
//            linkRepository.save(link);
//
//            link.setTitle("Auditing with Spring Boot2");
//            linkRepository.save(link);
//
//            Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//            AuditReader auditReader = AuditReaderFactory.get(session);
//            AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Link.class, true, true);
//            query.add(AuditEntity.id().eq(link.getId()));
//            List<Link> audit = query.getResultList();
//            System.out.println(audit);

        };
    }


}
