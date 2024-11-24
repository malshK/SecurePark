package com.example.securepark.models;

public class User {
        public String email;
        public String password;
        public String confirm_password;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setConfirm_password(String confirmPassword) {
        this.confirm_password = confirmPassword;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    // Constructor, Getters, and Setters
}
