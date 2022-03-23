package com.REST2.Webservices2.Versioning;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    //URI VERSIONING
    @GetMapping("v1/User")
    public User1 personV1() {
        return new User1("Jayesh");
    }
    @GetMapping("v2/User")
    public User2 personV2() {
        return new User2(new Details("Jayesh","Gupta","9876465787"));
    }

    //Request Parameter versioning

    @GetMapping(value="/User/param",params = "version=1")
    public User1 paramV1() {
        return new User1("Jayesh");
    }
    @GetMapping(value="/User/param",params = "version=2")
    public User2 paramV2() {
        return new User2(new Details("Jayesh","Gupta","987654321"));
    }

    //Custom Header Versioning

    @GetMapping(value="/User/header",headers ="X-API-VERSION=1")
    public User1 headerV1() {
        return new User1("Jayesh");
    }
    @GetMapping(value="/User/header",headers = "X-API-VERSION=2")
    public User2 headerV2() {
        return new User2(new Details("Jayesh","Gupta","987654321"));
    }



    //MimeType Versioning
    @GetMapping(value="/Employee/produces",produces ="application/vnd.company.app-v1+json")
    public User1 producesV1() {
        return new User1("Jayesh");
    }
    @GetMapping(value="/Employee/produces", produces =  "application/vnd.company.app-v2+json")
    public User2 producesV2() {
        return new User2(new Details("Jayesh","Gupta","987654321"));
    }


}
