package com.example.mhaprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Account.UpdateGuardianAccount;
import Appointment.AppointmentModel;
import Appointment.ErrorPages.NoAppointmentPage;
import Appointment.TabbedAppointmentMain;
import Barcode.Barcode_Modified_Scanner;
import Chat.ChatPsychList;
import Chat.Counsellor.CounsellorChatBox;
import ChildList.ChildListMain;
import ChildList.ChildListRedesigned;
import ChildList.ErrorPages.NoChildLinkedMain;
import PsychologistList.SearchablePsychologistList;
import PsychologistList.nearbyPsychologistList;
import ResourceHub.ResourceHubMain;
import StartUpStuff.GuardianChildRegister.GuardianRegistersChild1;
import StartUpStuff.NewStartUp;
import psychologistHome.psychologist_home;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Guardian_Home extends AppCompatActivity{ //implements AdapterView.OnItemSelectedListener{
    APIInterface apiInterface;

    CardView cardMeeting;
    CardView cardFindPsychologist;
    CardView cardResourceHub;
    CardView cardReports;
    CardView cardAccount;
    CardView cardLinkChild;
    CardView cardMyChildren;
    CardView cardRegisterChild;

    CardView guardianLogout;
    CardView guardianChat;


    TextView txtGuardianName;
    String pairStatus;

    GridLayout gridLayout;

    Dialog dLog;

    //variables for dynamic home
    int numChildren = 0;
    int countBookings=0;

    private static final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;


    //cardview for the chat bubble
    CardView crdChatCounsellorGuardianBubble;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardian_home_redesign); //activity_guardian__home

        gridLayout = (GridLayout)findViewById(R.id.guardian_home_grid);

        ImageView imgGuardianProfilePic = (ImageView)findViewById(R.id.guardianProfilePic);
        txtGuardianName = (TextView)findViewById(R.id.txtName);
        cardMeeting = (CardView) findViewById(R.id.guardian_view_meetings);
        cardFindPsychologist = (CardView) findViewById(R.id.guardian_find_psychologist);
        cardReports = (CardView) findViewById(R.id.guardian_view_reports);
        cardResourceHub = (CardView) findViewById(R.id.guardian_resource_hub);
        cardAccount = (CardView) findViewById(R.id.guardian_personal_info);
        cardLinkChild = (CardView) findViewById(R.id.guardian_link_child);
        cardMyChildren = (CardView)findViewById(R.id.guardian_children);
        cardRegisterChild = (CardView) findViewById(R.id.guardian_registerChild);
        guardianChat = findViewById(R.id.guardian_chat_counsellor);
        guardianLogout = findViewById(R.id.guardian_logout);
        crdChatCounsellorGuardianBubble = findViewById(R.id.crdChatCounsellorGuardianBubble);


        //setting the default visibility of the chat bubble
        crdChatCounsellorGuardianBubble.setVisibility(View.INVISIBLE);


        Intent i = getIntent();
        pairStatus = i.getStringExtra("PairStatus");

        if(pairStatus != null)
        {
            if(pairStatus.equals("No pair"))
            {
                addPairAlertDialog();
            }
        }



        gridLayout.removeAllViews();
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(6);


       /* gridLayout.addView(cardMeeting);
        gridLayout.addView(cardLinkChild);
        gridLayout.addView(cardReports);
        gridLayout.addView(cardAccount);
        gridLayout.addView(cardFindPsychologist);
        gridLayout.addView(cardResourceHub);*/

        String GuardianName = GlobalVariables.loggedInUser.getName().trim();
        //GuardianName

        txtGuardianName.setText(GuardianName);

        //setting the profile pic for the guardian********************************
        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        imgGuardianProfilePic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
        //setting the profile pic for the guardian end*****************************


        //Api calls to set the home page accordingly************************************
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        //checking if the user is linked to a psychologist or not


        apiInterface.isLinkedToParent(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                 int ID =0;
                if(response.code()==200)
                {
                    //get the psychologist ID that is linked to the parent
                     ID = response.body();

                     //getting the user bookings
                    int finalID = ID;
                    apiInterface.GetUserBookingsForApp(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModel>>() {
                         @Override
                         public void onResponse(Call<List<AppointmentModel>> call, Response<List<AppointmentModel>> response) {

                             if(response.code()==200)
                             {

                                 for(AppointmentModel appModel : response.body())
                                 {
                                     countBookings++;
                                 }
                             }

                             //get the linked Children for the parent
                             apiInterface.getLinkedChildren(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<Integer>>() {
                                 @Override
                                 public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                                     if(response.code()==200)
                                     {
                                         if(response.body() != null)
                                         {
                                             for(Integer i : response.body())
                                             {
                                                 numChildren++;
                                             }
                                             setHomeLayout(finalID,countBookings, numChildren);
                                         }

                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<List<Integer>> call, Throwable t) {

                                 }
                             });

                         }

                         @Override
                         public void onFailure(Call<List<AppointmentModel>> call, Throwable t) {

                         }
                     });


                }
                //function to set the final layout for the home page
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

        //Api calls to set the home page accordingly************************************



        //card that takes the guardian to appointments page
        cardMeeting.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(Guardian_Home.this, NoAppointmentPage.class);
                                startActivity(i);
                            }else
                            {
                                Intent intent = new Intent(Guardian_Home.this, TabbedAppointmentMain.class); //navigation page
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

        //card that takes the guardian to the nearby psychologist page
        cardFindPsychologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guardian_Home.this, SearchablePsychologistList.class);
                startActivity(intent);
            }
        });
        //guardian chat with the cousellor
        guardianChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Guardian_Home.this, CounsellorChatBox.class);
                startActivity(i);
            }
        });

        cardReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take to Guardian reports page
                Intent i = new Intent(Guardian_Home.this, ChildListRedesigned.class);
                //message to see which tile or page is calling the childlistredesign
                i.putExtra("ChildListMessage", "ReportsCard");
                startActivity(i);
            }
        });

        cardLinkChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    //            addPairAlertDialog();
       //         BarcodeAlertDialog();
                Intent intent = new Intent(Guardian_Home.this, Barcode_Modified_Scanner.class); //Barcode_Scanner
                startActivity(intent);
            }
        });



        cardAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guardian_Home.this, UpdateGuardianAccount.class);
                intent.putExtra("from", "guardianHome");
                startActivity(intent);
            }
        });

        cardResourceHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guardian_Home.this, ResourceHubMain.class);
                intent.putExtra("from", "guardianHome");
                startActivity(intent);
            }
        });

        cardRegisterChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Guardian_Home.this, GuardianRegistersChild1.class);
               // Intent intent = new Intent(getApplicationContext(), ChildRegisterRedesign3.class);
