package com.manikesh.restaurant.domain;

import com.manikesh.restaurant.domain.entities.Address;
import com.manikesh.restaurant.domain.entities.OperatingHour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {

    private String name;
    private String cuisineType;
    private String contactInformation;
    private Address address;
    private OperatingHour operatingHours;
    private List<String> photoIds;

}
