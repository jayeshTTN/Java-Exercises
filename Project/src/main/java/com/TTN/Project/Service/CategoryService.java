package com.TTN.Project.Service;

import com.TTN.Project.dtos.category.CategoryFieldValueResDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldDTO;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CategoryService {
    CategoryMetadataField addField(String name);
    Category addCategory(String name);
    Category addCategory(String name,long id);
    Category viewCategory(long id);

    Category updateCategory(long id ,String name);

    Map<String, List<Category>> viewCategoryById(long id);

    CategoryFieldValueResDTO addMetadataValue(long id , long mid , Set<String> value);

    CategoryFieldValueResDTO updateMetadataValue(long id , long mid , Set<String> value);
}
