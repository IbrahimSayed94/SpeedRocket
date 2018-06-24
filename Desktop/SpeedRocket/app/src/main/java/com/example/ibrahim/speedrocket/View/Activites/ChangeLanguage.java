package com.example.ibrahim.speedrocket.View.Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ibrahim.speedrocket.R;

import java.util.Locale;

public class ChangeLanguage extends AppCompatActivity {


    TextView txt_english , txt_arabic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        txt_arabic = (TextView) findViewById(R.id.arabic);
        txt_english = (TextView) findViewById(R.id.english);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        txt_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String languageToLoad  = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                editor.putString("lang","ar");
                Intent intent = new Intent(getApplicationContext(),NavigationMenu.class);
                startActivity(intent);
            }
        }); // arabic


        txt_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String languageToLoad  = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                Intent intent = new Intent(getApplicationContext(),NavigationMenu.class);
                startActivity(intent);
            }
        }); // english
    } // onCreate function
} // changeLanguage class
