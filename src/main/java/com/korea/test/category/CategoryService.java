package com.korea.test.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> oc = categoryRepository.findById(id);
        if (oc.isPresent()) {
            return oc.get();
        }
        throw new IllegalArgumentException("없는 카테고리번호입니다.");
    }

    public Category saveDefaultCategory() {
        Category category = new Category();
        category.setTitle("새로운 카테고리");
        category.setCreateDate(LocalDateTime.now());

        return categoryRepository.save(category);
    }

}
