package com.mogaco.project.meet.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class Location {
    private String location;
    private String detail;

    public Location(String location, String detail) {
        this.location = location;
        this.detail = detail;
    }
}
