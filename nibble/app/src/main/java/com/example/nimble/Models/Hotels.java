package com.example.nimble.Models;

public class Hotels {
    private String hname, haddress, hphone, hid;

    public Hotels() {
    }

    public Hotels(String hname, String haddress, String hphone, String hid) {
        this.hname = hname;
        this.haddress = haddress;
        this.hphone = hphone;
        this.hid = hid;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHaddress() {
        return haddress;
    }

    public void setHaddress(String haddress) {
        this.haddress = haddress;
    }

    public String getHphone() {
        return hphone;
    }

    public void setHphone(String hphone) {
        this.hphone = hphone;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }
}
