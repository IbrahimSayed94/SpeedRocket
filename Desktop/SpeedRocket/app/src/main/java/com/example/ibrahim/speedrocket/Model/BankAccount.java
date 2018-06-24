package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 5/31/2018.
 */

public class BankAccount
{
    int   id,swift , userId , bankAccount ;
    String bankAddress , name ;

    public  BankAccount () {} // zero constructor

    public BankAccount(int swift, int id, int bankAccount, String bankAddress, String name) {
        this.swift = swift;
        this.id = id;
        this.bankAccount = bankAccount;
        this.bankAddress = bankAddress;
        this.name = name;
    } // param constructor

    public int getSwift() {
        return swift;
    }

    public void setSwift(int swift) {
        this.swift = swift;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
} // class of BankAccount
