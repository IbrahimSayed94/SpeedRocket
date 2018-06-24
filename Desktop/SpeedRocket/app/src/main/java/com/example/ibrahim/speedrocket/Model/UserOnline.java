package com.example.ibrahim.speedrocket.Model;



/**
 * Created by Ibrahim on 4/15/2018.
 */

public class UserOnline
{
    int userid1 , userid2 , amount , accept , offerid;
    String firstName , lastName ;


    public  UserOnline () {}
    public UserOnline(int userid1, int userid2, int amount,int accept,String firstName,String
                      lastName, int offerid) {
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.amount = amount;
        this.accept = accept;
        this.firstName = firstName;
        this.lastName = lastName;
        this.offerid = offerid;
    }

    public UserOnline (int accept)
    {
        this.accept = accept ;
    }

    public int getUserid1() {
        return userid1;
    }

    public void setUserid1(int userid1) {
        this.userid1 = userid1;
    }

    public int getUserid2() {
        return userid2;
    }

    public void setUserid2(int userid2) {
        this.userid2 = userid2;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
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

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }
}
