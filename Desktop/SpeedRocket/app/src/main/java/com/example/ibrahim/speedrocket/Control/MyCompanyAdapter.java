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
import android.widget.TextView;

import com.example.ibrahim.speedrocket.Model.Company;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.OfferScreen;
import com.example.ibrahim.speedrocket.View.Activites.ProductScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ibrahim on 5/21/2018.
 */

public class MyCompanyAdapter extends RecyclerView.Adapter<MyCompanyAdapter.MyViewHolder> {

    private List<Company> companyList;
    Context context;


    private ProgressDialog progress;
    private Handler handler;


    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);
    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView companyLogo ;
        TextView companyName ;

        public MyViewHolder(View itemView) {
            super(itemView);

            companyLogo = (CircleImageView) itemView.findViewById(R.id.c_companyLogo);
            companyName = (TextView) itemView.findViewById(R.id.c_companyName);
        }
    }

    public  MyCompanyAdapter(List<Company> companyList , Context context)
    {

        this.context = context;
        this.companyList = companyList ;

    } // constructor


    @Override
    public MyCompanyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_company, parent, false);
      /*  Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();*/

        return new MyCompanyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyCompanyAdapter.MyViewHolder holder, int position) {
        final Company company = companyList.get(position);

        holder.companyName.setText(company.getEn_name());
        Picasso.with(context).load("https://speed-rocket.com/upload/company/"
                +company.getLogo()).
                fit().centerCrop().into(holder.companyLogo);

        holder.companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(company.getCheck() == 0) {
                    Intent intent = new Intent(view.getContext(), ProductScreen.class);
                    intent.putExtra("companyId", company.getId());
                    view.getContext().startActivity(intent);
                }
                else if (company.getCheck() == 1)
                {
                    Intent intent = new Intent(view.getContext(), OfferScreen.class);
                    intent.putExtra("companyId", company.getId());
                    view.getContext().startActivity(intent);
                }
            }
        });

        holder.companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(company.getCheck() == 0) {
                    Intent intent = new Intent(view.getContext(), ProductScreen.class);
                    intent.putExtra("companyId", company.getId());
                    view.getContext().startActivity(intent);
                }
                else if (company.getCheck() == 1)
                {
                    Intent intent = new Intent(view.getContext(), OfferScreen.class);
                    intent.putExtra("companyId", company.getId());
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }


}
