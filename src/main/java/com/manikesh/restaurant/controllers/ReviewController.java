package com.manikesh.restaurant.controllers;


import com.manikesh.restaurant.domain.ReviewCreateUpdateRequest;
import com.manikesh.restaurant.domain.dtos.ReviewCreateupdateRequestDto;
import com.manikesh.restaurant.domain.dtos.ReviewDto;
import com.manikesh.restaurant.domain.entities.Review;
import com.manikesh.restaurant.domain.entities.User;
import com.manikesh.restaurant.mappers.ReviewMapper;
import com.manikesh.restaurant.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable String restaurantId,
            @Valid @RequestBody ReviewCreateupdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt) {

        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(review);
        User user = jwtToUser(jwt);
        Review createdReview = reviewService.createReview(user, restaurantId, reviewCreateUpdateRequest);

        return ResponseEntity.ok(reviewMapper.toReviewDto(createdReview));
    }

    @GetMapping
    public Page<ReviewDto> listReviews(
            @PathVariable String restaurantId,
            @PageableDefault(
                    size=20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.DESC)Pageable pageable
            ){
        return reviewService.listReviews(restaurantId, pageable)
                .map(reviewMapper::toReviewDto);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId
            ){
        return reviewService.getReview(restaurantId, reviewId)
                .map(reviewMapper::toReviewDto)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.noContent().build());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId,
           @Valid @RequestBody ReviewCreateupdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt
    ){
             ReviewCreateUpdateRequest reviewCreateUpdateRequest= reviewMapper.toReviewCreateUpdateRequest(review);
            User user = jwtToUser(jwt);
          Review updatedReview =  reviewService.updateReview(
                  user, restaurantId, reviewId, reviewCreateUpdateRequest
          );

        return ResponseEntity.ok(reviewMapper.toReviewDto(updatedReview));

    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId
    ){
        reviewService.deleteReview(restaurantId, reviewId);
        return ResponseEntity.noContent().build();
    }


    private User jwtToUser(Jwt jwt) {
        return User.builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .build();



    }

}
