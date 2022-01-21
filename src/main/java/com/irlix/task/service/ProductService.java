package com.irlix.task.service;

import com.irlix.task.dto.ProductRequestDto;
import com.irlix.task.entity.ProductEntity;
import com.irlix.task.exception.InvalidInputException;
import javassist.NotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;


public interface ProductService {

    void deleteByName(String name);
    List<ProductEntity> getAll();
    List<ProductEntity> getAllBySpec(Specification<ProductEntity> spec);
    ProductEntity findByName(String name) throws NotFoundException;
    ProductEntity save(ProductEntity product) throws InvalidInputException;
    void setUpProductRequestDto(ProductRequestDto requestDto) throws NotFoundException, InvalidInputException;

    List<ProductEntity> findAllByFilter(HashMap<String, String> filterMap);

    List<ProductEntity> getAllByCategoryName(String categoryName);
}
