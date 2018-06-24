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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.Offer;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.AddCompany;
import com.example.ibrahim.speedrocket.View.Activites.AddOffer;
import com.example.ibrahim.speedrocket.View.Activites.CompanyScreen;
import com.example.ibrahim.speedrocket.View.Activites.OfferScreen;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
 * Created by Ibrahim on 5/21/2018.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {


    private Context context ;
    private ProgressDialog progress;
    private Handler handler;
    private List<Offer> offerList;
    int o ;

    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);

    int topSrcoin = 0 ;
    int view = 0 ;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public Button start_btn , edit , delete , yes , no;
        public  int postID;
        public TextView companyName , srcoin , price , view , description ,offertime;
        public CircleImageView profileimage ;
        public ImageView postimage ;
        public LinearLayout offerAction ;
        Dialog warningDialog;
        public MyViewHolder(View itemView) {
            super(itemView);

            companyName = (TextView) itemView.findViewById(R.id.Cname);
            description = (TextView) itemView.findViewById(R.id.Cdescription);
            profileimage = (CircleImageView) itemView.findViewById(R.id.Cprofile_image);
            postimage = (ImageView) itemView.findViewById(R.id.Cpost_image);
            offertime = (TextView) itemView.findViewById(R.id.CofferTime);

            warningDialog = new Dialog(itemView.getContext()); // Context, this, etc.
            warningDialog.setContentView(R.layout.dialog_delete_item);

            yes = (Button) warningDialog.findViewById(R.id.yes);
            no = (Button) warningDialog.findViewById(R.id.no);

            start_btn=(Button)itemView.findViewById(R.id.Cbutton_start);
            edit=(Button)itemView.findViewById(R.id.offer_edit);
            delete=(Button)itemView.findViewById(R.id.offer_delete);



            offerAction = (LinearLayout) itemView.findViewById(R.id.offerAction);
        }
    }

    public  OfferAdapter(List<Offer> offerList , Context context)
    {
        this.context = context;
        this.offerList = offerList ;

    }
    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post1, parent, false);
        return new OfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OfferAdapter.MyViewHolder holder, int position) {

        final Offer offer = offerList.get(position);


        holder.offerAction.setVisibility(View.INVISIBLE);

        double timeN = Double.parseDouble(offer.getTime()); // 94.5
        int t = (int) timeN; //94
        holder.offertime.setText(""+t);

        holder.companyName.setText(offer.getCompanyName());
        holder.description.setText(offer.getEn_description());

        holder.edit.setVisibility(View.VISIBLE);
        holder.delete.setVisibility(View.VISIBLE);

        Picasso.with(context).load("https://speed-rocket.com/upload/offers/"
                +offer.getImage()).
                fit().centerCrop().into(holder.postimage);

        Picasso.with(context).load("https://speed-rocket.com/upload/company/"
                +offer.getCompanyLogo()).
                fit().centerCrop().into(holder.profileimage);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                Intent intent = new Intent(view.getContext(),AddOffer.class);
                intent.putExtra("offerId",offer.getId());
                view.getContext().startActivity(intent);

            }
        }); // edit button

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.warningDialog.show();
            }
        });

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

                        final Call<ResultModel> deleteOfferConnection= userApi.deleteOffer(offer.getId());

                        deleteOfferConnection.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                try
                                {
                                    Log.i("QP","companyId"+offer.getId());
                                    Toast.makeText(view.getContext(),"Item Deleted",Toast.LENGTH_LONG).show();
                                    progress.dismiss();
                                    Intent intent = new Intent(view.getContext(), OfferScreen.class);
                                    view.getContext().startActivity(intent);
                                } // try
                                catch (Exception e)
                                {
                                    Toast.makeText(view.getContext(),"Connection Faild",
                                            Toast.LENGTH_LONG).show();
                                    Log.i("QP","Error : "+e.toString()+"\n companyId"+offer.getId());
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


        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.warningDialog.cancel();
            }
        }); // no button


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }


}
