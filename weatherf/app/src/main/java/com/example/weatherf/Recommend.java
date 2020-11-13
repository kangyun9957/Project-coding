package com.example.weatherf;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Recommend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Bitmap bitmap;
        Intent intent=getIntent();
        String number=intent.getExtras().getString("number");
        number="a"+number;
        System.out.println(number);
        ImageView imageView = findViewById(R.id.imageView6);
        String resName="@drawable/"+number;
        String packName=this.getPackageName();
        int resID=getResources().getIdentifier(resName,"drawable",packName);
        imageView.setImageResource(resID);


    }
}