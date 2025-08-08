package com.manikesh.restaurant.service.impl;

import com.manikesh.restaurant.domain.GeoLocation;
import com.manikesh.restaurant.domain.RestaurantCreateUpdateRequest;
import com.manikesh.restaurant.domain.entities.Address;
import com.manikesh.restaurant.domain.entities.Photo;
import com.manikesh.restaurant.domain.entities.Restaurant;
import com.manikesh.restaurant.repositories.RestaurantRepository;
import com.manikesh.restaurant.service.GeoLocationService;
import com.manikesh.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public Restaurant createRestaurant(RestaurantCreateUpdateRequest request) {
        Address address = request.getAddress();
        GeoLocation geoLocation = geoLocationService.getLocate(address);
        GeoPoint geoPoint = new GeoPoint(geoLocation.getLongitude(), geoLocation.getLatitude());
        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream()
                .map(photoUrl-> Photo.builder()
                .url(photoUrl)
                .uploadDate(LocalDateTime.now())
                .build())
                .toList();

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInformation(request.getContactInformation())
                .address(address)
                .geoLocation(geoPoint)
                .operatingHours(request.getOperatingHours())
                .averageRating(0f)
                .photos(photos)
                .build();

        return restaurantRepository.save(restaurant);


    }
}
