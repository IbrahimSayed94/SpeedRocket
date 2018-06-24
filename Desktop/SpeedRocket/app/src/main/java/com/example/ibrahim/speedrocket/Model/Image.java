package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 4/30/2018.
 */

public class Image
{
    int offerId ;
    String image ;

    public Image(int offerId, String image) {
        this.offerId = offerId;
        this.image = image;
    }


    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
} // class of Image
