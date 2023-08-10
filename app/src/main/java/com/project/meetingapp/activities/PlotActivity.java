package com.project.meetingapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.project.meetingapp.MyMarkerView;
import com.project.meetingapp.R;

import java.util.ArrayList;

public class PlotActivity extends AppCompatActivity {
    //Initialize variable
    BarChart barChart;
    PieChart pieChart;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        //Assign variable
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        lineChart = findViewById(R.id.line_chart);



        //Draft
        //connect to db
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //read data for LineCHart
        db.collection("summary")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    private static final String TAG2 = "received data";
                    ArrayList<Entry> entries = new ArrayList<>();
                    float i = 1;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String SV = document.get("patient_number").toString();
                                Float SensorValue = Float.parseFloat(SV);
                                entries.add(new Entry(i,SensorValue));
                                i = i + 1;
                            }
                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            List<String> xAxisValues = new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "Decemeber"));
//                            List<Entry> incomeEntries = getIncomeEntries();
                            dataSets = new ArrayList<>();
                            LineDataSet set1;

                            set1 = new LineDataSet(entries, "Monthly patient overview");
                            set1.setColor(Color.rgb(65, 168, 121));
                            set1.setValueTextColor(Color.rgb(55, 70, 73));
                            set1.setValueTextSize(10f);
                            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                            dataSets.add(set1);

                            //customization
                            lineChart.setTouchEnabled(true);
                            lineChart.setDragEnabled(true);
                            lineChart.setScaleEnabled(false);
                            lineChart.setPinchZoom(false);
                            lineChart.setDrawGridBackground(false);
                            lineChart.setExtraLeftOffset(15);
                            lineChart.setExtraRightOffset(15);
                            //to hide background lines
                            lineChart.getXAxis().setDrawGridLines(true);
                            lineChart.getAxisLeft().setDrawGridLines(true);
                            lineChart.getAxisRight().setDrawGridLines(true);

                            //to hide right Y and top X border
                            YAxis rightYAxis = lineChart.getAxisRight();
                            rightYAxis.setEnabled(true);
                            YAxis leftYAxis = lineChart.getAxisLeft();
                            leftYAxis.setEnabled(true);
                            XAxis topXAxis = lineChart.getXAxis();
                            topXAxis.setEnabled(false);


                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setGranularity(1f);
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setEnabled(true);
                            xAxis.setDrawGridLines(false);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setLabelRotationAngle(-60f);

                            set1.setLineWidth(4f);
                            set1.setCircleRadius(3f);
                            set1.setDrawValues(false);

                            //String setter in x-Axis
                            lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

                            LineData data = new LineData(dataSets);
                            lineChart.setData(data);
                            lineChart.animateX(2000);
                            lineChart.invalidate();
                            lineChart.getLegend().setEnabled(false);
                            lineChart.getDescription().setEnabled(true);
//                            lineChart.setContentDescription("Monthly overview");

                        } else {
                            Log.w(TAG2, "Error getting documents.", task.getException());
                        }
                    }

                });



        //read data for barchart
        db.collection("disease")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    private static final String TAG2 = "received data";
                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    List<String> disease_name = new ArrayList<>();
                    float i = 1;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                String SV1 = document.get("Disease").toString();
                                disease_name.add(SV1);
                                String SV = document.get("patient_number").toString();
                                Float SensorValue = Float.parseFloat(SV);
                                barEntries.add(new BarEntry(i,SensorValue));
                                i = i + 1;

                            }
                            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                            List<String> xAxisValues = disease_name;
//                            List<Entry> incomeEntries = getIncomeEntries();
                            dataSets = new ArrayList<>();
                            BarDataSet set1;

                            set1 = new BarDataSet(barEntries, "Patients");
                            set1.setColors(ColorTemplate.COLORFUL_COLORS);
//                            set1.setColor(Color.rgb(65, 168, 121));
                            set1.setValueTextColor(Color.rgb(55, 70, 73));
                            set1.setValueTextSize(10f);
                            dataSets.add(set1);

                            //customization
//        LineChart lineChart = findByViewId(R.id.line_chart);
                            barChart.setTouchEnabled(true);
                            barChart.setDragEnabled(true);
                            barChart.setScaleEnabled(false);
                            barChart.setPinchZoom(false);
                            barChart.setDrawGridBackground(false);
                            barChart.setExtraLeftOffset(15);
                            barChart.setExtraRightOffset(15);
                            //to hide background lines
                            barChart.getXAxis().setDrawGridLines(false);
                            barChart.getAxisLeft().setDrawGridLines(true);
                            barChart.getAxisRight().setDrawGridLines(false);

                            //to hide right Y and top X border
                            YAxis rightYAxis = barChart.getAxisRight();
                            rightYAxis.setEnabled(false);
                            YAxis leftYAxis = barChart.getAxisLeft();
                            leftYAxis.setEnabled(true);
                            XAxis topXAxis = barChart.getXAxis();
                            topXAxis.setEnabled(false);


                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setGranularity(1f);
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setEnabled(true);
                            xAxis.setDrawGridLines(false);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            float val = -60;
                            xAxis.setLabelRotationAngle(val);
                            xAxis.setCenterAxisLabels(true);

                    /*        set1.setLineWidth(4f);
                            set1.setCircleRadius(3f);
                            set1.setDrawValues(false);*/
//        set1.setCircleHoleColor(getResources().getColor(R.color.bliue));
//        set1.setCircleColor(getResources().getColor(R.color.pie_color_4));

                            //String setter in x-Axis
                            barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

                            BarData data = new BarData(dataSets);
                            barChart.setData(data);
                            barChart.animateX(5000);
                            barChart.invalidate();
                            barChart.getLegend().setEnabled(false);
                            barChart.getDescription().setEnabled(false);

                        } else {
                            Log.w(TAG2, "Error getting documents.", task.getException());
                        }
                    }

                });



        //read data for PieChart
        db.collection("ICU")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    private static final String TAG2 = "received data";
                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                    //List<String> patient_cond = new ArrayList<>();


                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG2, document.getId() + " => " + document.getData());
                                //Put line chart value
//                                int patient_number = Integer.parseInt((String) document.get("patient_number"));


                                /*String SV1 = document.get("patients_condition").toString();
                                patient_cond.add(SV1);*/
                                String SV2 = document.get("num").toString();
                                Float id_num = Float.parseFloat(SV2);
                                String SV = document.get("patient_number").toString();
                                Float SensorValue = Float.parseFloat(SV);
                                pieEntries.add(new PieEntry(SensorValue,id_num));


                            }

                            //Initialize pie dataset
                            PieDataSet pieDataSet = new PieDataSet(pieEntries, "Death|Emergency|Normal|ICU|Moribund");
                            //set colors
                            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            //hide draw value
                            pieDataSet.setDrawValues(true);
                            //set bar data
                            pieChart.setData(new PieData(pieDataSet));
                            //set animation
                            pieChart.animateXY(5000,5000);
                            pieChart.getDescription().setEnabled(false);

                        } else {
                            Log.w(TAG2, "Error getting documents.", task.getException());
                        }
                    }

                });


    }

}
