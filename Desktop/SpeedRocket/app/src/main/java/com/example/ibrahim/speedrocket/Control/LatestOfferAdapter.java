package com.example.ibrahim.speedrocket.Control;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

import com.example.ibrahim.speedrocket.Model.CurrentOffers;
import com.example.ibrahim.speedrocket.Model.Post;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.PostDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ibrahim on 3/24/2018.
 */

public class LatestOfferAdapter extends RecyclerView.Adapter<LatestOfferAdapter.MyViewHolder>
        {


private List<CurrentOffers> postslist;
private Context context ;


public  class MyViewHolder extends RecyclerView.ViewHolder
{



    public TextView latest_offer_name , latest_offer_points ;
    public ImageView latest_offer_image ;
    public MyViewHolder(View itemView) {
        super(itemView);

        latest_offer_image = (ImageView) itemView.findViewById(R.id.latest_offer_image);
        latest_offer_name = (TextView) itemView.findViewById(R.id.latest_offer_name);

    }
}

    public  LatestOfferAdapter(List<CurrentOffers> postslist , Context context)
    {
        this.context = context;
        this.postslist = postslist ;
    }
    @Override
    public LatestOfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_latest_offer, parent, false);
        return new LatestOfferAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final LatestOfferAdapter.MyViewHolder holder, final int position) {

        final CurrentOffers currentOffers = postslist.get(position);




        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);
        holder.latest_offer_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
               /* Toast.makeText(view.getContext(), "Item " ,Toast.LENGTH_LONG).show();*/


                if(currentOffers.getFinish() == 0) {
                    Intent intent = new Intent(view.getContext(), PostDetails.class);
                    intent.putExtra("offerID", currentOffers.getOfferid());
                    view.getContext().startActivity(intent);
                }
                else if (currentOffers.getFinish() == 1)
                {
                    Toast.makeText(view.getContext(),"Offer Finished"
                            ,Toast.LENGTH_LONG).show();
                }


            }
        });

        holder.latest_offer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                /*Toast.makeText(view.getContext(), "Item ", Toast.LENGTH_LONG).show();*/

                Log.e("feed : ",currentOffers.getFinish()+"");

                if(currentOffers.getFinish() == 0) {
                    Intent intent = new Intent(view.getContext(), PostDetails.class);
                    intent.putExtra("offerID", currentOffers.getOfferid());
                    view.getContext().startActivity(intent);
                }
                else if (currentOffers.getFinish() == 1)
                {
                    Toast.makeText(view.getContext(),"Offer Finished"
                    ,Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.latest_offer_name.setText(currentOffers.getTitle());
        //holder.latest_offer_points.setText(post.getPoints());

        Picasso.with(context).load(currentOffers.getImage()).fit().centerCrop().into(holder.latest_offer_image);

    }



    @Override
    public int getItemCount() {
        return postslist.size();
    }
} // class of LatestOfferAdapter
