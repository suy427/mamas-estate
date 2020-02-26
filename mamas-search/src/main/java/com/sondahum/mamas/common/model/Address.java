package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address1;
    private String address2;
    private String address3;

    @Override
    public String toString() {
        return address1 + " " + address2 + " " + address3;
    }

    @Override
    public int hashCode() {
        int prime = 137;
        int hashCode = prime * address1.hashCode() + 1; // 0 방지
        hashCode = prime * hashCode + address2.hashCode();
        hashCode = prime * hashCode + address3.hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(Object address) {
        if (!(address instanceof Address)) {
            return false;
        }
        Address other = (Address) address;

        return (this.address1.equals(other.address1)
                && this.address2.equals(other.address2)
                && this.address3.equals(other.address3)
        );
    }
}
