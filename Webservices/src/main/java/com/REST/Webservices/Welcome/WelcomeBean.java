package com.REST.Webservices.Welcome;

public class WelcomeBean {
    private String message;
    public WelcomeBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WelcomeBean{" +"message='" + message + '\'' +'}';
    }
}
