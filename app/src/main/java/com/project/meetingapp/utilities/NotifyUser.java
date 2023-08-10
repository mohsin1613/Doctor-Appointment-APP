package com.project.meetingapp.utilities;

import static java.lang.Boolean.TRUE;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;

import androidx.core.app.NotificationCompat;

public class NotifyUser extends BroadcastReceiver {
    public Context context;
    String recepientName,date,msg;

    @Override
    public void onReceive(Context context, Intent intent) {

        // Receive SmsID of the alarm to update the SMS status in database, Receive recepient name to display in notifications
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            recepientName = bundle.getString("name");
            date = bundle.getString("date");
            msg = "Remainder for your appointment on "+date;
        }

        setContext(context);
        sendNotification(msg);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Build notification using helper class
    public void sendNotification(String notificationMessage) {
        NotificationHelper notificationHelper = new NotificationHelper(getContext());
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(notificationMessage, recepientName);
        notificationHelper.getManager().notify(1, nb.build());

    }
}
