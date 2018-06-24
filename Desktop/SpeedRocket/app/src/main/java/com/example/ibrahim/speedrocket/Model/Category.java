package com.example.ibrahim.speedrocket.Model;

/**
 * Created by Ibrahim on 4/11/2018.
 */

public class Category
{
    int id , sort , type ,subCategory;
    String en_title , ar_title  , created_at , updated_at ;

    public  Category () {} // zero param Constructor
    public Category(int id, int sort, String en_title, String ar_title, int subCategory, String created_at, String updated_at) {
        this.id = id;
        this.sort = sort;
        this.en_title = en_title;
        this.ar_title = ar_title;
        this.subCategory = subCategory;
        this.created_at = created_at;
        this.updated_at = updated_at;
    } // param Constructor


    public  Category (int id , String en_title)
    {
        this.id = id;
        this.en_title = en_title;
    } // 2 param Constructor

    public  Category (int id , String en_title, int type)
    {
        this.id = id;
        this.en_title = en_title;
        this.type = type;
    } // 3 param Constructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
} // class of Category
