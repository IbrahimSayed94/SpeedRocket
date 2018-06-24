package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 4/19/2018.
 */

public class CurrentOffers
{
    int offerid , userid  , image , finish;


    String title ;


    public  CurrentOffers () {} // zero constructor
    public CurrentOffers(int image, String title,int offerid,int finish)
    {
        this.image = image;
        this.title = title;
        this.offerid = offerid;
        this.finish = finish;
    } // param constructor

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
