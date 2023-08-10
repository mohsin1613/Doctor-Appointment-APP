package com.project.meetingapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.project.meetingapp.R;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.NotifyUser;
import com.project.meetingapp.utilities.PreferenceManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tactivity);

        preferenceManager = new PreferenceManager(getApplicationContext());
        addToSms("Msg");

    }

    public void addToSms(String messageText) {
        String name = preferenceManager.getString(Constants.KEY_FULL_NAME);
        String number = preferenceManager.getString(Constants.KEY_MOBILE_NUM);
        String message = messageText;
        String messageDate = Integer.toString(5) + "/" + Integer.toString(5) + "/" + Integer.toString(2022); //Convert integers to string
        String messageTime = String.format("%02d:%02d", 9, 38); // Convert to String and format hours and minutes
        String messageStatus = "Pending";

        // Start multi-thread to insert sms to database and start alarm manager
        ScheduleSmsAsyncTask task = new ScheduleSmsAsyncTask();
        task.execute(name, number, messageDate, messageTime, message, messageStatus);
    }

    private class ScheduleSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... string) {
            String result = "SMS Successfully Scheduled";
            try {
                // Construct a Sms object and pass it to the helper for database insertion
                //int SmsID = smsDatabaseHelper.addSms(new Sms(string[0], string[1], string[2], string[3], string[4], string[5]));

                // Create calendar with selected date and time
                Calendar c = Calendar.getInstance();
               /* c.set(Calendar.YEAR, setYear);
                c.set(Calendar.MONTH, setMonth);
                c.set(Calendar.DAY_OF_MONTH, setDay);
                c.set(Calendar.HOUR_OF_DAY, setHour);
                c.set(Calendar.MINUTE, setMinute);
                c.set(Calendar.SECOND, 0);*/

                // Create alarm manager
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Pass SmsID to AlarmReceiver class
                Intent intent = new Intent(getApplicationContext(), NotifyUser.class);
                intent.putExtra("name", "Rofiqul");
                intent.putExtra("date", "21:56:00");

                //Set SmsID as unique id, Set time to calender, Start alarm
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+(60*1000), pendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
                result = "SMS failed to schedule";
            }
            return result;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Display result message
            Toast.makeText(TActivity.this, result, Toast.LENGTH_SHORT).show();

            // Clear Sms Fields
            //resetInput();
        }
    }
}