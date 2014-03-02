package com.example.NotePad;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by jeahyukkim on 2014. 3. 1..
 */
public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){

       // Toast.makeText(context, "alarm TEST", Toast.LENGTH_LONG).show();


        NotificationManager notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notify = new Notification(android.R.drawable.arrow_up_float, "text", System.currentTimeMillis());

        Intent intent2 = new Intent(context, MyActivity.class);
        PendingIntent pender = PendingIntent.getActivity(context, 0, intent2, 0);

        notify.setLatestEventInfo(context, "alimtitle", "hackjang", pender);

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        //notify.vibrate = new long[] { 200, 200, 500, 300 };
        // notify.sound=Uri.parse("file:/");
        notify.number++;

        notifier.notify(1, notify);

    }
}
