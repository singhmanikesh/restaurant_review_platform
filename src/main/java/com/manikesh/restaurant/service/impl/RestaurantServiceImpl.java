package com.manikesh.restaurant.service.impl;

import com.manikesh.restaurant.domain.GeoLocation;
import com.manikesh.restaurant.domain.RestaurantCreateUpdateRequest;
import com.manikesh.restaurant.domain.entities.Address;
import com.manikesh.restaurant.domain.entities.Photo;
import com.manikesh.restaurant.domain.entities.Restaurant;
import com.manikesh.restaurant.exceptions.RestaurantNotFoundException;
import com.manikesh.restaurant.repositories.RestaurantRepository;
import com.manikesh.restaurant.service.GeoLocationService;
import com.manikesh.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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

    @Override
    public Page<Restaurant> getRestaurants(
            String query, Float minRating, Float latitude,
            Float longitude, Float radius, Pageable pageable) {

        if(null!=minRating&&(null==query||query.isEmpty())) {
            return restaurantRepository.findByAverageRatingGreaterThanEqual(minRating, pageable);
        }
        Float searchMinRating = null == minRating ? 0f : minRating;

        if(null!=query&&!query.trim().isEmpty()) {
            return restaurantRepository.findByQueryAndMinRating(query, searchMinRating, pageable);
        }
        if(null!=latitude && null!=longitude && null!=radius) {
            return restaurantRepository.findByLocationNear(latitude,longitude, radius, pageable);
        }

        return restaurantRepository.findAll(pageable);

    }

    @Override
    public Optional<Restaurant> getRestaurant(String id) {
       return restaurantRepository.findById(id);
    }

    @Override
    public Restaurant updateRestaurant(String id, RestaurantCreateUpdateRequest request) {
        Restaurant restaurant = getRestaurant(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
        GeoLocation newGeoLocation = geoLocationService.getLocate(request.getAddress());
        GeoPoint geoPoint = new GeoPoint(newGeoLocation.getLatitude(), newGeoLocation.getLongitude());
        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream()
                .map(photoUrl-> Photo.builder()
                        .url(photoUrl)
                        .uploadDate(LocalDateTime.now())
                        .build())
                .toList();

        restaurant.setName(request.getName());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setContactInformation(request.getContactInformation());
        restaurant.setAddress(request.getAddress());
        restaurant.setGeoLocation(geoPoint);
        restaurant.setOperatingHours(request.getOperatingHours());
        restaurant.setPhotos(photos);

        return restaurantRepository.save(restaurant);


    }

}
