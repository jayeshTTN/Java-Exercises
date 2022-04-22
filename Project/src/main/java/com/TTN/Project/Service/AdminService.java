package com.TTN.Project.Service;


import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.ProCate.ProductRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.entities.ProCate.Product;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    UserRepo userRepository;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    EmailService emailService;

    @Transactional
    public boolean activate(String email) {
        UserEntity userFound = userRepository.findByEmail(email);
        if (userFound!=null) {
            if (!userFound.isIs_active()) {
                userFound.setIs_active(true);
                userRepository.updateUser(email,true);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deactivate(String email) {
        UserEntity userFound = userRepository.findByEmail(email);
        if (userFound!=null) {
            if (userFound.isIs_active()) {
                userFound.setIs_active(false);
                userRepository.updateUser(email,false);
            }
            return true;
        }
        return false;
    }


    public ResponseEntity<String> activateProduct(long id) {
        Product product = productRepo.findById(id);
        Seller seller = product.getSeller();
        UserEntity user = seller.getUser();

        if(product==null){
            throw new ResourceDoesNotExistException("No product exist for Id"+id);
        }

        if(!product.isActive()){
            product.setActive(true);
            productRepo.save(product);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Product Activation");
            mailMessage.setText("Your product is activated by admin");
            mailMessage.setTo(user.getEmail());
            emailService.sendEmail(mailMessage);
            return new ResponseEntity<>("Product activated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product is already activated",HttpStatus.OK);
    }

    public ResponseEntity<String> deactivateProduct(long id) {
        Product product = productRepo.findById(id);
        Seller seller = product.getSeller();
        UserEntity user = seller.getUser();
        if(product==null){
            throw new ResourceDoesNotExistException("No product exist for Id"+id);
        }

        if(product.isActive()){
            product.setActive(false);
            productRepo.save(product);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Product Activation");
            mailMessage.setText("Your product is activated by admin");
            mailMessage.setTo(user.getEmail());
            emailService.sendEmail(mailMessage);
            return new ResponseEntity<>("Product deactivated",HttpStatus.OK);
        }
        return new ResponseEntity<>("Product is already deactivated",HttpStatus.OK);
    }

}
