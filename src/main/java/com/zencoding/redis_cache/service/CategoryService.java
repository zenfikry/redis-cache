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
        List<Category> parents = categoryRepository.findAllByParentIsNull();
        List<CategoryResponse> responses = parents.stream().map(category -> {
            return CategoryResponse.builder().id(category.getId()).name(category.getName()).build();
        }).toList();

        for (CategoryResponse response : responses) {
            List<Category> children = categoryRepository.findAllByParentId(response.getId());
            List<CategoryResponse> childrenResponses = children.stream().map(category -> {
                return CategoryResponse.builder().id(category.getId()).name(category.getName()).build();
            }).toList();
            response.setChildren(childrenResponses);
        }

        return responses;
    }
}
