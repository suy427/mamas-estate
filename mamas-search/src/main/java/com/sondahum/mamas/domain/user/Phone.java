package com.sondahum.mamas.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Builder
@Embeddable
@JsonIgnoreProperties({"first", "middle", "last"})
public class Phone {

    @Column(name = "phone_number")
    private String wholeNumber;
    private String first;
    private String middle;
    private String last;

    public Phone() {
        wholeNumber = first + middle + last;
    }
}
