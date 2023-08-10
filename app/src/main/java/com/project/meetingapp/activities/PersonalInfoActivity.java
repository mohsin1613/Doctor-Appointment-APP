package com.project.meetingapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.meetingapp.R;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.PreferenceManager;

public class PersonalInfoActivity extends AppCompatActivity {

    String username,password,user_type;
    //DatabaseHelper db;
    TextView name,sex,dob,utype,mobno,email,pword,specialist,schedule,visit;
    private final String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    private PreferenceManager preferenceManager;
    private Button btn_pesonal_info;
    private LinearLayout linearLayoutSchedule,linearLayoutSpecalist,linearLayoutVisit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        preferenceManager = new PreferenceManager(getApplicationContext());

        linearLayoutSchedule = findViewById(R.id.scheduleLayout);
        linearLayoutSpecalist = findViewById(R.id.specialistLayout);
        linearLayoutVisit = findViewById(R.id.visitLayout);
        btn_pesonal_info = findViewById(R.id.up);
        name = (TextView) findViewById(R.id.name);
        mobno = (TextView) findViewById(R.id.mobile_num);
        sex = (TextView) findViewById(R.id.sex);
        dob = (TextView) findViewById(R.id.dob);
        utype = (TextView) findViewById(R.id.utype);
        specialist = (TextView) findViewById(R.id.specialist);
        schedule = (TextView) findViewById(R.id.schedule);
        visit = (TextView) findViewById(R.id.visit);
        email = (TextView) findViewById(R.id.email);
        pword = (TextView) findViewById(R.id.password);


        if (!preferenceManager.getString(Constants.KEY_DATE_OF_BIRTH).isEmpty())
        {
            dob.setText(date_of_birth(preferenceManager.getString(Constants.KEY_DATE_OF_BIRTH)));
        }

        if(preferenceManager.getString(Constants.KEY_USER_TYPE).equals("Doctor")){
            linearLayoutSchedule.setVisibility(View.VISIBLE);
            linearLayoutSpecalist.setVisibility(View.VISIBLE);
            linearLayoutVisit.setVisibility(View.VISIBLE);
        }
        else {
            linearLayoutSchedule.setVisibility(View.GONE);
            linearLayoutSpecalist.setVisibility(View.GONE);
            linearLayoutVisit.setVisibility(View.GONE);
        }

        name.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
        mobno.setText(preferenceManager.getString(Constants.KEY_MOBILE_NUM));
        sex.setText(preferenceManager.getString(Constants.KEY_SEX));
        utype.setText(preferenceManager.getString(Constants.KEY_USER_TYPE));
        specialist.setText(preferenceManager.getString(Constants.KEY_SPECIALIZATION));
        schedule.setText(preferenceManager.getString(Constants.KEY_SCHEDULE));
        visit.setText(preferenceManager.getString(Constants.KEY_FEE));
        email.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        pword.setText(preferenceManager.getString(Constants.KEY_PASSWORD));

        btn_pesonal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalInfoActivity.this,UpdateActivity.class));
            }
        });

        /*db = new DatabaseHelper(this);

        Bundle bb = getIntent().getExtras();
        username = bb.getString("username");
        password = bb.getString("password");
        user_type = bb.getString("password");



        Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));

        if (y.moveToFirst()) {
            String name1 = y.getString(1);
            String name2 = y.getString(2);

            name.setText(name1+" "+name2);
            age.setText(y.getString(3));
            sex.setText(y.getString(6));
            dob.setText(y.getString(5));
            bgroup.setText(y.getString(4));
            utype.setText(y.getString(7));
            city.setText(y.getString(8));
            pincode.setText(y.getString(9));
            mobno.setText(y.getString(10));
            uname.setText(y.getString(12));
            pword.setText(y.getString(11));
        }*/
    }

    private String date_of_birth(String dob){
        if(dob.isEmpty()) return "";
        String[] arrOfStr = dob.split("/", 3);
        return arrOfStr[0]+" "+(month[Integer.parseInt(arrOfStr[1])-1])+" "+arrOfStr[2];
    }

    /*public void onClick(View view){

        Intent i;
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        b.putString("user_type",user_type);

        i = new Intent(PersonalInfoActivity.this, Update.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }*/
}
