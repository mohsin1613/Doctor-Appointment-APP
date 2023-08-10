package com.project.meetingapp.utilities;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.project.meetingapp.R;
import com.project.meetingapp.activities.MainActivity;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channeID";
    public static final String channelName = "channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        // Create a channel for version Oreo or higher. Not required if lower version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Create new channel, set id, name and level of importance
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        // If manager is null create a new one, if not use current manager
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String notifyUser, String recepientName) {

        // Set text to the blue colour of the application
        int appBlueColour = Color.parseColor("#039be5");

        // Set pendingintent for user clicking notification, opening home page
        PendingIntent mainActivityIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        return new NotificationCompat.Builder(getApplicationContext(), channelID)

                // Set large icon, small icon, title, text
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.sms_logo))
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Scheduled SMS to " + recepientName)
                .setContentText(notifyUser)
                .setColor(appBlueColour)

                // When notification is clicked open sms manager page
                .setContentIntent(mainActivityIntent)
                // When notification is clicked remove it.
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "SMS Manager", mainActivityIntent)
                .addAction(R.mipmap.ic_launcher, "Schedule New SMS", mainActivityIntent);
    }
}
