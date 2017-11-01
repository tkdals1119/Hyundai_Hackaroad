package com.sourcey.Hackaroad.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.sourcey.Hackaroad.R;

/**
 * Created by Jeong on 2017-10-30.
 */

public class VideoViewActivity extends AppCompatActivity {
    MediaController mediaController;
    TextView textView;
    String date;
    String content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        final VideoView videoView = (VideoView)findViewById(R.id.videoView);
        textView = (TextView)findViewById(R.id.textView);

        final int position = 0;

        Intent intent = getIntent();

        String uriPath = "android.resource://"+ getPackageName()+"/raw/video";
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);


//        Set the videoView that acts as the anchor for the MediaController.
//        mediaController.setAnchorView(videoView);
//
//
//        // Set MediaController for VideoView
//        videoView.setMediaController(mediaController);
//
//        try {
//            // ID of video file.
//            int id = this.getRawResIdByName("video");
//            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
//
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//        }
//
//        videoView.requestFocus();
//
        // When the video file ready for playback.
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
//                        mediaController = new MediaController(VideoViewActivity.this);
//                        videoView.setMediaController(mediaController);
//                        mediaController.setAnchorView(videoView);
//
//                    }
//                });
//            }
//        });


       final MediaController mediaController = new MediaController(this);
       mediaController.setAnchorView(videoView);
       videoView.setMediaController(mediaController);

        videoView.start();

        videoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaController.show(0);
                videoView.pause();
            }
        }, 100);
    }

//    // Find ID corresponding to the name of the resource (in the directory raw).
//    public int getRawResIdByName(String resName) {
//        String pkgName = this.getPackageName();
//        // Return 0 if not found.
//        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
//        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
//        return resID;

}