//                String message = "PairWithMe";
//                i.putExtra( "guardianText", message);
                //startActivity(intent);
                startActivity(i);
            }
        });

        guardianLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Guardian_Home.this, NewStartUp.class);
                startActivity(i);
            }
        });


        cardMyChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);
                apiInterface.getLinkedChildren(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        if(response.code()==200)
                        {
                            int count = 0;
                            for(int i : response.body())
                            {
                                count++;
                            }
                            if(count > 0)
                            {
                                Intent i = new Intent(Guardian_Home.this, ChildListRedesigned.class); //ChildListMain
                                i.putExtra("ChildListMessage", "MyChildrenCard");
                                startActivity(i);
                            }else if(count == 0)
                            {

                                Intent i = new Intent(Guardian_Home.this, NoChildLinkedMain.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {

                    }
                });

            }
        });



        /*Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);*/
    }

    //back button should take to guardian login class
    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        Intent intent = new Intent(Guardian_Home.this, NewStartUp.class);
        startActivity(intent);
    }

    private void addPairAlertDialog() {
        AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.remove_account_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextInputEditText txtCodeValue = dialogView.findViewById(R.id.txtEmailValue);
        final TextInputLayout codeLayout = dialogView.findViewById(R.id.txtRemoveEmail);

        codeLayout.setHint("Pairing code");

        dialogBuilder.setTitle("Pair with child");
        dialogBuilder.setMessage("Enter your child's pairing code below");
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean[] closeDialog = {false};

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GlobalVariables.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIInterface apiInterface = retrofit.create(APIInterface.class);

                // userID & Email
                HashMap<String, Object> map = new HashMap<>();


                map.put("userId" ,GlobalVariables.loggedInUser.getUserID());
                map.put("pairCode",txtCodeValue.getText().toString().trim());



                apiInterface.pair ("application/json; charset=utf-8", map).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == 200){
                            if(response.body().equals("Paired")){
                                Toast.makeText(Guardian_Home.this, "Successfully Paired", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();

                            }
                            else if(response.body().equals("The pair code is incorrect")){
                                codeLayout.setError("Failed to pair: Invalid pairing code provided");
                            }
                            else{
                                closeDialog[0] = true;
                                Toast.makeText(Guardian_Home.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            closeDialog[0] = true;
                            Toast.makeText(Guardian_Home.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        closeDialog[0] = true;
                        Toast.makeText(Guardian_Home.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                if(closeDialog[0])
                    alertDialog.dismiss();
            }
        });
    }

    private void PairChildGuardian() {
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GlobalVariables.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIInterface apiInterface = retrofit.create(APIInterface.class);

                // userID & Email
                HashMap<String, Object> map = new HashMap<>();


                map.put("userId" ,GlobalVariables.loggedInUser.getUserID());
               // map.put("pairCode",txtCodeValue.getText().toString().trim());



                apiInterface.pair ("application/json; charset=utf-8", map).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == 200){
                            if(response.body().equals("Paired")){
                                Toast.makeText(Guardian_Home.this, "Successfully Paired", Toast.LENGTH_LONG).show();


                            }
                            else if(response.body().equals("The pair code is incorrect")){

                            }
                            else{
                                Toast.makeText(Guardian_Home.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(Guardian_Home.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Guardian_Home.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }

//    private void BarcodeAlertDialog(){
//        dLog.setContentView(R.layout.activity_barcode_modified_scanner);
//        dLog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        scannerView = dLog.findViewById(R.id.scanner_view);
//        mCodeScanner = new CodeScanner(this, scannerView);
//
//
//        mCodeScanner.setDecodeCallback(new DecodeCallback() {
//            @Override
//            public void onDecoded(@NonNull final Result result) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(Guardian_Home.this, result.getText(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        });
////        if(Build.VERSION.SDK_INT >= 23){
////            if(CheckPermission(Manifest.permission.CAMERA)){
////                scannerView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        mCodeScanner.startPreview();
////                    }
////                });
////            }
////        }
//       // dLog.setTitle("Pairing Code");
//
////        //ImageView imgViewClose = dLog.findViewById(R.id.imageviewClose);
////        ImageView imgViewQRCode = dLog.findViewById(R.id.imageViewBarcode);
////        MultiFormatWriter mfw = new MultiFormatWriter();
////        Button btnClose = dLog.findViewById(R.id.buttonCloseQR);
//
//        //generating and fetching the paircode for the child
////        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
////
////        apiInterface = retrofit.create(APIInterface.class);
////        Map<String, Integer> hashMap = new HashMap<>();
////        hashMap.put("UserID" , GlobalVariables.loggedInUser.getUserID());
//
//
//
//
////
////        Window window = dLog.getWindow();
////        WindowManager.LayoutParams wlp = window.getAttributes();
////
////        wlp.gravity = Gravity.BOTTOM;
////        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
////        window.setAttributes(wlp);
//        dLog.show();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    protected void onPause() {
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }
//
//    // *************************************************
//    private boolean CheckPermission(String permission){
//        int result = ContextCompat.checkSelfPermission(Guardian_Home.this, permission);
//
//        if(result != PackageManager.PERMISSION_GRANTED){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    private void requestPermission(String permission, int code){
//        if(ActivityCompat.shouldShowRequestPermissionRationale(Guardian_Home.this,permission)){
//
//        }else{
//            ActivityCompat.requestPermissions(Guardian_Home.this, new String[]{permission},code); //requesting permission
//        }
//    }





   /* @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void takeToNavigation(View view)
    {
        Intent intent = new Intent(this, NavigationPage.class);
        startActivity(intent);
    }

    public void takeToViewAllPsych(View view)
    {
        Intent intent = new Intent(this, nearbyPsychologistList.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position == 1){
            Intent intent = new Intent(this, UpdateAccountGuardian.class);
            startActivity(intent);
        }else if(position == 2)
        {
            Intent intent = new Intent(this, AddPair.class);
            startActivity(intent);
        }


    }*/


    private void setHomeLayout(int finalID, int countBookings, int numChildren)
    {
        if(finalID ==0 && countBookings==0 && numChildren ==0)
        {
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardResourceHub);
            gridLayout.addView(guardianChat);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(cardAccount);
            gridLayout.addView(guardianLogout);
        }
        //if linked but no bookings
        else if(finalID>0 && countBookings==0 )
        {
            gridLayout.addView(cardMeeting);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardReports);
            gridLayout.addView(cardResourceHub);
            //gridLayout.addView(guardianChat);
            gridLayout.addView(cardMyChildren);
            gridLayout.addView(cardAccount);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(guardianLogout);

        }//if linked and bookings
        else if(finalID>0) //can only be linked if there is a booking
        {
            gridLayout.addView(cardMeeting);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardReports);
            gridLayout.addView(cardResourceHub);
            //gridLayout.addView(guardianChat);
            gridLayout.addView(cardMyChildren);
            gridLayout.addView(cardAccount);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(guardianLogout);
        }//if no child linked
        else if(finalID==-1 && numChildren==0) //means no child is linked, no bookings and no psych linked
        {
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(cardResourceHub);
            gridLayout.addView(guardianChat);
            gridLayout.addView(cardAccount);
            gridLayout.addView(guardianLogout);

        }else if(finalID ==-1 && numChildren >0) //means that no psychologist and bookings made but a pair is made
        {
            gridLayout.addView(cardResourceHub);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(guardianChat);
            gridLayout.addView(cardAccount);
            gridLayout.addView(cardMyChildren);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(guardianLogout);

        }else if(finalID > 0 && numChildren >0)
        {
            gridLayout.addView(cardMeeting);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardReports);
            //gridLayout.addView(guardianChat);
            gridLayout.addView(cardResourceHub);
            gridLayout.addView(cardMyChildren);
            gridLayout.addView(cardAccount);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(guardianLogout);

        }else if(finalID == 0 && numChildren >0 && countBookings == 0)
        {
//            gridLayout.addView(cardMeeting);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardMyChildren);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(cardResourceHub);
            gridLayout.addView(cardReports);
            //gridLayout.addView(guardianChat);
            gridLayout.addView(cardAccount);
            gridLayout.addView(guardianLogout);
        }else
        {
            //if anything goes wrong
            gridLayout.addView(cardMeeting);
            gridLayout.addView(cardLinkChild);
            gridLayout.addView(cardReports);
            gridLayout.addView(cardResourceHub);
            gridLayout.addView(cardAccount);
            gridLayout.addView(cardFindPsychologist);
            gridLayout.addView(cardMyChildren);
            gridLayout.addView(cardRegisterChild);
            gridLayout.addView(guardianChat);
            gridLayout.addView(guardianLogout);
        }
    }
}