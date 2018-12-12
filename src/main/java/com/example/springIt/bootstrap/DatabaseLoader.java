package com.example.springIt.bootstrap;

import com.example.springIt.domain.Comment;
import com.example.springIt.domain.Link;
import com.example.springIt.domain.Role;
import com.example.springIt.domain.User;
import com.example.springIt.repository.CommentRepository;
import com.example.springIt.repository.LinkRepository;
import com.example.springIt.repository.RoleRepository;
import com.example.springIt.repository.UserRepository;
import com.example.springIt.service.MailService;
import com.example.springIt.service.RoleService;
import com.example.springIt.service.UserService;
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
    private RoleService roleService;
    private MailService mailService;

    private Map<String, User> users = new HashMap<>();

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
    }



    @Override
    public void run(String... args) {

        ArrayList<Link> links = new ArrayList<>();

        createUsersAndRole();
        createLinks(links);
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

    private void addComments(ArrayList<Link> links) {
        for (Link link : links ){
            Comment spring = new Comment("Thank you buddy", link);
            Comment security = new Comment("I love Spring Security", link);
            Comment pwa = new Comment("What are PWAs?", link);
            Comment comments[] = {spring, security, pwa};
            for (Comment comment : comments) {
                commentRepository.save(comment);
                //link.addComment(comment);
            }
        };
    }

    private void createLinks(ArrayList<Link> links) {
        links.add(new Link("Securing Spring Boot APIs and SPAs with OAuth 2.0", "https://auth0.com/blog/securing-spring-boot-apis-and-spas-with-oauth2/?utm_source=reddit&utm_medium=sc&utm_campaign=springboot_spa_securing"));
        links.add(new Link("Easy way to detect Device in Java Web Application using Spring Mobile - Source code to download from GitHub", "https://www.opencodez.com/java/device-detection-using-spring-mobile.htm"));
        links.add(new Link("Tutorial series about building microservices with SpringBoot (with Netflix OSS)", "https://medium.com/@marcus.eisele/implementing-a-microservice-architecture-with-spring-boot-intro-cdb6ad16806c"));
        links.add(new Link("Detailed steps to send encrypted email using Java / Spring Boot - Source code to download from GitHub", "https://www.opencodez.com/java/send-encrypted-email-using-java.htm"));
        links.add(new Link("Build a Secure Progressive Web App With Spring Boot and React", "https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo"));
        links.add(new Link("Building Your First Spring Boot Web Application - DZone Java", "https://dzone.com/articles/building-your-first-spring-boot-web-application-ex"));
        links.add(new Link("Building Microservices with Spring Boot Fat (Uber) Jar", "https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/"));
        links.add(new Link("Spring Cloud GCP 1.0 Released", "https://cloud.google.com/blog/products/gcp/calling-java-developers-spring-cloud-gcp-1-0-is-now-generally-available"));
        links.add(new Link("Simplest way to Upload and Download Files in Java with Spring Boot - Code to download from Github", "https://www.opencodez.com/uncategorized/file-upload-and-download-in-java-spring-boot.htm"));
        links.add(new Link("Add Social Login to Your Spring Boot 2.0 app", "https://developer.okta.com/blog/2018/07/24/social-spring-boot"));
        links.add(new Link("File download example using Spring REST Controller", "https://www.jeejava.com/file-download-example-using-spring-rest-controller/"));

        for(Link link : links) {
            User u1 = users.get("user@gmail.com");
            User u2 = users.get("super@gmail.com");
            if (link.getTitle().startsWith("Build")) {
                link.setUser(u1);
            } else {
                link.setUser(u2);
            }
            linkRepository.save(link);
        }
    }

    private void createUsersAndRole() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleService.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleService.save(adminRole);

        User user = new User("user@gmail.com",secret,true,"Joe","User","joedirt");
        user.setConfirmPassword(secret);
        user.addRole(userRole);
        users.put("user@gmail.com",user);

        User admin = new User("admin@gmail.com",secret,true,"Joe","Admin","masteradmin");
        admin.setConfirmPassword(secret);
        admin.setAlias("joeadmin");
        admin.addRole(adminRole);
        users.put("admin@gmail.com",admin);

        User master = new User("super@gmail.com",secret,true,"Super","User","superduper");
        master.setConfirmPassword(secret);
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
        users.put("super@gmail.com",master);

        UserService userService = new UserService(userRepository, roleService, mailService);
        userService.saveUsers(user, admin, master);

    }
}
