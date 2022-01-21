package com.irlix.task.service.impl;

import com.irlix.task.entity.CategoryEntity;
import com.irlix.task.repository.CategoryRepository;
import com.irlix.task.service.CategoryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity findByName(String name) throws NotFoundException {
        return categoryRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException(String.format("No such category with the name: %s", name)));
    }

    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteByName(String name) {
        categoryRepository.deleteByName(name);
        logger.info(String.format(
                "The category with the name: %s has been deleted successfully!", name));
    }
}
