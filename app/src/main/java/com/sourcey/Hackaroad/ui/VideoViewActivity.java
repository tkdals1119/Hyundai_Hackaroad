package com.sourcey.Hackaroad.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.model.Case_List;
import com.sourcey.Hackaroad.utils.ApiRequester;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jeong on 2017-10-30.
 */

public class VideoViewActivity extends AppCompatActivity {
    private static final String TAG = "VideoViewActivity";
    private MediaController mediaController;
    private TextView textView_explain;
    VideoView videoView;
    String uriPath;
    private int id;
    private String date;
    private String content;
    private String site;
    private String time;
    private String address;

    private String latitude;
    private String longtitude;

    private String[] list_habit_arr;
    private String[] list_habit_date_arr;
    private String list_latitude;
    private String list_longtitude;

    private String[] list_arr;
    private String[] list_arr_date;

    @BindView(R.id.mapBt)
    ImageButton mapBt;

    @OnClick(R.id.mapBt)
    void onClickMapBt() {
        Intent i = new Intent(VideoViewActivity.this, MapActivity.class);
        i.putExtra("latitude", list_latitude);
        i.putExtra("longtitude", list_longtitude);
        startActivity(i);
    }

    public void setMyData(String[] list_arr) {
        this.list_habit_arr = list_arr;
    }

    public void setMyDataDAte(String[] list_arr_date) {
        this.list_habit_date_arr = list_arr_date;
    }

    public void setLatitude(String latitude) {
        this.list_latitude = latitude;
    }

    public void setLongtitude(String longtitude) {
        this.list_longtitude = longtitude;
    }


    private void requestCaseList() {
        ApiRequester.getInstance().getList(new ApiRequester.UserCallback<List<Case_List>>() {
            @Override
            public void onSuccess(List<Case_List> result) {
                if(result==null)
                {
                    Toast.makeText(VideoViewActivity.this, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int size = result.size();
                    list_arr = new String[size];
                    list_arr_date = new String[size];
                    int count = 0;

                    for(Case_List list : result)
                    {
                        list_arr[count] = list.gethabbitname();
                        latitude = list.getlatitude();
                        longtitude = list.getlongtitude();

                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date date = null;
                        try {
                            date = sourceFormat.parse(list.getcreated_at());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDate = destFormat.format(date);
                        list_arr_date[count] = formattedDate;

                        count++;
                    }
                    setMyData(list_arr);
                    setMyDataDAte(list_arr_date);
                    setLatitude(latitude);
                    setLongtitude(longtitude);

                    ConverterGeo();
                    MakeVideo();
                }
            }
            @Override
            public void onFail() {
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestCaseList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        videoView = (VideoView) findViewById(R.id.videoView);
        textView_explain = (TextView) findViewById(R.id.textView);
        ButterKnife.bind(this);

        }

    private void MakeVideo() {
        Intent intent = getIntent();
        id = 1;
        content = intent.getStringExtra("content");

        uriPath = "android.resource://" + getPackageName() + "/raw/video" + id;
        //String uriPath = "rtsp://127.0.0.1:80/dinosaur.mp4";

        Uri uri = Uri.parse(uriPath);

        videoView.setVideoURI(uri);
        textView_explain.setText(address + " 에서 " + content + " 발생");

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

    private void ConverterGeo() {
        final Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(Double.parseDouble(list_latitude), Double.parseDouble(list_longtitude), 10);

        } catch (Exception e) {
        }

        if (list != null)
        {
            if (list.size() == 0) {
                Log.d(TAG, "onCreate: 리스트없음");
            } else {
                this.address = list.get(0).getAddressLine(0).toString();
                System.out.println("주소는" + address);
            }
        }
    }
}

