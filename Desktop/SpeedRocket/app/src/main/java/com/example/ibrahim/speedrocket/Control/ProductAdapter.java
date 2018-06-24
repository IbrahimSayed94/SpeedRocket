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

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.ibrahim.speedrocket.Model.Image;
import com.example.ibrahim.speedrocket.Model.Post;
import com.example.ibrahim.speedrocket.Model.Product;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.PaymentScreen;
import com.example.ibrahim.speedrocket.View.Activites.PostDetails;
import com.example.ibrahim.speedrocket.View.Activites.carddetails;
import com.example.ibrahim.speedrocket.View.Activites.image_item;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim on 3/21/2018.
 */

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>
{


    private List<Product> productlist;

    private ProgressDialog progress;
    private Handler handler;
    private Context context ;
    List <Image> images;
    ArrayList<String> imagesUrl = new ArrayList<String>();

    String urls []={"https://image.ibb.co/mUVkQd/11_9664.png"
            ,"https://image.ibb.co/mbBgyy/Gionee_A1_64_GB_Black_SDL352791824_1_ff379.jpg"
    ,"https://image.ibb.co/hVbEJy/i_phone_6_500x500.jpg"};



    public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        public Button buy_btn , moreInfo_btn , dialog_buyNow_btn , dialog_cancel_btn;
        public TextView product_name , product_points , product_description ,
                dialog_product_description , companyName ,product_price , egWord;
        public ImageView product_image ;
        public Dialog dialogProductMoreInfo;
        public SliderLayout sliderShow;
        TextSliderView textSliderView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            buy_btn = (Button) itemView.findViewById(R.id.productstart);
            moreInfo_btn = (Button) itemView.findViewById(R.id.productInfo);
            product_image = (ImageView) itemView.findViewById(R.id.productimage);
            product_name = (TextView) itemView.findViewById(R.id.productname);
            product_description = (TextView) itemView.findViewById(R.id.productDescription);
            companyName = (TextView) itemView.findViewById(R.id.companNameProduct);

            product_price = (TextView) itemView.findViewById(R.id.priceProduct);
            egWord = (TextView) itemView.findViewById(R.id.egWord);

            dialogProductMoreInfo = new Dialog(itemView.getContext()); // Context, this, etc.
            dialogProductMoreInfo.setContentView(R.layout.dialog_moreinfo_product);

            dialog_buyNow_btn = (Button) dialogProductMoreInfo.findViewById(R.id.buyMoreInfoProduct);
            dialog_cancel_btn = (Button) dialogProductMoreInfo.findViewById(R.id.cancelMoreInfoProduct);
            dialog_product_description =(TextView)  dialogProductMoreInfo.findViewById(R.id.descriptionMoreInfoProduct);
         //   sliderShow = (SliderLayout) dialogProductMoreInfo.findViewById(R.id.sliderInMoreInfo);
           /* sliderShow.setCustomIndicator((PagerIndicator) dialogProductMoreInfo.findViewById(R.id.custom_indicator));*/
           // textSliderView= new TextSliderView (itemView.getContext());
        }
    }

    public  ProductAdapter(List<Product> productlist , Context context)
    {

        this.context = context;
        this.productlist = productlist ;

    }
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
      /*  Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();*/

        return new ProductAdapter.MyViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, final int position) {

        final Product product = productlist.get(position);


        holder.egWord.setVisibility(View.VISIBLE);
        holder.product_price.setVisibility(View.VISIBLE);
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);
        holder.buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
               /*Toast.makeText(view.getContext(), "Product "+post.getPostID(), Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(view.getContext(), PaymentScreen.class);
                view.getContext().startActivity(intent);


            }
        });


        holder.dialog_product_description.setText(product.getEn_discription()
        );
        holder.moreInfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setAnimation(buttonClick);

                holder.dialogProductMoreInfo.show();
                progress = new ProgressDialog(view.getContext());
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
                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .readTimeout(100,TimeUnit.SECONDS).build();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://speed-rocket.com/api/")
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                .build();

                        final UserApi userApi = retrofit.create(UserApi.class);
                        Call<ResultModel> getProductConnection =
                                userApi.getProductDetails(product.getId());

                        getProductConnection.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                                try {


                                    SliderLayout sliderShow = (SliderLayout) holder.dialogProductMoreInfo.findViewById(R.id.sliderInMoreInfo);

                                     sliderShow.removeAllSliders();
                                    images=response.body().getImages();
                                    for(int i = 0 ; i < images.size() ; i++)
                                    {

                                        TextSliderView  textSliderView = new TextSliderView (view.getContext());
                                        textSliderView
                                                .image("https://speed-rocket.com/upload/product/"+ images.get(i).getImage());
                                        sliderShow.addSlider(textSliderView);
                                        Log.e("QP",images.get(i).getImage()+" // "+i);
                                    }





                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "Connection Success\n" +
                                                    "Exception"+e.toString()
                                            ,Toast.LENGTH_LONG).show();


                                }
                                progress.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResultModel> call, Throwable t) {
                                Toast.makeText(view.getContext(), "Connection faild\n" +
                                                "Exception"+t.toString()
                                        , Toast.LENGTH_LONG).show();
                                progress.dismiss();


                            }
                        });
                        //Retrofit

                    }

                }.start();




                // display dialogu with image slider of product and discreptions
            }
        });

        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
               /*Toast.makeText(view.getContext(), "Product "+post.getPostID(), Toast.LENGTH_LONG).show();*/





            }
        });

        holder.dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dialogProductMoreInfo.cancel();
            }
        });

        holder.dialog_buyNow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),carddetails.class);
                view.getContext().startActivity(intent);
            }
        });




        holder.product_price.setText(product.getPrice()+"");
        holder.product_name.setText(product.getEn_title());
        holder.product_description.setText(product.getEn_discription());

        holder.companyName.setVisibility(View.VISIBLE);
        holder.companyName.setText(product.getCompanyName());

        Picasso.with(context).load("https://speed-rocket.com/upload/product/"+product.getImage())
                .fit().centerCrop().into(holder.product_image);


    }



    @Override
    public int getItemCount() {
        return productlist.size();
    }



} // class of PostsAdapter