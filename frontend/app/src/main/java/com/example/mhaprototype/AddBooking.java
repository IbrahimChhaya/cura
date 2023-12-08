package com.example.mhaprototype;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import Appointment.AppointmentMain;
import Appointment.TabbedAppointmentMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBooking extends AppCompatActivity{
    APIInterface apiInterface;
    EditText bookingddate;
    DatePickerDialog datePicker;
    Button btnAddBooking;
    Spinner spinnerTime;
    String getBookingDate;
    Map<String, TimeModel> timeMap;
    TimeModel model;
    String time;
    String getMyBookingDate;
    String stTime;
    String childName = "";
    CheckBox checkBox;
    boolean consentStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        String finalDate = GlobalVariables.bookingDate + GlobalVariables.startTime;
        String myDate = GlobalVariables.bookingDate;
        stTime = GlobalVariables.startTime;
        checkBox = findViewById(R.id.consentBox);

        Intent intent = getIntent();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        TextView txtBookingDetail = (TextView)findViewById(R.id.txtBookingDetails);
        TextView txtBookingDate = (TextView)findViewById(R.id.txtBookingDate);
        TextView txtBookingTime = (TextView)findViewById(R.id.confirmBookingTime);
        TextView txtPsychologistName = findViewById(R.id.txtPsychologistName2);
        TextView txtConfirmBookingType = findViewById(R.id.confirmBookingType);
        btnAddBooking = (Button) findViewById(R.id.btnAddBooking);

        ImageView imgChildPic = findViewById(R.id.imgChildPic);
        ImageView imgPsychologist = findViewById(R.id.imgPsychImage);

        ImageView btnBack = (ImageView)findViewById(R.id.imgbtnPrevadd);

        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.confToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.conftextview);
        heading.setText("Appointment Confirmation");


        //check to see if the consent status is false
        checkBox.setVisibility(View.INVISIBLE);
        alreadyRecurring();

        //set default of checkbox to be invisible

        //if it is the first recurring session then display the checkbox


        apiInterface.getAllUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.code()==200)
                {
                    for(UserModel u : response.body())
                    {
                        int userID = (int) u.getUserID();
                        if(userID==GlobalVariables.GuardianChildID)
                        {
                            childName = u.getName();
                            txtBookingDetail.setText(childName);
                            //set the image also
                            String imageName = u.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                            String imageUrl = "@drawable/" + imageName;
                            int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                            imgChildPic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));

                        }else if(userID ==GlobalVariables.psychologistID)
                        {
                            childName = u.getName();
                            txtPsychologistName.setText(childName);
                            String imageName = u.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                            String imageUrl = "@drawable/" + imageName;
                            int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                            imgPsychologist.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });

        Date date = null;
        String dayOfWeek = "";
        dayOfWeek = getSelectedDay(date,GlobalVariables.bookingDate);


        //Getting the booking type from the calendar *************************
        String bookingType = intent.getStringExtra("bookingType");
        String endDate = "";
        //getting the booking date*********************************************
        String dt = GlobalVariables.bookingDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 28);  // number of days to add
        endDate = sdf.format(c.getTime());

        String startDate = getCurrentDate(GlobalVariables.bookingDate);
        String fDate = getCurrentDate(endDate);



        //getting the booking date********************************************* end


        if(bookingType.equals("Recurring Booking Session"))
        {
            txtBookingDate.setText(dayOfWeek+"s"+"\n"+
                    startDate+"-"+fDate);
            txtConfirmBookingType.setText("Recurring booking session");
        }else
        {
            txtBookingDate.setText(dayOfWeek+" " + startDate);
            txtConfirmBookingType.setText("Single booking session");
        }

        txtBookingTime.setText("Starting time: " +GlobalVariables.startTime.toString().substring(1,6));




        btnAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> hashMap = new HashMap<>();
                //getBookingDate = bookingddate.getText().toString();

               // bookingddate.setText(finalDate);
                hashMap.put("PsychID", GlobalVariables.psychologistID);
                hashMap.put("date", finalDate);//getBookingDate + time);
                hashMap.put("calendarID", GlobalVariables.CalendarID);// 2);
                hashMap.put("userId", GlobalVariables.GuardianChildID);


                apiInterface.addBooking("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.code() == 200) {
                            JSONObject gsonObject = null;
                            try {
                                gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                RegisterModel regModel = new RegisterModel(gsonObject);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //add the link with the psychologist here
                            //psychiD = Globalvariables.psychID
                            //childID = GlobalVariables.GuardianChildID
                            //guardianID = GlobalVariables.loggedInUser.getUseriD;
                            //AddPatient code Start***********************************************************
                            HashMap<String,Object> linkMap = new HashMap<>();
                            linkMap.put("psychID", GlobalVariables.psychologistID);
                            linkMap.put("childID",GlobalVariables.GuardianChildID);
                            linkMap.put("guardianID", GlobalVariables.loggedInUser.getUserID());
                            apiInterface.addPatient("application/json; charset=utf-8",linkMap).enqueue(new Callback<Link>() {
                                @Override
                                public void onResponse(Call<Link> call, Response<Link> response) {
                                    if(response.code()==200)
                                    {
                                        //boooking was successful
                                    }

                                }

                                @Override
                                public void onFailure(Call<Link> call, Throwable t) {

                                }
                            });
                            //AddPatient code End***********************************************************


                            Toast.makeText(AddBooking.this, "Booking Successfully added", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddBooking.this, TabbedAppointmentMain.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(AddBooking.this, "Booking Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(AddBooking.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //utility function to get the selected day
    private String getSelectedDay(Date date, String sDate)
    {
        date = null;
        String dayOfWeek = "";
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(sDate);
            dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOfWeek;
    }

    //methid to get month
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

    //function to check if its the first booking session or recurring****************************************
    public void alreadyRecurring()
    {

        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("ChildID",GlobalVariables.GuardianChildID);
        hashmap.put("PsychID", GlobalVariables.psychologistID);
        Intent i = getIntent();

        apiInterface.alreadyHasRecurringBookings("application/json; charset=utf-8",hashmap).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==200)
                {
                    consentStatus = response.body();
                    if(consentStatus == false && i.getStringExtra("bookingType").equals("Recurring Booking Session") )
                    {
                        checkBox.setVisibility(View.VISIBLE);
                        btnAddBooking.setVisibility(View.INVISIBLE);
                    }

                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked)
                            {
                                btnAddBooking.setVisibility(View.VISIBLE);
                            }else
                            {
                                btnAddBooking.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }


}