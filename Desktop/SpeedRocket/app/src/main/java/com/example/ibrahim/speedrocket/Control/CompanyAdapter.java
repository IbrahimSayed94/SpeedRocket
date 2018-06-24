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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.Company;
import com.example.ibrahim.speedrocket.Model.Product;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.AddCompany;
import com.example.ibrahim.speedrocket.View.Activites.CompanyScreen;
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
 * Created by Ibrahim on 5/20/2018.
 */

public class CompanyAdapter  extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {


    private List<Company> companyList;
    Context context;


    private ProgressDialog progress;
    private Handler handler;


    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);


    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button edit , delete , yes , no;
        TextView companyName ;
        CircleImageView companyLogo ;
        Dialog warningDialog;

        public MyViewHolder(View itemView) {
            super(itemView);

            edit = (Button) itemView.findViewById(R.id.item_edit);
            delete = (Button) itemView.findViewById(R.id.item_delete);
            companyName = (TextView) itemView.findViewById(R.id.item_companyName);
            companyLogo = (CircleImageView) itemView.findViewById(R.id.item_companyLogo);

            warningDialog = new Dialog(itemView.getContext()); // Context, this, etc.
            warningDialog.setContentView(R.layout.dialog_delete_item);

            yes = (Button) warningDialog.findViewById(R.id.yes);
            no = (Button) warningDialog.findViewById(R.id.no);
        } // constructor
    } // class myholder


    public  CompanyAdapter(List<Company> companyList , Context context)
    {

        this.context = context;
        this.companyList = companyList ;

    } // constructor

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_item, parent, false);
      /*  Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();*/

        return new CompanyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Company company = companyList.get(position);


        holder.companyName.setText(company.getEn_name());
        Log.i("QP",company.getLogo());

        Picasso.with(context).load("https://speed-rocket.com/upload/logo/"
                +company.getLogo()).
                fit().centerCrop().into(holder.companyLogo);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                Intent intent = new Intent(view.getContext(),AddCompany.class);
                intent.putExtra("companyId",company.getId());
                view.getContext().startActivity(intent);

            }
        }); // edit button


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                holder.warningDialog.show();
            }
        }); // delete button

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

                        final Call<ResultModel>  deleteCompanyConnection= userApi.deleteCompany(company.getId());

                        deleteCompanyConnection.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                try
                                {
                                    Log.i("QP","companyId"+company.getId());
                                     Toast.makeText(view.getContext(),"Item Deleted",Toast.LENGTH_LONG).show();
                                    progress.dismiss();
                                    Intent intent = new Intent(view.getContext(), CompanyScreen.class);
                                    view.getContext().startActivity(intent);
                                } // try
                                catch (Exception e)
                                {
                                    Toast.makeText(view.getContext(),"Connection Faild",
                                            Toast.LENGTH_LONG).show();
                                    Log.i("QP","Error : "+e.toString()+"\n companyId"+company.getId());
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
        });// no button



    } // onBindHolder




    @Override
    public int getItemCount() {
        return companyList.size();
    }


}
