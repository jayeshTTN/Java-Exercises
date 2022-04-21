package com.TTN.Project.Repository;

import com.TTN.Project.entities.Address;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SellerRepo extends JpaRepository<Seller,Long> {
    Seller findByGst(String gst);
    Seller findByCompanyName(String CompanyName);
    Seller findByUserId(long id);
}
