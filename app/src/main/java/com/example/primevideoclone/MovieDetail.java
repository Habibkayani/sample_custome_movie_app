package com.example.primevideoclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetail extends AppCompatActivity {

    public static final String TAG = "TAG";
    ImageView movieImage;
    TextView moviename;
    Button play_btn;
    String mName,mImage,mId,mFileUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movieImage=findViewById(R.id.detail_image);
        moviename=findViewById(R.id.movie_name);
        play_btn=findViewById(R.id.Play_button);
        mId=getIntent().getStringExtra("movieId");
        mName=getIntent().getStringExtra("movieName");
        mImage=getIntent().getStringExtra("movieImageUrl");
        mFileUrl=getIntent().getStringExtra("movieFile");

        //set data layout
        Glide.with(this).load(mImage).into(movieImage);
        moviename.setText(mName);
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MovieDetail.this,VideoPlayerActivity.class);
                i.putExtra("url",mFileUrl);
                startActivity(i);
            }
        });

    }
}