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
import com.TTN.Project.dtos.product.*;
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

    @GetMapping(value = "/product/view/{id}")
    public ProductResDTO viewProductById(@PathVariable Long id){
        return productService.viewProductById(id);
    }

    @GetMapping(value = "/product/view/all")
    public List<ProductResDTO> viewAllProducts(){
        return productService.viewAllProducts();
    }

    @GetMapping(value = "/product/view/all/{offset}/{size}")
    public List<ProductResDTO> viewAllProducts(@PathVariable long offset,@PathVariable long size){
        return productService.viewAllProducts(offset, size);
    }

    @GetMapping(value = "/product/view/variation/{id}")
    public ProductVariationResDTO viewProductVariation(@PathVariable long id){
        return productService.viewProductVariation(id);
    }

    @GetMapping(value = "/view/productVariationOfProduct/{id}")
    public List<ProductVariationResDTO> viewAllProductVariationOfSingleProduct(@PathVariable long id){
        return productService.viewAllProductVariationOfSingleProduct(id);
    }

    @GetMapping(value = "/view/productVariationOfProduct/{id}/{offset}/{size}")
    public List<ProductVariationResDTO> viewAllProductVariationOfSingleProduct(@PathVariable long id,@PathVariable long offset,@PathVariable long size){
        return productService.viewAllProductVariationOfSingleProduct(id, offset, size);
    }

    @DeleteMapping(value = "/delete/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
    }

    @PutMapping(value = "/update/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id,@Valid @RequestBody ProductUpdateDTO productUpdateDTO){
        return productService.updateProduct(id, productUpdateDTO);
    }

    @PutMapping(value = "/update/product/variation/{id}")
    public ResponseEntity<String> updateProductVariation(@PathVariable long id,@Valid @RequestBody ProductVariationUpdateDTO variationUpdateDTO){
        return productService.updateProductVariation(id, variationUpdateDTO);
    }


}
