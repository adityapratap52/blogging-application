package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.CategoryDto;
import com.blogging.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto createCat = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createCat, HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto updateCat = this.categoryService.updateCategory(categoryDto);

        return new ResponseEntity<>(updateCat, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategory/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {

        this.categoryService.deleteCategory(catId);

        return new ResponseEntity<>(new ApiResponse("Category is deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/category/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {

        CategoryDto categoryDto = this.categoryService.getCategory(catId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/allCategory")
    public ResponseEntity<List<CategoryDto>> getCategories() {

        List<CategoryDto> categories = this.categoryService.getAllCategory();

        return ResponseEntity.ok(categories);
    }
}
