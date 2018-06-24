package com.example.ibrahim.speedrocket.View.Activites;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.ApiConfig;
import com.example.ibrahim.speedrocket.Model.AppConfig;
import com.example.ibrahim.speedrocket.Model.Category;
import com.example.ibrahim.speedrocket.Model.Company;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.ServerResponse;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;

public class AddCompany extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {



    EditText ed_enName , ed_arName , ed_email , ed_companyLogo , ed_mobile , ed_fax ;
    Spinner sp_country , sp_city , sp_category ;
    public static final int GET_FROM_GALLERY = 10;
    private static final int REQUEST_CAMERA = 1888;
    List<Category> categoryList;

    List<Company> company ;
    String companyEnName , companyLogo , companyEmail ,companyArName , companyFax , companyMobile ;


    ArrayAdapter<String> spinnerArrayAdapter;
    private List<Category> offerList = new ArrayList<>();

    String imagePath="";
    String enName="";
    String arName="";
    String mobile="";
    String fax="";
    String country="";
    String city="";
    int categoryS;
    String logo="";
    String categoryTitle="";

    int categoryId , userId , copmpanyId=0 ;

    Category category;

    Button bt_addCompany ;



    private ProgressDialog progress;
    private Handler handler;

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
        setContentView(R.layout.add_company_navigation_menu);

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
        userId=prefs1.getInt("id",0);


        ed_enName = (EditText) findViewById(R.id.cEnName);
        ed_arName = (EditText) findViewById(R.id.cArName);
        ed_email = (EditText) findViewById(R.id.cEmail);
        ed_companyLogo = (EditText) findViewById(R.id.cCompanyLogo);
        ed_mobile = (EditText) findViewById(R.id.cMobile);
        ed_fax = (EditText) findViewById(R.id.cFax);

        sp_country = (Spinner) findViewById(R.id.cCountry);
        sp_city = (Spinner) findViewById(R.id.cCity);
        sp_category= (Spinner) findViewById(R.id.cCategory);


        bt_addCompany = (Button) findViewById(R.id.cAddCompany);


        spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item);



        sp_category.setAdapter(spinnerArrayAdapter);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.Country, R.layout.spinner_item);
        sp_country.setAdapter(adapter1);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, R.layout.spinner_item);
        sp_city.setAdapter(adapter2);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            copmpanyId=(int)b.get("companyId");
            bt_addCompany.setText("Update");
            getCompanyDataToUpdate();
        }
       else {
            getCategory();
        }


        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutAddCompany);
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });

    } // onCreate function


    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void uploadCompanyLogo(View view)
    {
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

        //ed_companyLogo.setText(imagePath.toString());

    } // uploadCompanyLogo function

    public void addCompanyButton(View view)
    {

        if(bt_addCompany.getText().equals("Update"))
        {
            Log.i("QPUpdate","hi");
            updateCompanyToserver();
        }
        else
        {
            Log.i("QPAdd","hi");
            addCopmanyToServer();

        }

    } // addCompanyButton function


    public  void addCopmanyToServer ()
    {
        enName = ed_enName.getText().toString();
        arName = ed_arName.getText().toString();
        email = ed_email.getText().toString();
        mobile = ed_mobile.getText().toString();
        fax = ed_fax.getText().toString();
        logo = ed_companyLogo.getText().toString();

        country = sp_country.getSelectedItem().toString();
        city = sp_city.getSelectedItem().toString();
        categoryS = sp_category.getSelectedItemPosition();

        if(enName.equals("") || arName.equals("") || email.equals("") || mobile.equals("")
                || fax.equals("") || logo.equals(""))

            Toast.makeText(getApplicationContext(),"Complete Fields",Toast.LENGTH_LONG).show();


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


                    ///////
                    File file = new File(imagePath);

                    // RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userID));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                    ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
                    Call call = getResponse.uploadFileAndCompanyProfile(filename, fileToUpload, enName, arName,
                            email, "1", offerList.get(categoryS).getId(), "3", mobile, fax, userId);
                    call.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            ServerResponse serverResponse = (ServerResponse) response.body();

                            try {


                                Log.i("QP1", ((ServerResponse) response.body()).getMessage() + "");

                                Intent intent = new Intent(getApplicationContext(), MyCompanyProfile.class);
                                startActivity(intent);
                                progress.dismiss();
                            } catch (Exception e) {
                                Log.i("QP2", "errorE" + e.toString());
                                progress.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.i("QP3U", "fail\n" + t.toString());
                            Toast.makeText(getApplicationContext(), "Retry Connection Faild", Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }


                    });

                }

            }.start();
        }//else

    } // function addCopmanyToServer


    public  void updateCompanyToserver()
    {
        enName = ed_enName.getText().toString();
        arName = ed_arName.getText().toString();
        email = ed_email.getText().toString();
        mobile = ed_mobile.getText().toString();
        fax = ed_fax.getText().toString();
        logo = ed_companyLogo.getText().toString();

        country = sp_country.getSelectedItem().toString();
        city = sp_city.getSelectedItem().toString();
        categoryS = sp_category.getSelectedItemPosition();

        if(enName.equals("") || arName.equals("") || email.equals("") || mobile.equals("")
                || fax.equals("") || logo.equals(""))

            Toast.makeText(getApplicationContext(),"Complete Fields",Toast.LENGTH_LONG).show();


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


                    Log.i("QP","image : "+imagePath);
                    ///////
                    if (imagePath.equals("")) {


                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .readTimeout(100,TimeUnit.SECONDS).build();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://speed-rocket.com/api/")
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                .build();
                        ApiConfig getResponse = retrofit.create(ApiConfig.class);
                        Call call = getResponse.uploadFileAndUpdateCompanyProfile2(copmpanyId, enName, arName,
                                email, "1", offerList.get(categoryS).getId(), "3", mobile, fax, userId);
                        call.enqueue(new Callback() {

                            @Override
                            public void onResponse(Call call, Response response) {
                                ServerResponse serverResponse = (ServerResponse) response.body();

                                try {


                                    Log.i("QP1U", ((ServerResponse) response.body()).getMessage() + "");

                                    Intent intent = new Intent(getApplicationContext(), MyCompanyProfile.class);
                                    startActivity(intent);
                                    progress.dismiss();
                                } catch (Exception e) {
                                    Log.i("QP2U", "errorE" + e.toString());
                                    progress.dismiss();
                                }

                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Log.i("QP3U", "fail\n" + t.toString());
                                Toast.makeText(getApplicationContext(), "Retry Connection Faild", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }


                        });

                    } // if

                    else {
                        File file = new File(imagePath);

                        // RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userID));
                        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                      /*  OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .readTimeout(100,TimeUnit.SECONDS).build();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://speed-rocket.com/api/")
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                .build();
                        ApiConfig getResponse = retrofit.create(ApiConfig.class);*/
                        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
                        Call call = getResponse.uploadFileAndUpdateCompanyProfile( filename, fileToUpload,copmpanyId, enName, arName,
                                email, "1", offerList.get(categoryS).getId(), "3", mobile, fax, userId);
                        call.enqueue(new Callback() {

                            @Override
                            public void onResponse(Call call, Response response) {
                                ServerResponse serverResponse = (ServerResponse) response.body();

                                try {


                                    Log.i("QP1", ((ServerResponse) response.body()).getMessage() + "");

                                    Intent intent = new Intent(getApplicationContext(), MyCompanyProfile.class);
                                    startActivity(intent);
                                    progress.dismiss();
                                } catch (Exception e) {
                                    Log.i("QP2", "errorE" + e.toString());
                                    progress.dismiss();
                                }

                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Log.i("QP3", "fail\n" + t.toString());
                                Toast.makeText(getApplicationContext(), "Retry Connection Faild", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }


                        });

                    }
                } // else



            }.start();

        }//else

    } // function updateCompanyToserver


    public  void getCategory()
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

        final Call<ResultModel> getCategoryConnection = userApi.getCategory();

        getCategoryConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                try
                {

                    categoryList = response.body().getCategory();

                    for(int i = 0 ; i<= categoryList.size() ; i++)
                    {
                        categoryId = categoryList.get(i).getId();
                        categoryTitle = categoryList.get(i).getEn_title();

                        category = new Category(categoryId,categoryTitle);

                        offerList.add(category);
                        spinnerArrayAdapter.add(categoryList.get(i).getEn_title());
                        spinnerArrayAdapter.notifyDataSetChanged();
                    } // for loop


                   /* Toast.makeText(getApplicationContext(),"Connection Success\n"
                                    ,

                            Toast.LENGTH_LONG).show();*/


                } // try
                catch (Exception e)
                {
                   /* Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                    "Exception Navigation menu\n"+e.toString(),
                            Toast.LENGTH_LONG).show();*/

                } // catch
            } // onResponse

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Connection Faild",
                        Toast.LENGTH_LONG).show();

            } // on Failure
        });




