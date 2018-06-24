package com.example.ibrahim.speedrocket.Model;

import android.app.Notification;

import java.util.List;

/**
 * Created by Ibrahim on 3/27/2018.
 */

public class ResultModel
{

  //  private List<PersonalUser> users ;  // same name of table josn


    private  List <Offer> offers;

    private  List<Product> products;

    private  List <Image> images ;

    private  List <Category> Category;
    private  List <Company> Companies;
    private  List<Error> errors;

    private  List <BankAccount> Banks;

    private List<String> email;
    private  List<String> type;

     private  int net , pending;
    String image ;

    String time ;
    String message ,data_1 ;
    private List<PersonalUser> data ;
    private List<PersonalUser> user ;

    private List<Company> Company ;

    private  List<ProductsWinner> Winners ;

    private List<UserInOffer> userInOffer ;
    public ResultModel(List<String> email) {
        this.email = email;
    }

    public List<String> getEmail() {
        return email;
    }

    public List<PersonalUser> getUser() {
        return user;
    }



    public List<UserInOffer> getUsers() {
        return userInOffer;
    }


    public List<com.example.ibrahim.speedrocket.Model.Category> getCategory() {
        return Category;
    }

    public void setCategory(List<com.example.ibrahim.speedrocket.Model.Category> category) {
        Category = category;
    }

    public void setUsers(List<UserInOffer> userInOffer) {
        this.userInOffer = userInOffer;
    }

    public void setUser(List<PersonalUser> user) {
    this.user = user;
}


    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }


    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public String getData_1() {

        return data_1;
    }

    public void setData_1(String data_1) {
        this.data_1 = data_1;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<PersonalUser> getData() {
        return data;
    }

    public void setData(List<PersonalUser> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductsWinner> getWinners() {
        return Winners;
    }

    public void setWinners(List<ProductsWinner> winners) {
        Winners = winners;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<com.example.ibrahim.speedrocket.Model.Company> getCompany() {
        return Company;
    }

    public void setCompany(List<com.example.ibrahim.speedrocket.Model.Company> company) {
        Company = company;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<com.example.ibrahim.speedrocket.Model.Company> getCompanies() {
        return Companies;
    }

    public void setCompanies(List<com.example.ibrahim.speedrocket.Model.Company> companies) {
        Companies = companies;
    }

    public List<BankAccount> getBanks() {
        return Banks;
    }

    public void setBanks(List<BankAccount> banks) {
        Banks = banks;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    /* public List<PersonalUser> getPersonalusers() {
        return users;
    }

    public void setPersonalusers(List<PersonalUser> personalusers) {
        this.users = personalusers;
    }*/
} // Class Of ResultModel
