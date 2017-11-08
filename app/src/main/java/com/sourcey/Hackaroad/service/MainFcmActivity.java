package com.sourcey.Hackaroad.service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;
import com.sourcey.Hackaroad.R;

public class MainFcmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fcm);

        FirebaseMessaging.getInstance().subscribeToTopic("notice");

    }
}
