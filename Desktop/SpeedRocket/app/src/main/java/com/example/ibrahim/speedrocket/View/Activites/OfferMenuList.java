package com.example.ibrahim.speedrocket.View.Activites;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.OfferMenuListAdapter;
import com.example.ibrahim.speedrocket.Control.PostsAdapter;
import com.example.ibrahim.speedrocket.Model.Category;
import com.example.ibrahim.speedrocket.Model.Offer;
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

public class OfferMenuList extends AppCompatActivity {






    List<String> interstList ;
    private RecyclerView recyclerView;
    private OfferMenuListAdapter mAdapter;
    private List<String> offerList = new ArrayList<>();

    private ProgressDialog progress;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_menu_list);

        recyclerView = (RecyclerView) findViewById(R.id.list_offerMenuList);

        mAdapter = new OfferMenuListAdapter(offerList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getOffersInNavigationMenu();



    } // function of OnCreate


    public  void getOffersInNavigationMenu()
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://speed-rocket.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final UserApi userApi = retrofit.create(UserApi.class);

        final Call<ResultModel> getInterestConnection = userApi.getUsersInterest();

        getInterestConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                try
                {
                    interstList = response.body().getType();
                    for(int i = 0 ; i< interstList.size();i++)
                    {
                        offerList.add(interstList.get(i));
                        mAdapter.notifyDataSetChanged();

                    } // for loop

/*Toast.makeText(getApplicationContext(),"Connection Success\n",
                            Toast.LENGTH_LONG).show();*/
                    progress.dismiss();
                } // try
                catch (Exception e)
                {
                 /*   Toast.makeText(getApplicationContext(),"Connection Success\n" +
                                    "Exception\n"+e.toString(),
                            Toast.LENGTH_LONG).show();*/    progress.dismiss();
                } // catch
            } // onResponse

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Connection Faild",
                        Toast.LENGTH_LONG).show();
                progress.dismiss();
            } // on Failure
        });

            } }.start();

// Retrofit
    } // function of getOffersInNavigationMenu
} // class off OfferMenuList
