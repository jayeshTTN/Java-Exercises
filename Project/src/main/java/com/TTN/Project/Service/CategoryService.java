package com.TTN.Project.Service;

import com.TTN.Project.dtos.category.CategoryMetadataFieldDTO;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;

public interface CategoryService {
    CategoryMetadataField addField(String name);
    Category addCategory(String name);
    Category addCategory(String name,long id);
    Category viewCategory(long id);
}
