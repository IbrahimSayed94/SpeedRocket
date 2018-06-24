package com.example.ibrahim.speedrocket.Control;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.Post;
import com.example.ibrahim.speedrocket.Model.UserInOffer;
import com.example.ibrahim.speedrocket.Model.UserOnline;
import com.example.ibrahim.speedrocket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ibrahim on 3/12/2018.
 */

public class OffersAdapter extends  RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private List<PersonalUser> offerslist;
    private Context context;
    Dialog offerhim_dialogue;
    int  coinSend =0;
    private DatabaseReference mDatabase;
    int offerid , userid1 , userid2 , accept = 0, expired;
    String firstName , lastName ;

    Dialog dialog4;
    public EditText amountOfCoins ;

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView firstname , lastname , srcoin , country , expire;
        public ImageView countryimage ;

         public CircleImageView profileimage;
        Button offerhim ;
        Button sendofferhim , cancelofferhim ;

        public MyViewHolder(View itemView) {
            super(itemView);

            firstname = (TextView) itemView.findViewById(R.id.firstnameoffer);
            lastname = (TextView) itemView.findViewById(R.id.lastnameoffer);
            srcoin = (TextView) itemView.findViewById(R.id.offer);
            country = (TextView) itemView.findViewById(R.id.countryoffer);
            countryimage = (ImageView) itemView.findViewById(R.id.imagecountryoffer);
            profileimage= (CircleImageView) itemView.findViewById(R.id.profileimageoffer);
            offerhim = (Button) itemView.findViewById(R.id.offerhim);
            expire = (TextView) itemView.findViewById(R.id.expired);

            offerhim_dialogue = new Dialog(itemView.getContext()); // Context, this, etc.
            offerhim_dialogue.setContentView(R.layout.sendofferhim);
            offerhim_dialogue.setTitle("send offer him");


            sendofferhim = (Button) offerhim_dialogue.findViewById(R.id.sendofferhim);
            cancelofferhim = (Button) offerhim_dialogue.findViewById(R.id.cancelofferhim) ;


        }
    }

    public  OffersAdapter(List<PersonalUser> offerslist ,
                          Context context,int userid1,int offerid,String firstName,
                          String lastName)
    {
        this.offerslist = offerslist ;
        this.context = context ;
        this.userid1 = userid1;
        this.offerid = offerid;
        this.firstName = firstName;
        this.lastName=lastName;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offerdetails, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

      final PersonalUser  user = offerslist.get(position);
        holder.firstname.setText(user.getFirstName());
        holder.lastname.setText(user.getLastName());
        holder.srcoin.setText(user.getSrCoin()+"");

        Picasso.with(context).load("https://speed-rocket.com/upload/users/"
                +user.getImage()).
                fit().centerCrop().into(holder.profileimage);

        Log.i("image",user.getImage()+"");

        userid2 = user.getId();
        holder.offerhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                userid2 = user.getId();
               /* Toast.makeText(v.getContext(), "offerID : " + offerid
                                + "\nuser1 : " + userid1 +
                                "\nuser2 : " + userid2
                        , Toast.LENGTH_LONG).show();*/
                offerhim_dialogue.show();

            }
        });

        if(userid1 == userid2) {
            holder.offerhim.setVisibility(View.INVISIBLE);
        }
        else
            {
                holder.offerhim.setVisibility(View.VISIBLE);
            }

        holder.cancelofferhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerhim_dialogue.cancel();
            }
        });







        holder.sendofferhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amountOfCoins = (EditText) offerhim_dialogue.
                        findViewById(R.id.ed_amountOfCoins);

                String coins = amountOfCoins.getText().toString();


                if(coins.equals(""))
                {

                    Toast.makeText(v.getContext(),"Enter Coins",Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    coinSend =Integer.parseInt(String.valueOf(coins));
                    /*Toast.makeText(v.getContext(), "offerID : " + offerid
                                    + "\nuser1 : " + userid1 +
                                    "\nuser2 : " + userid2
                            , Toast.LENGTH_LONG).show();*/



                    UserOnline userOnline = new UserOnline(userid1, userid2, coinSend,accept,firstName
                    ,lastName,offerid);
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("online").push().setValue(userOnline);
                    offerhim_dialogue.cancel();
                }

            }
        });





        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild("srCoin");


        if(ref != null) {


          /*  ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserInOffer value = snapshot.getValue(UserInOffer.class);
                        int offId = value.getOfferId();
                        int usId = value.getUserId();
                        int ex = value.getExpired();


                        if (offId == offerid && usId == userid2 && ex == 1) {
                        holder.expire.setVisibility(View.VISIBLE);
                        }

                        else if (offId == offerid && usId == userid1 && ex == 0)
                            {
                                holder.expire.setVisibility(View.INVISIBLE);
                            }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/


        } // if
    }

    @Override
    public int getItemCount() {
        return offerslist.size();
    }




  /*  public void sendOfferHimDialogue(View view)
    {
       if(coinSend.equals(""))
       {
           Toast.makeText(view.getContext(),"Coins Null",Toast.LENGTH_LONG).show();
       }
        userid2 = user.getId();
        Toast.makeText(view.getContext(),"offerID : "+offerid
                        +"\nuser1 : "+userid1+
                        "\nuser2 : "+userid2
                ,Toast.LENGTH_LONG).show();



        String sId = String.valueOf(offerid);
        UserOnline userOnline = new UserOnline(userid1,userid2,coinSend);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("online").child(sId).setValue(userOnline);
    } // function send offerhim button

    public void cancelOfferHimDialogue(View view)
    {
         offerhim_dialogue.cancel();
    } // function cancel offerhim button*/

}
