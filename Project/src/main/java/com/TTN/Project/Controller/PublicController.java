package com.TTN.Project.Controller;


import com.TTN.Project.Security.SecurityService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    @Autowired
    private SecurityService securityService;

//    @PostMapping("/login")
//    public String login(@RequestBody String email,@RequestBody String password) {

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
}
