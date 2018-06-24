package com.example.ibrahim.speedrocket.Control;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.Image;
import com.example.ibrahim.speedrocket.Model.Product;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.AddCompany;
import com.example.ibrahim.speedrocket.View.Activites.AddProduct;
import com.example.ibrahim.speedrocket.View.Activites.CompanyScreen;
import com.example.ibrahim.speedrocket.View.Activites.ProductScreen;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim on 5/21/2018.
 */

public class Padapter extends RecyclerView.Adapter<Padapter.MyViewHolder>
{
    private List<Product> productlist;

    private ProgressDialog progress;
    private Handler handler;
    private Context context ;
    List <Image> images;

    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public Button buy_btn , moreInfo_btn , yes , no;
        public TextView product_name  , product_description ;
        public ImageView product_image ;

        Dialog warningDialog;
        public MyViewHolder(View itemView) {
            super(itemView);

            buy_btn = (Button) itemView.findViewById(R.id.productstart);
            moreInfo_btn = (Button) itemView.findViewById(R.id.productInfo);
            product_image = (ImageView) itemView.findViewById(R.id.productimage);
            product_name = (TextView) itemView.findViewById(R.id.productname);
            product_description = (TextView) itemView.findViewById(R.id.productDescription);

            warningDialog = new Dialog(itemView.getContext()); // Context, this, etc.
            warningDialog.setContentView(R.layout.dialog_delete_item);

            yes = (Button) warningDialog.findViewById(R.id.yes);
            no = (Button) warningDialog.findViewById(R.id.no);
        }
    }


    public  Padapter(List<Product> productlist , Context context)
    {

        this.context = context;
        this.productlist = productlist ;

    }
    @Override
    public Padapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
      /*  Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();*/

        return new Padapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Padapter.MyViewHolder holder, int position) {
        final Product product = productlist.get(position);

        holder.product_name.setText(product.getEn_title());
        holder.product_description.setText(product.getEn_discription());


        Picasso.with(context).load("https://speed-rocket.com/upload/product/"+product.getImage())
                .fit().centerCrop().into(holder.product_image);

        holder.buy_btn.setText("Edit");
        holder.moreInfo_btn.setText("Delete");

        holder.moreInfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.warningDialog.show();
            }
        });


        holder.buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                Intent intent = new Intent(view.getContext(),AddProduct.class);
                intent.putExtra("productId",product.getId());
                view.getContext().startActivity(intent);

            }
        }); // edit button

        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.warningDialog.cancel();
            }
        }); // no button


        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                progress = new ProgressDialog(view.getContext());
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

                        final Call<ResultModel> deleteProductConnection= userApi.deleteProduct(product.getId());

                        deleteProductConnection.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                try
                                {
                                    Log.i("QP","companyId"+product.getId());
                                    Toast.makeText(view.getContext(),"Item Deleted",Toast.LENGTH_LONG).show();
                                    progress.dismiss();
                                    Intent intent = new Intent(view.getContext(), ProductScreen.class);
                                    view.getContext().startActivity(intent);
                                } // try
                                catch (Exception e)
                                {
                                    Toast.makeText(view.getContext(),"Connection Faild",
                                            Toast.LENGTH_LONG).show();
                                    Log.i("QP","Error : "+e.toString()+"\n companyId"+product.getId());
                                    progress.dismiss();
                                } // catch


                            } // onResponse

                            @Override
                            public void onFailure(Call<ResultModel> call, Throwable t) {

                                Toast.makeText(view.getContext(),"Connection Faild",
                                        Toast.LENGTH_LONG).show();
                                progress.dismiss();

                            } // on Failure
                        });


                    }

                }.start();


            }
        }); // yes button




    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }


} // class Padapter
