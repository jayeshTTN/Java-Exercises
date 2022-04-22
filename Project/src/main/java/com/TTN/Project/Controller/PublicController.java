package com.TTN.Project.Controller;


import com.TTN.Project.Exception.InvalidTokenException;
import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.Repository.TokenRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.Service.EmailService;
import com.TTN.Project.dtos.customer.CustomerResDTO;
import com.TTN.Project.dtos.seller.SellerResDTO;
import com.TTN.Project.entities.ConfirmationToken;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Collection;


@RestController
public class PublicController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    TokenRepo tokenRepository;

    @Autowired
    UserRepo userRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    DataSource dataSource;


    @PostMapping("/login")
    public String login(@RequestBody ObjectNode objectNode) {
        String email = objectNode.get("email").asText();
        String password = objectNode.get("password").asText();

        boolean loginResponse = securityService.login(email, password);


        if (loginResponse) {
            return "Successfully Logged in ";
        }
        return "Account does not exist wit email "+email;
    }

    @PutMapping(value = "/confirm/customer")
    public ResponseEntity<CustomerResDTO> confirmCustomerAccount(@RequestParam("token") String confirmationToken) throws InvalidTokenException {
        ConfirmationToken token = tokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(token.getUserEntity().getEmail());
        if (token != null) {
            UserEntity user = userRepo.findByEmail(token.getUserEntity().getEmail());
            if (user == null) {
                throw new InvalidTokenException("Invalid token");
            }
            user.setIs_active(true);
            userRepo.save(user);
            Customer customer = customerRepo.findByUserId(user.getId());
            CustomerResDTO customerResDTO = new CustomerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),customer.getContact());

            return new ResponseEntity<CustomerResDTO>(customerResDTO, HttpStatus.CREATED);
        } else {
            throw new InvalidTokenException("token cannot be null");
        }
    }

    @PutMapping(value = "/confirm/seller")
    public ResponseEntity<SellerResDTO> confirmSellerAccount(@RequestParam("token") String confirmationToken) throws InvalidTokenException {
        ConfirmationToken token = tokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(token.getUserEntity().getEmail());
        if (token != null) {
            UserEntity user = userRepo.findByEmail(token.getUserEntity().getEmail());
            if (user == null) {
                throw new InvalidTokenException("Invalid token");
            }
            user.setIs_active(true);
            userRepo.save(user);

            Seller seller = sellerRepo.findByUserId(user.getId());
            SellerResDTO sellerResDTO = new SellerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),seller.getCompanyContact(),seller.getCompanyName(),seller.getGst());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Account Activation");
            mailMessage.setText("Your Account is activated by admin");
            mailMessage.setTo(user.getEmail());
            emailService.sendEmail(mailMessage);

            return new ResponseEntity<SellerResDTO>(sellerResDTO, HttpStatus.CREATED);
        } else {
            throw new InvalidTokenException("token cannot be null");
        }
    }


    @PutMapping(value = "customer/resend/activation/{email}")
    public ResponseEntity<CustomerResDTO> resendActivation(@PathVariable String email) {
            UserEntity user = userRepo.findByEmail(email);
            if (user == null) {
                throw new ResourceDoesNotExistException("User Not found");
            }
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            tokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Complete registration");
            mailMessage.setText("To confirm your account, please click on the given link : "
                    +"http://localhost:9092/customer/confirm?token="+confirmationToken.getConfirmationToken());
            mailMessage.setTo(user.getEmail());
            Customer customer = customerRepo.findByUserId(user.getId());
            CustomerResDTO customerResDTO = new CustomerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),customer.getContact());
            emailService.sendEmail(mailMessage);
            return new ResponseEntity<CustomerResDTO>(customerResDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public String logout(Principal principal) {
        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        OAuth2AccessToken accessToken = jdbcTokenStore.getAccessToken(oAuth2Authentication);
        jdbcTokenStore.removeAccessToken(accessToken.getValue());
        jdbcTokenStore.removeRefreshToken(accessToken.getRefreshToken());
        return "You have been LoggedOut";
    }
}

