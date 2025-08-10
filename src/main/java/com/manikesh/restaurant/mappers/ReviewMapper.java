package com.manikesh.restaurant.mappers;

import com.manikesh.restaurant.domain.ReviewCreateUpdateRequest;
import com.manikesh.restaurant.domain.dtos.ReviewCreateupdateRequestDto;
import com.manikesh.restaurant.domain.dtos.ReviewDto;
import com.manikesh.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateupdateRequestDto dto);

    ReviewDto toReviewDto(Review review);

}
