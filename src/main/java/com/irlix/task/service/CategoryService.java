package com.irlix.task.service;

import com.irlix.task.entity.CategoryEntity;
import javassist.NotFoundException;
import java.util.List;

public interface CategoryService {

    CategoryEntity findByName(String name) throws NotFoundException;
    List<CategoryEntity> getAll();
    CategoryEntity save(CategoryEntity category);

    void deleteByName(String name);
}
