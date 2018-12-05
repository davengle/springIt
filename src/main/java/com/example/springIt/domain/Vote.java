package com.example.springIt.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@Audited
public class Vote extends Auditable{

    @Id
    @GeneratedValue
    private Long id;
    private int vote;

}
