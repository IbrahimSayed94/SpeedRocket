package com.example.ibrahim.speedrocket.Control;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.ibrahim.speedrocket.Model.ProductsWinner;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.PaymentScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ibrahim on 4/23/2018.
 */

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder>
{


    private List<ProductsWinner> offerList;
    private Context context ;

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        public Button buy_btn , more_info , dialog_cancel;
        public TextView product_name , company_Name , restDays , product_srCoin , wordDays
                , egWord , product_price , product_description , dialogue_product_description
                ,product_alreadyBuyed;
        public ImageView product_image ;
        public LinearLayout counterDaysLayout , srCoinLayout;
        public Dialog moreInfoDialg;

        public MyViewHolder(View itemView) {
            super(itemView);

            buy_btn = (Button) itemView.findViewById(R.id.productstart);
            more_info = (Button) itemView.findViewById(R.id.productInfo);
            product_image = (ImageView) itemView.findViewById(R.id.productimage);
            product_name = (TextView) itemView.findViewById(R.id.productname);
            company_Name = (TextView) itemView.findViewById(R.id.companNameProduct);
            counterDaysLayout = (LinearLayout) itemView.findViewById(R.id.counterDaysLayout);
            restDays = (TextView) itemView.findViewById(R.id.restDays);
            srCoinLayout = (LinearLayout) itemView.findViewById(R.id.srCoinOfProductLayout);
            product_srCoin = (TextView) itemView.findViewById(R.id.srCoinOfProduct);
            wordDays = (TextView) itemView.findViewById(R.id.wordDays);
            product_price = (TextView) itemView.findViewById(R.id.priceProduct);
            egWord = (TextView) itemView.findViewById(R.id.egWord);
            product_description = (TextView) itemView.findViewById(R.id.productDescription);
            product_alreadyBuyed = (TextView) itemView.findViewById(R.id.alreadyBuyed);

            moreInfoDialg = new Dialog(itemView.getContext()); // Context, this, etc.
            moreInfoDialg.setContentView(R.layout.activity_dialog_more_details);

            dialogue_product_description = (TextView) moreInfoDialg.findViewById(R.id.offerdetails_description);
            dialog_cancel = (Button)moreInfoDialg.findViewById(R.id.bt_cancel_dialog_moredetails);


        }
    } // class of MyViewHolder


    public  MyProductAdapter(List<ProductsWinner> offerList , Context context)
    {

        this.context = context;
        this.offerList = offerList ;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        /*Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();*/

        return new MyProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProductsWinner offer = offerList.get(position);




        holder.company_Name.setVisibility(View.VISIBLE);
        holder.counterDaysLayout.setVisibility(View.VISIBLE);
        holder.srCoinLayout.setVisibility(View.VISIBLE);
        holder.egWord.setVisibility(View.VISIBLE);
        holder.product_price.setVisibility(View.VISIBLE);


        if(offer.getStates() == 1)
        {
            holder.product_alreadyBuyed.setVisibility(View.VISIBLE);
        }

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);


        holder.buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                Intent intent = new Intent(view.getContext(),PaymentScreen.class);
                intent.putExtra("kind","product"); // kind may be product or coin
                intent.putExtra("price" ,offer.getPrice());
                intent.putExtra("productId",offer.getOfferId());
                view.getContext().startActivity(intent);

            }
        }); // buy button


        holder.more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.moreInfoDialg.show();
            }
        }); // moreInfo Button

        holder.dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.moreInfoDialg.cancel();
            }
        }); // cancel button

        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

           /*   Intent intent = new Intent(view.getContext(),PaymentScreen.class);
              view.getContext().startActivity(intent);*/

            }
        });

        int rest_Days = 7-offer.getDays();

        if(rest_Days == 0)
        {
            holder.restDays.setText("latest");
            holder.wordDays.setText("day");
            holder.restDays.setTextColor(Color.parseColor("#ffd700"));
            holder.wordDays.setTextColor(Color.parseColor("#ffd700"));
        }
        else if (rest_Days < 0)
        {

            holder.buy_btn.setVisibility(View.INVISIBLE);
            holder.more_info.setVisibility(View.INVISIBLE);
            holder.restDays.setVisibility(View.INVISIBLE);
            holder.wordDays.setText("expired");
            holder.wordDays.setTextColor(Color.parseColor("#ff0000"));


        }
        else if (rest_Days > 0)
        {
            holder.restDays.setText(rest_Days+"");
        }
        holder.product_name.setText(offer.getTitle());
        holder.product_srCoin.setText(offer.getSrCoin()+"");
        holder.product_price.setText(offer.getPrice()+"");
        holder.product_description.setText(offer.getEn_description());
        holder.dialogue_product_description.setText(offer.getEn_description());

        holder.company_Name.setText(offer.getCompanyName());

        Picasso.with(context).load("https://speed-rocket.com/upload/offers/"+offer.getImage())
                .fit().centerCrop().into(holder.product_image);


        //Picasso.with(context).load(offer.getPostImage()).fit().centerCrop().into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }


} // class of MyProductAdapter
