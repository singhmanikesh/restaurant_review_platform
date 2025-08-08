package com.manikesh.restaurant.service;

import com.manikesh.restaurant.domain.GeoLocation;
import com.manikesh.restaurant.domain.entities.Address;

public interface GeoLocationService {

    GeoLocation getLocate(Address address);

}
