package com.TTN.Project.Controller;


import com.TTN.Project.Exception.ResourceAlreadyExistException;
import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.ProCate.CategoryMetadataFieldRepo;
import com.TTN.Project.Repository.ProCate.CategoryRepo;
import com.TTN.Project.Service.CategoryService;
import com.TTN.Project.dtos.category.CategoryDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldDTO;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @PostMapping("/category/metadata")
    public CategoryMetadataField addField(@Valid @RequestBody CategoryMetadataFieldDTO categoryMetadataFieldDTO){
        return categoryService.addField(categoryMetadataFieldDTO.getName());
    }

    @GetMapping("/category/metadata/view")
    public List<CategoryMetadataField> getFieldList(){
       return categoryMetadataFieldRepo.findAll(Sort.by("id"));
    }



}
