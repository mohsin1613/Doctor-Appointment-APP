package com.project.meetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.meetingapp.AESHelper;
import com.project.meetingapp.R;
import com.project.meetingapp.adapters.GrantAppointmentAdapter;
import com.project.meetingapp.adapters.RemainBillsAdapter;
import com.project.meetingapp.models.Appointment;
import com.project.meetingapp.utilities.Constants;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RemainingBillsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> billList;
    RemainBillsAdapter remainBillsAdapter;
    private final String key = "1122334455667700";
    String nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining_bills);

        billList = new ArrayList<>();
        recyclerView = findViewById(R.id.lv_remaining_bills);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getInfo();

    }

    private void getInfo(){
        remainBillsAdapter = new RemainBillsAdapter(this,billList);
        recyclerView.setAdapter(remainBillsAdapter);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_APPOINTMENT)
                .whereEqualTo(Constants.KEY_GRANT_APOINMNT, true)
                .whereEqualTo(Constants.KEY_BILL_STATUS, "unpaid")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {

                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            billList.add(
                                    documentSnapshot.getString(Constants.KEY_PATIENT_NUM)+"  Unpaid Bill  "+
                                            documentSnapshot.getString(Constants.KEY_FEE)
                            );
                        }
                        remainBillsAdapter.notifyDataSetChanged();
                    }
                });
    }
}