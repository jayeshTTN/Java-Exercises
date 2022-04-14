package com.TTN.Project.Controller;


import com.TTN.Project.Exception.ResourceAlreadyExistException;
import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.ProCate.CategoryMetadataFieldRepo;
import com.TTN.Project.Repository.ProCate.CategoryRepo;
import com.TTN.Project.Service.CategoryService;
import com.TTN.Project.dtos.category.CategoryDTO;
import com.TTN.Project.dtos.category.CategoryFieldValueResDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldValueDTO;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import com.TTN.Project.entities.ProCate.CategoryMetadataFieldValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @GetMapping("/get/customers")
    public List<Customer> getCustomers() {

        Pageable paging = PageRequest.of(0, 10, Sort.by("id"));

        Page<Customer> pagedResult = customerRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Customer>();
        }
    }

    //Categories

    @PostMapping("/category/add")
    public Category addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        if(categoryDTO.getParentId()==0)
            return categoryService.addCategory(categoryDTO.getName());
        else{
            return categoryService.addCategory(categoryDTO.getName(),categoryDTO.getParentId());
        }
    }

    @PostMapping("/metadata/add")
    public CategoryMetadataField addField(@Valid @RequestBody CategoryMetadataFieldDTO categoryMetadataFieldDTO){
        return categoryService.addField(categoryMetadataFieldDTO.getName());
    }

    @GetMapping("/metadata/view")
    public List<CategoryMetadataField> getFieldList(){
       return categoryMetadataFieldRepo.findAll(Sort.by("id"));
    }


    @GetMapping("/category/view")
    public List<Category> getCategoryList() {
        return categoryRepo.findAll(Sort.by("id"));
    }

    @GetMapping("/category/{id}")
    public Map<String,List<Category>> getCategoryById(@PathVariable long id) {
        return categoryService.viewCategoryById(id);
    }


    @PutMapping("/category/update/{id}")
    public Category addCategory(@PathVariable long id,@Valid @RequestBody CategoryDTO categoryDTO){
        return categoryService.updateCategory(id,categoryDTO.getName());
    }

    @PostMapping("category/metadatavalue/add")
    public CategoryFieldValueResDTO addMetadataValue(@Valid @RequestBody CategoryMetadataFieldValueDTO metadataFieldValueDTO){
        return categoryService.addMetadataValue(metadataFieldValueDTO.getCategoryId(), metadataFieldValueDTO.getFieldId(),metadataFieldValueDTO.getValues());
    }

    @PutMapping("category/metadatavalue/update")
    public CategoryFieldValueResDTO updateMetadataValue(@Valid @RequestBody CategoryMetadataFieldValueDTO metadataFieldValueDTO){
        return categoryService.updateMetadataValue(metadataFieldValueDTO.getCategoryId(), metadataFieldValueDTO.getFieldId(),metadataFieldValueDTO.getValues());
    }


}
