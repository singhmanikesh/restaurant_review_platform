package com.manikesh.restaurant.domain.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateupdateRequestDto {
    @NotBlank(message = "Content cannot be blank")
    private String content;
    @NotNull(message = "Rating cannot be null")
    @Min(1)
    @Max(5)
    private Integer rating;
    private List<String> photoIds = new ArrayList<>();  // Changed from List<String> to String[] for simplicity in DTO
}
