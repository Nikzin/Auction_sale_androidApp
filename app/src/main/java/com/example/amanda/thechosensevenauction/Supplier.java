package com.example.amanda.thechosensevenauction;

import java.io.Serializable;

/**
 * Created by redte on 2017-03-17.
 */

public class Supplier implements Serializable{

    private String id;
    private String companyName;
    private String email;
    private String phone;
    private String addresss;
    private String postalCode;
    private String city;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Supplier(String id, String companyName, String email, String phone, String addresss, String postalCode, String city) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.addresss = addresss;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
