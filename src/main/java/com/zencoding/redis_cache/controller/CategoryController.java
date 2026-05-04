package com.zencoding.redis_cache.controller;

import com.zencoding.redis_cache.model.CategoryResponse;
import com.zencoding.redis_cache.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "api/categories", produces = "application/json")
    public List<CategoryResponse> findALl() {
        return categoryService.findAll();
    }
}
