package com.example.ibrahim.speedrocket.View.Activites;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;

public class PaymentScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {




    String kind , paymentMethod  , shipping_name="" , shipping_address="" , shipping_phone="" ;
    int price , productId;

    RadioGroup radioGroupPaymentMethods ;
    RadioButton radioButtonCashOnDelivery ;
    LinearLayout layoutCash ;

    private Handler handler;
    private ProgressDialog progress;

    TextView txt_price , txt_shipping_name  , txt_shipping_phone , txt_shipping_address ;

    Dialog purchaseDialog ;

    // menu
    String firstName , lastName , email="" , interest ,userProfileImage;
    boolean login ;
    int userID ;
    TextView nav_firstname , nav_lastname , nav_email;
    CircleImageView nav_profileimage ;
    public static  boolean log  ;
    Menu menu;
    NavigationView navigationView;
    //menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_screen_navigation_menu);

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


        layoutCash = (LinearLayout) findViewById(R.id.layoutCash);
        radioButtonCashOnDelivery = (RadioButton) findViewById(R.id.rbCash);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();


        if(b!=null)
        {
            kind=(String) b.get("kind");
            price=(int) b.get("price");
            productId=(int) b.get("productId");
        }

        if(kind.equals("product"))
        {
            layoutCash.setVisibility(View.VISIBLE);
            radioButtonCashOnDelivery.setVisibility(View.VISIBLE);
        } // if kind product show cashOnDelivery
        else if (kind.equals("coin"))
        {
            layoutCash.setVisibility(View.GONE);
            radioButtonCashOnDelivery.setVisibility(View.GONE);
        } // if kind product disappear cashOnDelivery

        radioGroupPaymentMethods = (RadioGroup) findViewById(R.id.rGPaymentMethods);

        radioGroupPaymentMethods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i)
                {
                    case R.id.rbVisa :
                        paymentMethod = "card";
                        break;
                    case R.id.rbAmericanExpress :
                        paymentMethod = "card";
                        break;
                    case R.id.rbPaypal :
                        paymentMethod = "card";
                        break;
                    case R.id.rbMasterCard :
                        paymentMethod = "card";
                        break;
                    case R.id.rbCash :
                        paymentMethod = "cash";
                        break;
                }
            }
        }); // radioGroupPaymentMethod


        purchaseDialog = new Dialog(this);
        purchaseDialog.setContentView(R.layout.dialog_continue_buy);

        txt_price = (TextView) findViewById(R.id.pricePayment);
        txt_price.setText(price+" EGP");

        txt_shipping_name = (TextView) findViewById(R.id.shipping_name);
        txt_shipping_phone = (TextView) findViewById(R.id.shipping_mobile);
        txt_shipping_address = (TextView) findViewById(R.id.shiping_address);


    } // onCreate function

    public void paymentPlaceOrder(View view)
    {

        shipping_name = txt_shipping_name.getText().toString();
        shipping_phone = txt_shipping_phone.getText().toString();
        shipping_address = txt_shipping_address.getText().toString();

        if(!shipping_name.equals("") && !shipping_phone.equals("") && !shipping_address.equals("")) {


            if (paymentMethod.equals("card")) {
                Intent intent = new Intent(getApplicationContext(), carddetails.class);
                startActivity(intent);
            } // id user choose to buy with card

            else if (paymentMethod.equals("cash")) {
                purchaseDialog.show();
            } // if user Choose to buy when delivery
        } // if

        else
        {
            Toast.makeText(getApplicationContext(),"Complete Fields",Toast.LENGTH_LONG).show();
        } // else

    } // button paymentPlaceOrder

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

        SearchManager searchManager = (SearchManager) PaymentScreen.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(PaymentScreen.this.getComponentName()));
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

    public void continuePurchase(View view)
    {



            progress = new ProgressDialog(this);
            progress.setTitle("Please Wait");
            progress.setMessage("Loading..");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    progress.dismiss();
                    super.handleMessage(msg);
                }

            };
            progress.show();
            new Thread() {
                public void run() {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(100, TimeUnit.SECONDS)
                            .readTimeout(100, TimeUnit.SECONDS).build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://speed-rocket.com/api/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .build();

                    UserApi userApi = retrofit.create(UserApi.class);
                    Call<ResultModel> cashOnDeliveryconnection =
                            userApi.orderCashOnDelivery(productId, userID, price, 1, shipping_name, shipping_phone, shipping_address);
                    cashOnDeliveryconnection.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                            try {

                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MyWinnerProducts.class);
                                startActivity(intent);

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Connection faild\n", Toast.LENGTH_LONG).show();
                            progress.dismiss();

                        }
                    });
                    //Retrofit


                }

            }.start();




    } // continue Button


    public void cancelPurchase(View view)
    {
        purchaseDialog.cancel();
    } // cancel button



} // class of PaymentScreen
