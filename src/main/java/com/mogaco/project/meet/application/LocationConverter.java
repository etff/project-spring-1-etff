package com.mogaco.project.meet.application;

import com.mogaco.project.meet.domain.Location;
import com.mogaco.project.meet.domain.MeetSupplier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 지역 변환기.
 */
@Component
public class LocationConverter {
    public Location getLocation(MeetSupplier meetSupplier) {
        String location = meetSupplier.getLocation();

        if (Objects.nonNull(location) && location.contains("/")) {
            String[] split = location.split("/");

            String city = split[0];
            String detail = split[1];
            return new Location(city, detail);
        }
        return new Location();
    }
}
