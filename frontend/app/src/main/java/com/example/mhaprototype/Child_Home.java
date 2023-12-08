package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.getkeepsafe.taptargetview.TapTarget;
//import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Account.Child.UpdateChildAccount;
import Appointment.AppointmentMain;
import Appointment.AppointmentModel;
import Appointment.ErrorPages.NoAppointmentPage;
import Appointment.TabbedAppointmentMain;

import Barcode.BarcodePairing;

import Chat.ChatPsychList;
import Chat.Counsellor.CounsellorChatBox;

import ChildMoodTracker.MoodTracker;
import ChildTestSubsystem.ChildTestMain;
import ChildTestSubsystem.Model.AssignedTest;
import ResourceHub.ResourceHubMain;
import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import StartUpStuff.NewStartUp;
import VideoSubsystem.BookingModelVideo;
import VideoSubsystem.VideoMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Child_Home extends AppCompatActivity{

    //String[] items = {"  My Account", "  Generate Pair Code", "   Logout"};
    private static final int CAMERA_REQUEST_CODE = 101;
    APIInterface apiInterface;
    CardView viewChildMeetings;
    CardView childMoodTracker;
    CardView childTodoList;
    CardView resourceHub;
    CardView childAccount;
    CardView generatePairCode;
    CardView childLogout;
    CardView childChat;
    CardView psychChildChat;
    CardView counsellorChat;
    CardView childVideoMeeting;

    ImageView picture;
    Dialog dLog;
    GridLayout gridLayout;

    //dynamic home variables
    int countTests = 0;
    int countBookings = 0;
    boolean virtualSession = false;
    int pairId;

    //chat bubbles
    CardView crdChatStatusPsych;
    CardView crdChildCounsellorChatBubble;

    //textview for child points
    TextView txtChildPoints;
    Dialog dLog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_home_redesign);

        //instantiating the card views
        viewChildMeetings = (CardView)findViewById(R.id.child_view_meetings);
        childMoodTracker = (CardView)findViewById(R.id.child_mood_tracker);
        childTodoList = (CardView)findViewById(R.id.child_todo_list);
        resourceHub = (CardView)findViewById(R.id.child_counsellor_chat);
        childAccount = (CardView)findViewById(R.id.child_personal_info);
        generatePairCode = (CardView)findViewById(R.id.child_link_code);
        crdChatStatusPsych = findViewById(R.id.crdChatStatusPsych);
        crdChildCounsellorChatBubble = findViewById(R.id.crdChildCounsellorChatBubble);
        txtChildPoints = findViewById(R.id.txtPoints);
        childVideoMeeting = findViewById(R.id.child_virtual_meeting);

        psychChildChat = findViewById(R.id.child_psych_chat);
        childLogout = findViewById(R.id.child_logout);
        childChat = findViewById(R.id.childCounsellorChat);
        counsellorChat = findViewById(R.id.childCounsellorChat);


        TextView txtChildName = findViewById(R.id.txtName);
        gridLayout = findViewById(R.id.child_home_grid);
        picture = findViewById(R.id.childHomePfp);
        dLog = new Dialog(this);

        txtChildName.setText(GlobalVariables.loggedInUser.getName().toString());

        //setting the default visibility of the chat bubble
        crdChatStatusPsych.setVisibility(View.INVISIBLE);
        crdChildCounsellorChatBubble.setVisibility(View.INVISIBLE);

        String receivedPassword;
        Intent intent = getIntent();
        receivedPassword  = intent.getStringExtra("childPass");

        //Setting the profile picture of the user *************************************
        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        picture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), imageResource,getApplicationContext().getTheme()));
        //Setting the profile picture of the user end *************************************

        //Setting the child points*********************************************************
        setChildPoints();
        //Setting the child points end*********************************************************

        gridLayout.removeAllViews();
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(4);

        //setting the home page for the child *****************************************************
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        //check for child bookings and tests
        getChildBookingsAndTests();
        //setting the home page for the child end ****************************************************


        //*********************************************TourGuide**************************************
