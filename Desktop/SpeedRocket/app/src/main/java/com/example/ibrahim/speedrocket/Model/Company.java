package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 5/14/2018.
 */

public class Company
{
    int id , userId , categoryId , check;
    String  en_name , ar_name , email , country , city , phone , fax , logo ,created_at;


    public  Company () {} // zero Constructor

    public Company(int id, String en_name) {
        this.id = id;
        this.en_name = en_name;
    } // 2 Constructor



    public Company(int id, String en_name,String logo,int check) {
        this.id = id;
        this.en_name = en_name;
        this.logo = logo;
        this.check = check;
    } // 2 Constructor


    public Company(int id, String en_name,String logo) {
        this.id = id;
        this.en_name = en_name;
        this.logo = logo;
    } // 3 Constructor

    public Company(int id, int userId, int categoryId, String en_name, String ar_name, String email, String country, String city, String phone, String fax, String logo)
    {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.en_name = en_name;
        this.ar_name = ar_name;
        this.email = email;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.fax = fax;
        this.logo = logo;
    } // param |Constructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
} // class Company
