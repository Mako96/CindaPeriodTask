package com.example.cindaperiodtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, OnNumberSelected {

    int cycleDuration = 30;
    String date = "";
    TextView mDayTextView = null;
    Calendar periodStartDate = Calendar.getInstance();
    Calendar currentDate = Calendar.getInstance();
    Slider mSlider = null;

    long diffDays = 0;

    float anglePerDay;

    float previousReading = 0.0f;

    //calendar
    int year = 2020;
    int month = 9;
    int dayOfMonth = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        periodStartDate.set(year, month, dayOfMonth);

        DialogFragment datepicker = new DatePickerFragment();
        datepicker.show(getSupportFragmentManager(), "date picker");

        DialogFragment numberpicker = new CyclePickerFragment(this);
        numberpicker.show(getSupportFragmentManager(), "cycle picker");

        anglePerDay = 300 / cycleDuration;



        mSlider = (Slider) findViewById(R.id.slider);
        mSlider.mMax = cycleDuration;
        mDayTextView = (TextView) findViewById(R.id.day);

        final Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        mSlider.registerForSliderUpdates(new Slider.IListenForSliderState() {
            @Override
            public void onSliderMove(float reading) {

                // Vibrate for every day change on the cycle slider.
                if (previousReading != reading)
                    v.vibrate(20);

                previousReading = reading;
                currentDate.set(year, month, dayOfMonth);
                currentDate.add(Calendar.DATE, (int) reading);
                date = DateFormat.getDateInstance().format(currentDate.getTime());
                mDayTextView.setText(date);
            }

            @Override
            public void onSliderUp(float reading) {

            }

            @Override
            public void onThumbSelected() {

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        periodStartDate.set(year, month, dayOfMonth);
        date = DateFormat.getDateInstance().format(periodStartDate.getTime());
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        mDayTextView.setText(date);

        //Calculating difference between dates.
        long millis1 = currentDate.getTimeInMillis();
        long millis2 = periodStartDate.getTimeInMillis();
        long diff = millis1 - millis2;
        if (diff < 0)
            diff = diff * -1;
        diffDays = diff / (24 * 60 * 60 * 1000);

        //Set the period arc based on difference between date entered by user and today.
        mSlider.setPeriodAngle(119, anglePerDay * (7 - diffDays));
    }

    @Override
    public void onNumberSelected(int number) {
        cycleDuration = number;
        //Setting the length of cycle based on user input
        mSlider.mMax = number;
    }
}