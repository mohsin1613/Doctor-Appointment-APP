package com.project.meetingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.meetingapp.AESHelper;
import com.project.meetingapp.R;
import com.project.meetingapp.adapters.DeleteUserAdapter;
import com.project.meetingapp.adapters.UpdateUserInfoAdapter;
import com.project.meetingapp.models.UserAllInfo;
import com.project.meetingapp.models.UserInfo;
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

public class UpdateUserInfoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserAllInfo> userList;
    UpdateUserInfoAdapter updateUserInfoAdapter;
    private final String key = "1122334455667700";
    private String  decryptedName = "",decryptedMail = "",decryptedPass = "",
            decryptedPhone = "",decryptedFee = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.lv_edit_users);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUserInfoAdapter = new UpdateUserInfoAdapter(this,userList);
        recyclerView.setAdapter(updateUserInfoAdapter);

        userInfo();
    }

    public void userInfo(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereNotEqualTo(Constants.KEY_USER_TYPE, "Desktop Admin")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            try {
                                SecretKey secret = AESHelper.generateKey(key);
                                decryptedName = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_FULL_NAME),secret);
                                decryptedMail = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_EMAIL),secret);
                                decryptedPass = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_PASSWORD),secret);
                                decryptedPhone = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_MOBILE_NUM),secret);
                                decryptedFee = AESHelper.decryptMsg(documentSnapshot.getString(Constants.KEY_FEE),secret);
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

                            userList.add(
                                    new UserAllInfo(
                                            decryptedName,
                                            documentSnapshot.getId(),
                                            decryptedPhone,
                                            documentSnapshot.getString(Constants.KEY_DATE_OF_BIRTH),
                                            documentSnapshot.getString(Constants.KEY_SEX),
                                            decryptedMail,
                                            decryptedPass,
                                            documentSnapshot.getString(Constants.KEY_SPECIALIZATION),
                                            documentSnapshot.getString(Constants.KEY_SCHEDULE),
                                            decryptedFee,
                                            documentSnapshot.getString(Constants.KEY_USER_TYPE)
                                    )
                            );
                        }
                        updateUserInfoAdapter.notifyDataSetChanged();
                    }
                });
    }
}