package com.manikesh.restaurant.controllers;

import com.manikesh.restaurant.domain.RestaurantCreateUpdateRequest;
import com.manikesh.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.manikesh.restaurant.domain.dtos.RestaurantSummaryDto;
import com.manikesh.restaurant.domain.dtos.RestuarantDto;
import com.manikesh.restaurant.domain.entities.Restaurant;
import com.manikesh.restaurant.mappers.RestaurantMapper;
import com.manikesh.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/restaurants")
@RequiredArgsConstructor
public class RestuarantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestuarantDto> createRestaurant(@Valid @RequestBody RestaurantCreateUpdateRequestDto request) {
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantMapper.toRestaurantCreateUpdateRequest(request);

        Restaurant restaurant = restaurantService.createRestaurant(restaurantCreateUpdateRequest);
        RestuarantDto restaurantDto = restaurantMapper.toRestaurantDto(restaurant);
        return ResponseEntity.ok(restaurantDto);

    }

    @GetMapping
    public Page<RestaurantSummaryDto> searchRestaurants(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<Restaurant> searchResults = restaurantService.getRestaurants(
                q, minRating, latitude, longitude, radius, PageRequest.of(page - 1, size)
        );

        return searchResults.map(restaurantMapper::toSummaryDto);

    }

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<RestuarantDto> getRestaurant(@PathVariable("restaurant_id") String restaurantId) {
        return restaurantService.getRestaurant(restaurantId)
                .map(restaurant -> ResponseEntity.ok(restaurantMapper.toRestaurantDto(restaurant)))
                .orElse(ResponseEntity.notFound().build());

    }


    @PutMapping("/{restaurant_id}")
    public ResponseEntity<RestuarantDto> updateRestaurant(
            @PathVariable("restaurant_id") String restaurantId,
            @Valid @RequestBody RestaurantCreateUpdateRequestDto request) {
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantMapper.toRestaurantCreateUpdateRequest(request);
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantCreateUpdateRequest);
        return ResponseEntity.ok(restaurantMapper.toRestaurantDto(updatedRestaurant));
    }

}
