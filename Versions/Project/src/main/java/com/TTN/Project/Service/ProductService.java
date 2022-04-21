package com.TTN.Project.Service;

import com.TTN.Project.Exception.ResourceAlreadyExistException;
import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.ProCate.*;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.dtos.PageableDTO;
import com.TTN.Project.dtos.product.*;
import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import com.TTN.Project.entities.ProCate.Product;
import com.TTN.Project.entities.ProCate.ProductVariation;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
        System.out.println(sameBrandCategorySeller);
        for(Product pr:sameBrandCategorySeller){
            if(pr.getName().equals(productDTO.getName())){
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


    public ProductResDTO viewProductById(Long id){
        Product product = productRepo.findById(id).orElse(null);
        if(product == null){
            throw new ResourceDoesNotExistException("Product Doesn't exist with id : " + id);
        }
        ProductResDTO productResponseDTO = new ProductResDTO();
        productResponseDTO.setBrand(product.getBrand());
        productResponseDTO.setCancellable(product.isCancellable());
        productResponseDTO.setDeleted(product.isDeleted());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setActive(product.isActive());
        productResponseDTO.setCategoryName(product.getCategory().getName());
        productResponseDTO.setReturnable(product.isReturnable());
        return productResponseDTO;
    }

    public List<ProductResDTO> viewAllProducts(){
        List<ProductResDTO> listNeeded = new ArrayList<>();
        List<Product> listProducts = productRepo.findAll();
        for(Product product : listProducts){
            ProductResDTO productResponseDTO = new ProductResDTO();
            productResponseDTO.setBrand(product.getBrand());
            productResponseDTO.setCancellable(product.isCancellable());
            productResponseDTO.setDeleted(product.isDeleted());
            productResponseDTO.setName(product.getName());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setActive(product.isActive());
            productResponseDTO.setCategoryName(product.getCategory().getName());
            productResponseDTO.setReturnable(product.isReturnable());
            listNeeded.add(productResponseDTO);
        }
        return listNeeded;
    }

    public List<ProductResDTO> viewAllProducts(long offset , long size){

        List<ProductResDTO> listNeeded = new ArrayList<>();
        Pageable page = PageRequest.of(Math.toIntExact(offset), Math.toIntExact(size),Sort.by("id"));

        List<Product> listProducts = productRepo.findAll(page).toList();
        for(Product product : listProducts){
            ProductResDTO productResponseDTO = new ProductResDTO();
            productResponseDTO.setBrand(product.getBrand());
            productResponseDTO.setCancellable(product.isCancellable());
            productResponseDTO.setDeleted(product.isDeleted());
            productResponseDTO.setName(product.getName());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setActive(product.isActive());
            productResponseDTO.setCategoryName(product.getCategory().getName());
            productResponseDTO.setReturnable(product.isReturnable());
            listNeeded.add(productResponseDTO);
        }
        return listNeeded;
    }

    public ProductVariationResDTO viewProductVariation(long id){

        ProductVariation productVariation = productVariationRepo.findById(id);

        if(productVariation == null){
            throw new ResourceDoesNotExistException("Product Variation exist of id : " + id);

        }

        ProductVariationResDTO productVariationResponseDTO = new ProductVariationResDTO();
        productVariationResponseDTO.setBrand(productVariation.getProduct().getBrand());
        productVariationResponseDTO.setCancellable(productVariation.getProduct().isCancellable());
        productVariationResponseDTO.setDescription(productVariation.getProduct().getDescription());
        productVariationResponseDTO.setMetadata(productVariation.getMetadata());
        productVariationResponseDTO.setName(productVariation.getProduct().getName());
        productVariationResponseDTO.setReturnable(productVariation.getProduct().isReturnable());
        productVariationResponseDTO.setActive(productVariation.getActive());
        productVariationResponseDTO.setPrice(productVariation.getPrice());
        productVariationResponseDTO.setQuantity(productVariation.getQuantityAvailable());
        productVariationResponseDTO.setDeleted(productVariation.getProduct().isDeleted());

        return productVariationResponseDTO;
    }

    @GetMapping(value = "/view/productVariationOfProduct/{id}")
    public List<ProductVariationResDTO> viewAllProductVariationOfSingleProduct(long id){

        List<ProductVariationResDTO> listNeeded = new ArrayList<>();


        List<ProductVariation> listProducts = productVariationRepo.findAllByProduct(productRepo.findById(id));

        if(listProducts == null){
            throw new ResourceDoesNotExistException("Product Variation does not exist for product  with id :" +id);
        }

        for(ProductVariation productVariation : listProducts){
            ProductVariationResDTO productVariationResponseDTO = new ProductVariationResDTO();
            productVariationResponseDTO.setBrand(productVariation.getProduct().getBrand());
            productVariationResponseDTO.setCancellable(productVariation.getProduct().isCancellable());
            productVariationResponseDTO.setDescription(productVariation.getProduct().getDescription());
            productVariationResponseDTO.setMetadata(productVariation.getMetadata());
            productVariationResponseDTO.setName(productVariation.getProduct().getName());
            productVariationResponseDTO.setReturnable(productVariation.getProduct().isReturnable());
            productVariationResponseDTO.setActive(productVariation.getActive());
            productVariationResponseDTO.setPrice(productVariation.getPrice());
            productVariationResponseDTO.setQuantity(productVariation.getQuantityAvailable());
            productVariationResponseDTO.setDeleted(productVariation.getProduct().isDeleted());

            listNeeded.add(productVariationResponseDTO);
        }
        return listNeeded;
    }

    @GetMapping(value = "/view/productVariationOfProduct/{id}")
    public List<ProductVariationResDTO> viewAllProductVariationOfSingleProduct(long id,long offset,long size){

        List<ProductVariationResDTO> listNeeded = new ArrayList<>();

        Pageable page = PageRequest.of(Math.toIntExact(offset), Math.toIntExact(size),Sort.by("id"));

        List<ProductVariation> listProducts = productVariationRepo.findAll(page).toList();

        if(listProducts == null){
            throw new ResourceDoesNotExistException("Product Variation does not exist for product  with id :" +id);
        }

        for(ProductVariation productVariation : listProducts){
            ProductVariationResDTO productVariationResponseDTO = new ProductVariationResDTO();
            productVariationResponseDTO.setBrand(productVariation.getProduct().getBrand());
            productVariationResponseDTO.setCancellable(productVariation.getProduct().isCancellable());
            productVariationResponseDTO.setDescription(productVariation.getProduct().getDescription());
            productVariationResponseDTO.setMetadata(productVariation.getMetadata());
            productVariationResponseDTO.setName(productVariation.getProduct().getName());
            productVariationResponseDTO.setReturnable(productVariation.getProduct().isReturnable());
            productVariationResponseDTO.setActive(productVariation.getActive());
            productVariationResponseDTO.setPrice(productVariation.getPrice());
            productVariationResponseDTO.setQuantity(productVariation.getQuantityAvailable());
            productVariationResponseDTO.setDeleted(productVariation.getProduct().isDeleted());

            listNeeded.add(productVariationResponseDTO);
        }
        return listNeeded;
    }

    public ResponseEntity<String> deleteProduct(long id){
        Product product = productRepo.findById(id);
        if(product == null){
            throw new ResourceDoesNotExistException("Product does not exist");
        }

        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!product.getSeller().getId().equals(user.getId())){
            throw new ResourceDoesNotExistException("You can't delete this resource it is not accessible");
        }
        productRepo.delete(product);
        return new ResponseEntity<String>("Product deleted successfully",HttpStatus.ACCEPTED);
    }
    public ResponseEntity<String> updateProduct(long id,ProductUpdateDTO productUpdateDTO){

        Product product = productRepo.findById(id);

        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(product == null){
            throw new ResourceDoesNotExistException("Product does not exist");
        }
        Seller seller = sellerRepo.findByUserId(id);
        List<Product> sameBrandCategorySeller = productRepo.findSameBrandCategorySeller(product.getBrand(), product.getCategory().getId(), user.getId());

        for(Product product1:sameBrandCategorySeller){
            if (product1.getName().equals(productUpdateDTO.getName()))
                throw new ResourceAlreadyExistException("This name already exists : ");
        }
        product.setName(productUpdateDTO.getName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setReturnable(productUpdateDTO.isReturnable());
        product.setCancellable(productUpdateDTO.isCancellable());

        productRepo.save(product);

        return new ResponseEntity<String>("Product Successfully Updated",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> updateProductVariation(long id,ProductVariationUpdateDTO variationUpdateDTO){

        ProductVariation productVariation = productVariationRepo.findById(id);

        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(productVariation == null){
            throw  new ResourceDoesNotExistException("Product Variation doesn't exist with id : "+id);
        }

        if(productVariation.getProduct().isActive()){
            throw new ResourceDoesNotExistException("Product Variation is not active ");
        }

        productVariation.setQuantityAvailable(variationUpdateDTO.getQuantity());
        productVariation.setPrice(variationUpdateDTO.getPrice());
        productVariation.setMetadata(variationUpdateDTO.getMetadata());
        productVariation.setActive(variationUpdateDTO.isActive());


        productVariationRepo.save(productVariation);
        return new ResponseEntity<String>("Product Variation Successfully Updated",HttpStatus.ACCEPTED);

    }

}
