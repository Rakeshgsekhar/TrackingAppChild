package com.example.devoprakesh.trackingappchild;

public class UserData {

    String name;
    String email;
    String unicode;

    public UserData(String name, String email, String unicode) {
        this.name = name;
        this.email = email;
        this.unicode = unicode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }
}
