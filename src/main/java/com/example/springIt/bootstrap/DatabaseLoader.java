package com.example.springIt.bootstrap;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private LinkRepository linkRepository;
    private CommentRepository commentRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) {
        Map<String,String> links = new HashMap<>();
        links.put("Securing Spring Boot APIs and SPAs with OAuth 2.0","https://auth0.com/blog/securing-spring-boot-apis-and-spas-with-oauth2/?utm_source=reddit&utm_medium=sc&utm_campaign=springboot_spa_securing");
        links.put("Easy way to detect Device in Java Web Application using Spring Mobile - Source code to download from GitHub","https://www.opencodez.com/java/device-detection-using-spring-mobile.htm");
        links.put("Tutorial series about building microservices with SpringBoot (with Netflix OSS)","https://medium.com/@marcus.eisele/implementing-a-microservice-architecture-with-spring-boot-intro-cdb6ad16806c");
        links.put("Detailed steps to send encrypted email using Java / Spring Boot - Source code to download from GitHub","https://www.opencodez.com/java/send-encrypted-email-using-java.htm");
        links.put("Build a Secure Progressive Web App With Spring Boot and React","https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo");
        links.put("Building Your First Spring Boot Web Application - DZone Java","https://dzone.com/articles/building-your-first-spring-boot-web-application-ex");
        links.put("Building Microservices with Spring Boot Fat (Uber) Jar","https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/");
        links.put("Spring Cloud GCP 1.0 Released","https://cloud.google.com/blog/products/gcp/calling-java-developers-spring-cloud-gcp-1-0-is-now-generally-available");
        links.put("Simplest way to Upload and Download Files in Java with Spring Boot - Code to download from Github","https://www.opencodez.com/uncategorized/file-upload-and-download-in-java-spring-boot.htm");
        links.put("Add Social Login to Your Spring Boot 2.0 app","https://developer.okta.com/blog/2018/07/24/social-spring-boot");
        links.put("File download example using Spring REST Controller","https://www.jeejava.com/file-download-example-using-spring-rest-controller/");

        links.forEach((k,v) -> {
            linkRepository.save(new Link(k,v));
            // we will do something with comments later
        });

        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount);


        Link link = new Link("Getting started with Spring Boot 2", "https://therealdanvega.com/spring-boot-2");
        linkRepository.save(link);

        Comment comment = new Comment("This Spring Boot 2 link is awesome!", link);
        commentRepository.save(comment);
        link.addComment(comment);

        link.setTitle("Continuing with Spring Boot2");
        linkRepository.save(link);

        link.setTitle("Auditing with Spring Boot2");
        linkRepository.save(link);

        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        AuditReader auditReader = AuditReaderFactory.get(session);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Link.class, true, true);
        query.add(AuditEntity.id().eq(link.getId()));
        List<Link> audit = query.getResultList();
        System.out.println(audit);
    }
}
