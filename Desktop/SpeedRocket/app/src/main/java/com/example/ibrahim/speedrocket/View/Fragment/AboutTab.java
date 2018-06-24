package com.example.ibrahim.speedrocket.View.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.R;
import com.example.ibrahim.speedrocket.View.Activites.CompanyProfile;
import com.example.ibrahim.speedrocket.View.Activites.ProfileAccount;
import com.example.ibrahim.speedrocket.View.Activites.UpdateProfile;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;

public class AboutTab extends Fragment {

    String gender="" , language="" , interest="" , joinAt="" ;


    private ProgressDialog progress;
    private Handler handler;

    List <PersonalUser> user ;
    String firstName , lastName , type  ;
    String user_firstName , user_lastName , user_email , user_password , user_interest
            ,user_language , user_mobile , user_gender , user_joinAt , user_type
            ,user_companyName , user_companyCity , user_companyCountry
            , user_companyMobile;

    String  encodedimage;
    int id  , userID;

    TextView t_gender , t_language , t_interest , t_joinAt ;

    ImageButton update ;
    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_about_tab,
                container,false);
        t_gender = (TextView)view.findViewById(R.id.tab_gender);
        t_language = (TextView)view.findViewById(R.id.tab_language);
        t_interest = (TextView)view.findViewById(R.id.tab_interest);
        t_joinAt = (TextView)view.findViewById(R.id.tab_joinAt);

        update = (ImageButton)view.findViewById(R.id.updateButton);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                Intent intent = new Intent(getContext(), UpdateProfile.class);
                startActivity(intent);
            }
        });



        Intent iin= getActivity().getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            userID=(int)b.get("userID");
        }



        /*Toast.makeText(getActivity(),"userID : "+userID,
                Toast.LENGTH_LONG).show();*/

        getProfielData();

        // SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME,
               // Context.MODE_PRIVATE);

      /*  Toast.makeText(getActivity(),gender +"1 "+language+"0"+interest+" 1",
                Toast.LENGTH_LONG).show();*/
    //    getProfielData();

        return view;



    } // onCreatView

    public  void getProfielData()
    {
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Please Wait");
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                progress.dismiss();
                super.handleMessage(msg);
            }

        };

        progress.show();
        new Thread()
        {
            public void run()
            {
        //Retrofit
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
        retrofit2.Call<ResultModel> getProfileConnection =
                userApi.getProfileAccount(userID);

        getProfileConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {
                    user = response.body().getUser();
                    user_firstName=user.get(0).getFirstName();
                    user_lastName=user.get(0).getLastName();
                    user_email=user.get(0).getEmail();
                    user_gender=user.get(0).getGender();
                    user_password=user.get(0).getPassword();
                    user_interest=user.get(0).getInterest();
                    user_language=user.get(0).getLanguage();
                    user_mobile=user.get(0).getMobile();
                    user_joinAt=user.get(0).getCreated_at();
                    user_type=user.get(0).getType();
                    user_companyName=user.get(0).getCompanyName();
                    user_companyCity=user.get(0).getCompanyCity();
                    user_companyCountry=user.get(0).getCompanyCountry();
                    user_companyMobile=user.get(0).getCompanyMobile();

                   t_gender.setText(user_gender);
                   t_interest.setText(user_interest);
                   t_joinAt.setText(user_joinAt);
                   t_language.setText(user_language);

                  /*  Toast.makeText(getActivity(), "Connection Success\n"+
                                    "FirstName : "+user_firstName
                            ,Toast.LENGTH_LONG).show();*/
                    progress.dismiss();






                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection faild\n"
                        , Toast.LENGTH_LONG).show();
                progress.dismiss();


            }
        });
        //Retrofit

            }

        }.start();

    } // getProfileData Function



}
