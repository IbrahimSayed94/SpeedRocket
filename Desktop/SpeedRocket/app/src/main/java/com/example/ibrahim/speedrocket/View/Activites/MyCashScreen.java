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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.BankAccountsAdapter;
import com.example.ibrahim.speedrocket.Model.BankAccount;
import com.example.ibrahim.speedrocket.Model.Category;
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

public class MyCashScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    //menu
    String firstName , lastName , email="" , interest ,userProfileImage  ;
    boolean login ;
    int userID;
    TextView nav_firstname , nav_lastname , nav_email;
    CircleImageView nav_profileimage ;
    public static  boolean log  ;
    Menu menu;
    NavigationView navigationView;

    int netCash=0 , pendingCash=0 , totalCash=0 ;
    //menu


     Dialog  myBankAccountsDialog ;

    private ProgressDialog progress;
    private Handler handler;

    List<BankAccount> bankList;

    BankAccountsAdapter mAdapter ;
    RecyclerView recyclerView;
    List <BankAccount> bankAccountList = new ArrayList<>();
    BankAccount bankAccount;


    TextView txt_totalCash , txt_netCash , txt_pendingCash , txt_amountCash;

    int swift , bankId , bankAcount;
    String bankName , bankAddress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cash_navigation_menu);

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


        myBankAccountsDialog = new Dialog(this);
        myBankAccountsDialog.setContentView(R.layout.dialog_with_my_bank_accounts);

        txt_amountCash = (TextView) myBankAccountsDialog.findViewById(R.id.txt_amountOfCash);


        recyclerView = (RecyclerView) myBankAccountsDialog.findViewById(R.id.recycleViewBankAccounts);


        mAdapter = new BankAccountsAdapter(getApplicationContext() , bankAccountList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(llm);


        txt_totalCash = (TextView) findViewById(R.id.totalCash);
        txt_netCash = (TextView) findViewById(R.id.netCash);
        txt_pendingCash = (TextView) findViewById(R.id.pendingCash);

        myCashAll();

    } // onCreate function


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

        SearchManager searchManager = (SearchManager) MyCashScreen.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MyCashScreen.this.getComponentName()));
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

    public void addBankAccountButton(View view)
    {
       Intent intent = new Intent(getApplicationContext(),AddBankAccount.class);
       startActivity(intent);
    } // addBankAccountButton function
    public void sendRequestForCash(View view)
    {

        int amount =Integer.parseInt(txt_amountCash.getText().toString());
        Log.i("QP","amount : "+amount +"\n net : "+ netCash);
        if(netCash < amount)
        {
            Toast.makeText(getApplicationContext(),"max amount can withdrawn : "+netCash,
                    Toast.LENGTH_LONG).show();
        }
    } // send on dialog
    public void cancelMyCompanyList(View view)
    {
        myBankAccountsDialog.cancel();
    } // cancel dialog

    public void requestCashButton(View view)
    {
        myBankAccountsDialog.show();
        getBankAccounts();

    } // requestCashButton function


    public  void getBankAccounts ()
    {
        bankAccountList.clear();
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

                final UserApi userApi = retrofit.create(UserApi.class);

                final Call<ResultModel> getBankAccountsWithUserIdConnection = userApi.getBankAccountWithUserId(userID);

                getBankAccountsWithUserIdConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                        try {

                            bankList = response.body().getBanks();

                            for(int i =0 ; i < bankList.size() ; i++)
                            {
                                swift = bankList.get(i).getSwift();
                                bankId = bankList.get(i).getId();
                                bankAddress = bankList.get(i).getBankAddress();
                                bankName = bankList.get(i).getName();
                                bankAcount = bankList.get(i).getBankAccount();

                                bankAccount = new BankAccount(swift,bankId,bankAcount,bankAddress,bankName);

                                bankAccountList.add(bankAccount);
                                mAdapter.notifyDataSetChanged();

                            }

                            progress.dismiss();
                        } // try

                        catch (Exception e) {
                   /* Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                    "Exception Navigation menu\n"+e.toString(),
                            Toast.LENGTH_LONG).show();*/
                            progress.dismiss();

                        } // catch
                    } // onResponse

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Connection Faild",
                                Toast.LENGTH_LONG).show();
                        progress.dismiss();

                    } // on Failure
                });


            } }.start();

// Retrofit
    } // function of getBankAccounts


    public  void myCashAll()
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
        Call<ResultModel> getMyCashConnection =
                userApi.getMyCash(userID);

                getMyCashConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                try {

                    netCash = response.body().getNet();
                    pendingCash = response.body().getPending();

                    totalCash = netCash + pendingCash;
                    txt_totalCash.setText(totalCash+" $");
                    txt_netCash.setText(netCash+" $");
                    txt_pendingCash.setText(pendingCash+" $");
                    progress.dismiss();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Connection Error\n"+e.toString()
                            ,Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n"
                        , Toast.LENGTH_LONG).show();
                progress.dismiss();

            }
        });

            } }.start();
        //Retrofit
    }

    public  void myCashDay()
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
                Call<ResultModel> mycashConnection =
                        userApi.getMyCashDay(userID);

                mycashConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                        try {

                            netCash = response.body().getNet();
                            pendingCash = response.body().getPending();

                            totalCash = netCash + pendingCash;
                            txt_totalCash.setText(totalCash+" $");
                            txt_netCash.setText(netCash+" $");
                            txt_pendingCash.setText(pendingCash+" $");
                            progress.dismiss();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Connection Error\n"+e.toString()
                                    ,Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection faild\n"
                                , Toast.LENGTH_LONG).show();
                        progress.dismiss();

                    }
                });

            } }.start();
        //Retrofit
    }
    public  void myCashMonth()
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
                Call<ResultModel> mycashConnection =
                        userApi.getMyCashMonth(userID);

                mycashConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                        try {

                            netCash = response.body().getNet();
                            pendingCash = response.body().getPending();

                            totalCash = netCash + pendingCash;
                            txt_totalCash.setText(totalCash+" $");
                            txt_netCash.setText(netCash+" $");
                            txt_pendingCash.setText(pendingCash+" $");
                            progress.dismiss();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Connection Error\n"+e.toString()
                                    ,Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection faild\n"
                                , Toast.LENGTH_LONG).show();
                        progress.dismiss();

                    }
                });

            } }.start();
        //Retrofit
    }
    public void dayReport(View view)
    {
         myCashDay();
    } // button day report

    public void monthReport(View view)
    {
          myCashMonth();
    } // button month report

    public void allReport(View view)
    {
        myCashAll();
    } //  button all report


} // class of MyCashScreen
