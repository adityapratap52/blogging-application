package com.blogging.services;

import com.blogging.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    String saveOrUpdateCategory(CategoryDto categoryDto);


    void deleteCategory(Integer categoryId);

    CategoryDto getCategory(Integer categoryId);

    List<CategoryDto> getAllCategory();
}
