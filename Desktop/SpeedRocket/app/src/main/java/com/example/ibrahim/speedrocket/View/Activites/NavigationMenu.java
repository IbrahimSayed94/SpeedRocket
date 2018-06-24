package com.example.ibrahim.speedrocket.View.Activites;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.PostsAdapter;
import com.example.ibrahim.speedrocket.Control.ProductAdapter;
import com.example.ibrahim.speedrocket.Model.Category;
import com.example.ibrahim.speedrocket.Model.CompanyUser;
import com.example.ibrahim.speedrocket.Model.NavMenuClass;
import com.example.ibrahim.speedrocket.Model.Offer;
import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.Post;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.Model.UserInOffer;
import com.example.ibrahim.speedrocket.Model.UserOnline;
import com.example.ibrahim.speedrocket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class NavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    private ProgressDialog progress;
    private Handler handler;

    Menu menu;
    NavigationView navigationView;
    private List<Offer> offerList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostsAdapter mAdapter;

    public  static  int numOfCategoryItems ;
    boolean flag = true ;
    int userID;
    int profile_im = (R.drawable.profile_image);
    int post_im = (R.drawable.post_image);
    int country_im = (R.drawable.f1);


    SwipeRefreshLayout mSwipeRefreshLayout;
    int idFromLogin;
   public static  boolean log  ;
    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F); // make animation on button

    CircleImageView nav_profileimage , profileImage ;
    Button start ;



    TextView nav_firstname , nav_lastname , nav_email;

    Offer offer;

    String firstName , lastName , email ,gender , language , interest ,userProfileImage  ;

    String o_firstName , o_lastName , o_description , o_image ,o_createdat , o_companyName
            ,o_logo , o_time;
    int o_srcoin , o_view , o_userid , o_id ,o_companyId ;
    double o_price ;

    List <Offer> offerList ;


    int offerId=0 ;
    List <UserInOffer> userInOfferList ;

    Category category;
    List<Category> categoryList;

    int categoryId ;
    String categoryTitle ;

    String chosenInterest ="";

    boolean login ;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);



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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */

                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // menu

       /* Toast.makeText(getApplicationContext() , "firstname : "+firstName
        +" lastName : "+lastName+"  email : "+email , Toast.LENGTH_LONG).show();*/

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            chosenInterest=(String)b.get("chosenInterest");
        }






        // start recycle view code of item post_list
        recyclerView = (RecyclerView) findViewById(R.id.item_postlist);

        mAdapter = new PostsAdapter(offerList1, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (interest.equals(""))
            preparePostData();

        else if (!chosenInterest.equals(""))
            getOfferWithOfferChosen(chosenInterest);
        else
            preparePostDataToUserInterest();


        Log.e("interest", interest);


        // end recycle view code of item post_list


       /* DatabaseReference dbNode =
                FirebaseDatabase.getInstance().getReference().getRoot().child("UserInOffer");
        dbNode.setValue(null);*/


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               refreshItems();
            }
        });




    }
    public void clear() {
        int size = this.offerList1.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.offerList1.remove(0);
            }

            mAdapter.notifyDataSetChanged();
        }
    }
    void refreshItems() {
        // Load items
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            if (interest.equals(""))
                preparePostData();

            else if (!chosenInterest.equals(""))
                getOfferWithOfferChosen(chosenInterest);

            else
                preparePostDataToUserInterest();
        } // check if conection found
        else
        {
            Toast.makeText(getApplicationContext(),"Connection Faild"
                    ,Toast.LENGTH_LONG).show();

        }

        onItemsLoadComplete();

        // Load complete

    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
    private void preparePostData()
    {
        clear();
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                progress.dismiss();
                super.handleMessage(msg);
            }

        };

        progress.show();


        new Thread()
        {
            public void run()
            {
        //Retrofit
                //Retrofit
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5,TimeUnit.MINUTES).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://speed-rocket.com/api/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();

        final UserApi userApi = retrofit.create(UserApi.class);

        final Call<ResultModel> getOfferConnection = userApi.getOffers();

        getOfferConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                try
                {


                    offerList = response.body().getOffers();
                     for(int i =0 ; i< offerList.size(); i++)
                     {
                         o_id=offerList.get(i).getId();
                    o_userid=offerList.get(i).getUserId();
                    o_price=offerList.get(i).getPrice();
                    o_view=offerList.get(i).getView();
                    o_srcoin=offerList.get(i).getSrcoin();
                    o_description=offerList.get(i).getEn_description();
                    o_image=offerList.get(i).getImage();
                    o_logo=offerList.get(i).getCompanyLogo();
                    o_time = offerList.get(i).getTime();
                    o_companyId = offerList.get(i).getCompanyId();




                    o_companyName = offerList.get(i).getCompanyName();


                    offer = new Offer(o_id,0,o_userid,0,0,o_srcoin,
                            o_view,o_price,"","","",o_description
                            ,"","",post_im,o_image,o_createdat,o_companyName,o_logo,
                            o_time,o_companyId);


                    offerList1.add(offer);
                /*    // firebase
                         String sId = String.valueOf(o_id);
                      DatabaseReference mDatabase;
                      mDatabase = FirebaseDatabase.getInstance().getReference();
                      mDatabase.child("offers").child(sId).setValue(offer);

                         //firebase*/



                         mAdapter.notifyDataSetChanged();
                      }// or loop


                  /*  Toast.makeText(getApplicationContext(),"Connection Success\n"
                                    +"userId : "+o_userid+"\n"
                                    +"Price : "+o_price+"\n"
                                    +"Views: "+o_view+"\n"
                                    +"srcoin : "+o_srcoin+"\n"
                                    +"description : "+o_description+"\n",

                            Toast.LENGTH_LONG).show();*/
                    progress.dismiss();

                } // try
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                    "Exception Home Page\n"+e.toString(),
                            Toast.LENGTH_LONG).show();
                    progress.dismiss();

                } // catch

                if(response.isSuccessful())
                {
                 //   getUsersInOffer();
                }

                 } // onResponse

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

               /* Toast.makeText(getApplicationContext(),"Connection Faild",
                        Toast.LENGTH_LONG).show();*/

                Log.e("OfferFaild",t.toString());
                progress.dismiss();

            } // on Failure
        });

