package com.TTN.Project.Controller;



import com.TTN.Project.Repository.AddressRepo;
import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Service.CategoryService;
import com.TTN.Project.Service.ProductService;
import com.TTN.Project.Service.SellerService;
import com.TTN.Project.dtos.PasswordDTO;
import com.TTN.Project.dtos.address.AddressResDTO;
import com.TTN.Project.dtos.product.ProductDTO;
import com.TTN.Project.dtos.product.ProductVariationDTO;
import com.TTN.Project.dtos.seller.SellerDTO;
import com.TTN.Project.dtos.seller.SellerResDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    private SellerService sellerService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody SellerDTO sellerDTO) {
        return sellerService.register(sellerDTO);
    }

    @GetMapping("/profile/view")
    public Map<String,Object>  viewProfile() {
        return sellerService.viewProfile();
    }

    @PatchMapping("/profile/update")
    public SellerResDTO editProfile( @RequestBody Map<Object, Object> map) {
        return sellerService.editProfile(map);
    }

    @PatchMapping("/password/update")
    public String passwordUpdate(@Valid @RequestBody PasswordDTO password){
       return sellerService.passwordUpdate(password);
    }

    @GetMapping("/address/view")
    public ResponseEntity<AddressResDTO> viewAddress(){
        return sellerService.viewAddress();
    }

    @PatchMapping("/address/update/{id}")
    public AddressResDTO updateAddress(@PathVariable long id , @RequestBody Map<Object, Object> map) {
        return sellerService.updateAddress(id,map);
    }



    //Categories
    @GetMapping("/category/view")
    public LinkedHashMap<String,Object> getCategoryList(){
        return categoryService.getCategoryList();
    }


    //Products
    @PostMapping(value = "/product/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }

    @PostMapping(value = "/product/add-variation")
    public ResponseEntity<String> addProductVariation(@Valid @RequestBody ProductVariationDTO productVariationDTO){
        return productService.addProductVariation(productVariationDTO);
    }

}
