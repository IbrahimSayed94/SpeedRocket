package com.example.ibrahim.speedrocket.View.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.OfferMenuListAdapter;
import com.example.ibrahim.speedrocket.Control.ProductMenuListAdapter;
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

public class ProductMenuList extends AppCompatActivity {

    Category category;
    List<Category> categoryList;
    public  static  int numOfCategoryItems ;
    int categoryId , subCategpryId =0 , subCategory;
    String categoryTitle , sub ;
    private RecyclerView recyclerView;
    private ProductMenuListAdapter mAdapter;

    int type ;
    private ProgressDialog progress;
    private Handler handler;
    private List<Category> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu_list);

        recyclerView = (RecyclerView) findViewById(R.id.list_offerMenuList);

        mAdapter = new ProductMenuListAdapter(productList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            subCategpryId=(int)b.get("cat");
        }
        getOffersInNavigationMenu();


    } // onCreate Function


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
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS).build();
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
                        try {

                            categoryList = response.body().getCategory();

                            numOfCategoryItems = categoryList.size();

                            for (int i = 0; i <= categoryList.size(); i++) {
                                categoryId = categoryList.get(i).getId();
                                categoryTitle = categoryList.get(i).getEn_title();

                                subCategory = categoryList.get(i).getSubCategory();
                                type = categoryList.get(i).getType();

                                if(subCategpryId != 0 && subCategpryId == subCategory) {
                                    category = new Category(categoryId, categoryTitle, type);
                                    productList.add(category);
                                    mAdapter.notifyDataSetChanged();
                                } // if
                                else if (subCategpryId == 0 && subCategory == 0)
                                    {
                                        category = new Category(categoryId, categoryTitle, type);
                                        productList.add(category);
                                        mAdapter.notifyDataSetChanged();
                                    } // else



                            } // for loop


                   /* Toast.makeText(getApplicationContext(),"Connection Success\n"
                                    ,

                            Toast.LENGTH_LONG).show();*/

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

    } // function of getOffersInNavigationMenu
} // classs of PrductMenyList