// Retrofit

            }

        }.start();





    } // function preparepostdata


    private  void  preparePostDataToUserInterest ()
    {

        clear();
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                progress.dismiss();
                super.handleMessage(msg);
            }

        };

        progress.show();


        new Thread()
        {
            public void run()
            {
                //Retrofit
                //Retrofit
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5,TimeUnit.MINUTES).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://speed-rocket.com/api/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();

                final UserApi userApi = retrofit.create(UserApi.class);

                final Call<ResultModel> getOfferConnection = userApi.getOffersToUserInterest(interest);

                getOfferConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                        try
                        {
                            offerList = response.body().getOffers();
                            for(int i =0 ; i< offerList.size(); i++)
                            {
                                o_id=offerList.get(i).getId();
                                o_userid=offerList.get(i).getUserId();
                                o_price=offerList.get(i).getPrice();
                                o_view=offerList.get(i).getView();
                                o_srcoin=offerList.get(i).getSrcoin();
                                o_description=offerList.get(i).getEn_description();
                                o_image=offerList.get(i).getImage();
                                o_logo=offerList.get(i).getCompanyLogo();
                                o_time = offerList.get(i).getTime();
                                o_companyId = offerList.get(i).getCompanyId();




                                o_companyName = offerList.get(i).getCompanyName();


                                offer = new Offer(o_id,0,o_userid,0,0,o_srcoin,
                                        o_view,o_price,"","","",o_description
                                        ,"","",post_im,o_image,o_createdat,o_companyName,o_logo,
                                        o_time,o_companyId);


                                offerList1.add(offer);
                /*    // firebase
                         String sId = String.valueOf(o_id);
                      DatabaseReference mDatabase;
                      mDatabase = FirebaseDatabase.getInstance().getReference();
                      mDatabase.child("offers").child(sId).setValue(offer);

                         //firebase*/



                                mAdapter.notifyDataSetChanged();
                            }// or loop


                  /*  Toast.makeText(getApplicationContext(),"Connection Success\n"
                                    +"userId : "+o_userid+"\n"
                                    +"Price : "+o_price+"\n"
                                    +"Views: "+o_view+"\n"
                                    +"srcoin : "+o_srcoin+"\n"
                                    +"description : "+o_description+"\n",

                            Toast.LENGTH_LONG).show();*/
                            progress.dismiss();

                        } // try
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                            "Exception Home Page\n"+e.toString(),
                                    Toast.LENGTH_LONG).show();
                            progress.dismiss();

                        } // catch

                        if(response.isSuccessful())
                        {
                            //   getUsersInOffer();
                        }

                    } // onResponse

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {

               /* Toast.makeText(getApplicationContext(),"Connection Faild",
                        Toast.LENGTH_LONG).show();*/

                        Log.e("OfferFaild",t.toString());
                        progress.dismiss();

                    } // on Failure
                });

