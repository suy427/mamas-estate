package com.sondahum.mamas.model

import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class NamedEntity extends BaseEntity{

    @Column(name = "name")
    protected String name;
}
