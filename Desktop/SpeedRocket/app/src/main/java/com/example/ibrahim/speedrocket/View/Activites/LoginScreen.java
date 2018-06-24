package com.example.ibrahim.speedrocket.View.Activites;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private Handler handler;
    private ProgressDialog progress;
    TextView forgetpassword;
    boolean log = true;
    TextView nav_firstname , nav_lastname , nav_email , welcomeName;
    CircleImageView nav_profileimage , profileImage ;

    int id  , userID;
    String firstName , lastName , menu_email ;

    String email = "", password = "";

    Button back_loginbar, registerPersonal, registerCompany, login;
    EditText ed_email, ed_password;

    int user_id , user_coins;
    String user_firstName , user_lastName , user_email , user_password , user_interest
            ,user_language , user_mobile , user_gender , user_joinAt , user_type
            ,user_companyName , user_companyCity , user_companyCountry
            , user_companyMobile , user_Image , user_city , user_country;


    Dialog dialog ;
    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F); // make animation on button

    Button facebook, twitter, googleplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_navigation_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        firstName = prefs.getString("firstName", "");//"No name defined" is the default value.
        lastName = prefs.getString("lastName", "");//"No name defined" is the default value.
        menu_email = prefs.getString("email", "");//"No name defined" is the default value.
        userID=prefs.getInt("id",0);

        forgetpassword = (TextView) findViewById(R.id.txt_forgetpassword);

        facebook = (Button) findViewById(R.id.facebook);
        twitter = (Button) findViewById(R.id.twitter);
        googleplus = (Button) findViewById(R.id.googleplus);
        login = (Button) findViewById(R.id.login_btn);




        dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.welcomeloginscreen);
        dialog.setTitle("welcomeloginscreen");


        welcomeName = (TextView)dialog.findViewById(R.id.namaOfWelcomeMessage);
        //  back_loginbar = (Button) findViewById(R.id.back_loginbar);


        registerPersonal = (Button) findViewById(R.id.bt_registerAsPersonal);


        ed_email = (EditText) findViewById(R.id.log_email);
        ed_password = (EditText) findViewById(R.id.log_paswword);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            email = (String) bundle.get("email");
            password = (String) bundle.get("password");
            ed_email.setText(email);
            ed_password.setText(password);
        }


      /*  Toast.makeText(getApplicationContext(), "email : " + email + " pass : " + password,
                Toast.LENGTH_LONG).show();*/



    }


    public void twitterclick(View view) {
        view.startAnimation(buttonClick);
    } // twitter click function

    public void googleplusclick(View view) {
        view.startAnimation(buttonClick);
    } // googleplus click function

    public void facebookclick(View view) {
        view.startAnimation(buttonClick);
    }// facebook click function


    public void bt_RegisterASPersonal(View view) {
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);
    } // function register as personal



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),NavigationMenu.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        nav_firstname = (TextView) findViewById(R.id.menu_firstname);
        nav_lastname = (TextView) findViewById(R.id.menu_lastname);
        nav_email = (TextView) findViewById(R.id.menu_email);

        nav_profileimage = (CircleImageView) findViewById(R.id.nav_profileimage);

        nav_firstname.setText(firstName);
        nav_lastname.setText(lastName);
        nav_email.setText(menu_email);


        nav_profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileAccount.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
        return true;
    }

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

        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);

        Menu m = nv.getMenu();

        int id = item.getItemId();

        if (id == R.id.nav_offers) {
            // Handle the camera action

            return true;

        } else if (id == R.id.nav_country) {
            return true;

        } else if (id == R.id.nav_language) {
            Intent intent = new Intent(getApplicationContext(),ChangeLanguage.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_logout) {
            if(log == true)
            {
                m.findItem(R.id.nav_logout).setTitle("Login");

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.remove("firstName");
                editor.remove("lastName");
                editor.remove("email");
                editor.apply();

                nav_firstname.setText("");
                nav_lastname.setText("");
                nav_email.setText("");
                log = false ;

            }
            else
            {
                m.findItem(R.id.nav_logout).setTitle("Logout");
                log = true ;
                Intent intent = new Intent(getApplicationContext() , LoginScreen.class );
                startActivity(intent);


            }
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), NavigationMenu.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    } // validation of email
    public void button_login(View view) {


        email = ed_email.getText().toString();
        password = ed_password.getText().toString();

        if(!isEmailValid(email))
         Toast.makeText(getApplicationContext(),"Email Not Vaild",
                 Toast.LENGTH_LONG).show();

       else if(!email.equals("") && !password.equals(""))
        {
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
                    // Write Your Downloading logic here
                    // at the end write this.
                    //Retrofit
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
                    Call<ResultModel> loginuserconnection =
                            userApi.loginuser(email, password);

                    loginuserconnection.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                            try {


                                List <PersonalUser> data = response.body().getData();
                                user_id = data.get(0).getId();
                                user_firstName=data.get(0).getFirstName();
                                user_lastName=data.get(0).getLastName();
                                user_email=data.get(0).getEmail();
                                user_gender=data.get(0).getGender();
                                user_password=data.get(0).getPassword();
                                user_interest=data.get(0).getInterest();
                                user_language=data.get(0).getLanguage();
                                user_mobile=data.get(0).getMobile();
                                user_joinAt=data.get(0).getCreated_at();
                                user_type=data.get(0).getType();
                                user_companyName=data.get(0).getCompanyName();
                                user_companyCity=data.get(0).getCompanyCity();
                                user_companyCountry=data.get(0).getCompanyCountry();
                                user_companyMobile=data.get(0).getCompanyMobile();
                                user_coins = data.get(0).getSrCoin();
                                user_Image = data.get(0).getImage();
                                user_city = data.get(0).getCity();
                                user_country = data.get(0).getCountry();

                                user_id=data.get(0).getId();
                               /* Toast.makeText(getApplicationContext(), "Connection Success" +
                                        "  email : " + email + " password : " + password +" message :"+
                                        response.body().getMessage()+"data : "+user_joinAt+" "+user_firstName+" "+user_lastName,Toast.LENGTH_LONG).show();*/



                                if(response.body().getMessage().equals("true"))
                                {

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("firstName",user_firstName );
                                    editor.putString("lastName",user_lastName );
                                    editor.putString("email",user_email );
                                    editor.putString("gender",user_gender );
                                    editor.putString("language",user_language );
                                    editor.putString("interest",user_interest );
                                    editor.putString("join",user_joinAt );
                                    editor.putString("mobile",user_mobile );
                                    editor.putString("city",user_city );
                                    editor.putString("country",user_country );
                                    editor.putString("type",user_type);
                                    editor.putString("companyName",user_companyName);
                                    editor.putString("companyCity",user_companyCity);
                                    editor.putString("companyCountry",user_companyCountry);
                                    editor.putString("companyMobile",user_companyMobile);
                                    editor.putString("profileImage",user_Image);
                                    editor.putInt("id",user_id);
                                    editor.putInt("coins",user_coins);
                                    editor.putBoolean("login",true);
                                    editor.apply();

                                    dialog.show();
                                   int SPLASH_DISPLAY_LENGTH = 3000;
                                    new Handler().postDelayed(new Runnable(){
                                        @Override
                                        public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                                            Intent intent = new Intent(getApplicationContext(),NavigationMenu.class);
                                            startActivity(intent);
                                            dialog.cancel();
                                        }
                                    }, SPLASH_DISPLAY_LENGTH);

                                    welcomeName.setText(user_firstName);


                                    progress.dismiss();



                                }


                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Connection faild", Toast.LENGTH_LONG).show();
                            progress.dismiss();

                        }
                    });
                    //Retrofit
                }

            }.start();

        }

        else
        {
            Toast.makeText(getApplicationContext(),"Enter Email & Password",Toast.LENGTH_LONG).show();

        }
    } // Button Login

    public void welcomeLoginButton(View view)
    {
        dialog.cancel();
    } // cance welcome Login Secreen

    public void continueWelcomeLoginButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(),NavigationMenu.class);
        startActivity(intent);
        dialog.cancel();
    }
}
