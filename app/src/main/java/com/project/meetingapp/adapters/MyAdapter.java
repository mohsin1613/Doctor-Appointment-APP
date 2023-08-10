package com.project.meetingapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.project.meetingapp.R;
import com.project.meetingapp.activities.AppointmentComplete;
import com.project.meetingapp.models.Doctor;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    int i=1;
    Context context;
    ArrayList<Doctor> list,viewmore;
    static String fullDate;
    static  String doctorName;

    public MyAdapter(Context context, ArrayList<Doctor> list, String fullDate) {
        this.context = context;
        this.list = list;
        this.fullDate = fullDate;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Doctor doctor = list.get(position);
        holder.name.setText(doctor.getName());
        holder.subtitle.setText(doctor.getSubtitle());
        holder.wplace.setText(doctor.getWplace());
        holder.specialization.setText("Specialization: " + doctor.getSpecialization());
        holder.day1.setText("Day: " + doctor.getDay1() + "," + doctor.getDay2() + "," + doctor.getDay3());
        holder.time.setText("Time: " + doctor.getTime());
        holder.visit.setText("Visit Fee: " + doctor.getVisit());

        doctorName=doctor.getName();

        if (i == 1) {
            holder.image.setImageResource(R.drawable.img2);
            holder.rating.setRating(4);
            i++;
        } else if (i == 2) {
            holder.image.setImageResource(R.drawable.img3); i++;
            holder.rating.setRating(3);
        } else if (i == 3) {
            holder.image.setImageResource(R.drawable.img4); i++;
            holder.rating.setRating(3);
        }
        else if (i==4){
            holder.image.setImageResource(R.drawable.img10);
            i++;
            holder.rating.setRating(4);
        }
        else if(i==5){
            holder.image.setImageResource(R.drawable.img8);
            i++;
            holder.rating.setRating(4);
        }
        else if(i==6){
            holder.image.setImageResource(R.drawable.img9);
            i++;
            holder.rating.setRating(4);
        }
        else if(i==7){
            holder.image.setImageResource(R.drawable.img11);
            i++;
            holder.rating.setRating(4);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, subtitle, wplace, visit, time, day1, specialization;
        ImageView image;
        AppCompatRatingBar rating;
        AppCompatButton btn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            subtitle = itemView.findViewById(R.id.subtitle);
            wplace = itemView.findViewById(R.id.wplace);
            visit = itemView.findViewById(R.id.visit);
            time = itemView.findViewById(R.id.time);
            day1 = itemView.findViewById(R.id.day1);
            specialization = itemView.findViewById(R.id.specialization);
            image = itemView.findViewById(R.id.image);
            rating = itemView.findViewById(R.id.rating);
            btn = itemView.findViewById(R.id.btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AppointmentComplete.class);
                    intent.putExtra("fullDate",fullDate);
                    intent.putExtra("doctor",doctorName);
                    v.getContext().startActivity(intent);

                }
            });

        }


    }


}


