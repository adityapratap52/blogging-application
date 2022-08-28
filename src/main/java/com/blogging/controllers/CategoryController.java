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

    @RequestMapping(value = "/saveOrUpdateCategory", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<String> saveOrUpdateCategory(@Valid @RequestBody CategoryDto categoryDto) {

        String createCat = this.categoryService.saveOrUpdateCategory(categoryDto);

        return new ResponseEntity<>(createCat, HttpStatus.CREATED);
    }

    @GetMapping("/deleteCategory/{catId}")
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
