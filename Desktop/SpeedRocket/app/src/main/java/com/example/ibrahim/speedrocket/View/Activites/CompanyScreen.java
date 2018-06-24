package com.example.ibrahim.speedrocket.View.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.CompanyAdapter;
import com.example.ibrahim.speedrocket.Control.ProductAdapter;
import com.example.ibrahim.speedrocket.Model.Company;
import com.example.ibrahim.speedrocket.Model.Product;
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

public class CompanyScreen extends AppCompatActivity {


    TextView fName ;
    CircleImageView myProfileImage ;

    String firstName1 , lastName1 , email1 ,userProfileImage , companyName , companyLogo ;
    int id  , userID , userID1 , companyId;


    private RecyclerView recyclerView;
    private CompanyAdapter mAdapter;
    Company company;
    private List<Company> companyList1 = new ArrayList<>();

    private ProgressDialog progress;
    private Handler handler;
    List<Company> CList;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_screen);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        firstName1 = prefs.getString("firstName", "");//"No name defined" is the default value.
        lastName1 = prefs.getString("lastName", "");//"No name defined" is the default value.
        email1 = prefs.getString("email", "");//"No name defined" is the default value.
        userID1=prefs.getInt("id",0);
        userProfileImage = prefs.getString("profileImage","");


        /*fName = (TextView) findViewById(R.id.t_companyName);
        myProfileImage = (CircleImageView) findViewById(R.id.i_profileImage);

        fName.setText(firstName1);
        Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                +userProfileImage).
                fit().centerCrop().into(myProfileImage);*/


        recyclerView = (RecyclerView) findViewById(R.id.companyList);

        mAdapter = new CompanyAdapter(companyList1, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getCompanies();



    } // onCreate function


    public  void  getCompanies()
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

                        company = new Company(companyId,companyName,companyLogo);

                        companyList1.add(company);
                        mAdapter.notifyDataSetChanged();

                    }
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

    public void addCompanyButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(),AddCompany.class);
        startActivity(intent);
    } // Add Company Button
} // CompanyScreen Class
