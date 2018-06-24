package com.example.ibrahim.speedrocket.Model;

import android.view.Menu;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 4/11/2018.
 */

public class NavMenuClass
{
    Menu menu;
    ArrayList items;

    public NavMenuClass(Menu menu,ArrayList items){

        this.items = items;
        this.menu = menu;

    }

    public Menu getMenu(){
        return menu;
    }

    public ArrayList getItems(){
        return items;
    }
}
