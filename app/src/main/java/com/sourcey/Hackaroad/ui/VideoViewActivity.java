package com.sourcey.Hackaroad.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.sourcey.Hackaroad.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jeong on 2017-10-30.
 */

public class VideoViewActivity extends AppCompatActivity{
    private MediaController mediaController;
    private TextView textView_explain;
    VideoView videoView;
    String uriPath;
    private int id;
    private String date;
    private String content;
    private String site;
    private String time;


    @BindView(R.id.mapBt) ImageButton mapBt;
    @OnClick(R.id.mapBt)
    void onClickMapBt()
    {
        Intent i = new Intent(VideoViewActivity.this, MapActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        ButterKnife.bind(this);


        videoView = (VideoView)findViewById(R.id.videoView);
        textView_explain = (TextView)findViewById(R.id.textView);

        Intent intent = getIntent();
        id = 1;
        date = intent.getStringExtra("date");
        content = intent.getStringExtra("content");
        site = "백제대로 567";
        time = "17시 30분";


        uriPath = "android.resource://"+ getPackageName()+"/raw/video" + id;
        //String uriPath = "rtsp://127.0.0.1:80/dinosaur.mp4";

        Uri uri = Uri.parse(uriPath);

        videoView.setVideoURI(uri);
        textView_explain.setText(" " + date + " " + content + " " + site + "에서 " + time + "에 발생");


        //잠시
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.requestFocus();
        videoView.start();

        videoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaController.show(0);
                videoView.pause();
            }
        }, 100);
    }
}
