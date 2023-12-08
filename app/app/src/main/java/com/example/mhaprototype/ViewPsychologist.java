package com.example.mhaprototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPsychologist extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_psychologist);
    }

    public void openNavigation(View v){
        Intent intent = new Intent(ViewPsychologist.this, DynamicCalendar.class);//AddBooking
        //GlobalVariables.psychID = get psychID based on psuchologists
        startActivity(intent);
    }
}