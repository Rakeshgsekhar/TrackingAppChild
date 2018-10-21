package com.example.devoprakesh.trackingappchild;

public class UserData {

    String name;
    String email;
    String unicode;
    String phonenumber;

    public UserData(String name, String email, String unicode,String phonenumber) {
        this.name = name;
        this.email = email;
        this.unicode = unicode;
        this.phonenumber = phonenumber;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
