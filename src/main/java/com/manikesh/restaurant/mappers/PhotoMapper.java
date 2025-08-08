package com.manikesh.restaurant.mappers;

import com.manikesh.restaurant.domain.dtos.PhotoDto;
import com.manikesh.restaurant.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    PhotoDto toDto(Photo photo);
}
