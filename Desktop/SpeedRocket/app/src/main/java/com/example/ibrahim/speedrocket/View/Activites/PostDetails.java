package com.example.ibrahim.speedrocket.View.Activites;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.ibrahim.speedrocket.Control.LatestOfferAdapter;
import com.example.ibrahim.speedrocket.Control.OffersAdapter;
import com.example.ibrahim.speedrocket.Model.CurrentOffers;
import com.example.ibrahim.speedrocket.Model.FakePeople;
import com.example.ibrahim.speedrocket.Model.Image;
import com.example.ibrahim.speedrocket.Model.Offer;
import com.example.ibrahim.speedrocket.Model.PersonalUser;
import com.example.ibrahim.speedrocket.Model.Post;
import com.example.ibrahim.speedrocket.Model.ResultModel;
import com.example.ibrahim.speedrocket.Model.UserApi;
import com.example.ibrahim.speedrocket.Model.UserInOffer;
import com.example.ibrahim.speedrocket.Model.UserOnline;
import com.example.ibrahim.speedrocket.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.speedrocket.View.Activites.LoginScreen.MY_PREFS_NAME;
import static java.util.Collections.sort;

public class PostDetails extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Integer>  idOfUsers1 = new ArrayList<Integer>();
    ArrayList<Integer>  srCoinsOfUsers1 = new ArrayList<Integer>();
    List <PersonalUser> user ;
    List <Offer> offer ;

    List <Image> images;
    private ProgressDialog progress;
    private Handler handler;
    String o_firstName , o_lastName , o_description , o_image ,o_title
            ,myFirstNameAccount , myLastNameAccount , o_created_at , congratFname
            ,congratLname , userProfileImage , o_logo;
    int myUserIDAccount ;
    int o_srcoin , o_view , o_userid , o_id , o_hours , o_minutes ;
    double o_price ;
    PersonalUser p;
    Button getOffer ;

    Dialog dialog , dialog1 , dialog2 , dialog3 , dialog4;

    int viewInhome , viewInhome1 , checkSrCoin = 0;
    TextView t_title , t_price , t_srcoin , t_dialog_description
            ,myfirstname , mylastname , t_companyname , t_createdat , t_myCoins
            ,congratFirstName , congratLastName , congratFirstName1 , congratLastName2;
    int srcoin = 0;
    CircleImageView myProfileImage;
    int offerID;


    String randomN ;
    TextView Thours , Tminutes , Tseconds ;
    private List<Post> offerList = new ArrayList<>();
    private List<CurrentOffers> offerList1 = new ArrayList<CurrentOffers>();
    public   List <Integer> idOfUsers ;
    private  List <Integer>  srCoinsOfUsers = new ArrayList<>();
    private  List<PersonalUser>  userList = new ArrayList<PersonalUser>();;
    private RecyclerView recyclerView , recyclerView_Latest_Offer;
    private  OffersAdapter mAdapter;
    private LatestOfferAdapter mAdapter1;
    int addOnCoins = 0 , winnerId = 0 , winnerCoins = 0;
    private  final int item_imgs[]={R.drawable.product,
            R.drawable.samsung1,
            R.drawable.samsung2,
            R.drawable.samsung3,
            R.drawable.samsung4,
            R.drawable.samsung5,
            };

    String  fakeFirstNames [] = {"Ali" , "Ahmed" , "Muhammed" ,
            "Hassan" , "Amr" , "Tamer" , "Mahmoud" , "Seif" , "abdo"};

    String fakeLastNames [] = {"Ali" , "Ahmed" , "mohamed" ,
            "Hassan" , "Amr" , "Tamer" , "Mahmoud" , "Seif" , "abdo"};

    String fakeFname , fakeLname ;
    int counterLatest = 0 ;

    Button cancel;
    int id  , userID , userCoin ,counterFake=0 , fakeid=1234 , addOffer=0;

    TextView nav_firstname , nav_lastname , nav_email , amountofcoinsrecvived
            ,nameofsender , nameofsender2;
    CircleImageView nav_profileimage , profileImage ;
    int sr , counter=0,users_counter=0 , fid , srCoin=1;
    String firstName , lastName , email , fname , lname , pImage;


    boolean  finish = false ;
    CircleImageView company_image ;
    private Retrofit retrofit;

    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F); // make animation on button

    Button Backdetails ;


    int f = 0 ;
    ArrayList<String> imagesUrl = new ArrayList<String>();


    UserOnline userOnline;

    String acceptOffer  , newsFeedTitle ;

    UserInOffer userInOffer;

    FakePeople fakePeople ;
    private String fake_firstName , fake_lastName , fake_Image , fake_email;
    private  int fake_id;


    // menu
    String  interest   ;
    boolean login ;
    public static  boolean log  ;
    Menu menu;
    NavigationView navigationView;
    //menu
    CountDownTimer timerFunction;
    int seconds , minutes , hours ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetails_login_navigation_menu);

        //menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        firstName = prefs.getString("firstName", "");//"No name defined" is the default value.
        lastName = prefs.getString("lastName", "");//"No name defined" is the default value.
        email = prefs.getString("email", "");//"No name defined" is the default value.
        userID = prefs.getInt("id", 0);
        interest = prefs.getString("interest", "");
        userProfileImage = prefs.getString("profileImage", "");
        login = prefs.getBoolean("login", false);

        if (login == false)
            navigationView.getMenu().findItem(R.id.nav_logout).setTitle("Login");

        else if (login == true)
            navigationView.getMenu().findItem(R.id.nav_logout).setTitle("Logout");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // menu

        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        firstName = prefs1.getString("firstName", "");//"No name defined" is the default value.
        lastName = prefs1.getString("lastName", "");//"No name defined" is the default value.
        email = prefs1.getString("email", "");//"No name defined" is the default value.
        userID=prefs1.getInt("id",0);
        userCoin=prefs1.getInt("coins",300);
        userProfileImage = prefs1.getString("profileImage","");


        dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.activity_dialog_more_details);
        dialog.setTitle(R.string.dialog_title);

        dialog1 = new Dialog(this); // Context, this, etc.
        dialog1.setContentView(R.layout.havenotenoughcoins);
        dialog1.setTitle("havenotenoughcoins");

        dialog2 = new Dialog(this); // Context, this, etc.
        dialog2.setContentView(R.layout.congratulation);
        dialog2.setTitle("congratulation");
        dialog2.setCancelable(false);
        dialog2.setCanceledOnTouchOutside(false);

         dialog3 = new Dialog(this); // Context, this, etc.
         dialog3.setContentView(R.layout.recieveofferfromhim);
         dialog3.setTitle("recieveofferfromhim");


        dialog4 = new Dialog(this); // Context, this, etc.
        dialog4.setContentView(R.layout.goodlucktryanotheroffer);
        dialog4.setTitle("goodlucktryanotheroffer");
        dialog4.setCancelable(false);
        dialog4.setCanceledOnTouchOutside(false);

         t_title=(TextView) findViewById(R.id.offerdetails_title);
        t_price=(TextView) findViewById(R.id.offerdetails_price);
       // t_srcoin=(TextView) findViewById(R.id.offerdetails_srcoin);
        t_dialog_description=(TextView)dialog.findViewById(R.id.offerdetails_description);
        t_companyname=(TextView) findViewById(R.id.offerdetails_companname);
        t_createdat=(TextView) findViewById(R.id.offerdetails_createdat);
        t_myCoins=(TextView) findViewById(R.id.offerdetails_mycoins);

        congratFirstName = (TextView)dialog2.findViewById(R.id.congrat_firstname);
        congratLastName = (TextView)dialog2.findViewById(R.id.congrat_secondname);

        congratFirstName1 = (TextView)dialog4.findViewById(R.id.congrat_firstname1);
        congratLastName2 = (TextView)dialog4.findViewById(R.id.congrat_secondname1);

        amountofcoinsrecvived = (TextView) dialog3.findViewById(R.id.amountofcoinRecived);
          nameofsender = (TextView) dialog3.findViewById(R.id.nameofsender);
        nameofsender2 = (TextView) dialog3.findViewById(R.id.nameofsender2);


        t_myCoins.setText(userCoin+"");


        LinearLayout lUp , lDown ;

        lUp = (LinearLayout) findViewById(R.id.linearUserUp);
        lDown = (LinearLayout) findViewById(R.id.linearUserDowun);

        if(userID != 0)
        {
          lUp . setVisibility(View.VISIBLE);
          lDown . setVisibility(View.VISIBLE);
        } // if user not login





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Thours = (TextView) findViewById(R.id.txt_hours);
        Tminutes = (TextView) findViewById(R.id.txt_minutes);
        Tseconds= (TextView) findViewById(R.id.txt_seconds);


        myfirstname=(TextView) findViewById(R.id.myfirstname_offerdetails);
       mylastname=(TextView) findViewById(R.id.mylastname_offerdetails);

        myProfileImage=(CircleImageView) findViewById(R.id.myProfileImage);
        Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                +userProfileImage).
                fit().centerCrop().into(myProfileImage);

        SharedPreferences pref1s = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        myFirstNameAccount = pref1s.getString("firstName", "");//"No name defined" is the default value.
        myLastNameAccount = pref1s.getString("lastName", "");//"No name defined" is the default value.
        myUserIDAccount=pref1s.getInt("id",0);

        myfirstname.setText(myFirstNameAccount);
        mylastname.setText(myLastNameAccount);

        myProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                Intent intent = new Intent(getApplicationContext(),MyCompanyProfile.class);
                intent.putExtra("userID",myUserIDAccount);
                startActivity(intent);
            }
        });

        getOffer = (Button) findViewById(R.id.getOffer);



        cancel = (Button) findViewById(R.id.bt_cancel_dialog_moredetails);
        // start recycle view code of item post_list
        recyclerView = (RecyclerView) findViewById(R.id.item_offerlist);
        recyclerView_Latest_Offer = (RecyclerView) findViewById(R.id.list_latest_offer);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();


        if(b!=null)
        {
            offerID=(int)b.get("offerID");
        }






        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {

                Random r = new Random();
                int i = r.nextInt(fakeFirstNames.length) + 0; // 0 min . f.lenght max
                int j = r.nextInt(fakeLastNames.length) + 0; // 0 min . f.lenght max

                fakeFname = fakeFirstNames[i];
                fakeLname = fakeLastNames[j];

                if(finish == false && srCoin < o_srcoin && userID == 7)
           //  fakePeople();
             Log.i("FakeC","hello");

            }
        }, 60*1000, 120*1000);

        getOfferData();



        mAdapter = new OffersAdapter(userList, getApplicationContext(),userID,
                offerID,firstName,lastName);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());       // mLayoutManager.setReverseLayout(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);



        mAdapter1 = new LatestOfferAdapter(offerList1 , getApplicationContext());
        RecyclerView.LayoutManager m1LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_Latest_Offer.setLayoutManager(m1LayoutManager);
        recyclerView_Latest_Offer.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Latest_Offer.setAdapter(mAdapter1);

        company_image = (CircleImageView) findViewById(R.id.destails_companyimage);





        preparePostData2();


        RatingBar ratingBar = (RatingBar) findViewById(R.id.myRatingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#E65100"), PorterDuff.Mode.SRC_ATOP);
        // end recycle view code of item post_list


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("online");

        //Query offerQuery = ref.orderByChild(phoneNo).equalTo("+923336091371");


        if(ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                            String k = singleSnapshot.getKey();
                            userOnline = singleSnapshot.getValue(UserOnline.class);

                            Log.e("online",k+"\n"+userOnline.getOfferid());


                            String userid = String.valueOf(userID);




                            if (offerID == userOnline.getOfferid() ) {
                                userOnline = singleSnapshot.getValue(UserOnline.class);



                                String userid2 = String.valueOf(userOnline.getUserid2());
                                String userid1 = String.valueOf(userOnline.getUserid1());
                                acceptOffer = String.valueOf(userOnline.getAccept());
                                if (userid.equals(userid2)) {


                                    amountofcoinsrecvived.setText(String.valueOf(userOnline.getAmount()));
                                    nameofsender.setText(userOnline.getFirstName());
                                    nameofsender2.setText(userOnline.getLastName());
                                /*Toast.makeText(getApplicationContext(), "HIin\n" +
                                                userid2 + " " + userid
                                                + "\n"
                                        , Toast.LENGTH_LONG).show();*/


                                    if (!isFinishing() && !acceptOffer.equals("1")) {
                                        dialog3.show();
                                    }

                                    if(acceptOffer.equals("1"))
                                    {
                                        getOffer.setVisibility(View.INVISIBLE);

                                    }
                                } // if

                                else if (userid.equals(userid1))
                                {
                                    if(acceptOffer.equals("1"))
                                    {
                                        t_myCoins.setText(userCoin-userOnline.getAmount()+"");
                                        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                        SharedPreferences.Editor prefEditor = settings.edit();
                                        prefEditor.putInt("coins", userCoin-userOnline.getAmount());

                                        //Retrofit
                                        OkHttpClient client = new OkHttpClient.Builder()
                                                .connectTimeout(100, TimeUnit.SECONDS)
                                                .readTimeout(100,TimeUnit.SECONDS).build();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("https://speed-rocket.com/api/")
                                                .client(client)
                                                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                                .build();

                                        UserApi userApi = retrofit.create(UserApi.class);
                                        Call<ResultModel> updateUserCoinConnection =
                                                userApi.updateUserCoin(userID,userCoin-userOnline.getAmount());
                                        updateUserCoinConnection.enqueue(new Callback<ResultModel>() {
                                            @Override
                                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                                                try {
                   /* Toast.makeText(getApplicationContext(), "Done"
                        , Toast.LENGTH_LONG).show(); */


                                                } catch (Exception e) {
                                                    Toast.makeText(getApplicationContext(), "exception" + (e.toString()), Toast.LENGTH_LONG).show();

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResultModel> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Connection faild\n" , Toast.LENGTH_LONG).show();

                                            }
                                        });
                                        //Retrofit
                                    }
                                }
                       /*     Toast.makeText(getApplicationContext(), "HIout\n" +
                                    userid2 + " " + userid, Toast.LENGTH_LONG).show();*/


                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }); // offerhim
        } // if condition


       getUserData();

        countViews();


    }

    private void preparePostData()
    {


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
        retrofit2.Call<ResultModel> enterInOfferConnection =
                userApi.getEnteredOnOffer(offerID);

        enterInOfferConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {


                    List <UserInOffer> users;
                    users = response.body().getUsers();
                    int userId ;

                    String message = response.body().getMessage();

                    if(message.equals("true"))
                    {
                  for(int i = 0 ; i< users.size() ; i++)
                    {
                        userId = users.get(i).getUserId();
                        idOfUsers.add(userId);

                    } // for loop*/


                    }
                    getUserData();
                   /* Toast.makeText(getApplicationContext(), "Connection Success\n"
                                    +"\nmessage : "+message+"  OfferID : "+offerID + "   UserIDListSize : "+idOfUsers.size()
                            ,Toast.LENGTH_LONG).show();*/



                } catch (Exception e) {
                  /*  Toast.makeText(getApplicationContext(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();*/


                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n" +
                                "Exception"+t.toString()
                        , Toast.LENGTH_LONG).show();




            }
        });
        //Retrofit

    } // function preparepostdata

    private void preparePostData2()

    {
        int profile_im = (R.drawable.profile_image);
        int post_im = (R.drawable.post_image);
        final int country_im = (R.drawable.f1);

        int profile_im2 = (R.drawable.p1);
        int profile_im3= (R.drawable.p2);
        int profile_im4 = (R.drawable.p3);

        final int country_im2 = (R.drawable.redicon);
        final int country_im3 = (R.drawable.greenicon);
        final int country_im4= (R.drawable.orangeicon);


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild(phoneNo).equalTo("+923336091371");


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                UserInOffer value = dataSnapshot.getValue(UserInOffer.class);

                // String key = dataSnapshot.getKey();

                int offerid , userid , position , view;
                offerid = value.getOfferId();
                userid = value.getId();
                position = value.getPosition();
                view = value.getView();
                f = value.getFinish();
                newsFeedTitle = value.getOfferTitle();



                if(position == 0 &&offerid != offerID && userid == userID && view == 1 && f==0) {
                    CurrentOffers currentOffers = new CurrentOffers(country_im2,newsFeedTitle,offerid,f);
                    offerList1.add(currentOffers);
                    mAdapter1.notifyDataSetChanged();
                }
               else if(position == 1 &&offerid != offerID && userid == userID && view == 1 && f==0) {
                    CurrentOffers currentOffers = new CurrentOffers(country_im3, newsFeedTitle,offerid,f);
                    offerList1.add(currentOffers);
                    mAdapter1.notifyDataSetChanged();
                }
                else if(position == 0 &&offerid != offerID && userid == userID && view == 1 && f==1) {
                    CurrentOffers currentOffers = new CurrentOffers(country_im2, newsFeedTitle,offerid,f);
                    offerList1.remove(currentOffers);
                    mAdapter1.notifyDataSetChanged();
                }
                else if(position == 1 &&offerid != offerID && userid == userID && view == 1 && f==1) {
                    CurrentOffers currentOffers = new CurrentOffers(country_im2, newsFeedTitle,offerid,f);
                    offerList1.remove(currentOffers);
                    mAdapter1.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    } // function Latest


    private  void  getUserData()
    {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild("srCoin");


        if(ref != null) {


            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    UserInOffer value = dataSnapshot.getValue(UserInOffer.class);

                    // String key = dataSnapshot.getKey();

                    sr = value.getSrCoin();

                    fname = value.getFirstName();
                    lname = value.getLastName();
                    fid = value.getUserId();
                    pImage = value.getProfileImage();



                    int offId = value.getOfferId();

                    if (offerID == offId ) {
                        p = new PersonalUser(sr, fname, lname, fid,pImage);
                        userList.add(0,p);
                        srCoinsOfUsers.add(0,sr);
                        mAdapter.notifyDataSetChanged();
                    }


                    if(userList.size() > 0) {
                        congratFname = userList.get(0).getFirstName();
                        congratLname = userList.get(0).getLastName();

                    winnerId = userList.get(0).getId();
                    winnerCoins = userList.get(0).getSrCoin();

                    }
                    congratFirstName.setText(congratFname);
                    congratLastName.setText(congratLname);
                    congratFirstName1.setText(congratFname);
                    congratLastName2.setText(congratLname);

              /* Toast.makeText(getApplicationContext(),"UserInOffer : \n"
                +value+"\n"+value.getSrCoin(),Toast.LENGTH_LONG).show();*/
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    UserInOffer valueChanged = dataSnapshot.getValue(UserInOffer.class);

                    final int country_im2 = (R.drawable.redicon);
                    final int country_im3 = (R.drawable.greenicon);
                    final int country_im4 = (R.drawable.orangeicon);

                    int position = valueChanged.getPosition();
                    int offerid = valueChanged.getOfferId();
                    int userid = valueChanged.getId();
                    newsFeedTitle = valueChanged.getOfferTitle();



                    if (position == 1 && offerid != offerID && userid == userID) {
                        for (int i = 0; i < offerList1.size(); i++) {

                          /*  Toast.makeText(getApplicationContext(), "Latest : " +
                                    "\n" + "offerlistID  " + offerList1.get(i).getOfferid()
                                    + "\nofferid : " + offerid, Toast.LENGTH_LONG).show();*/
                            if (offerid == offerList1.get(i).getOfferid()) {
                                offerList1.remove(i);
                                mAdapter1.notifyItemRemoved(i);

                               /* Toast.makeText(getApplicationContext() , "removed1",
                                        Toast.LENGTH_LONG).show();*/
                            }
                        }

                        CurrentOffers currentOffers = new CurrentOffers(country_im3, newsFeedTitle, offerid,f);
                        offerList1.add(currentOffers);
                        mAdapter1.notifyDataSetChanged();
                   /* Toast.makeText(getApplicationContext(),"Position : "+
                            position,Toast.LENGTH_LONG).show();*/
                    } else if (position == 0 && offerid != offerID && userid == userID) {
                        for (int i = 0; i < offerList1.size(); i++) {

                            /*Toast.makeText(getApplicationContext(), "Latest : " +
                                    "\n" + "offerlistID  " + offerList1.get(i).getOfferid()
                                    + "\nofferid : " + offerid, Toast.LENGTH_LONG).show();*/
                            if (offerid == offerList1.get(i).getOfferid()) {
                                offerList1.remove(i);
                                mAdapter1.notifyItemRemoved(i);
                            }
                        }
                        CurrentOffers currentOffers = new CurrentOffers(country_im2, newsFeedTitle, offerid,f);
                        offerList1.add(currentOffers);
                        mAdapter1.notifyDataSetChanged();
                       /* Toast.makeText(getApplicationContext(), "Position : " +
                                position, Toast.LENGTH_LONG).show();*/
                    }


                   /* Toast.makeText(getApplicationContext(),"Position : "+
                            position
                            +"\n FireOffer : "+offerid +" Offer : "+offerID
                                    +"\n FireUser : "+userid +" User : "+userID
                            ,Toast.LENGTH_LONG).show();*/


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } // if check if firebase null


    } // get user data



    public void button_MoreDetails(View view)
    {

        dialog.show();

    } // function for open dialogue display more details about product

    public void CancelDialogueMoreDetails(View view)
    {
        dialog.cancel();
    }  // function of CancelDialogueMoreDetails



    public void Timer (double seconds1,double minutes1)
    {
        double l1 = seconds1  * 1000 ;
        double l2 = minutes1 * 60 * 1000;
        double l = l1 + l2 ;
        timerFunction=  new CountDownTimer((long) l, 1000) {
            @Override
            public void onTick(long l) {
              seconds = (int) (l/1000);     // where l = 7,200,000
              minutes = seconds / 60 ;
              hours = minutes / 60 ;
             seconds = seconds % 60;
             if(minutes >= 60) {
                 minutes = minutes % 60;
             }

             Tseconds.setText(""+seconds);
             Tminutes.setText(""+minutes);
             Thours.setText(""+hours);

            }

            @Override
            public void onFinish()
            {
                finish = true ;

                final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference ref = database.child("UserInOffer");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {
                        for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                            String key = snapshot.getKey();
                            UserInOffer value = snapshot.getValue(UserInOffer.class);

                            int ofId = value.getOfferId();
                            Log.e("hello", "ofId : "
                                    + ofId);
                            if (offerID == ofId)
                            {
                                ref.child(key).setValue(null);
                               // ref.child(key).child("finish").setValue(1);

                            } // if
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                    //show dialog
                if (!isFinishing()) {


                    if(userID == winnerId) {



                        dialog2.show();

                    }
                    else {
                        dialog4.show();

                    }
                   // PostDetails.this.finish();
                }



            }
        }.start();

    } // function of Timer

    public void company_profile_iamge(View view)
    {
        view.startAnimation(buttonClick);

        Intent intent = new Intent(view.getContext() , CompanyProfile.class);
        intent.putExtra("userID",o_userid);
        view.getContext().startActivity(intent);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation_menu, menu);

        menu = navigationView.getMenu();

        if(userID != 0)
        {
            menu.findItem(R.id.nav_myProducts).setVisible(true);
            menu.findItem(R.id.nav_cPanal).setVisible(true);
        } // if


        nav_firstname = (TextView) findViewById(R.id.menu_firstname);
        nav_lastname = (TextView) findViewById(R.id.menu_lastname);
        nav_email = (TextView) findViewById(R.id.menu_email);

        nav_profileimage = (CircleImageView) findViewById(R.id.nav_profileimage);

        nav_firstname.setText(firstName);
        nav_lastname.setText(lastName);
        nav_email.setText(email);


        Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/users/"
                +userProfileImage).
                fit().centerCrop().into(nav_profileimage);
        nav_profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyCompanyProfile.class);
                intent.putExtra("userID",userID);
                startActivity(intent);


            }
        });
        MenuItem searchItem = menu.findItem(R.id.action_settings);

        SearchManager searchManager = (SearchManager) PostDetails.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(PostDetails.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);

    }  // control search icon on toolbar

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);

        Menu m=nv.getMenu();




        int id = item.getItemId();

        //  for(int i =1 ; i<= numOfCategoryItems ; i++) {



        if (id == R.id.nav_category) {


            Intent intent = new Intent(getApplicationContext(),ProductMenuList.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_myProducts)
        {
            Intent intent = new Intent(getApplicationContext(), MyWinnerProducts.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_offers)
        {
            Intent intent = new Intent(getApplicationContext(),OfferMenuList.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_country) {

            return true;

        } else if (id == R.id.nav_language) {
            Intent intent = new Intent(getApplicationContext(),ChangeLanguage.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_logout) {


            if (m.findItem(R.id.nav_logout).getTitle().toString().equals("Logout")) {
                m.findItem(R.id.nav_logout).setTitle("Login");

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.remove("firstName");
                editor.remove("lastName");
                editor.remove("email");
                editor.remove("id");
                editor.remove("coin");
                editor.remove("profileImage");
                editor.remove("interest");
                editor.putBoolean("login", false);
                editor.apply();

                menu.findItem(R.id.nav_myProducts).setVisible(false);
                menu.findItem(R.id.nav_cPanal).setVisible(false);

                nav_profileimage.setVisibility(View.INVISIBLE);
                nav_firstname.setText("");
                nav_lastname.setText("");
                nav_email.setText("");
                log = false;

            } else {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), NavigationMenu.class);
            startActivity(intent);
            return true;

        }  else if (id == R.id.nav_buycoins) {
            Intent intent = new Intent(getApplicationContext(), BuyCoins.class);
            startActivity(intent);
            return true;

        }
        else if (id == R.id.nav_cPanal) {
            if(userID == 0)
            {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            } // check if login 0 ---> user not login
            else {
                Intent intent = new Intent(getApplicationContext(), MyCompanyProfile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        //  }
        return true;
    }

    public  void menu()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        menu = navigationView.getMenu();



        navigationView.setNavigationItemSelectedListener(this);

    }



    public  void getOfferData()
    {
        progress = new ProgressDialog(this);
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
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://speed-rocket.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        final UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> getOfferConnection =
                userApi.getOfferDetails(offerID);




        getOfferConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                try {

                    offer=response.body().getOffers();
                    o_description=offer.get(0).getEn_description();
                    o_price=offer.get(0).getPrice();
                    o_srcoin=offer.get(0).getSrcoin();
                    o_title=offer.get(0).getEn_title();
                    o_userid=offer.get(0).getUserId();
                    o_hours=offer.get(0).getHours();
                    o_minutes=offer.get(0).getMinutes();
                    o_created_at=offer.get(0).getCreated_at();
                    o_logo=offer.get(0).getCompanyLogo();


                    images=response.body().getImages();
                    for(int i = 0 ; i < images.size() ; i++)
                    {
                        imagesUrl.add(images.get(i).getImage());
                    }

                    imageSlider(imagesUrl);

                    Picasso.with(getApplicationContext()).load("https://speed-rocket.com/upload/logo/"
                            +o_logo).
                            fit().centerCrop().into(company_image);
                    String time = response.body().getTime();


                    double timeN = Double.parseDouble(time); // 94.5

                    int n = (int) timeN; //5

                    double hours=0 , minutes = 0,seconds=0;

                    minutes = n;
                    seconds =(timeN-n) *60;

                   // hours = timeN / 60 ;
                   /* if(timeN >= 60) {
                        minutes = timeN % 60;
                    }*/

                 /*   else
                        {
                            minutes = timeN;
                            hours = 0 ;
                        }*/

                    Timer(seconds,minutes);
                 //   getCompanyNameOnline(o_userid);



                   /* Toast.makeText(getApplicationContext(), "Connection Success\n"
                            +"CreatedAt : "+hours+":"+minutes
                            ,Toast.LENGTH_LONG).show();*/


                    String companyName = offer.get(0).getCompanyName();

                    t_companyname.setText(companyName);

                    t_title.setText(o_title);
                   t_price.setText(o_price+"");
                 //  t_srcoin.setText(o_srcoin+"");

                    //int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                    String thisMonth , thisDay , thisYear;
                    //int thisDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    Calendar rightNow = Calendar.getInstance();
                    SimpleDateFormat df1 = new SimpleDateFormat("dd");
                    SimpleDateFormat df2 = new SimpleDateFormat("MM");
                    SimpleDateFormat df3 = new SimpleDateFormat("YYYY");
                    thisDay = df1.format(rightNow.getTime());
                    thisMonth = df2.format(rightNow.getTime());
                    thisYear = df3.format(rightNow.getTime());


                   t_createdat.setText(thisDay+"/"+thisMonth+"/"+thisYear);
                 try {
                     t_dialog_description.setText(o_description);
                 }
                 catch (Exception e)
                 {
                     /*Toast.makeText(getApplicationContext(),"hi\n"
                     +e.toString(),Toast.LENGTH_LONG).show();*/
                 }

                } catch (Exception e) {
                   /* Toast.makeText(getApplicationContext(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();*/


                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n" +
                                "Exception"+t.toString()
                        , Toast.LENGTH_LONG).show();
                progress.dismiss();


            }
        });
        //Retrofit

            }

        }.start();



    } // getProfileData Function

    public  void getCompanyNameOnline(final int id)
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
                userApi.getProfileAccount(id);

        getProfileConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {

                try {
                    user = response.body().getUser();
                    String companyName = user.get(0).getCompanyName();

                    t_companyname.setText(companyName);
                   /* Toast.makeText(getApplicationContext(), "Connection Success\n"
                            +"Company Name : "+companyName+" id : "+id
                            ,Toast.LENGTH_LONG).show();*/



                } catch (Exception e) {
                  /*  Toast.makeText(getApplicationContext(), "Connection Success\n" +
                                    "Exception"+e.toString()
                            ,Toast.LENGTH_LONG).show();*/
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n" +
                                "Exception"+t.toString()
                        , Toast.LENGTH_LONG).show();


            }
        });
        //Retrofit
    } // getProfileData Function

    public void getOffer(View view)
    {

        view.startAnimation(buttonClick);
        countViews();

        if(userID == 0)
        {
            Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
            startActivity(intent);
        } // if user not login*/

       else
        {
            addOffer = 1;

            Random rand = new Random();

            int  n = rand.nextInt(3000) + 1;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("QP","hello");
                    getLastSrCoin();                }
            }, n);


        } // else





    } // get offer function button



    public void cancelDialogHaveNotEnoughCoins(View view)
    {
        dialog1.cancel();
    } // cancel dialog haven't enough coins


    public void CancelReciveOfferFromHimDialogue(View view)
    {

        String offerid = String.valueOf(offerID);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = database.child("online");

        //Query offerQuery = ref.orderByChild(phoneNo).equalTo("+923336091371");


        if(ref != null) {
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                        String key = singleSnapshot.getKey();
                        userOnline = singleSnapshot.getValue(UserOnline.class);

                        int offerid , userid ;
                        offerid = userOnline.getOfferid();
                        userid = userOnline.getUserid2();
                        Log.i("QP",offerid+" : "+userid);

                        if(userID == userid && offerID == offerid)
                        {
                            ref.child(key).setValue(null);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


   DatabaseReference dbNode =
                FirebaseDatabase.getInstance().getReference().getRoot().child("online")
                        .child(offerid);
        dbNode.setValue(null);
             dialog3.cancel();
    }  // cancel dialog CancelReciveOfferFromHimDialogue


    public void fakePeople ()
    {



        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("fakePeople");
        //Query offerQuery = ref.orderByChild("srCoin");

        Random rand = new Random();

        int  randomNumber = rand.nextInt(53) + 1;
         randomN = String.valueOf(randomNumber);

        if(ref != null) {


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            String num = snapshot.getKey().toString();
                            if(randomN.equals(num)) {
                                FakePeople value = snapshot.getValue(FakePeople.class);
                               fake_firstName = value.getFirstName();
                                fake_lastName = value.getLastName();
                                 fake_Image = value.getProfileImage();
                                 fake_email = value.getEmail();
                                  fake_id = value.getId();


                                Log.i("fakePeople", num + " : " +
                                        fake_firstName + " : " + fake_lastName + " : "
                                        + fake_Image + " : "+randomN+" : "
                                +fake_id);
                                if(!srCoinsOfUsers.isEmpty()) {

                                    addOnCoins++;
                                    srCoin = srCoinsOfUsers.get(0);
                                    srCoin = 1 + srCoin;
                                }
                                else
                                {

                                    srCoin = 1 + srCoin;
                                }

                                    userInOffer =
                                            new UserInOffer(fake_id, fake_id, offerID, srCoin, 0, fake_firstName
                                                    , fake_lastName, fake_email, 1,0,fake_Image,o_title,0);
      /*  Toast.makeText(getApplicationContext(),"Sr : "
        +srCoin,Toast.LENGTH_LONG).show();
         userInOfferList.add(userInOffer);*/

                                congratFname = firstName;
                                congratLname = lastName;
                                winnerId = userID;
                                winnerCoins = srCoin;
                                congratFirstName.setText(congratFname);
                                congratLastName.setText(congratLname);
                                congratFirstName1.setText(congratFname);
                                congratLastName2.setText(congratLname);

                                DatabaseReference mDatabase;
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                final String key = mDatabase.push().getKey();
                                mDatabase.child("UserInOffer").child(key).setValue(userInOffer);


                                mDatabase.child("UserInOffer").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                                            String userid = String.valueOf(fake_id);
                                            String offerid = String.valueOf(offerID);

                                            String userIdFire = data.child("id").getValue().toString();
                                            String offerIdFire = data.child("offerId").getValue().toString();


                                            if (userid.equals(userIdFire) && offerid.equals(offerIdFire)) {
                                                data.getRef().child("position").setValue(1);
                                                break;

                                            } else if (!userid.equals(userIdFire) && offerid.equals(offerIdFire))
                                                data.getRef().child("position").setValue(0);

                 /*  Toast.makeText(getApplicationContext(),"Update : \n"
                    +"child id : "+data.child("id").getValue() + ":user id : "+userid +"\n"
                                   +"child offer : "+data.child("offerId").getValue()
                                   + " offerid : "+offerid
                    , Toast.LENGTH_LONG).show();*/
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                break;
                            }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } // if




        /////////////////////////////////// make offer



        }




    public void AcceptReciveOfferFromHimDialogue(View view)
    {


        DatabaseReference mDatabase;
        String sId = String.valueOf(offerID);

        final int userid1 = (userOnline.getUserid1());
        final int userid2 = (userOnline.getUserid2());
        final int coinSend = (userOnline.getAmount());



        DatabaseReference database1 = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref1 = database1.child("online");

        //Query offerQuery = ref.orderByChild(phoneNo).equalTo("+923336091371");


        if(ref1 != null) {
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                            String k = singleSnapshot.getKey();
                            userOnline = singleSnapshot.getValue(UserOnline.class);

                            Log.e("onlineAccept",k+"\n"+userOnline.getOfferid());



                            if (offerID == userOnline.getOfferid() &&
                                    userid1 == userOnline.getUserid1() &&
                                    userid2 == userOnline.getUserid2())
                            {
                                userOnline = new UserOnline(userid1, userid2, coinSend,1,firstName,lastName,offerID);
                                ref1.child(k).setValue(userOnline);
                                SharedPreferences.Editor editor =
                                        getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                                editor.putInt("coins",userCoin+coinSend);
                                editor.commit();
                                Log.e("onlineAcceptIf", ref1.child(k)+"\n"+userOnline.getOfferid());
                            } // if
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }); // offerhim
        } // if condition




        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild("srCoin");


        if(ref != null) {


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserInOffer value = snapshot.getValue(UserInOffer.class);
                        int offId = value.getOfferId();
                        int usId = value.getUserId();


                        Log.i("sayed","offerId :"+offId
                                +"  userId : "+usId);
                        if (offId == offerID && usId == userID) {
                            snapshot.getRef().child("expired").setValue(1);
                        }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } // if


        //Retrofit
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://speed-rocket.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> updateUserCoinConnection =
                userApi.updateUserCoin(userID,userCoin+userOnline.getAmount());
        updateUserCoinConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                try {
                   /* Toast.makeText(getApplicationContext(), "Done"
                        , Toast.LENGTH_LONG).show();*/


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "exception" + (e.toString()), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n" +
                        "" + t.toString(), Toast.LENGTH_LONG).show();

            }
        });
        //Retrofit

        t_myCoins.setText(userCoin+userOnline.getAmount()+"");


        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putInt("coins", userCoin+userOnline.getAmount());
        // getOffer.setVisibility(View.INVISIBLE);
        dialog3.cancel();

    } // cancel dialog CancelReciveOfferFromHimDialogue


    public void continueDialogCongratulations(View view)
    {
        if(userID == winnerId) {
            Toast.makeText(getApplicationContext(),"Congratulations",
                    Toast.LENGTH_LONG).show();

           // winnerCoins minus from userCoin on DB
            SharedPreferences.Editor editor =
                    getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

            editor.putInt("coins",userCoin - winnerCoins);
            editor.commit();

            //Retrofit
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100,TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://speed-rocket.com/api/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
            UserApi userApi = retrofit.create(UserApi.class);
            Call<ResultModel> updateUserCoinConnection =
                    userApi.updateUserCoin(userID,userCoin - winnerCoins);
            updateUserCoinConnection.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                    try {
                      /*  Toast.makeText(getApplicationContext(), "Done"
                                , Toast.LENGTH_LONG).show();*/


                    //  test ();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "exception" + (e.toString()), Toast.LENGTH_LONG).show();
                    }


                    // second request
                    addOfferToWinners();
                }
                @Override
                public void onFailure(Call<ResultModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Connection faild\n" +
                            "" + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
            //Retrofit

        }

        else
        {

            Intent intent = new Intent(getApplicationContext(),NavigationMenu.class);
            startActivity(intent);
        }

    } //  continueDialogCongratulations function


    public  void addOfferToWinners()
    {
        //Retrofit
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://speed-rocket.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> addToWinnerConnection =
                userApi.addProductOnWinners(userID,offerID,winnerCoins);
        addToWinnerConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                try {
                    /*Toast.makeText(getApplicationContext(), "Done Winner"
                            , Toast.LENGTH_LONG).show();*/


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(getApplicationContext(), MyWinnerProducts.class);
             /*   intent.putExtra("userID", userID);
                intent.putExtra("offerID", offerID);*/
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection faild\n" +
                        "" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        //Retrofit

    }


    public  void countViews()
    {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("UserInOffer");
        //Query offerQuery = ref.orderByChild("srCoin");


        if(ref != null) {


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserInOffer value = snapshot.getValue(UserInOffer.class);
                        int offId = value.getOfferId();
                        int usId = value.getUserId();
                        int vi = value.getView();

                        if (offId == offerID && usId == userID && vi == 1) {
                            viewInhome++;
                        }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } // if

    }


    public void tryAnotherOfferGoodLuckDialogue(View view)
    {
        Intent intent = new Intent(getApplicationContext() , NavigationMenu.class);
        startActivity(intent);
    } // tryAnotherOfferGoodLuckDialogue Button


    public  void  imageSlider (ArrayList <String> urls)
    {

        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);


        for(int i = 0 ; i< urls.size() ; i++) {
            TextSliderView  textSliderView = new TextSliderView (this);
            textSliderView
                    .image("https://speed-rocket.com/upload/offers/"+urls.get(i));
            sliderShow.addSlider(textSliderView);
            Log.e("IMAGE",urls.get(i));
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Intent intent = new Intent(getApplicationContext(),image_item.class);
                    intent.putExtra("imageList",imagesUrl);
                    startActivity(intent);
                }
            });
        }



    } // imageSlider function




    public  void getLastSrCoin ()
    {

        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String key = mDatabase.push().getKey();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {


            mDatabase.child("UserInOffer").runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {


                    for (MutableData data : mutableData.getChildren()) {


                        String offerid = String.valueOf(offerID);
                        Log.i("QP","offerId : "+offerid);

                        String offerIdFire = data.child("offerId").getValue().toString();


                        if (offerid.equals(offerIdFire)) {

                            if(data.getValue() == null)
                            {
                                srCoin = Integer.parseInt(data.child("srCoin").getValue().toString());

                            }
                            else {
                                srCoin = Integer.parseInt(data.child("srCoin").getValue().toString()) + 1;

                                //  checkSrCoin = Integer.parseInt(data.child("srCoin").getValue().toString());
                            }
                        }

                    }
                    //Log.i("QP","srcoin : "+srCoin);


                            insertInFireBase();



                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });

        /*    mDatabase.child("UserInOffer").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {


                        String offerid = String.valueOf(offerID);

                        String offerIdFire = data.child("offerId").getValue().toString();

                        if (offerid.equals(offerIdFire)) {

                            srCoin = Integer.parseInt(data.child("srCoin").getValue().toString())+1;
                          //  checkSrCoin = Integer.parseInt(data.child("srCoin").getValue().toString());

                        }

                    }

                    Log.i("QP","srcoin : "+srCoin);

                    insertInFireBase();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Connection Faild"
                    ,Toast.LENGTH_LONG).show();
        }
    } // get max srcoin in offer from firebase

    public  void insertInFireBase () {

     //   Log.i("QP1", "srCoin : " + srCoin);


        if (srCoin != checkSrCoin) {


            if (srCoin > userCoin) {
                Intent intent = new Intent(getApplicationContext(), BuyCoins.class);
                startActivity(intent);
            } // check when press button offer if your coins less than srcions to recharge his coins

            else {


                if (viewInhome > 0) {
                    userInOffer =
                            new UserInOffer(userID, userID, offerID, srCoin, userCoin, firstName
                                    , lastName, email, 0, 0, userProfileImage, o_title, 0);
                } else if (viewInhome == 0) {
                    userInOffer =
                            new UserInOffer(userID, userID, offerID, srCoin, userCoin, firstName
                                    , lastName, email, 1, 0, userProfileImage, o_title, 0);
                }
      /*  Toast.makeText(getApplicationContext(),"Sr : "
        +srCoin,Toast.LENGTH_LONG).show();
         userInOfferList.add(userInOffer);*/

                congratFname = firstName;
                congratLname = lastName;
                winnerId = userID;
                winnerCoins = srCoin;
                congratFirstName.setText(congratFname);
                congratLastName.setText(congratLname);
                congratFirstName1.setText(congratFname);
                congratLastName2.setText(congratLname);


           /* p = new PersonalUser(srCoin, myFirstNameAccount, myLastNameAccount, userID);
            userList.add(0, p);
            mAdapter.notifyDataSetChanged();*/

            }// user already login


            final DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            final String key = mDatabase.push().getKey();
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                try {
                    mDatabase.child("UserInOffer").runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {



                            mDatabase.child("UserInOffer").child(key).setValue(userInOffer);

                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            if(dataSnapshot != null) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    String userid = String.valueOf(userID);
                                    String offerid = String.valueOf(offerID);

                                    String userIdFire = data.child("id").getValue().toString();
                                    String offerIdFire = data.child("offerId").getValue().toString();


                                    if (userid.equals(userIdFire) && offerid.equals(offerIdFire)) {
                                        data.getRef().child("position").setValue(1);
                                        break;

                                    } else if (!userid.equals(userIdFire) && offerid.equals(offerIdFire))
                                        data.getRef().child("position").setValue(0);

                                }
                            }//if datasanpshot != null
                        }
                    });

                } // try
                catch (Exception e)
                {
                    Log.i("QPError : ",e.toString());
                }// catch

             /*   mDatabase.child("UserInOffer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            String userid = String.valueOf(userID);
                            String offerid = String.valueOf(offerID);

                            String userIdFire = data.child("id").getValue().toString();
                            String offerIdFire = data.child("offerId").getValue().toString();


                            if (userid.equals(userIdFire) && offerid.equals(offerIdFire)) {
                                data.getRef().child("position").setValue(1);
                                break;

                            } else if (!userid.equals(userIdFire) && offerid.equals(offerIdFire))
                                data.getRef().child("position").setValue(0);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            } else {
                Toast.makeText(getApplicationContext(), "Connection Faild"
                        , Toast.LENGTH_LONG).show();
            }
        }
    }// if serCoin != srcoin.get(0)
    public  void  test()
    {
        //Retrofit
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://speed-rocket.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> updateUserCoinConnection =
                userApi.test(offerID);
        updateUserCoinConnection.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

                try {
                      /*  Toast.makeText(getApplicationContext(), "Done"
                                , Toast.LENGTH_LONG).show();*/

                    Log.i("QP","done");

                } catch (Exception e) {
                    Log.i("QP","Exception"+e.toString());
                }


            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Log.i("QP","Connection"+t.toString());
            }
        });
        //Retrofit
    }

} // postdetails class

