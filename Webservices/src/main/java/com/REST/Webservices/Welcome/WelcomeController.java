package com.REST.Webservices.Welcome;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WelcomeController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/welcome")
    public String welcome(){
        return "Welcome to SpringBoot";
    }
    @GetMapping(path = "/WelcomeBean")
    public WelcomeBean WelcomeBean() {
        return new WelcomeBean("Welcome to SpringBoot");
    }
    //   /Welcome/path-variable/Jayesh
    @GetMapping(path = "/Welcome/path-variable/{name}")
    public WelcomeBean helloWorldPathVariable(@PathVariable String name) {
        return new WelcomeBean(String.format("Hello World, %s", name));
    }
    @GetMapping(path = "/welcome-internationalized")
    public String welcomeInternationalized() {
        return messageSource.getMessage("good.morning.message", null,
                LocaleContextHolder.getLocale());
    }
}
