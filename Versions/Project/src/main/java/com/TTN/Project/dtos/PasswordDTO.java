package com.TTN.Project.dtos;

import javax.validation.constraints.NotBlank;

public class PasswordDTO {
    @NotBlank(message = "Enter Password")
    public String password;
    @NotBlank(message = "Re-Enter your Password")
    public String confirmPassword;

    public PasswordDTO(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
