package VideoSubsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.BookingModel;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.MainActivity;
import com.example.mhaprototype.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.StringTokenizer;

import PsychologistList.PsychologistInfoModel;
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


public class VideoMain extends AppCompatActivity {

    String meetingID;
    String meetingPassword;
    ImageView imgPsychologist;
    TextView txtPsychologistName;
    ImageView imgBackButton;
    /* private ZoomSDKAuthenticationListener authListener = new ZoomSDKAuthenticationListener() {
        *//**
         * This callback is invoked when a result from the SDK's request to the auth server is
         * received.
         *//*
        @Override
        public void onZoomSDKLoginResult(long result) {
            if (result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
                // Once we verify that the request was successful, we may start the meeting
                startMeeting(VideoMain.this);
            }
        }

        @Override
        public void onZoomSDKLogoutResult(long l) {
        }

        @Override
        public void onZoomIdentityExpired() {
        }

        @Override
        public void onZoomAuthIdentityExpired() {
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main);
        imgBackButton = findViewById(R.id.imgbtnPrevadd);

        if(ContextCompat.checkSelfPermission(VideoMain.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(VideoMain.this, new String[]{Manifest.permission.CAMERA}, 101);
        }

        ImageView imgChildPic = findViewById(R.id.imgChildPic);
        imgPsychologist = findViewById(R.id.imgPsychImage);
        ImageView btnBack = (ImageView)findViewById(R.id.imgbtnPrevadd);
        TextView txtBookingDetail = (TextView)findViewById(R.id.txtBookingDetails);
        TextView txtBookingDate = (TextView)findViewById(R.id.txtBookingDate);
        TextView txtBookingTime = (TextView)findViewById(R.id.confirmBookingTime);
        txtPsychologistName = findViewById(R.id.txtPsychologistName2);
        TextView txtConfirmBookingType = findViewById(R.id.confirmBookingType);


        //setting the name and the profile picture for the child*************************************************
        txtBookingDetail.setText(GlobalVariables.loggedInUser.getName().toString());
        //set the image also
        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        imgChildPic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
        //setting the name and the profile picture for the child*************************************************



        //api call for video session
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        apiInterface.getVirtualSession(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<BookingModelVideo>() {
            @Override
            public void onResponse(Call<BookingModelVideo> call, Response<BookingModelVideo> response) {
                if(response.code()==200)
                {
                    //get the booking time date and type
                    if(response.body().getType().equals("Repeat"))
                    {
                        txtConfirmBookingType.setText("Recurring session");
                    }else
                    {
                        txtConfirmBookingType.setText("Single session");
                    }

                    //setting the psychologist image and name
                    int calendarID = (int)response.body().getCalendarID();
                    setPsychImage(calendarID);

                    //displaying the date of the meeting
                    String date = response.body().getDate().toString().substring(0,10);
                    date = getCurrentDate(date);
                    txtBookingDate.setText(date);

                    //setting the time for the meeting
                    int time = (int)response.body().getTime().getHours();
                    int minutes=(int) response.body().getTime().getMinutes();
                    String strTime = String.valueOf(time);
                    String strMinutes = String.valueOf(minutes);

                    if(minutes <10)
                    {
                        strMinutes = "0"+ strMinutes;
                    }
                    if(time < 10)
                    {
                        strTime = "0"+strTime;
                    }


                    String startTime = "Starting time: "+strTime+":"+strMinutes;
                    txtBookingTime.setText(startTime);

                    if(response.body().getMeetingID()!=null && response.body().getMeetingPassword()!=null)
                    {
                        meetingID = response.body().getMeetingID();
                        meetingPassword = response.body().getMeetingPassword();

                        meetingID = meetingID.replaceAll("\\s","");

                    }

                }
            }

            @Override
            public void onFailure(Call<BookingModelVideo> call, Throwable t) {

            }
        });





        //initializing the video session
        initializeSdk(VideoMain.this);
        findViewById(R.id.join_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createJoinMeetingDialog(meetingID,meetingPassword);
            }
        });

        //back button
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    //functions for the video meeting
    public void initializeSdk(Context context) {
        ZoomSDK sdk = ZoomSDK.getInstance();
        // TODO: For the purpose of this demo app, we are storing the credentials in the client app itself. However, you should not use hard-coded values for your key/secret in your app in production.
        ZoomSDKInitParams params = new ZoomSDKInitParams();
        params.appKey = "QJ7zijp8yFbxF9GnnSLOBXJ5ps6l6jisuvhy"; // TODO: Retrieve your SDK key and enter it here
        params.appSecret = "HJuHwhAZUlYSpnieJClo2l9Bn3wFLWK2YgUC"; // TODO: Retrieve your SDK secret and enter it here
        params.domain = "zoom.us";
        params.enableLog = true;
        // TODO: Add functionality to this listener (e.g. logs for debugging)
        ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
            /**
             * @param errorCode {@link us.zoom.sdk.ZoomError#ZOOM_ERROR_SUCCESS} if the SDK has been initialized successfully.
             */
            @Override
            public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
            }

            @Override
            public void onZoomAuthIdentityExpired() {
            }
        };
        sdk.initialize(context, listener, params);
        //joinMeeting(VideoMain.this,"86724917255","w1R6Tj" );
    }

    //1. Write the joinMeeting function.
    private void joinMeeting(Context context, String meetingNumber, String password) {
        MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
        JoinMeetingOptions options = new JoinMeetingOptions();
        JoinMeetingParams params = new JoinMeetingParams();
        params.displayName = GlobalVariables.loggedInUser.getName().toString(); // TODO: Enter your name
        params.meetingNo = meetingNumber;
        params.password = password;
        meetingService.joinMeetingWithParams(context, params, options);
    }

    // 1. Write the login function
