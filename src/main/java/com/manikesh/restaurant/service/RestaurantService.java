package com.manikesh.restaurant.service;

import com.manikesh.restaurant.domain.RestaurantCreateUpdateRequest;
import com.manikesh.restaurant.domain.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest restaurant);
    Page<Restaurant> getRestaurants(
            String query,
            Float minRating,
            Float latitude,
            Float longitude,
            Float radius,
            Pageable pageable);


    Optional<Restaurant> getRestaurant(String id);


    Restaurant updateRestaurant(String id, RestaurantCreateUpdateRequest requestCreateUpdateRequest);

}
