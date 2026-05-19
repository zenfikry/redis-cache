package com.zencoding.redis_cache.service;

import com.zencoding.redis_cache.entity.Category;
import com.zencoding.redis_cache.model.CategoryResponse;
import com.zencoding.redis_cache.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper;

    public List<CategoryResponse> findAll() {

        String json = stringRedisTemplate.opsForValue().get("categories");

        if (json != null) {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        }

        List<Category> parents = categoryRepository.findAllCategories();

        List<CategoryResponse> responses = parents.stream().map(category -> {

            List<Category> children = category.getChildren();
            List<CategoryResponse> childrenResponses = children.stream().map(child -> {
                return CategoryResponse.builder().id(child.getId()).name(child.getName()).build();
            }).toList();

            return CategoryResponse.builder().id(category.getId()).name(category.getName()).children(childrenResponses).build();
        }).toList();

        json = objectMapper.writeValueAsString(responses);
        stringRedisTemplate.opsForValue().set("categories", json, Duration.ofDays(1) );

        return responses;
    }
}