/*
    private void login(String username, String password) {
        int result = ZoomSDK.getInstance().loginWithZoom(username, password);
        if (result == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {

            // 2. After request is executed, listen for the authentication result prior to starting a meeting
            ZoomSDK.getInstance().addAuthenticationListener(authListener);
        }
    }

    // 3. Write the startMeeting function
    private void startMeeting(Context context) {
        ZoomSDK sdk = ZoomSDK.getInstance();
        if (sdk.isLoggedIn()) {
            MeetingService meetingService = sdk.getMeetingService();
            StartMeetingOptions options = new StartMeetingOptions();
            meetingService.startInstantMeeting(context, options);
        }
    }*/

    // 1. Create a dialog where a participant can enter the meeting information to join a meeting.
    private void createJoinMeetingDialog(String meetingID,String meetingPassword) {
        /*new AlertDialog.Builder(this).setView(R.layout.zoom_dialog).setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog dialog = (AlertDialog) dialogInterface;
                TextInputEditText numberInput = dialog.findViewById(R.id.meeting_no_input);
                TextInputEditText passwordInput = dialog.findViewById(R.id.password_input);
                if (numberInput != null && numberInput.getText() != null && passwordInput != null && passwordInput.getText() != null) {
                    String meetingNumber = numberInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    if (meetingNumber.trim().length() > 0 && password.trim().length() > 0) {
                        joinMeeting(VideoMain.this, meetingNumber, password);
                    }
                }
            }
        }).show();*/
        joinMeeting(VideoMain.this, meetingID, meetingPassword);
    }


    // 2. Create a dialog where a host can enter Zoom email and password to login and start an instant meeting.
   /* private void createLoginDialog() {
        new AlertDialog.Builder(this).setView(R.layout.zoom_login).setPositiveButton("Log in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog dialog = (AlertDialog) dialogInterface;
                TextInputEditText emailInput = dialog.findViewById(R.id.email_input);
                TextInputEditText passwordInput = dialog.findViewById(R.id.pw_input);
                if (emailInput != null && emailInput.getText() != null && passwordInput != null && passwordInput.getText() != null) {
                    String email = emailInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    if (email.trim().length() > 0 && password.trim().length() > 0) {
                        login(email, password);
                    }
                }
            }
        }).show();
    }*/

    //1. Write initViews method to handle onClick events for the Join Meeting and Login & Start Meeting buttons.
   /* private void initViews() {
        findViewById(R.id.join_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createJoinMeetingDialog();
            }
        });*/

       /* findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 2.  If a user is logged in, start the instant meeting, else prompt the user to login.
                if (ZoomSDK.getInstance().isLoggedIn()) {
                    startMeeting(VideoMain.this);
                } else {
                    createLoginDialog();
                }
            }
        });*/
    //}


    //function to get the current date
    public String getCurrentDate(String strDate) {
        StringTokenizer token = new StringTokenizer(strDate, "-");
        String MonthName = "";
        String Day = token.nextToken();
        String Month = token.nextToken();
        String Year = token.nextToken();

        switch (Month) {
            case "01":
                MonthName = "January";
                break;
            case "02":
                MonthName = "February";
                break;
            case "03":
                MonthName = "March";
                break;
            case "4":
                MonthName = "April";
                break;
            case "05":
                MonthName = "May";
                break;
            case "06":
                MonthName = "June";
                break;
            case "07":
                MonthName = "July";
                break;
            case "08":
                MonthName = "August";
                break;
            case "09":
                MonthName = "September";
                break;
            case "10":
                MonthName = "October";
                break;
            case "11":
                MonthName = "November";
                break;
            case "12":
                MonthName = "December";
                break;

        }

        return Year + " " + MonthName+" "+Day;//Day + " " + MonthName +" " + Year;
    }

    //function to set the psychologist image
    public void setPsychImage(int calendarID)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        apiInterface.getPsychByCalendarID(calendarID).enqueue(new Callback<PsychologistInfoModel>() {
            @Override
            public void onResponse(Call<PsychologistInfoModel> call, Response<PsychologistInfoModel> response) {
                if(response.code()==200)
                {
                    String imageName = response.body().getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                    String imageUrl = "@drawable/" + imageName;
                    int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                    imgPsychologist.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
                    txtPsychologistName.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<PsychologistInfoModel> call, Throwable t) {

            }
        });
    }

}