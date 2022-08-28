package com.blogging.servicesimpl;

import com.blogging.commons.CommonDao;
import com.blogging.entities.Category;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.CategoryDto;
import com.blogging.repositories.CategoryRepo;
import com.blogging.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public String saveOrUpdateCategory(CategoryDto categoryDto) {

        String result = "error";

         Category updatedCat = this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class));

         if (updatedCat != null)
             result = "success";

         return result;
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId){

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category>  categories =  this.categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

        return catDtos;
    }
}
