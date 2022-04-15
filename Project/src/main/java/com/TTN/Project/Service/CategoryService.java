package com.TTN.Project.Service;

import com.TTN.Project.Exception.ResourceAlreadyExistException;
import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.ProCate.CategoryMetaDataFieldValueRepo;
import com.TTN.Project.Repository.ProCate.CategoryMetadataFieldRepo;
import com.TTN.Project.Repository.ProCate.CategoryRepo;
import com.TTN.Project.dtos.category.CategoryFieldValueResDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldDTO;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @Autowired
    CategoryMetaDataFieldValueRepo categoryMetaDataFieldValueRepo;

    public CategoryMetadataField addField(String name){
        if(categoryMetadataFieldRepo.findByName(name)==null){
            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
            categoryMetadataField.setName(name);
            return categoryMetadataFieldRepo.save(categoryMetadataField);
        }else {
            throw new ResourceAlreadyExistException("Field Already Exist With name :- "+name);
        }
    }


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
    public Map<String,List<Category>> viewCategoryById(long id) {
        if(categoryRepo.findById(id)==null)
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        Map<String,List<Category>> map = new HashMap<>();
        Category category = categoryRepo.findById(id);
        if(category.getParentId()==0){
            map.put("Parent Category", Arrays.asList(category));
        }
        List<Category> childCat = categoryRepo.findAllByParentId(id);
        if (category.getParentId()!=0){
            childCat.add(category);
        }
        map.put("Child Category",childCat);
        return map;
    }
    public Category viewCategory(long id){
        Category category = categoryRepo.findById(id);
        if(category==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        return category;
    }
    public Category updateCategory(long id, String name) {
        Category category = categoryRepo.findById(id);
        if(categoryRepo.findByName(name)!=null)
            throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);
        if(category!=null){
            category.setName(name);
            categoryRepo.save(category);
            return category;
        }else {
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
    }


    @Transactional
    public CategoryFieldValueResDTO addMetadataValue(long id, long mid, Set<String> value) {
        if(categoryRepo.findById(id)==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        else if (categoryMetadataFieldRepo.findById(mid)==null){
            throw new ResourceDoesNotExistException("Field does not exist with id " +mid);
        }
        if(categoryMetaDataFieldValueRepo.viewMetadataValues(id, mid)!=null){
            throw new ResourceAlreadyExistException("Field Value Already Exist With Category ID :- "+id+" and Field ID :-"+mid);
        }
        StringBuilder sb=new StringBuilder();
        for (String name : value) {
            sb.append(name);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        categoryMetaDataFieldValueRepo.addMetadataValues(id,mid,sb.toString());
        return new CategoryFieldValueResDTO(id,mid,sb.toString());
    }

    @Transactional
    public CategoryFieldValueResDTO updateMetadataValue(long id, long mid, Set<String> value) {
        if(categoryRepo.findById(id)==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        if (categoryMetadataFieldRepo.findById(mid)==null){
            throw new ResourceDoesNotExistException("Field does not exist with id " +mid);
        }
        if(categoryMetaDataFieldValueRepo.viewMetadataValues(id, mid)==null){
            throw new ResourceAlreadyExistException("Field Value does not Exist With Category ID :- "+id+" and Field ID :-"+mid);
        }
        List<Object[]> result = categoryMetaDataFieldValueRepo.viewMetadataValues2(id, mid);
        Object[] temp = result.get(0);
        String[] str = temp[2].toString().split(",");
        for(String st: str){
            value.add(st);
        }
        StringBuilder sb=new StringBuilder();
        for (String name : value) {
            sb.append(name);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        categoryMetaDataFieldValueRepo.updateMetadataValues(id,mid,sb.toString());
        return new CategoryFieldValueResDTO(id,mid,sb.toString());
    }
}
