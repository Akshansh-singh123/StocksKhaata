package com.akshansh.stockskhaata.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.akshansh.stockskhaata.R;
import com.akshansh.stockskhaata.screens.main.MainActivity;

public class NotificationHelper {
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void sendNotification(){
        int notificationId = 0;
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.NOTIFICATION_ID,notificationId);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        String title = "Checkout your stock progress";
        String subtitle = "Tap for more details";

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context,0,intent,0);
        NotificationCompat.Builder notification = new NotificationCompat
                .Builder(context,Constants.NOTIFICATION_CHANNEL)
                .setContentTitle(title)
                .setContentText(subtitle)
                .setSmallIcon(R.drawable.ic_favorite)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification.setChannelId(Constants.NOTIFICATION_CHANNEL);
            notificationManager
                    .createNotificationChannel(new NotificationChannel(
                            Constants.NOTIFICATION_CHANNEL,
                            Constants.NOTIFICATION_NAME,
                            NotificationManager.IMPORTANCE_HIGH));
        }
        Log.e("NOTIFY", "sendNotification: ");
        notificationManager.notify(notificationId,notification.build());
    }
}
