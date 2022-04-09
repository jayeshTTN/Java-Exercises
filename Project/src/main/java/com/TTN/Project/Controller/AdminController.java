package com.TTN.Project.Controller;


import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CustomerRepo customerRepo;


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
}
