package com.project.meetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.meetingapp.AESHelper;
import com.project.meetingapp.R;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Calendar;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AddUserActivity extends AppCompatActivity {
    private EditText inputFullName,inputMobileNum, inputEmail, inputPassword,
            inputSpecialization,inputSchedule,inputVisitFee;
    private MaterialButton buttonSignUp;
    private ProgressBar signUpProgress;
    private TextView dateView;
    private int year, month, day;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String date = " ",user_type = " ",encryptedName = " ",
            encryptedPhoneNum = "",encryptedPass="",encryptedFee="",encryptedMail = "";
    private final String key = "1122334455667700";
    private ExpandableRelativeLayout expandableLayout;
    private Button btn_expand;
    SecretKey secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        signUpProgress    = findViewById(R.id.progressBarSignUp);

        ImageButton imageButtonClick = findViewById(R.id.click);
        dateView       = findViewById(R.id.dateOfBirth);
        inputSpecialization       = findViewById(R.id.specialization);
        inputSchedule       = findViewById(R.id.schedule);
        inputVisitFee       = findViewById(R.id.visit_fee);
        inputFullName       = findViewById(R.id.inputFirstName);
        inputMobileNum       = findViewById(R.id.inputMobileNo);
        inputEmail           = findViewById(R.id.inputEmail);
        inputPassword        = findViewById(R.id.inputPassword);
        buttonSignUp         = findViewById(R.id.buttonSignUp);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        expandableLayout = findViewById(R.id.expandableLayout);
        btn_expand = findViewById(R.id.expandableButton);

        btn_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.toggle();
            }
        });

        buttonSignUp.setOnClickListener(view -> {
            if (inputFullName.getText().toString().trim().isEmpty()) {
                Toast.makeText(AddUserActivity.this, "Enter full name", Toast.LENGTH_SHORT).show();
            } else if (inputMobileNum.getText().toString().trim().isEmpty()) {
                Toast.makeText(AddUserActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
            } else if (inputEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(AddUserActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            } else if (date.isEmpty()) {
                Toast.makeText(AddUserActivity.this, "Select date of birth", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(AddUserActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    secret = AESHelper.generateKey(key);
                    encryptedMail = AESHelper.encryptMsg(inputEmail.getText().toString(),secret);
                    encryptedName = AESHelper.encryptMsg(inputFullName.getText().toString(),secret);
                    encryptedPhoneNum = AESHelper.encryptMsg(inputMobileNum.getText().toString(),secret);
                    encryptedPass = AESHelper.encryptMsg(inputPassword.getText().toString(),secret);
                    if(!inputVisitFee.getText().toString().isEmpty()){
                        encryptedFee = AESHelper.encryptMsg(inputVisitFee.getText().toString(),secret);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidParameterSpecException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                signUp();
            }
        });

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        imageButtonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.users));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //if you want to set any action you can do in this listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                user_type = arg0.getItemAtPosition(position).toString();
                if(position == 1){
                    btn_expand.setVisibility(View.VISIBLE);
                }
                else {
                    btn_expand.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date = day+"/"+month+"/"+year;
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void signUp() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        buttonSignUp.setVisibility(View.INVISIBLE);
        signUpProgress.setVisibility(View.VISIBLE);

        FirebaseFirestore database    = FirebaseFirestore.getInstance();
        HashMap<String, Object> users = new HashMap<>();
        users.put(Constants.KEY_FULL_NAME,encryptedName );
        users.put(Constants.KEY_PAIR_STATUS, false);
        users.put(Constants.KEY_MOBILE_NUM, encryptedPhoneNum);
        users.put(Constants.KEY_DATE_OF_BIRTH, date);
        users.put(Constants.KEY_SEX,radioButton.getText().toString());
        users.put(Constants.KEY_USER_TYPE, user_type);
        users.put(Constants.KEY_EMAIL, encryptedMail);
        users.put(Constants.KEY_PASSWORD, encryptedPass);
        users.put(Constants.KEY_SCHEDULE, inputSchedule.getText().toString());
        users.put(Constants.KEY_SPECIALIZATION, inputSpecialization.getText().toString());
        users.put(Constants.KEY_FEE, encryptedFee);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(users)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddUserActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    buttonSignUp.setVisibility(View.VISIBLE);
                    signUpProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(AddUserActivity.this, "Error woi : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}