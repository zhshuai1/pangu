package com.sepism.pangu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"postalCode", "detailedAddress"})
public class Address {
    private String province;
    private String city;
    private String district;
    private String postalCode;
    private String detailedAddress;
}
