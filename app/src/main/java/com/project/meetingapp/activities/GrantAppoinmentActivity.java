package com.project.meetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.meetingapp.R;
import com.project.meetingapp.adapters.GrantAppointmentAdapter;
import com.project.meetingapp.models.Appointment;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.PreferenceManager;

import java.util.ArrayList;

public class GrantAppoinmentActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    ArrayList<Appointment> appointmentArrayList;
    private RecyclerView recyclerView;
    GrantAppointmentAdapter grantAppointmentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String p_nam,d_nam,prblm, ap_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grant_appoinment);


        appointmentArrayList = new ArrayList<>();
        preferenceManager = new PreferenceManager(getApplicationContext());

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.all_pending_appontments);

        swipeRefreshLayout.setRefreshing(true);
        appointmentArrayList = getUsers();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        grantAppointmentAdapter = new GrantAppointmentAdapter(this,appointmentArrayList);
        recyclerView.setAdapter(grantAppointmentAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appointmentArrayList.clear();
                appointmentArrayList = getUsers();
            }
        });
    }

    private ArrayList<Appointment> getUsers() {
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    database.collection(Constants.KEY_COLLECTION_APPOINTMENT)
            .whereEqualTo(Constants.KEY_GRANT_APOINMNT, false)
            .get()
            .addOnCompleteListener(task -> {
                swipeRefreshLayout.setRefreshing(false);
                if (task.isSuccessful() && task.getResult() != null) {

                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        p_nam = documentSnapshot.getString(Constants.KEY_PATIENT_NUM);
                        d_nam = documentSnapshot.getString(Constants.KEY_DOCTOR_NUM);
                        prblm = documentSnapshot.getString(Constants.KEY_PROBLEM);
                        ap_id = documentSnapshot.getId();

                        appointmentArrayList.add(new Appointment(
                                p_nam,
                                d_nam,
                                prblm,
                                ap_id
                        ));
                    }
                    grantAppointmentAdapter.notifyDataSetChanged();
                }
            });
       return appointmentArrayList;
    }

}