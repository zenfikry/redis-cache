package com.zencoding.redis_cache.service;

import com.zencoding.redis_cache.entity.Category;
import com.zencoding.redis_cache.model.CategoryResponse;
import com.zencoding.redis_cache.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        List<Category> parents = categoryRepository.findAllCategories();

        return parents.stream().map(category -> {

            List<Category> children = category.getChildren();
            List<CategoryResponse> childrenResponses = children.stream().map(child -> {
                return CategoryResponse.builder().id(child.getId()).name(child.getName()).build();
            }).toList();

            return CategoryResponse.builder().id(category.getId()).name(category.getName()).children(childrenResponses).build();
        }).toList();
    }
}
