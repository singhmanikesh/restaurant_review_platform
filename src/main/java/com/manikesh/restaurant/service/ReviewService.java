package com.manikesh.restaurant.service;

import com.manikesh.restaurant.domain.ReviewCreateUpdateRequest;
import com.manikesh.restaurant.domain.entities.Review;
import com.manikesh.restaurant.domain.entities.User;

public interface ReviewService {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);

}
