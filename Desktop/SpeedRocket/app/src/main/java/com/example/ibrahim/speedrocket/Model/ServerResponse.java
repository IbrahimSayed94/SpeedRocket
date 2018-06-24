package com.example.ibrahim.speedrocket.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ibrahim on 5/2/2018.
 */

public class ServerResponse
{
    // variable name should be same as in the json response from php


    @SerializedName("id")
    private Integer id;

    @SerializedName("success")
    boolean success;

    @SerializedName("message")
    String message;

    @SerializedName("message1")
    String message1;

    public String getMessage() {
        return message;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public boolean getSuccess() {
        return success;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




} // class of ServerResponse