// Retrofit

            }

        }.start();



    } // function preparePostDataToUserInterest

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

        SearchManager searchManager = (SearchManager) NavigationMenu.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(NavigationMenu.this.getComponentName()));
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

    } // function fill gategory on menu

    private  void getUsersInOffer()
    {


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

        final Call<ResultModel> getOfferInConnection = userApi.getUsersByOffers();

        getOfferInConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                try
                {

                    userInOfferList = response.body().getUsers();
                    for(int i =0 ; i< userInOfferList.size(); i++)
                    {
                        int id , userId , offersId , srCoin , coins ;
                        String firstName , lastName , email ;

                        id=userInOfferList.get(i).getId();
                        userId=userInOfferList.get(i).getUserId();
                        offersId=userInOfferList.get(i).getOfferId();
                        srCoin=userInOfferList.get(i).getSrCoin();
                        coins=userInOfferList.get(i).getCoins();
                        firstName=userInOfferList.get(i).getFirstName();
                        lastName=userInOfferList.get(i).getLastName();
                        email=userInOfferList.get(i).getEmail();


                        UserInOffer userInOffer =
                                new UserInOffer(id,userId,offersId,srCoin,coins,firstName
                                ,lastName,email,0,0,"","",0);

                       // userInOfferList.add(userInOffer);


                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        String key =  mDatabase.push().getKey();
                        mDatabase.child("UserInOffer").child(key).setValue(userInOffer);


                      //  if(response.isSuccessful() && i==userInOfferList.size()-1)break;
                         /*Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                        "\nUserInOffer"+userInOfferList.size(),
                                Toast.LENGTH_LONG).show();*/


                    }// for loop




                } // try
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                    "Exception UserInOffer\n"+e.toString(),
                            Toast.LENGTH_LONG).show();

                } // catch
            } // onResponse

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Connection Faild",
                        Toast.LENGTH_LONG).show();

            } // on Failure
        });




// Retrofit
    }// function getUsersInOffer

    private  void getOfferWithOfferChosen (final String chosenInterest)
    {
        //Retrofit
        clear();
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                progress.dismiss();
                super.handleMessage(msg);
            }

        };

        progress.show();


        new Thread()
        {
            public void run()
            {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100,TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://speed-rocket.com/api/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();

                UserApi userApi = retrofit.create(UserApi.class);
                Call<ResultModel> chooseInterestConnection =
                        userApi.getOffersToUserInterest(chosenInterest);
                chooseInterestConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                        try
                        {
                            offerList = response.body().getOffers();
                            for(int i =0 ; i< offerList.size(); i++)
                            {
                                o_id=offerList.get(i).getId();
                                o_userid=offerList.get(i).getUserId();
                                o_price=offerList.get(i).getPrice();
                                o_view=offerList.get(i).getView();
                                o_srcoin=offerList.get(i).getSrcoin();
                                o_description=offerList.get(i).getEn_description();
                                o_image=offerList.get(i).getImage();
                                o_logo=offerList.get(i).getCompanyLogo();
                                o_time = offerList.get(i).getTime();
                                o_companyId = offerList.get(i).getCompanyId();




                                o_companyName = offerList.get(i).getCompanyName();


                                offer = new Offer(o_id,0,o_userid,0,0,o_srcoin,
                                        o_view,o_price,"","","",o_description
                                        ,"","",post_im,o_image,o_createdat,o_companyName,o_logo,
                                        o_time,o_companyId);


                                offerList1.add(offer);
                                // firebase
                                String sId = String.valueOf(o_id);
                                DatabaseReference mDatabase;
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("offers").child(sId).setValue(offer);

                                //firebase



                                mAdapter.notifyDataSetChanged();
                            }// or loop


                            progress.dismiss();

                        } // try
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                            "Exception Home Page\n"+e.toString(),
                                    Toast.LENGTH_LONG).show();
                            progress.dismiss();

                        } // catch
                    }

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection faild\n" , Toast.LENGTH_LONG).show();

                    }
                });

            }

        }.start();
        //Retrofit
    }





}
