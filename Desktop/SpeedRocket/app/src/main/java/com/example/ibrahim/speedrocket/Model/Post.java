package com.example.ibrahim.speedrocket.Model;

import java.lang.ref.SoftReference;

/**
 * Created by Ibrahim on 3/20/2018.
 */

public class Post
{
  private  String firstName , lastName , country ;
   private  String points , cost , share ;
    private int profileImage , postImage , postID , countryImage;



    Post () {} // default constructor

    public Post(String firstName, String lastName, int profileImage, int postImage
            , String points, String cost, String share,int postID,String country,int countryImage)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.points = points ;
        this.cost = cost ;
        this.share = share;
        this.postID = postID;
        this.country = country;
        this.countryImage = countryImage;
    } // constructor


    public  String getFirstName()
    {
       return  firstName;
    }
    public void  setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public  String getLastName()
    {
        return  lastName;
    }
    public  void  setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public  int getPostID()
    {
        return postID;
    }
    public void  setPostID(int postID)
    {
        this.postID = postID;
    }
    public  int getProfileImage()
    {
        return profileImage;
    }
    public void  setProfileImage(int profileImage)
    {
        this.profileImage = profileImage;
    }
    public  int getPostImage()
    {
        return postImage;
    }
    public void  setPostImage(int postImage)
    {
        this.postImage = postImage;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public int getCountryImage() {
        return countryImage;
    }

    public void setCountryImage(int countryImage) {
        this.countryImage = countryImage;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
} // Class of Posts
