package com.TTN.Project.Repository.ProCate;

import com.TTN.Project.entities.ProCate.Product;
import com.TTN.Project.entities.ProCate.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariationRepo extends JpaRepository<ProductVariation,Long> {

    List<ProductVariation> findAllByProduct(Product product, Pageable pageable);

    List<ProductVariation> findAllByProduct(Product product);

    ProductVariation findById(long id);

}
