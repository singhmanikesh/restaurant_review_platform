package com.manikesh.restaurant.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantSummaryDto {
    private String id;
    private String name;
    private String cuisineType;
    private Float averageRating;
    private AddressDto address;
    private Integer totalReviews;
    private List<PhotoDto> photos;
}
