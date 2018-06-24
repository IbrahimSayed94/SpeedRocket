package com.example.ibrahim.speedrocket.Control;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahim.speedrocket.Model.Category;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.NavigationMenu;
import com.example.ibrahim.speedrocket.View.Activites.ProductMenuList;
import com.example.ibrahim.speedrocket.View.Activites.ProductsScreen;

import java.util.List;

/**
 * Created by Ibrahim on 5/17/2018.
 */

public class ProductMenuListAdapter extends   RecyclerView.Adapter<ProductMenuListAdapter.MyViewHolder>
{

    private Context context ;
    private List<Category> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView offerMenyItem ;
        public MyViewHolder(View itemView) {
            super(itemView);

            offerMenyItem = (TextView) itemView.findViewById(R.id.txt_offerMenuList);

        }
    }

    public ProductMenuListAdapter (List<Category> productList , Context context)
    {
        this.productList = productList;
        this.context = context;
    } // constructor

    @Override
    public ProductMenuListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemoffermenulist, parent, false);
        return new ProductMenuListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductMenuListAdapter.MyViewHolder holder, int position) {
        final Category category = productList.get(position);

        holder.offerMenyItem.setText(category.getEn_title());

        holder.offerMenyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(category.getType() == 0) {
                        Intent intent = new Intent(view.getContext(), ProductsScreen.class);
                        intent.putExtra("categoryId", category.getId());
                        view.getContext().startActivity(intent);
                    }
                    else
                        {
                            Intent intent = new Intent(view.getContext(), ProductMenuList.class);
                            intent.putExtra("cat", category.getId());
                            view.getContext().startActivity(intent);
                        }


            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
