package com.project.meetingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.meetingapp.R;
import com.project.meetingapp.models.Appointment;

import java.util.ArrayList;

public class RemainBillsAdapter extends RecyclerView.Adapter<RemainBillsAdapter.RemainBillsViewHolder>{
    Context mContext;
    ArrayList<String> appointmentArrayLists;

    public RemainBillsAdapter(Context mContext, ArrayList<String> appointmentArrayLists) {
        this.mContext = mContext;
        this.appointmentArrayLists = appointmentArrayLists;
    }

    @NonNull
    @Override
    public RemainBillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.remaing_bills, parent, false);
        return new RemainBillsAdapter.RemainBillsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RemainBillsViewHolder holder, int position) {
        holder.textView.setText(appointmentArrayLists.get(position));
    }

    @Override
    public int getItemCount() {
        return appointmentArrayLists.size();
    }

    public class RemainBillsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public RemainBillsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt);
        }
    }
}
