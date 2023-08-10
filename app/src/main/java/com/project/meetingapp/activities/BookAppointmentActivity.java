package com.project.meetingapp.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.project.meetingapp.R;
import com.project.meetingapp.utilities.Constants;
import com.project.meetingapp.utilities.NotifyUser;
import com.project.meetingapp.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookAppointmentActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    ///date picker
    private DatePickerDialog datePickerDialog;
    private Button datepickerbtn;
    int week;
    String res;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        preferenceManager = new PreferenceManager(getApplicationContext());
        addToSms("Msg");
        ///date picker
        initDatePicker();
        datepickerbtn = findViewById(R.id.datepicker);
        ///find all autocomplete view and imageview

        final AutoCompleteTextView doctor = (AutoCompleteTextView) findViewById(R.id.doctor);
        final AutoCompleteTextView specialization = (AutoCompleteTextView) findViewById(R.id.specialization);
        final AppCompatButton btn = (AppCompatButton) findViewById(R.id.btn);
        final ImageView image2 = (ImageView) findViewById(R.id.imag2);
        final ImageView image3 = (ImageView) findViewById(R.id.imag3);
        final TextView rofiq=(TextView)findViewById(R.id.rofiq);
        final TextView mehedi=(TextView)findViewById(R.id.mehedi);
        final TextView shariar=(TextView)findViewById(R.id.shariar);
        final TextView aminul=(TextView)findViewById(R.id.aminul);
        final AppCompatButton btn1=(AppCompatButton)findViewById(R.id.btn1);
        final AppCompatButton btn2=(AppCompatButton)findViewById(R.id.btn2);
        final AppCompatButton btn3=(AppCompatButton)findViewById(R.id.btn3);
        final AppCompatButton btn4=(AppCompatButton)findViewById(R.id.btn4);

        final TextView viewmore = (TextView) findViewById(R.id.viewmore);


        doctor.setThreshold(2);
        specialization.setThreshold(1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, BookAppointmentActivity.doctor);
        doctor.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, BookAppointmentActivity.specialization);
        specialization.setAdapter(adapter2);


        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor.showDropDown();
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specialization.showDropDown();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Doctor = doctor.getText().toString();
                String Specialization = specialization.getText().toString();
                String fullDate = datepickerbtn.getText().toString();
                String dateToday = res;
                String check="clickbtn";

                Intent intentDoctorList = new Intent(BookAppointmentActivity.this, DoctorListActivity.class);
                intentDoctorList.putExtra("keydoctor", Doctor);
                intentDoctorList.putExtra("keyspecialization", Specialization);
                intentDoctorList.putExtra("fullDate", fullDate);
                intentDoctorList.putExtra("day", dateToday);
                intentDoctorList.putExtra("clickbtn",check);
                intentDoctorList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentDoctorList);
            }
        });

        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookAppointmentActivity.this,DoctorListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName=rofiq.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                String fullDate=formatter.format(date);
                Intent intent=new Intent(BookAppointmentActivity.this,AppointmentComplete.class);
                intent.putExtra("doctor",doctorName);
                intent.putExtra("fullDate",fullDate);
                startActivity(intent);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName=mehedi.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                String fullDate=formatter.format(date);
                Intent intent=new Intent(BookAppointmentActivity.this,AppointmentComplete.class);
                intent.putExtra("doctor",doctorName);
                intent.putExtra("fullDate",fullDate);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName=shariar.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                String fullDate=formatter.format(date);
                Intent intent=new Intent(BookAppointmentActivity.this,AppointmentComplete.class);
                intent.putExtra("doctor",doctorName);
                intent.putExtra("fullDate",fullDate);
                startActivity(intent);
            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName=aminul.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                String fullDate=formatter.format(date);
                Intent intent=new Intent(BookAppointmentActivity.this,AppointmentComplete.class);
                intent.putExtra("doctor",doctorName);
                intent.putExtra("fullDate",fullDate);
                startActivity(intent);
            }
        });

    }


    ///init of date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
                Date dayy = new Date(year, month, day - 1);
                res = simpledateformat.format(dayy);
                month = month + 1;
                String date = makeDateString(day, month, year);
                datepickerbtn.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        //style of Calendar with alertdialog box
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        return month + "/ " + day + "/ " + year;
    }


    private static final String[] doctor = new String[]{"Dr.Rofiqul Islam", "Dr.Kashem Tipu", "Dr.Mohsin Hossain", "Dr.Akram Hossain", "Dr.Abdul Aziz", "Dr.Shariar"};
    private static final String[] specialization = new String[]{"Neurology", "Gastrology", "psychiatrist", "Cardiology", "Oncology", "Eye Specialist"};


    //open datepicker dialog to show the result
    public void openDatePicker(View view) {

        datePickerDialog.show();
    }

    public void addToSms(String messageText) {
        name = preferenceManager.getString(Constants.KEY_FULL_NAME);
        String number = preferenceManager.getString(Constants.KEY_MOBILE_NUM);
        String message = messageText;
        String messageDate = Integer.toString(5) + "/" + Integer.toString(5) + "/" + Integer.toString(2022); //Convert integers to string
        String messageTime = String.format("%02d:%02d", 9, 38); // Convert to String and format hours and minutes
        String messageStatus = "Pending";

        // Start multi-thread to insert sms to database and start alarm manager
        BookAppointmentActivity.ScheduleSmsAsyncTask task = new BookAppointmentActivity.ScheduleSmsAsyncTask();
        task.execute(name, number, messageDate, messageTime, message, messageStatus);
    }

    private class ScheduleSmsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... string) {
            String result = "SMS Successfully Scheduled";
            try {
                // Construct a Sms object and pass it to the helper for database insertion
                //int SmsID = smsDatabaseHelper.addSms(new Sms(string[0], string[1], string[2], string[3], string[4], string[5]));

                // Create calendar with selected date and time
                Calendar c = Calendar.getInstance();
               /* c.set(Calendar.YEAR, setYear);
                c.set(Calendar.MONTH, setMonth);
                c.set(Calendar.DAY_OF_MONTH, setDay);
                c.set(Calendar.HOUR_OF_DAY, setHour);
                c.set(Calendar.MINUTE, setMinute);
                c.set(Calendar.SECOND, 0);*/

                // Create alarm manager
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Pass SmsID to AlarmReceiver class
                Intent intent = new Intent(getApplicationContext(), NotifyUser.class);
                intent.putExtra("name", name);
                intent.putExtra("date", "22:30:00");

                //Set SmsID as unique id, Set time to calender, Start alarm
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+(60*1000), pendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
                result = "SMS failed to schedule";
            }
            return result;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Display result message
            Toast.makeText(BookAppointmentActivity.this, result, Toast.LENGTH_SHORT).show();

            // Clear Sms Fields
            //resetInput();
        }
    }
}
