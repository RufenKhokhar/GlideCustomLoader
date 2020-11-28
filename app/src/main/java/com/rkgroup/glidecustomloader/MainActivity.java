package com.rkgroup.glidecustomloader;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.iv_thumbnail);
        Glide.with(this).asBitmap()
                .load("file:android_asset/index.pdf").into(imageView);
    }

}