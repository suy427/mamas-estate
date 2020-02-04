package com.sondahum.mamas.model

import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
class NamedEntity extends BaseEntity{

    @Column(name = "name")
    private String name;
}
