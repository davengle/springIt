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

import javax.persistence.EntityManagerFactory;

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


              //TODO: code is commentted out while I set up Database Loader in bootstrap.  Need to move this code over to that class.
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
