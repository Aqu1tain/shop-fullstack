package com.shop.backend.service;

import com.shop.backend.entity.Category;
import com.shop.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category create(String name) {
        return categoryRepository.save(Category.builder().name(name).build());
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
