package com.example.security.JWT;

public class UserNameAndPwdAuthRequest {

    private String username;
    private String password;

    public UserNameAndPwdAuthRequest(){

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
