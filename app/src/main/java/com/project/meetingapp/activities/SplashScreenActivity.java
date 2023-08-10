package com.project.meetingapp.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.project.meetingapp.R;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.PreferenceManager;


public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar splashProgress;
    int SPLASH_TIME = 3000;

    private boolean action = false;

    int count =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        //Button entryBtn = findViewById(R.id.entryBtn) ;
       /* entryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mySuperIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mySuperIntent);
                count = 1;
                SPLASH_TIME = 0 ;
                finish();
                action = true ;
                Log.d("TAG", "action1: "+action) ;
            }
        });*/




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(action == false){
                    PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

                    if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent mySuperIntent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(mySuperIntent);
                    }
                    finish();
                }


            }
        }, SPLASH_TIME);

        Log.d("TAG", "action: "+action) ;
        Log.d("TAG", "splashtime: "+SPLASH_TIME) ;

    }

    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(SPLASH_TIME)
                .start();
    }
}