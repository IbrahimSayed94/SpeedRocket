package com.example.ibrahim.speedrocket.Model;



/**
 * Created by Ibrahim on 5/14/2018.
 */

public class Product
{
    int id , price , companyId ;
    String en_title , ar_title , en_description , ar_description , image , companyName ;

    public  Product () {}

    public Product(int id, String en_title, String en_description, String image, String companyName,int price) {
        this.id = id;
        this.en_title = en_title;
        this.en_description = en_description;
        this.image = image;
        this.companyName = companyName;
        this.price = price;
    }

    public  Product (int id, String en_title)
    {
        this.id = id;
        this.en_title = en_title;
    }

    public Product(int id, int price, int companyId, String en_title, String ar_title, String en_description, String ar_description, String image)
    {
        this.id = id;
        this.price = price;
        this.companyId = companyId;
        this.en_title = en_title;
        this.ar_title = ar_title;
        this.en_description = en_description;
        this.ar_description = ar_description;
        this.image = image;
    } // param Constructor



    public Product(int id, int price, int companyId, String en_title, String ar_title, String en_description, String ar_description, String image,String companyName)
    {
        this.id = id;
        this.price = price;
        this.companyId = companyId;
        this.en_title = en_title;
        this.ar_title = ar_title;
        this.en_description = en_description;
        this.ar_description = ar_description;
        this.image = image;
        this.companyName = companyName;
    } // param Constructor



    public  Product(String en_title,String en_description,String image,int id)
    {
        this.en_title = en_title;
        this.en_description = en_description;
        this.image = image;
        this.id = id;
    } // constructor

    public  Product(String en_title,String en_description,String image,int id,int price)
    {
        this.en_title = en_title;
        this.en_description = en_description;
        this.image = image;
        this.id = id;
        this.price = price;
    } // constructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public String getEn_discription() {
        return en_description;
    }

    public void setEn_discription(String en_discription) {
        this.en_description = en_discription;
    }

    public String getAr_discription() {
        return ar_description;
    }

    public void setAr_discription(String ar_discription) {
        this.ar_description = ar_discription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}// class Product
