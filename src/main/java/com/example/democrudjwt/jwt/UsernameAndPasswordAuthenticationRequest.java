package com.example.democrudjwt.jwt;

public class UsernameAndPasswordAuthenticationRequest {

    private String email;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return "password"; //I set a password for everyone to access
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
