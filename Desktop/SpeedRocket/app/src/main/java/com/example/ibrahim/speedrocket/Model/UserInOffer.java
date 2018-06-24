package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 4/9/2018.
 */

public class UserInOffer
{
    int id , userId , offersId , srCoin ,coins , position , view , expired , finish;
    String firstName , lastName , email , profileImage , offerTitle ;


    public  UserInOffer () {}
    public UserInOffer (int id, int userId, int offersId, int srCoin, int coins, String firstName, String lastName, String email,int view,int expired,String profileImage, String offerTitle , int  finish)
    {
        this.id = id;
        this.userId = userId;
        this.offersId = offersId;
        this.srCoin = srCoin;
        this.coins = coins;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.view = view;
        this.expired = expired;
        this.profileImage = profileImage;
        this.offerTitle = offerTitle;
        this.finish = finish;
    } // param constructor

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

    public int getOfferId() {
        return offersId;
    }

    public void setOfferId(int offersId) {
        this.offersId = offersId;
    }

    public int getSrCoin() {
        return srCoin;
    }

    public void setSrCoin(int srCoin) {
        this.srCoin = srCoin;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
} // class UserInOffer
