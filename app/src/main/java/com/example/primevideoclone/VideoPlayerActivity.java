package com.example.primevideoclone;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private PlayerView videoPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    private static final String File_URL="";
    String urr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoPlayer=findViewById(R.id.playvideo);
        urr= getIntent().getStringExtra(("url"));

        setUpExoPlayer(urr);
    }
    private void setUpExoPlayer(String Url){
        Log.d(TAG, "onResponse: " + Url);
        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this);
        videoPlayer.setPlayer(simpleExoPlayer);
        DataSource.Factory datasourcefactory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"moviesapp"));
        MediaSource mediaSource=new ExtractorMediaSource.Factory(datasourcefactory).createMediaSource(Uri.parse(Url));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}