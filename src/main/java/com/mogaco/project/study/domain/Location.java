package com.mogaco.project.study.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class Location {
    private String city;
    private String detail;

    public Location(String city, String detail) {
        this.city = city;
        this.detail = detail;
    }
}
