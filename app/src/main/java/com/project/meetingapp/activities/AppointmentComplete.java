package com.project.meetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.meetingapp.R;
import com.project.meetingapp.models.Appointment2;

public class AppointmentComplete extends AppCompatActivity {

    //appointmentComplete data
    EditText name, email, phone, address, age, date;
    CheckBox male, female;
    Button button;
    DatabaseReference appReff;
    Appointment2 appointment2;
    // end of activity_home
    String DoctorName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmentcomplete);


        ///getting date from patient selection

        String fullDate = getIntent().getStringExtra("fullDate");
        DoctorName = getIntent().getStringExtra("doctor");

        /// for passing data into the firebase

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        age = findViewById(R.id.age);
        date = findViewById(R.id.date);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        button = findViewById(R.id.btn);

        //setting the date.
        date.setText(fullDate);
        //end of date


        appReff = FirebaseDatabase.getInstance().getReference().child("Appointment");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAppointmentData();
            }
        });


    }


    private void insertAppointmentData() {
        String Name = name.getText().toString().trim();
        String Email = email.getText().toString().trim();
        int Phone = Integer.parseInt(phone.getText().toString().trim());
        String Address = address.getText().toString().trim();
        int Age = Integer.parseInt(age.getText().toString().trim());
        String Date = date.getText().toString().trim();

        String Male = male.getText().toString().trim();
        String Female = female.getText().toString().trim();

        appointment2 = new Appointment2();
        appointment2.setName(Name);
        appointment2.setAddress(Address);
        appointment2.setAge(Age);
        appointment2.setDate(Date);
        appointment2.setMale(Male);
        appointment2.setPhone(Phone);
        appointment2.setEmail(Email);
        appointment2.setDoctor(DoctorName);

        appReff.push().setValue(appointment2);
        Toast.makeText(AppointmentComplete.this, "Data Insert Successfully!", Toast.LENGTH_LONG).show();


    }


}

