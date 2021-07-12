package com.example.weatherf;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class recom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom);
        Intent intent=getIntent();
        String number=intent.getExtras().getString("number");
        ConstraintLayout background2 = findViewById(R.id.background2);
        background2.setBackground(getResources().getDrawable(R.drawable.s2));
        number="a"+number;
        ImageView imageView = findViewById(R.id.imageView6);
        //String resName="@drawable/"+number;
        String packName=this.getPackageName();
        //int resID=getResources().getIdentifier(resName,"drawable",packName);
        //imageView.setImageResource(resID);
        TextView tvLinkify = (TextView) findViewById(R.id.textView14);
        String text ="옷 구매 링크";
        tvLinkify.setText(text);
        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() { @Override public String transformUrl(Matcher match, String url) { return ""; } };
        Pattern pattern2 = Pattern.compile("옷 구매 링크");
        Linkify.addLinks(tvLinkify, pattern2, "https://store.musinsa.com/app/",null,mTransform);

    }
}