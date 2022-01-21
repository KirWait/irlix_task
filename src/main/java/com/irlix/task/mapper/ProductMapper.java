package com.irlix.task.mapper;

import com.irlix.task.dto.ProductRequestDto;
import com.irlix.task.dto.ProductResponseDto;
import com.irlix.task.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductEntity productRequestDtoToProductEntity(ProductRequestDto requestDto);
    ProductResponseDto productEntityToProductResponseDto(ProductEntity entity);
}
