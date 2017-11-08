package com.sourcey.Hackaroad.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.sourcey.Hackaroad.R;

/**
 * Created by BSM on 2017-11-08.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendPushNotification(remoteMessage.getData().get("message"));
    }

    private void sendPushNotification(String message) {
        System.out.println("received message : " + message);
        Intent intent = new Intent(this, MainFcmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.car48).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.car144) )
                .setContentTitle("운전이 종료되었습니다.")
                .setContentText("운전 결과를 확인해 볼까요?")
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255,500,2000)
                .setVibrate(new long[]{1, 1000}) // 진동
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
//        wakelock.acquire(5000);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE );
        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
        wakeLock.acquire(3000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
