package com.project.meetingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.meetingapp.AESHelper;
import com.project.meetingapp.R;
import com.project.meetingapp.adapters.GridviewAdapter;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String drawerImageUrl, drawerUserName, drawerStatus ;
    private PreferenceManager preferenceManager;
    private GridviewAdapter mAdapter;
    private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceManager = new PreferenceManager(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HMS");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.header);

        //=======   drawer_header
        View headerview = navigationView.getHeaderView(0);
        ImageView drawerImage = headerview.findViewById(R.id.drawer_image);
        TextView drawerUserTV = (TextView) headerview.findViewById(R.id.drawer_userName);

        Log.d("user","name1: "+drawerUserName+" "+ drawerStatus) ;
        drawerUserTV.setText(preferenceManager.getString(Constants.KEY_FULL_NAME));
        /*drawerUserTV.setText("Rofiqul Islam");
        Picasso.get().load("https://m.cricbuzz.com/a/img/v1/192x192/i1/c170912/shakib-al-hasan.jpg")
                .placeholder(R.drawable.profile_image)
                .into(drawerImage);*/
        /*headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
            }
        });*/

        //======  drawer_menu
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        //Gridvi


        //Log.d("typ",preferenceManager.getString(Constants.KEY_USER_TYPE));
        String user_type = preferenceManager.getString(Constants.KEY_USER_TYPE);
        prepareList(user_type);
        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(this,listCountry, listFlag);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, mAdapter.g(position), Toast.LENGTH_SHORT).show();
                changeActivity(user_type,position);
            }
        });

    }

    private void prepareList(String type) {
        listCountry = new ArrayList<String>();
        listFlag = new ArrayList<Integer>();

        switch (type) {
            case "Patient":
                listCountry.add("Personal Info");
                listCountry.add("Call For Appointment");
                listCountry.add("Book an Appointment");
                listCountry.add("View Reports");
                listCountry.add("Online Payment");
                listCountry.add("Telemedicine");
                listCountry.add("Location");

                listFlag.add(R.drawable.personal_information);
                listFlag.add(R.drawable.call);
                listFlag.add(R.drawable.appointment);
                listFlag.add(R.drawable.report);
                listFlag.add(R.drawable.online_payment_96);
                listFlag.add(R.drawable.calling);
                listFlag.add(R.drawable.maps);
                break;
            case "Doctor":
                listCountry.add("Personal Info");
                listCountry.add("Add Specializations");
                listCountry.add("Set Referral Timings");
                listCountry.add("Upload Patient Report");
                listCountry.add("View Staff Assigned");
                listCountry.add("Give/View Feedback");
                listCountry.add("Apply For Leaves");

                listFlag.add(R.drawable.personal_information);
                listFlag.add(R.drawable.add_sp);
                listFlag.add(R.drawable.add_sp);
                listFlag.add(R.drawable.report);
                listFlag.add(R.drawable.assign_staff);
                listFlag.add(R.drawable.feedback);
                listFlag.add(R.drawable.leave);
                break;
            case "Staff Member":
                listCountry.add("Personal Info");
                listCountry.add("List of Assigned Doctors");
                listCountry.add("Give/View Feedback");

                listFlag.add(R.drawable.personal_information);
                listFlag.add(R.drawable.assign_staff);
                listFlag.add(R.drawable.feedback);
                break;
            default:
                listCountry.add("Personal Info");
                listCountry.add("Grant Appointment");
                listCountry.add("Assign Staff");
                listCountry.add("View All Remaining Bills");
                listCountry.add("Add User");
                listCountry.add("Edit User Info");
                listCountry.add("Delete User");
                listCountry.add("Statistics");

                listFlag.add(R.drawable.personal_information);
                listFlag.add(R.drawable.appointment);
                listFlag.add(R.drawable.assign_staff);
                listFlag.add(R.drawable.online_payment_96);
                listFlag.add(R.drawable.add_user);
                listFlag.add(R.drawable.edit_profile);
                listFlag.add(R.drawable.delete);
                listFlag.add(R.drawable.plot);
                break;
        }


    }


    private void changeActivity(String userType,int position){
        switch (userType){
            case "Patient":
                if(position == 0)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 1){
                    String phone = "01713141447";
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                }
                else if(position == 2)
                    startActivity(new Intent(this, BookAppointmentActivity.class));
                else if(position == 3)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 4)
                    startActivity(new Intent(this, PaymentGateayActivity.class));
                else if(position == 5)
                    startActivity(new Intent(this, TelemeetingActivity.class));
                else if(position == 6)
                    startActivity(new Intent(this, MapsActivity.class));
                break;
            case "Doctor":
                if(position == 0)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 1)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 2)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 3)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 4)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 5)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 6)
                    startActivity(new Intent(this, TelemeetingActivity.class));
                break;
            case "Staff Member":
                if(position == 0)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 1)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 2)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                break;
            default:
                if(position == 0)
                    startActivity(new Intent(this, PersonalInfoActivity.class));
                else if(position == 1)
                    startActivity(new Intent(this, GrantAppoinmentActivity.class));
                else if(position == 2)
                    startActivity(new Intent(this, StaffAssignActivity.class));
                else if(position == 3) {
                    Intent i = new Intent(MainActivity.this, RemainingBillsActivity.class);
                    startActivity(i);
                }
                else if(position == 4)
                    startActivity(new Intent(this, AddUserActivity.class));
                else if(position == 5)
                    startActivity(new Intent(this, UpdateUserInfoActivity.class));
                else if(position == 6) {
                    Intent i = new Intent(MainActivity.this, DeleteUserActivity.class);
                    startActivity(i);
                }
                else if(position == 7)
                    startActivity(new Intent(this, PlotActivity.class));
                break;

        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        closeDrawer();

        switch (item.getItemId()){
            case R.id.dashboard:
                break;
            case R.id.hospital_lm:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.logout:
                signOut();
                break;
        }

        return false;
    }

    private void signOut() {
        Toast.makeText(this, "Signing Out...", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(aVoid -> {
                    preferenceManager.clearPreferences();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Unable to sign out", Toast.LENGTH_SHORT).show());
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        super.onBackPressed();
    }


}