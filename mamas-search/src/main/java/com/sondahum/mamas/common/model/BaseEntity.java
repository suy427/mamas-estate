package com.sondahum.mamas.common.model;

import com.sondahum.mamas.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;


    protected boolean isDeleted;
}
