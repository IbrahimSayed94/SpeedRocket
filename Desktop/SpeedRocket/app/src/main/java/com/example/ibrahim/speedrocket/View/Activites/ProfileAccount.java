package com.example.ibrahim.speedrocket.View.Activites;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.TabAdapter;
import com.example.ibrahim.speedrocket.Model.ApiConfig;
import com.example.ibrahim.speedrocket.Model.AppConfig;
import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.ServerResponse;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;

public class ProfileAccount extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private ProgressDialog progress;
    private Handler handler;


    private static final int REQUEST_CAMERA = 1888;
    List <PersonalUser> user ;
    String firstName , lastName , type  ;
    String user_firstName , user_lastName , user_email , user_password , user_interest
            ,user_language , user_mobile , user_gender , user_joinAt , user_type
            ,user_companyName , user_companyCity , user_companyCountry
            , user_companyMobile , user_Image;
    TextView nav_firstname , nav_lastname , nav_email;
    CircleImageView nav_profileimage , profileImage ;

    String firstName1 , lastName1 , email1 ,userProfileImage ;

    TextView pro_companyName ;
    String  encodedimage;
    int id  , userID , userID1;
    Context context = this;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.qabout
    };
    public static final int GET_FROM_GALLERY = 10;  // upload images from gallery

    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F); // make animation on button

    Button back_profile;

    ImageView edit_image;

    String imagePath;
    TextView pro_firstname , pro_lastname , pro_type;


    private Bitmap bitmap;


    // menu
    String  email="" , interest   ;
    boolean login ;

    public static  boolean log  ;
    Menu menu;
    NavigationView navigationView;
    //menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalprofile_navigation_menu);


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




        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            userID=(int)b.get("userID");
        }

        /*Toast.makeText(getApplicationContext(),"userID : "+userID,
                Toast.LENGTH_LONG).show();*/




        pro_firstname = (TextView) findViewById(R.id.pro_firstname);
       pro_lastname = (TextView) findViewById(R.id.pro_lastname);
        pro_type=(TextView)findViewById(R.id.pro_type);

        getProfielData();
        pro_firstname.setText(user_firstName);
        pro_lastname.setText(user_lastName);

        edit_image = (ImageView) findViewById(R.id.edit_image);

        profileImage = (CircleImageView) findViewById(R.id.profile_image_account);

        Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                +userProfileImage).
                fit().centerCrop().into(profileImage);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupTabIcons();



    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
    }


    public void edit_image(View view) {
        view.startAnimation(buttonClick);


        int PERMISSION_REQUEST_CODE = 1;

        if (Build.VERSION.SDK_INT >= 23) {
            //do your check here
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            // ask user for permission to access storage // allow - deni

        }





        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                , GET_FROM_GALLERY);


    } // edit image fnction


    public  void getProfielData()
    {
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
        retrofit2.Call<ResultModel> getProfileConnection =
                userApi.getProfileAccount(userID);

        getProfileConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {
                        user = response.body().getUser();
                    user_firstName=user.get(0).getFirstName();
                    user_lastName=user.get(0).getLastName();
                    user_email=user.get(0).getEmail();
                    user_gender=user.get(0).getGender();
                    user_password=user.get(0).getPassword();
                    user_interest=user.get(0).getInterest();
                    user_language=user.get(0).getLanguage();
                    user_mobile=user.get(0).getMobile();
                    user_joinAt=user.get(0).getCreated_at();
                    user_type=user.get(0).getType();
                    user_companyName=user.get(0).getCompanyName();
                    user_companyCity=user.get(0).getCompanyCity();
                    user_companyCountry=user.get(0).getCompanyCountry();
                    user_companyMobile=user.get(0).getCompanyMobile();
                    userID = user.get(0).getId();


                   if(response.body().getMessage().equals("true"))
                    {


                    } // if
                   /* Toast.makeText(getApplicationContext(), "Connection Success\n"+
                            "FirstName : "+user_firstName
                            ,Toast.LENGTH_LONG).show();*/

                    pro_firstname.setText(user_firstName);
                    pro_lastname.setText(user_lastName);

                    if(user_type.equals("0"))
        pro_type.setText("User");
        if(user_type.equals("1"))
        {
            pro_type.setText("Admin");
            pro_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),
                            CompanyProfile.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }
            });

        }



                } catch (Exception e) {
                  /*  Toast.makeText(getApplicationContext(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();*/
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n" +
                        "Exception"+t.toString()
                        , Toast.LENGTH_LONG).show();


            }
        });
        //Retrofit
    } // getProfileData Function
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (resultCode == RESULT_OK) {

            if (requestCode == GET_FROM_GALLERY) {
              onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }


            /*CircleImageView imageView = (CircleImageView) findViewById(R.id.profile_image_account);
            imageView.setImageBitmap(bitmap);*/


           uploadProfileImageFast();


       }
    } // on Activity result

    public void onSelectFromGalleryResult(Intent data)
    {
        if (data != null) {

              /*  bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/

            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = getRealPathFromURI(imageUri);
            }



            // upload image
        }
    } // onSelectFromGalleryResult



    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    //get encodedImage of takePhoto
    public void onCaptureImageResult(Intent data)
    {
        if (data !=null) {

           /* bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/

            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.ACTION_IMAGE_CAPTURE};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = getRealPathFromURI(imageUri);
            }


            Log.i("QP",imagePath);
        }
    } // onCaptureImageResult

/*   public  void  uploadProfileImage ()
    {
        // uploadImage
            //Retrofit

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
                    .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://speed-rocket.com/api/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();

            UserApi userApi = retrofit.create(UserApi.class);
            Call<ResultModel> updateProfileImageConnection =
                    userApi.updateProfileImage(userID,encodedimage);

        Log.e("IMAGE_API","START");

        updateProfileImageConnection.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                    if(response.isSuccessful()) {
                        try {

                            user_Image = response.body().getImage();
                            Log.e("IMAGE" , user_Image);
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("profileImage",user_Image);
                            editor.commit();
                            Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                                    +user_Image).
                                    fit().centerCrop().into(profileImage);
                            Toast.makeText(getApplicationContext(), "Uploaded"
                                    , Toast.LENGTH_LONG).show();
                            Log.e("IMAGE_API", "try  |  "+response.body().getMessage());


                        } catch (Exception e) {
                            Log.e("IMAGE_API", "CATCH"+e.getMessage());

                        }

                        progress.dismiss();
                    }else{
                        Log.e("IMAGE_API","ELSE_SUCCESS"+response.message());
                        progress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResultModel> call, Throwable t) {
//        Toast.makeText(getApplicationContext(), "Connection faild\n" +
//                ""+ t.toString(), Toast.LENGTH_LONG).show();

                    Log.e("IMAGE_API","FAILURE"+t.getMessage());
                    progress.dismiss();

    }
           });

            }

        }.start();







            //Retrofit
    } // uploadProfileImage*/

    public  void  uploadProfileImageFast ()
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


        ///////
              File file = new File(imagePath);

              // RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userID));
             RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
                Call call = getResponse.uploadFile(filename,fileToUpload,userID);
                call.enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) {
                        ServerResponse serverResponse = (ServerResponse) response.body();

                        try {

                              /*  if (serverResponse.getSuccess()) {
                                    Log.i("QP1", serverResponse.getMessage());
                                    progress.dismiss();
                                } else {*/
                            user_Image = ((ServerResponse) response.body()).getMessage();
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("profileImage",user_Image);
                            editor.commit();
                                    Log.i("QP2", ((ServerResponse) response.body()).getMessage()+"");


                                    progress.dismiss();
                               /* }*/

                            progress.dismiss();
                        }
                        catch (Exception e)
                        {
                            Log.i("QP3","errorE"+e.toString());
                            progress.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.i("QP1", "fail\n"+t.toString());
                        progress.dismiss();
                    }


                });

            }

        }.start();



    } // uploadProfileImageFast

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

        SearchManager searchManager = (SearchManager) ProfileAccount.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ProfileAccount.this.getComponentName()));
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
