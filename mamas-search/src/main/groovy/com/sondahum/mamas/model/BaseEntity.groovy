package com.sondahum.mamas.model

import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import java.time.LocalDate

@MappedSuperclass
abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    private LocalDate createdDate
}
