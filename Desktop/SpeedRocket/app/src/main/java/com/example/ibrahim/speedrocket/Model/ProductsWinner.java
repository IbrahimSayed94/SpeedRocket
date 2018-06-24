package com.example.ibrahim.speedrocket.Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Ibrahim on 4/23/2018.
 */

public class ProductsWinner
{
    int userId , offerId , srCoin , days , price , states;
    String en_title , postImage , companyName , image , en_description;

   // Timestamp created_at ;

    public ProductsWinner(int userId, int offerId, int srCoin, String en_title) {
        this.userId = userId;
        this.offerId = offerId;
        this.srCoin = srCoin;
        this.en_title = en_title;

    } // param constructor
    public ProductsWinner(int userId, int offerId, int srCoin, String en_title,int days,String companyName,String image,int price,
                          String en_description,int states) {
        this.userId = userId;
        this.offerId = offerId;
        this.srCoin = srCoin;
        this.en_title = en_title;
        this.days = days;
        this.companyName = companyName;
        this.image = image;
        this.price = price;
        this.en_description = en_description;
        this.states = states;

    } // param constructor

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getSrCoin() {
        return srCoin;
    }

    public void setSrCoin(int srCoin) {
        this.srCoin = srCoin;
    }

    public String getTitle() {
        return en_title;
    }

    public void setTitle(String en_title) {
        this.en_title = en_title;
    }

 /*   public Timestamp getCreated_at() {
        return created_at;
    }*/

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEn_description() {
        return en_description;
    }

    public void setEn_description(String en_description) {
        this.en_description = en_description;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    /* public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }*/
} // class of ProductsWinner
