package com.sondahum.mamas.common.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;



@MappedSuperclass
@Getter
@Setter
@Where(clause = "is_active = true")
public abstract class BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;

    @Column(name = "is_active")
    protected boolean active = true;
}
