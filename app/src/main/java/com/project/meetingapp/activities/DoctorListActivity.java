package com.project.meetingapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.meetingapp.R;
import com.project.meetingapp.adapters.MyAdapter;
import com.project.meetingapp.adapters.ViewMoreAdapter;
import com.project.meetingapp.models.Doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DoctorListActivity extends AppCompatActivity {


    //move to one activity to another activity.
//   private AppCompatButton btn;
    ///end the btn
    RecyclerView recyclerView, recyclerView2;
    DatabaseReference databaseReference, getDatabaseReference;
    MyAdapter myAdapter;
    ArrayList<Doctor> list;

    ViewMoreAdapter viewMoreAdapter;
    ArrayList<Doctor> viewmoreDoctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        recyclerView = (RecyclerView) findViewById(R.id.doctorlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView2 = (RecyclerView) findViewById(R.id.doctorlist);
        getDatabaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        viewmoreDoctor = new ArrayList<>();

        ///getting data from one activity to another

        String check = getIntent().getStringExtra("clickbtn");

        System.out.println(check);

        if (check!=null) {

            String doctorSelect = getIntent().getStringExtra("keydoctor");
            String specializationSelect = getIntent().getStringExtra("keyspecialization");
            String FullDate = getIntent().getStringExtra("fullDate");
            String getDate = getIntent().getStringExtra("day");


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    boolean ck2=true,ck3=true;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        System.out.println(doctorSelect + "  " + specializationSelect);
                        Log.d("data", dataSnapshot.toString());
                        Doctor doctor = dataSnapshot.getValue(Doctor.class);

                        String name = dataSnapshot.child("name").getValue().toString();
                        String day1 = dataSnapshot.child("day1").getValue().toString();
                        String day2 = dataSnapshot.child("day2").getValue().toString();
                        String day3 = dataSnapshot.child("day3").getValue().toString();
                        String specialization = dataSnapshot.child("specialization").getValue().toString();

                        boolean b = getDate.trim().equalsIgnoreCase(day1) || getDate.trim().equalsIgnoreCase(day2) || getDate.trim().equalsIgnoreCase(day3);

                        if (doctorSelect.compareToIgnoreCase(name)!=1) {
                            if (b==true) {
                                if (specializationSelect.compareToIgnoreCase(specialization)!=1) {
                                    list.add(doctor);
                                  ck2=false;
                                  ck3=false;
                                }
                            }
                        } else if (doctorSelect.compareToIgnoreCase(name)!=1 && b==true) {
                            list.add(doctor);
                            ck2=false;
                            ck3=false;
                        } else if (specializationSelect.compareToIgnoreCase(specialization)!=1 && b==true && ck2==true) {
                            list.add(doctor);
                            ck3=false;
                        }else if (b==true && ck3==true) {
                            list.add(doctor);
                        }

                    }


                    int siz = list.size();
                    if (siz == 0) {
                        Toast.makeText(DoctorListActivity.this, "Sorry! Doctor Is Not Found!!", Toast.LENGTH_SHORT).show();
                        Intent intentBack = new Intent(DoctorListActivity.this, MainActivity.class);
                        startActivity(intentBack);
                    }

                    if (FullDate!=null)
                        myAdapter = new MyAdapter(DoctorListActivity.this, list, FullDate);

                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());

                }
            });

        } else {
            getDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Log.d("data", dataSnapshot.toString());
                        Doctor doctor = dataSnapshot.getValue(Doctor.class);

                        viewmoreDoctor.add(doctor);
                    }


                    viewMoreAdapter = new ViewMoreAdapter(DoctorListActivity.this, viewmoreDoctor);

                    recyclerView2.setAdapter(viewMoreAdapter);
                    viewMoreAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());

                }
            });
        }

    }


}
