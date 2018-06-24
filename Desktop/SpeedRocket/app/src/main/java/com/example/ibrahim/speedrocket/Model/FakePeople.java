package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 4/29/2018.
 */

public class FakePeople
{
    String email , firstName , lastName , profileImage ;
    int id ;


    public  FakePeople () {}

    public FakePeople(String email, String firstName, String lastName, String profileImage,int id) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
} // clas FakePeople
