package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 4/4/2018.
 */

public class Offer
{
   int  id , categoryId  , userId , hours , minutes , srcoin , view , postImage ,companyId;
   double price ;
   String  ar_title , en_title , ar_description , en_description , startTime , endTime,
           image ,created_at , companyName , companyLogo , time;


    public Offer(int id, int categoryId, int userId, int hours, int minutes, int srcoin, int view, double price, String ar_title, String en_title, String ar_description, String en_description, String startTime, String endTime,int postImage,String image,String created_at,String companyName,String companyLogo,String time,int companyId)
    {
        this.id = id;
        this.categoryId = categoryId;
        this.userId = userId;
        this.hours = hours;
        this.minutes = minutes;
        this.srcoin = srcoin;
        this.view = view;
        this.price = price;
        this.ar_title = ar_title;
        this.en_title = en_title;
        this.ar_description = ar_description;
        this.en_description = en_description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.postImage = postImage;
        this.image = image;
        this.created_at = created_at;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.time = time ;
        this.companyId = companyId;
    } // param constructor


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSrcoin() {
        return srcoin;
    }

    public void setSrcoin(int srcoin) {
        this.srcoin = srcoin;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getAr_description() {
        return ar_description;
    }

    public void setAr_description(String ar_description) {
        this.ar_description = ar_description;
    }

    public String getEn_description() {
        return en_description;
    }

    public void setEn_description(String en_description) {
        this.en_description = en_description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
} //  offer class