//        if(countBookings==0 && countTests==0){
//            new TapTargetSequence(this)
//                    .targets(
//                            TapTarget.forView(childMoodTracker, "Mood Tracker", "This is a Mood Tracker")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.8f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60),
//
//                            TapTarget.forView(generatePairCode, "Mood Tracker", "This is a Mood Tracker")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.8f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60),
//
//                            TapTarget.forView(counsellorChat, "Chat", "Chat with counsellor")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.96f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60),
//
//                            TapTarget.forView(resourceHub, "Resource Hub", "View in depth description about mental illness")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.96f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60),
//
//                            TapTarget.forView(childAccount, "Account", "Vies your personal details here")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.96f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60),
//                            TapTarget.forView(childLogout, "Logout", "Click here to logout of the app")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.96f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60)).listener(new TapTargetSequence.Listener() {
//                @Override
//                public void onSequenceFinish() {
//                    Toast.makeText(Child_Home.this, "Tour Finished", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//                    Toast.makeText(Child_Home.this, "Great", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onSequenceCanceled(TapTarget lastTarget) {
//
//                }
//            }).start();
//        }else if(countBookings == 0 && countTests > 0){
//            new TapTargetSequence(this)
//                    .target(
//
//                            TapTarget.forView(childTodoList, "Tests", "Here you complete all the tests")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.96f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60)).listener(new TapTargetSequence.Listener() {
//                @Override
//                public void onSequenceFinish() {
//
//                }
//
//                @Override
//                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//
//                }
//
//                @Override
//                public void onSequenceCanceled(TapTarget lastTarget) {
//
//                }
//            }).start();
//        }else if (countBookings > 0 && countTests==0){
//            new TapTargetSequence(this)
//                    .target(
//
//                            TapTarget.forView(viewChildMeetings, "Child Meeting", "View upcoming bookings with psychologist")
//                                    .outerCircleColor(R.color.teal_200)
//                                    .outerCircleAlpha(0.96f)
//                                    .targetCircleColor(R.color.white)
//                                    .titleTextSize(10)
//                                    .titleTextColor(R.color.white)
//                                    .descriptionTextSize(10)
//                                    .descriptionTextColor(R.color.black)
//                                    .textColor(R.color.black)
//                                    .textTypeface(Typeface.SANS_SERIF)
//                                    .dimColor(R.color.black)
//                                    .drawShadow(true)
//                                    .cancelable(false)
//                                    .tintTarget(true)
//                                    .transparentTarget(true)
//                                    .targetRadius(60)).listener(new TapTargetSequence.Listener() {
//                @Override
//                public void onSequenceFinish() {
//
//                }
//
//                @Override
//                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//
//                }
//
//                @Override
//                public void onSequenceCanceled(TapTarget lastTarget) {
//
//                }
//            }).start();
//        }



        //takes child to the appointments page
        viewChildMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);
                apiInterface.GetUserBookingsForApp(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModel>>() {
                    @Override
                    public void onResponse(Call<List<AppointmentModel>> call, Response<List<AppointmentModel>> response) {
                        if(response.code()==200)
                        {
                            int bookingCount = 0;
                            for(AppointmentModel appModel: response.body())
                            {
                                bookingCount++;
                            }
                            if(bookingCount==0)
                            {
                                Intent i = new Intent(Child_Home.this, NoAppointmentPage.class);
                                startActivity(i);
                            }else
                            {
                                Intent intent = new Intent(Child_Home.this, TabbedAppointmentMain.class); //navigation page
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AppointmentModel>> call, Throwable t) {

                    }
                });
            }
        });

        childMoodTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //related to mood tracker activities
                Intent i = new Intent(Child_Home.this, MoodTracker.class);
                startActivity(i);
            }
        });
        childTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //related to child's to do list
                Intent i = new Intent(Child_Home.this, ChildTestMain.class);
                startActivity(i);
            }
        });
        resourceHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //related to councellor chat functionality
                Intent intent = new Intent(Child_Home.this, ResourceHubMain.class);
                intent.putExtra("from", "childHome");
                startActivity(intent);
            }
        });

        childAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //related to child account activities
                Intent intent = new Intent(Child_Home.this, UpdateChildAccount.class);
                intent.putExtra( "childPassHome", receivedPassword);
                startActivity(intent);
            }
        });

        psychChildChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Child_Home.this, ChatPsychList.class);
                startActivity(i);
            }
        });

        //card to logout
        childLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take the child to login page
                Intent i = new Intent(Child_Home.this, NewStartUp.class);
                startActivity(i);
            }
        });

        generatePairCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent(Child_Home.this, BarcodePairing.class);
               // startActivity(intent);

                BarcodeAlertDialog();


                //generating and fetching the paircode for the child
