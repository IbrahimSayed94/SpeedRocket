package com.example.ibrahim.speedrocket.View.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.Company;
import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;


public class CompanyAboutTab extends Fragment {

    private ProgressDialog progress;
    private Handler handler;


    List<Company> company ;
    int id  , companyId;
    String companyCity ="" , companyCountry="" , companyMobile="" , companyCreatedAt="";

    TextView t_companyCity , t_companyCountry , t_companyMobile , t_companyCreatedAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_company_about_tab,
                container,false);

        t_companyCity=(TextView)view.findViewById(R.id.tab_comapnyCity);
        t_companyCountry=(TextView)view.findViewById(R.id.tab_companyCountry);
        t_companyCreatedAt=(TextView)view.findViewById(R.id.tab_companyCreatedAt);
        t_companyMobile=(TextView)view.findViewById(R.id.tab_companyMobile);


        Intent iin= getActivity().getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            companyId=(int)b.get("companyId");
        }



      /*  Toast.makeText(getActivity(),"userID : "+userID,
                Toast.LENGTH_LONG).show();*/

        getProfielData();
        return view;
    }

    public  void getProfielData()
    {
        progress = new ProgressDialog(getActivity());
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
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://speed-rocket.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        final UserApi userApi = retrofit.create(UserApi.class);
        retrofit2.Call<ResultModel> getProfileConnection =
                userApi.getCompanyAccount(companyId);

        getProfileConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {
                    company = response.body().getCompany();

                    companyCity=company.get(0).getCity();
                    companyCountry=company.get(0).getCountry();
                    companyMobile=company.get(0).getPhone();
                    companyCreatedAt=company.get(0).getCreated_at();


                    t_companyCity.setText(companyCity);
                    t_companyCountry.setText(companyCountry);
                    t_companyMobile.setText(companyMobile);
                    t_companyCreatedAt.setText(companyCreatedAt);

                    /*Toast.makeText(getActivity(), "Connection Success\n"
                            ,Toast.LENGTH_LONG).show();*/
                    progress.dismiss();






                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection faild\n" +
                                "Exception"+t.toString()
                        , Toast.LENGTH_LONG).show();
                progress.dismiss();


            }
        });
            }

        }.start();
        //Retrofit
    } // getProfileData Function
}
