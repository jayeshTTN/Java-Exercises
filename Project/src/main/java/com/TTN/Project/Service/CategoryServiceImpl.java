package com.TTN.Project.Service;


import com.TTN.Project.Exception.ResourceAlreadyExistException;
import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.ProCate.CategoryMetadataFieldRepo;
import com.TTN.Project.Repository.ProCate.CategoryRepo;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;
    public Category addCategory(String name){
        if(categoryRepo.findByName(name)==null){
            Category category = new Category();
            category.setName(name);
            categoryRepo.save(category);
            return category;
        }else {
            throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);
        }
    }
    public Category addCategory(String name, long id){
        if(categoryRepo.findById(id) == null||categoryRepo.findById(id).getParentId()!=0)
            throw new ResourceDoesNotExistException("Parent Category does not exist with id " +id);
        if(categoryRepo.findByName(name)==null && categoryRepo.findById(id).getParentId()==0){
            Category category = new Category();
            category.setName(name);
            category.setParentId(id);
            categoryRepo.save(category);
            return category;
        }else {
            throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);
        }
    }
    public Category viewCategory(long id){
        Category category = categoryRepo.findById(id);
        if(category==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        return category;
    }

    public CategoryMetadataField addField(String name){
        if(categoryMetadataFieldRepo.findByName(name)==null){
            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
            categoryMetadataField.setName(name);
            return categoryMetadataFieldRepo.save(categoryMetadataField);
        }else {
            throw new ResourceAlreadyExistException("Field Already Exist With name :- "+name);
        }
    }
}
