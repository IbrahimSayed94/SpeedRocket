package com.example.ibrahim.speedrocket.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibrahim.speedrocket.Model.Offer;
import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.UserInOffer;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.CompanyProfile;
import com.example.ibrahim.speedrocket.View.Activites.PostDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ibrahim on 3/11/2018.
 */

public class PostsAdapter extends  RecyclerView.Adapter<PostsAdapter.MyViewHolder>
{



    List <PersonalUser> user ;
    String user_firstName , user_lastName , user_companName  ;

    private Context context ;
    private ProgressDialog progress;
    private Handler handler;
    private  List<Offer> offerList;
    int o ;


    int topSrcoin = 0 ;
    int view = 0 ;


    List <Integer> idOfUsers = new ArrayList<Integer>();
    List <Integer> srCoinsOfUsers = new ArrayList<Integer>();
    private void preparePostData(int offerID)
    {



    } // function preparepostdata


    public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        public Button start_btn;
        public  int postID;
        public TextView companyName , srcoin , price , view , description ,offertime;
        public CircleImageView profileimage ;
        public ImageView postimage ;

        public MyViewHolder(View itemView) {
            super(itemView);

            companyName = (TextView) itemView.findViewById(R.id.companName);
            description = (TextView) itemView.findViewById(R.id.description);
            srcoin = (TextView) itemView.findViewById(R.id.srcoin);
            price = (TextView) itemView.findViewById(R.id.price);
            view = (TextView) itemView.findViewById(R.id.view);
            profileimage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            postimage = (ImageView) itemView.findViewById(R.id.post_image);
            offertime = (TextView) itemView.findViewById(R.id.offerTime);


            start_btn=(Button)itemView.findViewById(R.id.button_start);


        }
    }

    public  PostsAdapter(List<Offer> offerList , Context context)
    {
        this.context = context;
     this.offerList = offerList ;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Offer offer = offerList.get(position);



        holder.postID = offer.getId();

        double timeN = Double.parseDouble(offer.getTime()); // 94.5
        int t = (int) timeN; //94
        holder.offertime.setText(""+t);

       // getProfielData(offer.getUserId());

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);


        holder.start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(buttonClick);

                                    Intent intent = new Intent(view.getContext() , PostDetails.class);
                                    intent.putExtra("offerID",offer.getId());
                                    view.getContext().startActivity(intent);




            }
        });

        holder.postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(buttonClick);

                            Intent intent = new Intent(view.getContext() , PostDetails.class);
                            intent.putExtra("offerID",offer.getId());
                            view.getContext().startActivity(intent);

               /* Toast.makeText(view.getContext(),"OfferId from method : "+offer.getId()
                        +"  Offer Id  From firebase : "+o+"   topCoin : "+topSrcoin
                        ,Toast.LENGTH_LONG).show(); */

            }
        });




        holder.profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Intent intent = new Intent(v.getContext() , CompanyProfile.class);
                intent.putExtra("companyId",offer.getCompanyId());
                v.getContext().startActivity(intent);
            }
        });

        holder.companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Intent intent = new Intent(v.getContext() , CompanyProfile.class);
                intent.putExtra("companyId",offer.getCompanyId());
                v.getContext().startActivity(intent);
            }
        });

      //  getMaxSrcoinInThisOffer(offer.getId());

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild("srCoin");


        if(ref != null) {

            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    UserInOffer value = dataSnapshot.getValue(UserInOffer.class);
                    int offId = value.getOfferId();


                    o=offId;

                    if(offer.getId()  == offId)
                    {

                        topSrcoin = value.getSrCoin();
                        holder.srcoin.setText(topSrcoin+"");
                    } // i
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    UserInOffer value = dataSnapshot.getValue(UserInOffer.class);
                    int offId = value.getOfferId();


                    o = offId ;
                    if(offer.getId() == offId)
                    {
                        topSrcoin = value.getSrCoin();
                        holder.srcoin.setText(topSrcoin+"");

                    } // i
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if(ref != null) {



                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        view = 0 ;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserInOffer value = snapshot.getValue(UserInOffer.class);
                            int offId = value.getOfferId();
                            int usId = value.getUserId();
                            int vi = value.getView();

                            if (offId == offer.getId()) {
                               view = vi + view ;
                                holder.view.setText(view+"");
                            }



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            } // if

        } // if

        holder.companyName.setText(offer.getCompanyName());
        holder.srcoin.setText(0+"");
        holder.view.setText(0+"");
        holder.price.setText(offer.getPrice()+"");
        holder.description.setText(offer.getEn_description());

       Picasso.with(context).load("https://speed-rocket.com/upload/offers/"
               +offer.getImage()).
               fit().centerCrop().into(holder.postimage);



        Picasso.with(context).load("https://speed-rocket.com/upload/logo/"
                +offer.getCompanyLogo()).
                fit().centerCrop().into(holder.profileimage);



    }//on Bind



    @Override
    public int getItemCount() {
        return offerList.size();
    }

  /*/  public  void getProfielData(int userID)
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
                userApi.getProfileAccount(userID);

        getProfileConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {
                    user = response.body().getUser();
                    user_companName=user.get(0).getCompanyName();

                    if(response.body().getMessage().equals("true"))
                    {


                    } // if


                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {

            }
        });
        //Retrofit
    } // getProfileData Function*/




    public  void  getMaxSrcoinInThisOffer(final int offerId)
    {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild("srCoin");


        if(ref != null) {


            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    UserInOffer value = dataSnapshot.getValue(UserInOffer.class);
                    int offId = value.getOfferId();

                    Log.d("fire","OfferId from method : "+offerId
                    +"  Offer Id  From firebase : "+offId+"   topCoin : "+topSrcoin);

                    if(offerId == offId)
                    {
                        topSrcoin = value.getSrCoin();
                    } // i
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    UserInOffer value = dataSnapshot.getValue(UserInOffer.class);
                    int offId = value.getOfferId();


                    if(offerId == offId)
                    {
                        topSrcoin = value.getSrCoin();
                    } // i
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } // if
    } // function of getMaxSrcoinInThisOffer
} // class of PostsAdapter


