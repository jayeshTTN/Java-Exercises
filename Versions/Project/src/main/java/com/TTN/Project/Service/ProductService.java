package com.TTN.Project.Service;

import com.TTN.Project.Exception.ResourceAlreadyExistException;
import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.ProCate.*;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.dtos.product.ProductDTO;
import com.TTN.Project.dtos.product.ProductVariationDTO;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import com.TTN.Project.entities.ProCate.Product;
import com.TTN.Project.entities.ProCate.ProductVariation;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    SellerRepo sellerRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;
    @Autowired
    CategoryMetaDataFieldValueRepo categoryMetaDataFieldValueRepo;
    @Autowired
    ProductVariationRepo productVariationRepo;



    public ResponseEntity<String> addProduct(ProductDTO productDTO){
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Seller seller = sellerRepo.findByUserId(user.getId());
        Category category = categoryRepo.findById(productDTO.getCategoryId());
        if(category == null){
            throw new ResourceDoesNotExistException("Category does not exist with category id:-"+productDTO.getCategoryId());
        }
        if ( category.getParentId()==0){
            throw new ResourceDoesNotExistException("Product cannot be added with Parent Category:-"+productDTO.getCategoryId()+" select sub-category");
        }
        List<Product> sameBrandCategorySeller = productRepo.findSameBrandCategorySeller(productDTO.getBrand(), productDTO.getCategoryId(), seller.getId());
        for(Product pr:sameBrandCategorySeller){
            if(pr.getName()==productDTO.getName()){
                throw new ResourceAlreadyExistException("A product with same brand and category already exist");
            }
        }
        Product product = new Product();
        product.setSeller(seller);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        product.setBrand(productDTO.getBrand());
        product.setActive(false);
        product.setCancellable(productDTO.isCancellable());
        product.setReturnable(productDTO.isReturnable());
        product.setDeleted(false);

        productRepo.save(product);
        return new ResponseEntity<String>("Product Successfully Added,Wait for Activation",HttpStatus.CREATED);
    }

    public ResponseEntity<String> addProductVariation(ProductVariationDTO productVariationDTO){

        Product  product = productRepo.findById(productVariationDTO.getProductId());
        System.out.println(product);
        if(product == null){
            throw new ResourceDoesNotExistException("Product doesn't exist");
        }
        Map<String,String> metadataExample = productVariationDTO.getMetadata();
        for(Map.Entry<String,String> entry : metadataExample.entrySet()){
            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepo.findByName(entry.getKey());
            if(categoryMetadataField == null){
                throw new ResourceDoesNotExistException("Category Metadata Field doesn't Exist");
            }
            List<Object[]> result = categoryMetaDataFieldValueRepo.viewMetadataValues2(product.getCategory().getId(), categoryMetadataField.getId());
            if(result.isEmpty()){
                throw new ResourceDoesNotExistException("No Metadata values Exist for category id:-"+product.getCategory().getId()+" and field id:-"+categoryMetadataField.getId());
            }
            Object[] temp = result.get(0);
            String[] str = temp[2].toString().split(",");

            boolean flag = false;
            for(String myStr: str) {
                if(entry.getValue().equals(myStr)){
                    flag= true;
                }
            }
            if(flag == false){
                throw  new ResourceDoesNotExistException(" Field values doesn't exist, Choose from :-"+temp[2].toString());
            }
        }

        ProductVariation productVariation = new ProductVariation();
        productVariation.setProduct(product);
        productVariation.setActive(true);
        productVariation.setPrice(productVariationDTO.getPrice());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantity());
        productVariation.setMetadata(productVariationDTO.getMetadata());

        productVariationRepo.save(productVariation);
        return new ResponseEntity<String>("Product Variation is successfully saved ",HttpStatus.CREATED);
    }

}
