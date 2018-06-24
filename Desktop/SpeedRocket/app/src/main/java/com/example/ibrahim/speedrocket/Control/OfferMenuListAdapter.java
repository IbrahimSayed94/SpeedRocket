package com.example.ibrahim.speedrocket.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahim.speedrocket.Model.Category;
import com.example.ibrahim.speedrocket.Model.Offer;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.NavigationMenu;
import com.example.ibrahim.speedrocket.View.Activites.OfferMenuList;

import java.util.List;

/**
 * Created by Ibrahim on 5/7/2018.
 */

public class OfferMenuListAdapter  extends  RecyclerView.Adapter<OfferMenuListAdapter.MyViewHolder>
{


    private Context context ;
    private  List<String> offerList;

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView offerMenyItem ;
        public MyViewHolder(View itemView) {
            super(itemView);

            offerMenyItem = (TextView) itemView.findViewById(R.id.txt_offerMenuList);

        }
    } // class off MyViewHolder

    public OfferMenuListAdapter (List<String> offerlist , Context context)
    {
        this.offerList = offerlist;
        this.context = context;
    } // constructor

        @Override
    public OfferMenuListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemoffermenulist, parent, false);
            return new OfferMenuListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfferMenuListAdapter.MyViewHolder holder, int position) {

        final String offer = offerList.get(position);

        holder.offerMenyItem.setText(offer);

        holder.offerMenyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), NavigationMenu.class);
                intent.putExtra("chosenInterest",offer);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
} // class off OfferMenuListAdapter
