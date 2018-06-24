package com.example.ibrahim.speedrocket.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Control.MyProductAdapter;
import com.example.ibrahim.speedrocket.Control.ProductAdapter;
import com.example.ibrahim.speedrocket.Model.Post;
import com.example.ibrahim.speedrocket.Model.ProductsWinner;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsTab extends Fragment {


    int userID , offerId , srCoin;

    Timestamp created_at ;

    List <ProductsWinner> productList ;

    String productTitle ;

    private List<ProductsWinner> productWinnerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyProductAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootview = inflater.inflate(R.layout.fragment_products_tab, container
                , false);



        Intent iin= getActivity().getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            userID=(int)b.get("userID");
        }


        getProductsWinnerByUser();

        recyclerView = (RecyclerView) rootview.findViewById(R.id.myProductList);
        mAdapter = new MyProductAdapter(productWinnerList ,getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);





        return rootview;

    } // onCreateView function


    public  void getProductsWinnerByUser()
    {
        /*Toast.makeText(getActivity() , "Product Tap : "+userID,Toast.LENGTH_LONG).show();*/

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
        retrofit2.Call<ResultModel> winnerProductConnection =
                userApi.getProductWinnersByUser(userID);

        winnerProductConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {

                    productList = response.body().getWinners();

                    for(int i = 0 ; i<productList.size() ; i++)
                    {
                        offerId = productList.get(i).getOfferId();
                        srCoin = productList.get(i).getSrCoin();
                     //   created_at = productList.get(i).getCreated_at();
                        productTitle = productList.get(i).getTitle();

                       ProductsWinner p = new ProductsWinner(userID,offerId,
                               srCoin,productTitle);

                       productWinnerList.add(p);
                       mAdapter.notifyDataSetChanged();

                    } // for Loop

                    /*Toast.makeText(getActivity(), "Connection Success\n"
                            ,Toast.LENGTH_LONG).show();*/





                } catch (Exception e) {
             /*       Toast.makeText(getActivity(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();*/
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection faild\n" +
                                "Exception\n Product Tap"+t.toString()
                        , Toast.LENGTH_LONG).show();


            }
        });
        //Retrofit
    } // function getProductsWinnerByUser


} // product spcialize personal user
