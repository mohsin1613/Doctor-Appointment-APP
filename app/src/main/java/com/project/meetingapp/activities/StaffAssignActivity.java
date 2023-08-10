package com.project.meetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.meetingapp.AESHelper;
import com.project.meetingapp.R;
import com.project.meetingapp.models.Appointment;
import com.project.meetingapp.models.Assign;
import com.project.meetingapp.utilities.Constants;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class StaffAssignActivity extends AppCompatActivity {
    Spinner s_doctor, s_staff, d, s;
    Button b_assign;
    ArrayList<Assign> staffList;
    ArrayList<String> staffListStr;
    ArrayList<Assign> doctorList;
    ArrayList<String> doctorListStr;
    String s_n,s_id,d_n,d_id;
    private final String key = "1122334455667700";
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_assign);

        swipeRefreshLayout =  findViewById(R.id.sp_rf_layout);
        s_doctor = (Spinner) findViewById(R.id.spinner_doctor);
        s_staff = (Spinner) findViewById(R.id.spinner_staff);
        b_assign = (Button) findViewById(R.id.b_assign);

        swipeRefreshLayout.setRefreshing(true);
        staffListStr = getStaffInfo();
        doctorListStr = getDoctorInfo();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                staffListStr = getStaffInfo();
                doctorListStr = getDoctorInfo();
            }
        });

        s_staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                s_n = arg0.getItemAtPosition(position).toString();
                s_id = staffList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        s_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                d_n = arg0.getItemAtPosition(position).toString();
                d_id = doctorList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        b_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffListStr.remove(s_n);
                staffList.remove(new Assign(s_n,s_id));
                doctorListStr.remove(d_n);
                doctorList.remove(new Assign(d_n,d_id));
                updateData();
            }
        });
    }

    private ArrayList<String> getStaffInfo(){
        staffList = new ArrayList<>();
        staffListStr = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_USER_TYPE, "Staff Member")
                .whereEqualTo(Constants.KEY_PAIR_STATUS, false)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            SecretKey secret = null;
                            String nam = "";
                            try {
                                secret = AESHelper.generateKey(key);
                                nam = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_FULL_NAME),secret);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeySpecException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            }

                            String id = documentSnapshot.getId();
                            staffListStr.add(nam);
                            staffList.add(new Assign(nam,id));
                        }

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, staffListStr);//setting the country_array to spinner
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_staff.setAdapter(adapter2);
                    }
                });
        return staffListStr;
    }

    private ArrayList<String> getDoctorInfo(){
        doctorList = new ArrayList<>();
        doctorListStr = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_USER_TYPE, "Doctor")
                .whereEqualTo(Constants.KEY_PAIR_STATUS, false)
                .get()
                .addOnCompleteListener(task -> {
                    swipeRefreshLayout.setRefreshing(false);
                    if (task.isSuccessful() && task.getResult() != null) {

                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            SecretKey secret = null;
                            String nam = "";
                            try {
                                secret = AESHelper.generateKey(key);
                                nam = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_FULL_NAME),secret);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeySpecException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            }

                            String id = documentSnapshot.getId();
                            doctorListStr.add(nam);
                            doctorList.add(new Assign(nam,id));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, doctorListStr);//setting the country_array to spinner
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_doctor.setAdapter(adapter);
                    }
                });
        return doctorListStr;
    }

    private void updateData() {
        FirebaseFirestore database    = FirebaseFirestore.getInstance();
        HashMap<String, Object> users = new HashMap<>();
        users.put(Constants.KEY_DOCTOR_NUM,d_n);
        users.put(Constants.KEY_DOCTOR_ID,d_id);
        users.put(Constants.KEY_STAFF_NUM, s_n);
        users.put(Constants.KEY_STAFF_ID,s_id);

        database.collection(Constants.KEY_ASSIGN_STAFFS)
                .add(users)
                .addOnSuccessListener(documentReference -> {
                     documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
                                    (s_id));

                    HashMap<String, Object> updates = new HashMap<>();
                    updates.put(Constants.KEY_PAIR_STATUS, true);
                    documentReference.update(updates);

                   DocumentReference documentReference2 = database.collection(Constants.KEY_COLLECTION_USERS).document(
                            (d_id));
                    documentReference2.update(updates);

                    swipeRefreshLayout.setRefreshing(true);
                    staffListStr = getStaffInfo();
                    doctorListStr = getDoctorInfo();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(StaffAssignActivity.this, "Error woi : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}