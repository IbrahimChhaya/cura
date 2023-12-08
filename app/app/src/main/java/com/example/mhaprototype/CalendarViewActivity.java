package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import java.util.Calendar;

public class CalendarViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);


        CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        for(int x= 0 ; x < 5 ; ++x)
        {
            calendarView.getFirstDayOfWeek();

        }
    }
}