package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Category;
import com.rosendo.company.Repository.General.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> categoryList(){
        return categoryRepository.findAll();
    }

    public Category getIdCategory(Long id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new Exception("Category not found!");
        }
        return category.get();
    }

    public Category updateCategory(Category updatedCategory, Long id) throws Exception {
        Category originalCategory = this.getIdCategory(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedCategory, originalCategory);
        return categoryRepository.save(originalCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
