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
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.CompanyAdapter;
import com.example.ibrahim.speedrocket.Control.MyCompanyAdapter;
import com.example.ibrahim.speedrocket.Model.Company;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

public class MyCompanyProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    String firstName1 , lastName1 , email1 ,userProfileImage ;
    int id  , userID , userID1 , companyId;

    TextView fName ;
    CircleImageView myProfileImage ;

    Dialog contactUsDialog ;

    EditText ed_contactName , ed_contactEmail , ed_contactMessage ;

    String contactName="" , contactEmail="" , contactMessage="",companyName , companyLogo ;

    // menu
    String firstName , lastName , email="" , interest   ;
    boolean login ;
    TextView nav_firstname , nav_lastname , nav_email;
    CircleImageView nav_profileimage ;
    public static  boolean log  ;
    Menu menu;
    NavigationView navigationView;
    //menu


    private RecyclerView recyclerView;
    private MyCompanyAdapter mAdapter;
    Company company;
    private List<Company> companyList1 = new ArrayList<>();

    private ProgressDialog progress;
    private Handler handler;
    List<Company> CList;

    Dialog companyListDialog;

    CardView cardProfile , cardAddOffer , cardAddCompany , cardAddProduct , cardFinance , cardContactUs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_company_profile_navigation_menu);


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

        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        firstName1 = prefs1.getString("firstName", "");//"No name defined" is the default value.
        lastName1 = prefs1.getString("lastName", "");//"No name defined" is the default value.
        email1 = prefs1.getString("email", "");//"No name defined" is the default value.
        userID1=prefs1.getInt("id",0);
        userProfileImage = prefs1.getString("profileImage","");



        companyListDialog = new Dialog(this);
        companyListDialog.setContentView(R.layout.dialogue_with_my_company);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            userID=(int)b.get("userID");
        }


        fName = (TextView) findViewById(R.id.myName);
        myProfileImage = (CircleImageView) findViewById(R.id.my_profile_image);

        fName.setText(firstName1);
        Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                +userProfileImage).
                fit().centerCrop().into(myProfileImage);


        contactUsDialog = new Dialog(this); // Context, this, etc.
        contactUsDialog.setContentView(R.layout.dialogu_contactus);



        ed_contactName = (EditText) contactUsDialog.findViewById(R.id.contactName);
        ed_contactEmail = (EditText) contactUsDialog.findViewById(R.id.contactEmail);
        ed_contactMessage = (EditText) contactUsDialog.findViewById(R.id.contactMessage);

        recyclerView = (RecyclerView) companyListDialog.findViewById(R.id.recycleViewCompany);

        mAdapter = new MyCompanyAdapter(companyList1, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(llm);

        cardProfile = (CardView) findViewById(R.id.cardViewMyProfile);
        cardAddCompany = (CardView) findViewById(R.id.cardViewAddCompany);
        cardAddOffer = (CardView) findViewById(R.id.cardViewAddOffer);
        cardAddProduct = (CardView) findViewById(R.id.cardViewAddProduct);
        cardFinance = (CardView) findViewById(R.id.cardViewFinance);
        cardContactUs = (CardView) findViewById(R.id.cardViewContactUs);

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileAccount.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        }); // cardProfile

        cardAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),CompanyScreen.class);
                startActivity(intent);
            }
        }); // cardAddCompany

        cardAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCompanies(1);
            }
        }); // cardAddOffer

        cardAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCompanies(0);
            }
        }); // cardAddProduct

        cardFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyCashScreen.class);
                startActivity(intent);
            }
        }); // cardFinance

        cardContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUsDialog.show();
            }
        }); // cardContactUs



    } // onCreate function

    public  void  getCompanies(final int check)
    {

        companyList1.clear();
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
                //Retrofit
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100,TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://speed-rocket.com/api/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();

                final UserApi userApi = retrofit.create(UserApi.class);

                final Call<ResultModel> getCompanyConnection = userApi.getCompany(userID1);

                getCompanyConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                        try
                        {

                            Log.i("QP",userID1+"");
                            CList = response.body().getCompanies();

                            for(int i = 0 ; i <CList.size() ; i++)
                            {
                                companyId = CList.get(i).getId();
                                companyName = CList.get(i).getEn_name();
                                companyLogo = CList.get(i).getLogo();

                                company = new Company(companyId,companyName,companyLogo,check);

                                companyList1.add(company);
                                mAdapter.notifyDataSetChanged();

                            }
                            companyListDialog.show();
                            progress.dismiss();
                        } // try
                        catch (Exception e)
                        {
                   /* Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                    "Exception Navigation menu\n"+e.toString(),
                            Toast.LENGTH_LONG).show();*/progress.dismiss();

                        } // catch
                    } // onResponse

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {

                        Toast.makeText(getApplicationContext(),"Connection Faild",
                                Toast.LENGTH_LONG).show();
                        progress.dismiss();

                    } // on Failure
                });


            }

        }.start();

// Retrofit
    } // finction get Companies

/*
    public void cardViewMyProfile(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ProfileAccount.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    } // cardViewMyProfile Function

    public void cardViewAddProduct(View view)
    {
        getCompanies(0);

    } // cardViewAddProduct Function

    public void cardViewAddOffer(View view)
    {
       getCompanies(1);

     *//*   Intent intent = new Intent(getApplicationContext(),AddOffer.class);
        startActivity(intent);*//*
    } // cardViewAddOffer Function


    public void cardViewAddCompany(View view)
    {

        Intent intent = new Intent(getApplicationContext(),CompanyScreen.class);
        startActivity(intent);
    } // function cardViewAddCompany

    public void cardViewFinance(View view)
    {
             Intent intent = new Intent(getApplicationContext(),MyCashScreen.class);
             startActivity(intent);
    } // function cardViewFinance

    public void cardViewContactUs(View view)
    {
        contactUsDialog.show();
    }// function cardViewContactUs*/

    public void cancelContactUsDialog(View view)
    {
        contactUsDialog.cancel();
    }// function cancelContactUsDialog

    public void sendMessage(View view)
    {
        contactName = ed_contactName.getText().toString();
        contactEmail = ed_contactEmail.getText().toString();
        contactMessage = ed_contactMessage.getText().toString();

        if(contactName.equals("") || contactEmail.equals("") || contactMessage.equals(""))
            Toast.makeText(getApplicationContext(),"Fields Empty",Toast.LENGTH_LONG).show();


        else {

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

                    //Retrofit
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(100, TimeUnit.SECONDS)
                            .readTimeout(100, TimeUnit.SECONDS).build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://speed-rocket.com/api/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .build();

                    UserApi userApi = retrofit.create(UserApi.class);
                    Call<ResultModel> contactUsConnection =
                            userApi.contactUs(contactName, contactEmail, contactMessage);
                    contactUsConnection.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                            try {
                   /* Toast.makeText(getApplicationContext(), "Done"
                        , Toast.LENGTH_LONG).show(); */
                                Log.i("QP", "Done");
                                progress.dismiss();


                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "exception" + (e.toString()), Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Connection faild\n", Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }
                    });

                }
            }.start();

        }//else
        //Retrofit

    }// function cancelContactUsDialog

    public void cancelMyCompanyList(View view)
    {
        companyListDialog.cancel();
    } // functtion cancelMyCompanyList


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

        SearchManager searchManager = (SearchManager) MyCompanyProfile.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MyCompanyProfile.this.getComponentName()));
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
}
