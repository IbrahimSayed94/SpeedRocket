package com.example.ibrahim.speedrocket.View.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.ibrahim.speedrocket.R;

import java.util.ArrayList;

public class image_item extends AppCompatActivity {

    SliderLayout sliderShow ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_item);

        sliderShow = (SliderLayout) findViewById(R.id.sliderFullScreen);

        ArrayList<String> urls =
                (ArrayList<String>) getIntent().
                        getSerializableExtra("imageList");



        for(int i = 0 ; i< urls.size() ; i++) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .description("1")
                    .image("https://speed-rocket.com/upload/offers/"+urls.get(i));
            sliderShow.addSlider(textSliderView);
        }


    }
}
