package com.example.ibrahim.speedrocket.Model;

import com.example.ibrahim.speedrocket.View.Activites.CompanyProfile;

/**
 * Created by Ibrahim on 3/26/2018.
 */

public class CompanyUser extends PersonalUser
{

   private  String companyName , companyCountry , companyCity , companyMobile , fax ,
            bankAccount , bankAddress , swift , companyLogo , companyInterest , companyEmail;
    private double totalCash , availableCash ;

    public  CompanyUser () {} // Default Constructor


    public CompanyUser(String companyName, String companyCountry, String companyCity, String companyMobile, String fax, String bankAccount, String bankAddress, String swift, String companyLogo, String companyInterest, String companyEmail, double totalCash, double availableCash)
    {
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyCity = companyCity;
        this.companyMobile = companyMobile;
        this.fax = fax;
        this.bankAccount = bankAccount;
        this.bankAddress = bankAddress;
        this.swift = swift;
        this.companyLogo = companyLogo;
        this.companyInterest = companyInterest;
        this.companyEmail = companyEmail;
        this.totalCash = totalCash;
        this.availableCash = availableCash;
    } // Param Constructor


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyInterest() {
        return companyInterest;
    }

    public void setCompanyInterest(String companyInterest) {
        this.companyInterest = companyInterest;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }

    public double getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(double availableCash) {
        this.availableCash = availableCash;
    }
} // Class Of CompanyUser
