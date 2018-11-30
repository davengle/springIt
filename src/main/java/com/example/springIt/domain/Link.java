package com.example.springIt.domain;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Audited
public class Link {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String url;

    @OneToMany(mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("List entries ");
        sb.append("Id: ");
        sb.append(this.getId());
        sb.append("Title: ");
        sb.append(this.getTitle());
        sb.append("URL: ");
        sb.append(this.getUrl());
        return sb.toString();
    }
}
