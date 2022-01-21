package com.irlix.task.service.impl;

import com.irlix.task.dto.ProductRequestDto;
import com.irlix.task.entity.CategoryEntity;
import com.irlix.task.entity.ProductEntity;
import com.irlix.task.exception.InvalidInputException;
import com.irlix.task.repository.ProductRepository;
import com.irlix.task.service.CategoryService;
import com.irlix.task.service.ProductService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    @Override
    public void deleteByName(String name) {
        productRepository.deleteByName(name);
        logger.info(String.format("The product with the name: %s has been deleted successfully!", name));
    }

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductEntity> getAllBySpec(Specification<ProductEntity> spec) {
        return productRepository.findAll(spec);
    }

    @Override
    public ProductEntity findByName(String name) throws NotFoundException {
        return productRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException(String.format("No such product with the name: %s", name)));
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public void setUpProductRequestDto(ProductRequestDto requestDto) throws NotFoundException, InvalidInputException {
        List<String> rawCategories = requestDto.getRawCategories();
        if (rawCategories == null) throw new InvalidInputException("The product must belong to at least one category!");
        List<CategoryEntity> categories = new ArrayList<>();
        for (String rawCategory : rawCategories) {
            CategoryEntity category = categoryService.findByName(rawCategory);
            categories.add(category);
        }
        requestDto.setCategories(categories);
    }

    @Override
    public List<ProductEntity> findAllByFilter(HashMap<String, String> filterMap) {
        List<ProductEntity> products = productRepository.findAll();
        List<ProductEntity> result = new ArrayList<>();

        for (ProductEntity product : products) {
            for (var entry : filterMap.entrySet()) {
                Map<String, String> attributes = product.getAttributes();
                if (attributes.containsKey(entry.getKey())){
                    if (entry.getValue() == null || entry.getValue().equals("")) {
                        result.add(product);
                    }
                    else {
                        if (attributes.containsValue(entry.getValue())) result.add(product);
                    }

                }
            }
        }
        return result;
    }

    @Override
    public List<ProductEntity> getAllByCategoryName(String categoryName) {
        return productRepository.findAll().stream().filter(product -> product.getCategories().stream()
                .anyMatch(category -> Objects.equals(category.getName(), categoryName))).collect(Collectors.toList());
    }
}
