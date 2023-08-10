package com.project.meetingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.project.meetingapp.AESHelper;
import com.project.meetingapp.R;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private MaterialButton buttonSignIn;
    private ProgressBar signInProgress;
    private PreferenceManager preferenceManager;
    private String encryptedPass = " ",encryptedMail = "";
    private final String key = "1122334455667700";
    SecretKey secret;
    private String  decryptedName = "",decryptedMail = "",
            decryptedPass = "",decryptedMobil = "", decryptedFee = "";
    private Button submit;
    private EditText Codetext;
    String checker="",phoneNumber="";
    private LinearLayout linearLayout;
    private ConstraintLayout constraintLayout;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResndToken;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        mAuth = FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        submit = findViewById(R.id.btn_submit);
        Codetext = findViewById(R.id.codeText);
        linearLayout = findViewById(R.id.linLayout);
        constraintLayout = findViewById(R.id.constraint_layout);

        preferenceManager = new PreferenceManager(getApplicationContext());
        signInProgress    = findViewById(R.id.progressBarSignIn);

       if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.textSignUp).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));

        inputEmail    = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        buttonSignIn  = findViewById(R.id.buttonSignIn);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(SignInActivity.this,"Invalid phone number.....",Toast.LENGTH_LONG).show();
                loadingbar.dismiss();
                constraintLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                constraintLayout.setVisibility(View.GONE);
                checker = "Code Sent";
                linearLayout.setVisibility(View.VISIBLE);

                mVerificationId=s;
                mResndToken=forceResendingToken;

                loadingbar.dismiss();
                Toast.makeText(SignInActivity.this,"Code has been sent,Please check...",Toast.LENGTH_LONG).show();

            }
        };

        buttonSignIn.setOnClickListener(view -> {
            if (inputEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignInActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
                Toast.makeText(SignInActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignInActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    secret = AESHelper.generateKey(key);
                    encryptedMail = AESHelper.encryptMsg(inputEmail.getText().toString(),secret);
                    encryptedPass = AESHelper.encryptMsg(inputPassword.getText().toString(),secret);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidParameterSpecException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                signIn();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificaticode = Codetext.getText().toString();
                if(verificaticode.equals("")){
                    Toast.makeText(SignInActivity.this,"Please write verification code", Toast.LENGTH_LONG).show();
                }
                else{
                    loadingbar.setTitle("Code Verification");
                    loadingbar.setMessage("Please Wait,While we are Varyfing your code");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,verificaticode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signIn() {
        buttonSignIn.setVisibility(View.INVISIBLE);
        signInProgress.setVisibility(View.VISIBLE);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, encryptedMail)
                .whereEqualTo(Constants.KEY_PASSWORD, encryptedPass)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        signInProgress.setVisibility(View.INVISIBLE);

                        try {
                            decryptedName = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_FULL_NAME),secret);
                            decryptedMobil = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_MOBILE_NUM),secret);
                            decryptedFee = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_FEE),secret);
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_USER_TYPE, documentSnapshot.getString(Constants.KEY_USER_TYPE));
                        preferenceManager.putString(Constants.KEY_FULL_NAME, decryptedName);
                        preferenceManager.putString(Constants.KEY_MOBILE_NUM, decryptedMobil);
                        preferenceManager.putString(Constants.KEY_SEX, documentSnapshot.getString(Constants.KEY_SEX));
                        preferenceManager.putString(Constants.KEY_DATE_OF_BIRTH, documentSnapshot.getString(Constants.KEY_DATE_OF_BIRTH));
                        preferenceManager.putString(Constants.KEY_SPECIALIZATION, documentSnapshot.getString(Constants.KEY_SPECIALIZATION));
                        preferenceManager.putString(Constants.KEY_SCHEDULE, documentSnapshot.getString(Constants.KEY_SCHEDULE));
                        preferenceManager.putString(Constants.KEY_FEE, decryptedFee);
                        preferenceManager.putString(Constants.KEY_EMAIL, inputEmail.getText().toString());
                        preferenceManager.putString(Constants.KEY_PASSWORD, inputPassword.getText().toString());
                        two_step_auth(decryptedMobil);
                    } else {
                        signInProgress.setVisibility(View.INVISIBLE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(SignInActivity.this, "Unable to sign in", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void two_step_auth(String decryptedMobil) {
        phoneNumber= "+88"+decryptedMobil ;
        if(!decryptedMobil.equals("")){
            loadingbar.setTitle("Phone Number Verification");
            loadingbar.setMessage("Please Wait,While we are Varyfing your phone number");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    SignInActivity.this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }
        else{
            Toast.makeText(SignInActivity.this,"incorrect phone number",Toast.LENGTH_LONG).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            loadingbar.dismiss();
                            Toast.makeText(SignInActivity.this,"Congratulations,you are login successfully",Toast.LENGTH_LONG).show();
                            sendUserToMainActivity();
                        } else {
                            loadingbar.dismiss();
                            String e = task.getException().toString();
                            Toast.makeText(SignInActivity.this,"Error: "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendUserToMainActivity() {
        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}