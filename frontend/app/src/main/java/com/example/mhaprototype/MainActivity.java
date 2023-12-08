package com.example.mhaprototype;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.github.badoualy.datepicker.MonthView;
import com.github.badoualy.datepicker.TimelineView;
import com.google.android.material.textfield.TextInputEditText;


import Barcode.Barcode_Modified_Scanner;
import ChildReports.ChildReportsMain;
import ChildTestSubsystem.AssignmentMain;
import ChildTestSubsystem.ChildTestMain;
import InAppGuidance.AppIntro;
import InAppGuidance.CustomAppIntro;
import PsychologistList.SearchablePsychologistList;
import StartUpStuff.GuardianChildRegister.GuardianRegistersChild1;
import ResourceHub.ResourceHubMain;

//import ResourceHub.ResourceHubMain;
//import StartUpStuff.NewStartUp;
//import StartUpStuff.SignInRoles;

import StartUpStuff.NewStartUp;
import VideoSubsystem.VideoMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;
//import StartUpStuff.NewStartUp;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private MonthView monthView;
    private TimelineView timelineView;

    //bottom sheet testing
    Button btnLogin;
    LinearLayout linearLayout;

    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testing for the function


         Intent intent = new Intent(MainActivity.this, NewStartUp.class); //NewStartUp   Barcode_Scanner  Barcode_Modified_Scanner CustomAppIntro
         startActivity(intent);
    }



    private Runnable sliderRunnable = new Runnable()
    {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };


    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private int currentPage;

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            currentPage = position;
            GlobalVariables.profilePicture = "child" + position;
        }
    }

}