package com.project.meetingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.project.meetingapp.R;
import com.project.meetingapp.models.Appointment;
import com.project.meetingapp.models.UserInfo;
import com.project.meetingapp.utilities.Constants;

import java.util.ArrayList;

public class DeleteUserAdapter extends RecyclerView.Adapter<DeleteUserAdapter.DeleteUserViewHolder>{
    Context mContext;
    ArrayList<UserInfo> appointmentArrayLists;

    public DeleteUserAdapter(Context mContext, ArrayList<UserInfo> appointmentArrayLists) {
        this.mContext = mContext;
        this.appointmentArrayLists = appointmentArrayLists;
    }

    @NonNull
    @Override
    public DeleteUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.remaing_bills, parent, false);
        return new DeleteUserAdapter.DeleteUserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteUserViewHolder holder, int position) {
        UserInfo userInfo;
        userInfo = appointmentArrayLists.get(position);

        holder.textView.setText(userInfo.getNam()+" ("+userInfo.getType()+")");

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_USERS)
                        .document(userInfo.getId()).delete();
                appointmentArrayLists.remove(userInfo);
                notifyDataSetChanged();
                Toast.makeText(mContext, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentArrayLists.size();
    }

    public class DeleteUserViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public DeleteUserViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt);
        }
    }
}
