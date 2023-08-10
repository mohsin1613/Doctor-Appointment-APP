package com.project.meetingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.meetingapp.R;
import com.project.meetingapp.models.Appointment;
import com.project.meetingapp.utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class GrantAppointmentAdapter extends RecyclerView.Adapter<GrantAppointmentAdapter.GrantAppointViewHolder>  {
    Context mContext;
    ArrayList<Appointment> appointmentArrayLists;

    public GrantAppointmentAdapter(Context mContext, ArrayList<Appointment> appointmentArrayLists) {
        this.mContext = mContext;
        this.appointmentArrayLists = appointmentArrayLists;
    }

    @NonNull
    @Override
    public GrantAppointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.grant_appoinment_cv, parent, false);
        return new GrantAppointmentAdapter.GrantAppointViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GrantAppointViewHolder holder, int position) {
        Appointment appointment = appointmentArrayLists.get(position);
        holder.text_name_p.setText(appointment.getPatient_name());
        holder.text_name_d.setText(appointment.getDoctor_name());
        holder.text_problm.setText(appointment.getProblem());

        holder.acpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                DocumentReference documentReference =
                        database.collection(Constants.KEY_COLLECTION_APPOINTMENT).document(
                                (appointment.getApoinment_id()));

                HashMap<String, Object> updates = new HashMap<>();
                updates.put(Constants.KEY_GRANT_APOINMNT, true);
                documentReference.update(updates)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(mContext, "Appointment Granted", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> Toast.makeText(mContext, "error ", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentArrayLists.size();
    }

    public class GrantAppointViewHolder extends RecyclerView.ViewHolder {
        TextView text_name_p,text_name_d,text_problm;
        Button acpt;
        public GrantAppointViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name_p = itemView.findViewById(R.id.p_name_adapter);
            text_name_d = itemView.findViewById(R.id.d_name_adapter);
            text_problm = itemView.findViewById(R.id.problem_adapter);
            acpt = itemView.findViewById(R.id.acpt);


        }
    }
}
