package com.example.ibrahim.speedrocket.View.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.Category;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim on 5/20/2018.
 */




public class UpdateCompany  extends AppCompatActivity
{

    EditText ed_enName , ed_arName , ed_email , ed_companyLogo , ed_mobile , ed_fax ;
    Spinner sp_country , sp_city , sp_category ;
    public static final int GET_FROM_GALLERY = 10;
    private static final int REQUEST_CAMERA = 1888;
    List<Category> categoryList;

    ArrayAdapter<String> spinnerArrayAdapter;
    private List<Category> offerList = new ArrayList<>();

    String imagePath="";
    String enName="";
    String arName="";
    String email="";
    String mobile="";
    String fax="";
    String country="";
    String city="";
    int categoryS;
    String logo="";
    String categoryTitle="";

    int categoryId , userId ;

    Category category;



    private ProgressDialog progress;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        ed_enName = (EditText) findViewById(R.id.cEnName);
        ed_arName = (EditText) findViewById(R.id.cArName);
        ed_email = (EditText) findViewById(R.id.cEmail);
        ed_companyLogo = (EditText) findViewById(R.id.cCompanyLogo);
        ed_mobile = (EditText) findViewById(R.id.cMobile);
        ed_fax = (EditText) findViewById(R.id.cFax);

        sp_country = (Spinner) findViewById(R.id.cCountry);
        sp_city = (Spinner) findViewById(R.id.cCity);
        sp_category= (Spinner) findViewById(R.id.cCategory);



        spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item);

        getCategory();

        sp_category.setAdapter(spinnerArrayAdapter);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.Country, R.layout.spinner_item);
        sp_country.setAdapter(adapter1);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.city, R.layout.spinner_item);
        sp_city.setAdapter(adapter2);

    } // on create function




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
    } // class of UpdateCompany
