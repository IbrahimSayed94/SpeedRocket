package com.example.ibrahim.speedrocket.Control;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ibrahim.speedrocket.Model.Product;
import com.example.ibrahim.speedrocket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim on 6/10/2018.
 */

public class ChooseProductAdapter extends  RecyclerView.Adapter<ChooseProductAdapter.MyViewHolder>
{


    private List<Product> productlist;
    private Context context ;
    ArrayList <Integer>productsChoosen = new ArrayList<Integer>() ;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_productName ;
        CheckBox ch_product;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_productName = (TextView) itemView.findViewById(R.id.itemChooseProduct);
            ch_product = (CheckBox) itemView.findViewById(R.id.checkboxChooseProduct);


        }
    } // class myHolder

    public  ChooseProductAdapter (List<Product> productlist , Context context)
    {
        this.context = context;
        this.productlist = productlist ;
    } // constructor

    @Override
    public ChooseProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_product, parent, false);
      /*  Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();*/

        return new ChooseProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChooseProductAdapter.MyViewHolder holder, final int position) {


        final Product product = productlist.get(position);

        holder.txt_productName.setText(product.getEn_title());


        holder.ch_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.ch_product.isChecked() == true)
                {
                    productsChoosen.add(product.getId());


                        Intent intent = new Intent("custom-message");
                        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                        intent.putExtra("quantity", productsChoosen);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                }
                else if (holder.ch_product.isChecked() == false)
                {
                    Log.i("QP","position : "+position);

                    productsChoosen.remove(Integer.valueOf(product.getId()));
                    Intent intent = new Intent("custom-message");
                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                    intent.putExtra("quantity", productsChoosen);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }


}
