package com.sepism.pangu.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"postalCode", "detailedAddress"})
public class Address {
    private String province;
    private String city;
    private String district;
    private String postalCode;
    private String detailedAddress;
}
