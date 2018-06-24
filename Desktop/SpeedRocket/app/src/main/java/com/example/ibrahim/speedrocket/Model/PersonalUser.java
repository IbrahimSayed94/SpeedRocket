package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 3/26/2018.
 */

public class PersonalUser
{

    private int id  , coins;
    private String firstName , lastName , email ,
            password , gender , language , interest , type , image ,
             created_at , confirm , mobile ,companyName ,companyCity ,companyCountry
            ,companyMobile , companyInterest ,city , country , companyLogo ;  // same name fields on database

    public  PersonalUser () {} // Default Constructor

    public PersonalUser(int id, int coins, String firstName, String lastName, String email, String password, String gender, String language, String interest, String type, String image, String created_at, String confirm, String mobile, String companyName, String companyCity, String companyCountry, String companyMobile, String companyInterest, String city, String country)
    {
        this.id = id;
        this.coins = coins;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.language = language;
        this.interest = interest;
        this.type = type;
        this.image = image;
        this.created_at = created_at;
        this.confirm = confirm;
        this.mobile = mobile;
        this.companyName = companyName;
        this.companyCity = companyCity;
        this.companyCountry = companyCountry;
        this.companyMobile = companyMobile;
        this.companyInterest = companyInterest;
        this.city = city;
        this.country = country;
    } // Param Constructor

    public  PersonalUser (int coins , String firstName , String lastName,int id,String image)
    {
        this.coins = coins ;
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.id = id ;
        this.image = image ;
    }// Param Constructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrCoin() {
        return coins;
    }

    public void setSrCoin(int coins) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getCompanyInterest() {
        return companyInterest;
    }

    public void setCompanyInterest(String companyInterest) {
        this.companyInterest = companyInterest;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}// Class Of PersonalUser
