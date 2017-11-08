package com.sourcey.Hackaroad.ui;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.model.Case_List;
import com.sourcey.Hackaroad.utils.ApiRequester;

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

    VideoView videoView;
    String uriPath;
    private int id;
    private String date;
    private String content;
    private String site;
    private String time;
    private String address;

    private String[] list_habit_arr;
    private String[] list_habit_date_arr;
    private String[] list_habit_time_arr;
    private String[] list_habit_latitude;
    private String[] list_habit_longtitude;

    private String[] list_latitude;
    private String[] list_longtitude;
    private String[] list_arr;
    private String[] list_arr_date;
    private String[] list_arr_time;

    private int position;

    @BindView(R.id.mapBt)
    ImageButton mapBt;

    @BindView(R.id.textView1) TextView textView1;
    @BindView(R.id.textView2) TextView textView2;
    @BindView(R.id.textView3) TextView textView3;
    @BindView(R.id.textView4) TextView textView4;
    @BindView(R.id.textView5) TextView textView5;
    @BindView(R.id.imageView_error) ImageView imageView_error;

    @OnClick(R.id.mapBt)
    void onClickMapBt() {
        Intent intent = new Intent(VideoViewActivity.this, MapActivity.class);
        intent.putExtra("latitude", list_habit_latitude[position]);
        intent.putExtra("longtitude", list_habit_longtitude[position]);
        startActivity(intent);
    }

    public void setMyData(String[] list_arr) {
        this.list_habit_arr = list_arr;
    }

    public void setMyDataDate(String[] list_arr_date) {
        this.list_habit_date_arr = list_arr_date;
    }

    public void setMyDataTime(String[] list_arr_time) {
        this.list_habit_time_arr = list_arr_time;
    }

    public void setLatitude(String[] list_latitude) {
        this.list_habit_latitude = list_latitude;
    }

    public void setLongtitude(String[] list_longtitude) {
        this.list_habit_longtitude = list_longtitude;
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
                    list_arr_time = new String[size];
                    list_latitude = new String[size];
                    list_longtitude =  new String[size];
                    int count = 0;

                    for(Case_List list : result)
                    {
                        list_arr[count] = list.gethabbitname();
                        list_latitude[count] = list.getlatitude();
                        list_longtitude[count] = list.getlongtitude();

                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sourceFormat2 = new SimpleDateFormat("HH:mm:ss.SSS");

                        Date date = null;
                        Date time = null;
                        try {
                            date = sourceFormat.parse(list.getcreated_at());
                            time = sourceFormat.parse(list.getcreated_at());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDate = destFormat.format(date);
                        String formattedTime = sourceFormat2.format(time);

                        list_arr_date[count] = formattedDate;
                        list_arr_time[count] = formattedTime;

                        count++;
                    }
                    setMyData(list_arr);
                    setMyDataDate(list_arr_date);
                    setMyDataTime(list_arr_time);
                    setLatitude(list_latitude);
                    setLongtitude(list_longtitude);

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

        Intent intent = getIntent();
        this.position = intent.getIntExtra("position", 1);

        requestCaseList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        videoView = (VideoView) findViewById(R.id.videoView);

        date = intent.getStringExtra("date");
        content = intent.getStringExtra("content");


        textView1.setText(" ");
        textView2.setText(" ");
        textView3.setText(" ");
        textView4.setText(" ");


        ButterKnife.bind(this);

        }

    private void MakeVideo() {
        textView1.setText(date + ", " + list_arr_time[position] + "에");
        textView2.setText(address + "에서");
        textView3.setText(content);
        textView4.setText("을(를) 하셨군요!");
        textView5.setText("잘못된 운전 상황을 다시 한번 볼까요?");

        int video_position = position+1;
        uriPath = "android.resource://" + getPackageName() + "/raw/video" + video_position;
        //String uriPath = "rtsp://127.0.0.1:80/dinosaur.mp4";

        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);

        //잠시
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        //mediaController.setEnabled(true);

        videoView.setMediaController(mediaController);
        videoView.start();
        videoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaController.setBackgroundColor(Color.parseColor("#00aeef"));

                mediaController.show(0);
                //videoView.pause();
            }
        }, 100);
    }

    private void ConverterGeo() {
        final Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(Double.parseDouble(list_habit_latitude[position]), Double.parseDouble(list_habit_longtitude[position]), 10);

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

