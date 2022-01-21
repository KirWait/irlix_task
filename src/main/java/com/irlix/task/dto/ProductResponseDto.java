package com.irlix.task.dto;

import com.irlix.task.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

public class ProductResponseDto {
    private Long quantity;
    private String name;
    private Map<String, Long> attributes;
    private List<CategoryEntity> categories;
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Long> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Long> attributes) {
        this.attributes = attributes;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }
}
