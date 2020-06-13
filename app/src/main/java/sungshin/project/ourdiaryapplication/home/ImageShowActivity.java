package sungshin.project.ourdiaryapplication.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import sungshin.project.ourdiaryapplication.R;

public class ImageShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        ImageView imageView = findViewById(R.id.big_imageview);
        Glide.with(this).load(url).into(imageView);
    }
}
