package com.example.springIt.domain;

import com.example.springIt.service.BeanUtil;
import lombok.*;
import org.hibernate.envers.Audited;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Audited
public class Comment extends Auditable{

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @NotEmpty(message = "Please enter a comment")
    private String body;

    @ManyToOne
    @NonNull
    private Link link;

    public String getPrettyTime() {
        PrettyTime pt = BeanUtil.getBean(PrettyTime.class);
        return pt.format(convertToDateViaInstant(getCreationDate()));
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
