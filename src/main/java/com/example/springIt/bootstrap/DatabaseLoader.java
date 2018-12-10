package com.example.springIt.bootstrap;

import com.example.springIt.domain.Comment;
import com.example.springIt.domain.Link;
import com.example.springIt.domain.Role;
import com.example.springIt.domain.User;
import com.example.springIt.repository.CommentRepository;
import com.example.springIt.repository.LinkRepository;
import com.example.springIt.repository.RoleRepository;
import com.example.springIt.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.*;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private LinkRepository linkRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        Map<String,String> links = new HashMap<>();

        addUsersAndRoles();
        addLinks(links);
        addComments(links);

        long linkCount = linkRepository.count();
        System.out.printf("Number of links in the database: %s \n", linkCount);

//        displayAuditLog();
    }

    private void displayAuditLog() {
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

    private void addComments(Map<String, String> links) {
        links.forEach((k,v) -> {
            Link link = new Link(k, v);
            linkRepository.save(link);

            Comment spring = new Comment("Thank you buddy", link);
            Comment security = new Comment("I love Spring Security", link);
            Comment pwa = new Comment("What are PWAs?", link);
            Comment comments[] = {spring, security, pwa};
            for (Comment comment : comments) {
                commentRepository.save(comment);
                link.addComment(comment);
            }
        });
    }

    private void addLinks(Map<String, String> links) {
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
    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com",secret,true);
        user.addRole(userRole);
        userRepository.save(user);

        User admin = new User("admin@gmail.com",secret,true);
        admin.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        userRepository.save(admin);

        User master = new User("master@gmail.com",secret,true);
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
        userRepository.save(master);

    }
}
