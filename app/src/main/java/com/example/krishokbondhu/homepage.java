package com.example.krishokbondhu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class homepage extends AppCompatActivity {
    ImageView profile,field,product,call1,call2,n1,h1,weather;
    private ImageSlider i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        profile = findViewById(R.id.header_3);
        field = findViewById(R.id.img1);
        product = findViewById(R.id.img2);
        n1 = findViewById(R.id.img3);
        h1 = findViewById(R.id.img4);
        call1 = findViewById(R.id.img5);
        call2 = findViewById(R.id.img6);
        i = findViewById(R.id.imgslider);
        weather = findViewById(R.id.img7);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.farmer1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.farmer2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.farmer3, ScaleTypes.FIT));

        i.setImageList(slideModels,ScaleTypes.FIT);
        i.startSliding(1000);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_pro = new Intent(homepage.this,profile.class);
                startActivity(intent_pro);
            }
        });
        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_field = new Intent(homepage.this,fieldlist.class);
                startActivity(intent_field);
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_product = new Intent(homepage.this,product.class);
                startActivity(intent_product);
            }
        });
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_notification = new Intent(homepage.this,notification.class);
                startActivity(intent_notification);
            }
        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_weather = new Intent(homepage.this,WeatherUpdate.class);
                startActivity(intent_weather);
            }
        });

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_history = new Intent(homepage.this,history.class);
                startActivity(intent_history);
            }
        });
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:02-9140850"));
                startActivity(intent);
            }
        });
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:999"));
                startActivity(intent);
            }
        });




    }
}