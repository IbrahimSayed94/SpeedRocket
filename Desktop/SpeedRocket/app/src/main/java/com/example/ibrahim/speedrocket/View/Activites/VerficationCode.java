package com.example.ibrahim.speedrocket.View.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ibrahim.speedrocket.R;
import com.jkb.vcedittext.VerificationCodeEditText;

public class VerficationCode extends AppCompatActivity {


    private VerificationCodeEditText verificationCodeEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_code);

        verificationCodeEditText = (VerificationCodeEditText) findViewById(R.id.verifyCode);
        verificationCodeEditText.setOnVerificationCodeChangedListener(new VerificationCodeEditText
                .OnVerificationCodeChangedListener() {

            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

                Log.i("QV","char/"+s+"/start/"+start+"/before/"+before+"/count/"+count);
            }

            @Override
            public void onInputCompleted(CharSequence s) {
                Log.i("QV1","char/"+s);
            }
        });
    }
}
