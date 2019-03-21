package com.mattm2812gmail.fyp;

public class User {

    private String email, password;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(){
        this.email = email;
    }

    public void setPassword(){
        this.password = password;
    }
}
