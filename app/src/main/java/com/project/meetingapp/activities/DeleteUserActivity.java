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
import com.project.meetingapp.adapters.RemainBillsAdapter;
import com.project.meetingapp.models.User;
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

public class DeleteUserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserInfo> userList;
    DeleteUserAdapter deleteUserAdapter;
    private final String key = "1122334455667700";
    String nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.lv_all_users);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        deleteUserAdapter = new DeleteUserAdapter(this,userList);
        recyclerView.setAdapter(deleteUserAdapter);

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

                            userList.add(
                                    new UserInfo(
                                            nam,
                                            documentSnapshot.getId(),
                                            documentSnapshot.getString(Constants.KEY_USER_TYPE)
                                    )
                            );
                        }
                        deleteUserAdapter.notifyDataSetChanged();
                    }
                });
    }
}