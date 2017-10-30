package com.sourcey.Hackaroad.service;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by SAMSUNG on 2017-10-31.
 */

public class BackPressCloseHandler {

    private long backKeypressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context)
    {
        this.activity = context;
    }
    public void onBackPressed()
    {
        if(System.currentTimeMillis() > backKeypressedTime + 2000)
        {
            backKeypressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if(System.currentTimeMillis() <= backKeypressedTime + 2000)
        {
            activity.finish();
            activity.moveTaskToBack(true);
            toast.cancel();
        }
    }

    private void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_LONG);

        toast.show();

    }
}
