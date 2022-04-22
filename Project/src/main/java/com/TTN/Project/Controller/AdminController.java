package com.TTN.Project.Controller;


import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.ProCate.CategoryMetadataFieldRepo;
import com.TTN.Project.Repository.ProCate.CategoryRepo;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.Service.AdminService;
import com.TTN.Project.Service.CategoryService;
import com.TTN.Project.Service.ProductService;
import com.TTN.Project.dtos.category.CategoryDTO;
import com.TTN.Project.dtos.category.CategoryFieldValueResDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldDTO;
import com.TTN.Project.dtos.category.CategoryMetadataFieldValueDTO;
import com.TTN.Project.dtos.product.ProductResDTO;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import com.TTN.Project.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    AdminService adminService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @Autowired
    ProductService productService;

    @GetMapping("/getcustomers")
    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }

    @GetMapping("/getsellers")
    public List<Seller> getSellers() {
        List<Seller> sellers = sellerRepo.findAll();
        return sellers;
    }

    @PutMapping("/activate-customer/{user}")
    public ResponseEntity<String> activateCustomer(@PathVariable String user) {

        if (adminService.activate(user)) {
            return ResponseEntity.accepted().body("Customer activated");
        }
        return new ResponseEntity<>("customer not activated", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/activate-seller/{user}")
    public ResponseEntity<String> activateSeller(@PathVariable String user) {

        if (adminService.activate(user)) {
            return ResponseEntity.accepted().body("Seller activated");
        }
        return new ResponseEntity<>("seller not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/deactivate-customer/{user}")
    public ResponseEntity<String> deactivateCustomer(@PathVariable String user) {

        if (adminService.deactivate(user)) {
            return ResponseEntity.accepted().body("Customer deactivated");
        }
        return new ResponseEntity<>("customer not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/deactivate-seller/{user}")
    public ResponseEntity<String> deactivateSeller(@PathVariable String user) {

        if (adminService.deactivate(user)) {
            return ResponseEntity.accepted().body("Seller deactivated");
        }
        return new ResponseEntity<>("seller not found", HttpStatus.NOT_FOUND);
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


    @GetMapping("/category/viewall")
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

    //product
    @GetMapping("/product/view/{id}")
    public ProductResDTO viewProductById(@PathVariable Long id){
        return productService.viewProductById(id);
    }

    @GetMapping(value = "/product/view/all")
    public List<ProductResDTO> viewAllProducts(){
        return productService.viewAllProducts();
    }

    @PutMapping(value = "product/activate/{id}")
    public ResponseEntity<String> activateProduct(@PathVariable long id) {
        return adminService.activateProduct(id);
    }

    @PutMapping(value = "product/deactivate/{id}")
    public ResponseEntity<String> deactivateProduct(@PathVariable long id) {
        return adminService.deactivateProduct(id);
    }
}
