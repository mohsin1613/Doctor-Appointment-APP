package com.project.meetingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.meetingapp.R;
import com.project.meetingapp.activities.UpdateUsersActivity;
import com.project.meetingapp.models.UserAllInfo;
import com.project.meetingapp.models.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdateUserInfoAdapter extends RecyclerView.Adapter<UpdateUserInfoAdapter.UpdateUserInfoViewHolder>{
    Context mContext;
    ArrayList<UserAllInfo> appointmentArrayLists;

    public UpdateUserInfoAdapter(Context mContext, ArrayList<UserAllInfo> appointmentArrayLists) {
        this.mContext = mContext;
        this.appointmentArrayLists = appointmentArrayLists;
    }


    @NonNull
    @Override
    public UpdateUserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.remaing_bills, parent, false);
        return new UpdateUserInfoAdapter.UpdateUserInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateUserInfoViewHolder holder, int position) {
        UserAllInfo userInfo;
        userInfo = appointmentArrayLists.get(position);

        holder.textView.setText(userInfo.getName()+" ("+userInfo.getTyp()+")");

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateUsersActivity.class);
                intent.putExtra("data",userInfo.getName()+"="+userInfo.getId()+"="+userInfo.getPhone_num()+
                        "="+userInfo.getDate_of_birth()+"="+userInfo.getSex()+"="+userInfo.getEmail()+"="+
                        userInfo.getPass()+"="+userInfo.getSchedule()+"="+userInfo.getSpecialization()+
                        "="+userInfo.getFee()+"="+userInfo.getTyp());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentArrayLists.size();
    }

    public class UpdateUserInfoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public UpdateUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt);
        }
    }
}