//                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
//
//                apiInterface = retrofit.create(APIInterface.class);
//                Map<String, Integer> hashMap = new HashMap<>();
//                hashMap.put("UserID" , GlobalVariables.loggedInUser.getUserID());
//
//                apiInterface.generatePairCode("application/json; charset=utf-8", hashMap).enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        if(response.code()== 200)
//                        {
//                            alertDialog(response.body());
//                        }else
//                        {
//                            Toast.makeText(Child_Home.this,response.body().toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });

            }
        });

        counsellorChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //related to mood tracker activities
                Intent i = new Intent(Child_Home.this, CounsellorChatBox.class);
                startActivity(i);
            }
        });

        //video meeting card to redirect to video main
        //comment
        childVideoMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Child_Home.this, VideoMain.class);
                    startActivity(intent);

            }
        });
    }

//    public void guidance(){
//        dLog1.setContentView(R.layout.in_app_guidance);
//        dLog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//       // ImageView imgViewCharachter = dLog1.findViewById(R.id.ImageCharachter);
//
//        dLog1.show();
//    }

    //back button should take to child login class
    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        Intent intent = new Intent(Child_Home.this, NewStartUp.class);
        startActivity(intent);
       // guidance();
    }

/*    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 1)
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

            apiInterface = retrofit.create(APIInterface.class);
            Map<String, Integer> hashMap = new HashMap<>();
            hashMap.put("UserID" , GlobalVariables.loggedInUser.getUserID());

            apiInterface.generatePairCode("application/json; charset=utf-8", hashMap).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code()== 200)
                    {
                        alertDialog(response.body());
                    }else
                    {
                        Toast.makeText(Child_Home.this,response.body().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    public void takeToNavigation(View view)
    {
        Intent intent = new Intent(this, NavigationPage.class);
        startActivity(intent);
    }

    private void BarcodeAlertDialog(){
        dLog.setContentView(R.layout.barcode_pairing_popup);
        dLog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dLog.setTitle("Pairing Code");

        //ImageView imgViewClose = dLog.findViewById(R.id.imageviewClose);
        ImageView imgViewQRCode = dLog.findViewById(R.id.imageViewBarcode);
        MultiFormatWriter mfw = new MultiFormatWriter();
        TextView qrCodeText = dLog.findViewById(R.id.textViewQRCode);
        Button btnClose = dLog.findViewById(R.id.buttonCloseQR);

        //generating and fetching the paircode for the child
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("UserID" , GlobalVariables.loggedInUser.getUserID());//GlobalVariables.loggedInUser.getUserID());

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // Intent inten  = new Intent(this, Child_Home.class);
               // AlertDialog.Builder build = new AlertDialog.Builder(Child_Home.this);
//                //finish();
//                //dLog.closeOptionsMenu();
//
//
             //   dLog = build.create();
                dLog.cancel();

            }
        });

        apiInterface.generatePairCode("application/json; charset=utf-8", hashMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()== 200)
                {
                    if(response.body().length() == 10){
                        qrCodeText.setText(response.body());

                    }else{

//                        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(btnClose.getLayoutParams());
//                        params.setMargins(0,10,0,10);
//                        imgViewQRCode.setLayoutParams(params);
                        qrCodeText.setText("");
                    }

                    try{
                        BitMatrix bMatrix = mfw.encode(response.body(), BarcodeFormat.QR_CODE,200,200); //set barcode height and width
                        //txtdata.setText("Data: " + response.body());
                        //creating encoder for barcode
                        //Toast.makeText(Child_Home.this, response.body(), Toast.LENGTH_LONG).show();
                        BarcodeEncoder bEncoder = new BarcodeEncoder();

                        //create bitmap image of barcode using the barcode encoder
                        Bitmap bMap = bEncoder.createBitmap(bMatrix);

                        //displaying to the screen

                        imgViewQRCode.setImageBitmap(bMap);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }else
                {
                    qrCodeText.setText("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        dLog.show();
    }

    private void alertDialog(String message) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);

        dialog.setTitle("Pairing Code");
        dialog.setMessage(message);
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }


    //setting the home layout

    private void setHomeLayout(int countBookings, int countTests, boolean virtualSession, int pairId)
    {
        //no psychologist linked, no bookings and no pair
        if(countBookings==0 && countTests==0) //dont display tests or bookings
        {
            gridLayout.addView(childMoodTracker);
            if(pairId ==0)
            {
                gridLayout.addView(generatePairCode);
            }
            gridLayout.addView(counsellorChat);
            gridLayout.addView(resourceHub);
            gridLayout.addView(childAccount);
            gridLayout.addView(childLogout);



        }else if(countBookings == 0 && countTests > 0) //display tests only
        {
            gridLayout.addView(childMoodTracker);
            if(pairId ==0)
            {
                gridLayout.addView(generatePairCode);
            }
            gridLayout.addView(childTodoList);
            gridLayout.addView(counsellorChat);
            gridLayout.addView(resourceHub);
            gridLayout.addView(childAccount);
            gridLayout.addView(childLogout);


        }else if (countBookings > 0 && countTests==0)
        {
            gridLayout.addView(childMoodTracker);
            gridLayout.addView(viewChildMeetings);
            if(virtualSession == true)
            {
                gridLayout.addView(childVideoMeeting);
            }
            if(pairId ==0)
            {
                gridLayout.addView(generatePairCode);
            }
            gridLayout.addView(psychChildChat);
            //gridLayout.addView(counsellorChat);
            gridLayout.addView(resourceHub);
            gridLayout.addView(childAccount);
            gridLayout.addView(childLogout);


        }
        else if(countBookings > 0 && countTests >0) // display everything
        {
            gridLayout.addView(childMoodTracker);
            gridLayout.addView(viewChildMeetings);
            gridLayout.addView(childTodoList);
            if(virtualSession == true)
            {
                gridLayout.addView(childVideoMeeting);
            }
            gridLayout.addView(psychChildChat);
            if(pairId ==0)
            {
                gridLayout.addView(generatePairCode);
            }
            gridLayout.addView(resourceHub);
            gridLayout.addView(childAccount);
            gridLayout.addView(childLogout);

            //video meeting



        }else   //if something goes wrong
        {
            gridLayout.addView(childMoodTracker);
            gridLayout.addView(viewChildMeetings);
            gridLayout.addView(childTodoList);
            if(pairId ==0)
            {
                gridLayout.addView(generatePairCode);
            }
            gridLayout.addView(resourceHub);
            gridLayout.addView(counsellorChat);
            gridLayout.addView(childAccount);
            gridLayout.addView(childLogout);
            if(virtualSession == true)
            {
                gridLayout.addView(childVideoMeeting);
            }


        }

    }
    private void getChildBookingsAndTests()
    {
        apiInterface.getuserBookings(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {


                if(response.code()==200)
                {
                    for(BookingModel mod: response.body())
                    {
                        countBookings++;
                    }
                }

                //get the tests assigned to the child(if no tests then dont display the child todo tile
                apiInterface.getAllChildTestsAssigned(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AssignedTest>>() {
                    @Override
                    public void onResponse(Call<List<AssignedTest>> call, Response<List<AssignedTest>> response) {
                        if(response.code()==200)
                        {
                            for(AssignedTest ast : response.body())
                            {
                                countTests++;
                            }
                        }
                        //check for child upcoming virtual session
                        apiInterface.getVirtualSession(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<BookingModelVideo>() {
                            @Override
                            public void onResponse(Call<BookingModelVideo> call, Response<BookingModelVideo> response) {
                                if(response.code()==200)
                                {
                                    if(response.body() != null)
                                    {
                                        if(response.body().getBookingID() != 0)
                                        {
                                            virtualSession= true;
                                        }

                                    }

                                }
                                apiInterface.getPairId(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if(response.code()==200)
                                        {
                                            pairId = response.body();
                                        }

                                        setHomeLayout(countBookings, countTests, virtualSession, pairId);
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {

                                    }
                                });


                            }

                            @Override
                            public void onFailure(Call< BookingModelVideo > call, Throwable t) {

                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<AssignedTest>> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {

            }
        });

    }

    //function to set the child points
    public void setChildPoints()
    {
        //GlobalVariables.loggedInUser.getUserID()
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getChildPoints(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200)
                {
                    txtChildPoints.setText(String.valueOf(response.body()));
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

}