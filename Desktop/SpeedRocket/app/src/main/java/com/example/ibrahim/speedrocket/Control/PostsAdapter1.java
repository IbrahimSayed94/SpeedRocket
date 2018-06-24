package com.example.ibrahim.speedrocket.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.Offer;
import com.example.ibrahim.speedrocket.Model.Post;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ibrahim on 3/21/2018.
 */

public class PostsAdapter1 extends RecyclerView.Adapter<PostsAdapter1.MyViewHolder>
        {


            private Context context ;
            private ProgressDialog progress;
            private Handler handler;
            private  List<Offer> offerList;
            int o ;


            int topSrcoin = 0 ;
            int view = 0 ;



public  class MyViewHolder extends RecyclerView.ViewHolder
{


    public Button start_btn;
    public  int postID;
    public TextView companyName , srcoin , price , view , description ,offertime;
    public CircleImageView profileimage ;
    public ImageView postimage ;

    public MyViewHolder(View itemView) {
        super(itemView);

        companyName = (TextView) itemView.findViewById(R.id.Cname);
        description = (TextView) itemView.findViewById(R.id.Cdescription);
        srcoin = (TextView) itemView.findViewById(R.id.Csrcoin);
        price = (TextView) itemView.findViewById(R.id.Cprice);
        view = (TextView) itemView.findViewById(R.id.Cview);
        profileimage = (CircleImageView) itemView.findViewById(R.id.Cprofile_image);
        postimage = (ImageView) itemView.findViewById(R.id.Cpost_image);
        offertime = (TextView) itemView.findViewById(R.id.CofferTime);


        start_btn=(Button)itemView.findViewById(R.id.Cbutton_start);

    }
}


    public  PostsAdapter1(List<Offer> offerList , Context context)
    {
        this.context = context;
        this.offerList = offerList ;

    }
    @Override
    public PostsAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post1, parent, false);
        return new PostsAdapter1.MyViewHolder(itemView);
    }




            @Override
    public void onBindViewHolder(final PostsAdapter1.MyViewHolder holder, final int position) {
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

    } // on Bind



    @Override
    public int getItemCount() {
        return offerList.size();
    }
} // class of PostsAdapter