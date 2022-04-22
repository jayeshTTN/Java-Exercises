package com.TTN.Project.Controller;


import com.TTN.Project.Service.CategoryService;
import com.TTN.Project.Service.CustomerService;
import com.TTN.Project.Service.ProductService;
import com.TTN.Project.dtos.PasswordDTO;
import com.TTN.Project.dtos.address.AddressDTO;
import com.TTN.Project.dtos.address.AddressResDTO;
import com.TTN.Project.dtos.customer.CustomerDTO;
import com.TTN.Project.dtos.customer.CustomerResDTO;
import com.TTN.Project.dtos.product.ProductResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody CustomerDTO customerDTO){
        return customerService.register(customerDTO);
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressDTO addressDTO){
        return customerService.addAddress(addressDTO);
    }

    @GetMapping("/address/view")
    public ResponseEntity<AddressResDTO> viewAddress(){
        return customerService.viewAddress();
    }

    @PatchMapping("/address/update/{id}")
    public AddressResDTO updateAddress( @PathVariable long id ,@RequestBody Map<Object, Object> map) {
        return customerService.updateAddress(id, map);
    }

    @DeleteMapping("/address/delete/{id}")
    public String addressDelete(@PathVariable long id ){
        return customerService.addressDelete(id);
    }

    @GetMapping("/profile/view")
    public CustomerResDTO viewProfile() {
        return customerService.viewProfile();
    }

    @PatchMapping("/profile/update")
    public CustomerResDTO editProfile( @RequestBody Map<Object, Object> map) {
        return customerService.editProfile(map);
    }

    @PatchMapping("/password/update")
    public String passwordUpdate(@Valid @RequestBody PasswordDTO password){
        return customerService.passwordUpdate(password);
    }

    //Categories
    @GetMapping("/category/view")
    public LinkedHashMap<String,Object> getCategoryList(){
        return categoryService.getCategoryList();
    }


    //product
    @GetMapping("/product/view/{id}")
    public ProductResDTO viewProductById(@PathVariable Long id){
        return productService.viewProductById(id);
    }

    @GetMapping(value = "/product/view/all")
    public List<ProductResDTO> viewAllProducts(){
        return productService.viewAllProducts();
    }
}
