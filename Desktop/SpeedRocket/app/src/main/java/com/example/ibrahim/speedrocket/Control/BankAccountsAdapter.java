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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.BankAccount;
import com.example.ibrahim.speedrocket.Model.Image;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.CompanyScreen;
import com.example.ibrahim.speedrocket.View.Activites.MyCashScreen;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim on 5/31/2018.
 */

public class BankAccountsAdapter extends  RecyclerView.Adapter<BankAccountsAdapter.myViewHolder>
{

    Context context ;
    List<BankAccount> bankAccountList ;

    private ProgressDialog progress;
    private Handler handler;



    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);

    public  class  myViewHolder extends RecyclerView.ViewHolder
    {
        TextView bankName ;
        Button  yes , no;

        ImageButton deleteBank;
        Dialog warningDialog;

        public myViewHolder(View itemView) {
            super(itemView);

            bankName = (TextView) itemView.findViewById(R.id.tx_bankName);
            deleteBank = (ImageButton) itemView.findViewById(R.id.bt_deleteBank);

            warningDialog = new Dialog(itemView.getContext()); // Context, this, etc.
            warningDialog.setContentView(R.layout.dialog_delete_item);

            yes = (Button) warningDialog.findViewById(R.id.yes);
            no = (Button) warningDialog.findViewById(R.id.no);

        } // constructor
    } // class of myViewHolder


    public  BankAccountsAdapter (Context context , List<BankAccount> bankAccountList)
    {
        this.context = context;
        this.bankAccountList = bankAccountList;
    } // param constructor

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_account, parent, false);
        return new BankAccountsAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        final BankAccount bankAccount = bankAccountList.get(position);

        holder.bankName.setText(bankAccount.getName().toString());

        holder.deleteBank.setOnClickListener(new View.OnClickListener() {
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

                        final Call<ResultModel> deleteBankAccountConnection= userApi.deleteBankAccount(bankAccount.getId());

                        deleteBankAccountConnection.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                try
                                {
                                    Toast.makeText(view.getContext(),"Item Deleted",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(view.getContext(), MyCashScreen.class);
                                    view.getContext().startActivity(intent);
                                    progress.dismiss();

                                } // try
                                catch (Exception e)
                                {
                                    Toast.makeText(view.getContext(),"Connection Faild",
                                            Toast.LENGTH_LONG).show();
                                    Log.i("QP","Error : "+e.toString()+"\n bankAccount"+bankAccount.getId());
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
    }

    @Override
    public int getItemCount() {
        return bankAccountList.size();
    }


} // class of BankAccountsAdapter
