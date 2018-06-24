package com.example.ibrahim.speedrocket.View.Activites;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;

public class carddetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    static final String[] Months = new String[] { "1", "2",
            "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12" };


    EditText ed_CvvCode , ed_cardNumber;
    String cvvCode , cardNumber;


    // menu
    String firstName , lastName , email="" , interest ,userProfileImage  ;
    boolean login ;
    int userID;
    TextView nav_firstname , nav_lastname , nav_email;
    CircleImageView nav_profileimage ;
    public static  boolean log  ;
    Menu menu;
    NavigationView navigationView;
    //menu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carddetails_navigation_menu);

        //menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        firstName = prefs.getString("firstName", "");//"No name defined" is the default value.
        lastName = prefs.getString("lastName", "");//"No name defined" is the default value.
        email = prefs.getString("email", "");//"No name defined" is the default value.
        userID = prefs.getInt("id", 0);
        interest = prefs.getString("interest", "");
        userProfileImage = prefs.getString("profileImage", "");
        login = prefs.getBoolean("login", false);

        if (login == false)
            navigationView.getMenu().findItem(R.id.nav_logout).setTitle("Login");

        else if (login == true)
            navigationView.getMenu().findItem(R.id.nav_logout).setTitle("Logout");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // menu

        ed_CvvCode = (EditText) findViewById(R.id.ed_cvvCode);
        ed_cardNumber = (EditText) findViewById(R.id.ed_cardNumber);








        // Set years
        ArrayList<String> years = new ArrayList<String>();
       // int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2018; i <= 2028; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.spinner_item, years);

        Spinner spinYear = (Spinner)findViewById(R.id.spinnerYears);
        spinYear.setAdapter(adapter);
        // Set months
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(this,
                R.layout.spinner_item, Months);
        adapterMonths.setDropDownViewResource( R.layout.spinner_item);

        Spinner spinMonths = (Spinner)findViewById(R.id.spinnerMonths);
        spinMonths.setAdapter(adapterMonths);
    } // onCreate function


    public void payCardDetails(View view)
    {
        cvvCode =   ed_CvvCode.getText().toString();
        cardNumber = ed_cardNumber.getText().toString();

        if(cvvCode.length() != 3)
        {
            Toast.makeText(getApplicationContext(),"Cvv Code Wrong"
                    ,Toast.LENGTH_LONG).show();
        } // check if cvvCode Correct
        if(cardNumber.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Enter Card Number"
                    ,Toast.LENGTH_LONG).show();
        } // check if cardNumber Correct


    } // pay Button on CardDetails


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation_menu, menu);

        menu = navigationView.getMenu();

        if(userID != 0)
        {
            menu.findItem(R.id.nav_myProducts).setVisible(true);
            menu.findItem(R.id.nav_cPanal).setVisible(true);
        } // if


        nav_firstname = (TextView) findViewById(R.id.menu_firstname);
        nav_lastname = (TextView) findViewById(R.id.menu_lastname);
        nav_email = (TextView) findViewById(R.id.menu_email);

        nav_profileimage = (CircleImageView) findViewById(R.id.nav_profileimage);

        nav_firstname.setText(firstName);
        nav_lastname.setText(lastName);
        nav_email.setText(email);


        Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                +userProfileImage).
                fit().centerCrop().into(nav_profileimage);
        nav_profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyCompanyProfile.class);
                intent.putExtra("userID",userID);
                startActivity(intent);


            }
        });
        MenuItem searchItem = menu.findItem(R.id.action_settings);

        SearchManager searchManager = (SearchManager) carddetails.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(carddetails.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);

    }  // control search icon on toolbar

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);

        Menu m=nv.getMenu();




        int id = item.getItemId();

        //  for(int i =1 ; i<= numOfCategoryItems ; i++) {



        if (id == R.id.nav_category) {


            Intent intent = new Intent(getApplicationContext(),ProductMenuList.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_myProducts)
        {
            Intent intent = new Intent(getApplicationContext(), MyWinnerProducts.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_offers)
        {
            Intent intent = new Intent(getApplicationContext(),OfferMenuList.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_country) {

            return true;

        } else if (id == R.id.nav_language) {
            Intent intent = new Intent(getApplicationContext(),ChangeLanguage.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_logout) {


            if (m.findItem(R.id.nav_logout).getTitle().toString().equals("Logout")) {
                m.findItem(R.id.nav_logout).setTitle("Login");

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.remove("firstName");
                editor.remove("lastName");
                editor.remove("email");
                editor.remove("id");
                editor.remove("coin");
                editor.remove("profileImage");
                editor.remove("interest");
                editor.putBoolean("login", false);
                editor.apply();

                menu.findItem(R.id.nav_myProducts).setVisible(false);
                menu.findItem(R.id.nav_cPanal).setVisible(false);

                nav_profileimage.setVisibility(View.INVISIBLE);
                nav_firstname.setText("");
                nav_lastname.setText("");
                nav_email.setText("");
                log = false;

            } else {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), NavigationMenu.class);
            startActivity(intent);
            return true;

        }  else if (id == R.id.nav_buycoins) {
            Intent intent = new Intent(getApplicationContext(), BuyCoins.class);
            startActivity(intent);
            return true;

        }
        else if (id == R.id.nav_cPanal) {
            if(userID == 0)
            {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            } // check if login 0 ---> user not login
            else {
                Intent intent = new Intent(getApplicationContext(), MyCompanyProfile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        //  }
        return true;
    }

    public  void menu()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        menu = navigationView.getMenu();



        navigationView.setNavigationItemSelectedListener(this);

    }

} // class of card details
