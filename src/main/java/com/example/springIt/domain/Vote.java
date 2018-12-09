package com.example.springIt.domain;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Audited
public class Vote extends Auditable{

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private short direction;

    @NonNull
    @ManyToOne
    private Link link;


}
