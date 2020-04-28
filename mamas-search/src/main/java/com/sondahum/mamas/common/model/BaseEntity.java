package com.sondahum.mamas.common.model;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;



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

    @Column(name = "is_deleted")
    protected boolean validity = true;
}
