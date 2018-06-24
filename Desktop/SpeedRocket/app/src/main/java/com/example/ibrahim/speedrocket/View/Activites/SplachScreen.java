package com.example.ibrahim.speedrocket.View.Activites;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ibrahim.speedrocket.R;

public class SplachScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    TextView progmine ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                 // full screen
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splach_screen);


        progmine = (TextView) findViewById(R.id.progmine);
        progmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://progmine.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplachScreen.this,NavigationMenu.class);
                SplachScreen.this.startActivity(mainIntent);
                SplachScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
