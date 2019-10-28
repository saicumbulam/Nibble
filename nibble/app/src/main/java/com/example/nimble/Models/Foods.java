package com.example.nimble.Models;

public class Foods {
    private String fname, fprice;

    public Foods() {
    }

    public Foods(String fname, String fprice) {
        this.fname = fname;
        this.fprice = fprice;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFprice() {
        return fprice;
    }

    public void setFprice(String fprice) {
        this.fprice = fprice;
    }
}
