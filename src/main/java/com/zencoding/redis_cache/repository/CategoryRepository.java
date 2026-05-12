package com.zencoding.redis_cache.repository;

import com.zencoding.redis_cache.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findAllByParentIsNull();

    List<Category> findAllByParentId(String parentId);

    @Query("from Category p join fetch p.children c where p.parent is null")
    List<Category> findAllCategories();

}