// Retrofit

    } // function of getCategory
    // SelectImage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (resultCode == RESULT_OK) {

            if (requestCode == GET_FROM_GALLERY) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }

            Log.i("QP",imagePath);
            ed_companyLogo.setText(imagePath.toString());

            uploadProfileImageFast();


        }
    } // on Activity result

    public  void  uploadProfileImageFast ()
    {
    } // uploadProfileImageFast

    public void onSelectFromGalleryResult(Intent data)
    {
        if (data != null) {


            Uri imageUri = data.getData();
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
    } // getRealPathFromURI

    public void onCaptureImageResult(Intent data)
    {
        if (data !=null) {

           /* bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/

            Uri imageUri = data.getData();
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



    public  void  getCompanyDataToUpdate()
    {
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
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100,TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://speed-rocket.com/api/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();

                final UserApi userApi = retrofit.create(UserApi.class);
                retrofit2.Call<ResultModel> getProfileConnection =
                        userApi.getCompanyAccount(copmpanyId);

                getProfileConnection.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                        try {
                            company = response.body().getCompany();
                            companyEnName=company.get(0).getEn_name();
                            companyLogo = company.get(0).getLogo();
                            companyEmail = company.get(0).getEmail();
                            companyArName = company.get(0).getAr_name();
                            companyFax = company.get(0).getFax();
                            companyMobile = company.get(0).getPhone();

                            ed_enName.setText(companyEnName);
                            ed_arName.setText(companyArName);
                            ed_email.setText(companyEmail);
                            ed_fax.setText(companyFax);
                            ed_mobile.setText(companyMobile);
                            ed_companyLogo.setText(companyLogo);

                            Log.i("QP","/"+companyMobile+"/"+companyEnName);


                   /* Toast.makeText(getApplicationContext(), "Connection Success\n"
                            ,Toast.LENGTH_LONG).show();*/




                            progress.dismiss();


                            getCategory();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Connection Success\n" +
                                            "Exception"+e.toString()
                                    ,Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection faild\n" +
                                        "Exception"+t.toString()
                                , Toast.LENGTH_LONG).show();
                        progress.dismiss();


                    }
                });
                //Retrofit

            }

        }.start();

    } // getCompanyDataToUpdate function
    // SelectImage


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

        SearchManager searchManager = (SearchManager) AddCompany.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(AddCompany.this.getComponentName()));
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
