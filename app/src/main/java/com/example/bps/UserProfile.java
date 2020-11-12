package com.example.bps;

import java.util.List;

public class UserProfile {
    private String userName;
    private String email;
    private List<String> uuid;

    public UserProfile(){

    }

    public UserProfile(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public UserProfile(String userName, String email, List<String> uuid){
        this.userName = userName;
        this.email = email;
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
