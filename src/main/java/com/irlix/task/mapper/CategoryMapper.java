package com.irlix.task.mapper;

import com.irlix.task.dto.CategoryRequestDto;
import com.irlix.task.dto.CategoryResponseDto;
import com.irlix.task.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryResponseDto categoryEntityToCategoryResponseDto(CategoryEntity entity);
    CategoryEntity categoryRequestDtoToCategoryEntity(CategoryRequestDto requestDto);
}
