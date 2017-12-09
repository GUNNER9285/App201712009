package com.example.programminglanguage;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DetailLogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_logo);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String picturePath = intent.getStringExtra("picture");

        TextView textView = findViewById(R.id.textView_logoDe);
        ImageView imageView = findViewById(R.id.imageView_logoDe);

        textView.setText(title);
        AssetManager am = this.getAssets();
        try {
            InputStream stream = am.open(picturePath);
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageView.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
