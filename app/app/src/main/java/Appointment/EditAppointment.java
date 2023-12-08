package Appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import Appointment.Models.BookingParties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditAppointment extends AppCompatActivity {

    TextView txtPatientName;
    TextView txtBookingDate;
    TextView txtBookingTime;
    Button btnCancelBooking;
    ImageView btnBackButton;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        Intent intent = getIntent();

        txtPatientName = (TextView) findViewById(R.id.txtPatientName);
        txtBookingDate = (TextView) findViewById(R.id.txtBookingDate);
        txtBookingTime = (TextView) findViewById(R.id.cancelBookingTime);
        btnCancelBooking = (Button) findViewById(R.id.btnCancelBooking);
        btnBackButton = (ImageView) findViewById(R.id.backButtonEditBooking);
        ImageView imgPatient = findViewById(R.id.imgPatientPic);
        ImageView imgPsychologist = findViewById(R.id.imgPsychImage);
        TextView txtPsychName = findViewById(R.id.txtPsychologistName);
        TextView txtBookingType = findViewById(R.id.cancelBookingType);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.editToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.textView28);
        heading.setText("Appointment Cancellation");


        Date date = null;
        String sDate = intent.getStringExtra("BookingDate");
        sDate = sDate.replace("T00:00:00","");
        String dayOfWeek = "";
        dayOfWeek = getSelectedDay(date,sDate);
        String bookingDate = getCurrentDate(sDate);

        //comparing past dates to current dates
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);

        //comparing the current date wih the date of the past to disable the cancel booking button
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = sdformat.parse(sDate);
            Date d2 = sdformat.parse(todayString);

            if(d2.compareTo(d1)>0)
            {
                btnCancelBooking.setVisibility(View.INVISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



        txtBookingDate.setText(bookingDate);
        txtBookingTime.setText(intent.getStringExtra("BookingTime").replace("Timings :",""));
        if(intent.getStringExtra("BookingType").contains("Repeat"))
        {
            txtBookingType.setText("Recurring Session");
        }else
        {
            txtBookingType.setText("Single Session");
        }


       // String bookingDate = intent.getStringExtra("BookingDate");
        String bookingStatus = intent.getStringExtra("BookingStatus");

        //setting the visibility of the button to invisible if the booking is already cancelled (cant cancel twice)
        if(bookingStatus.equals("Cancelled"))
        {
            btnCancelBooking.setVisibility(View.INVISIBLE);
        }

        //getting the booking id from the appointments page
        int bookingID = intent.getIntExtra("BookingID", 0);

        //getting psychologist and patient information and displaying on the card
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getBookingPatientAndPsych(bookingID).enqueue(new Callback<BookingParties>() {
            @Override
            public void onResponse(Call<BookingParties> call, Response<BookingParties> response) {
                if(response.code()==200)
                {
                    BookingParties bookingParties = response.body();
                  /*  Toast.makeText(EditAppointment.this,"ID patient"+
                            bookingParties.getPatient().getUserID(),Toast.LENGTH_SHORT).show();*/
                    //getting the psychologist name and profile picture
                    String psychologistName = bookingParties.getPsychologist().getName();
                    String psychImageName = bookingParties.getPsychologist().getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                    String imageUrl = "@drawable/" +  psychImageName;
                    int psychImageResource = getResources().getIdentifier(imageUrl, null, getPackageName());

                    //getting patients name and the profile picture
                    String patientName = bookingParties.getPatient().getName();
                    String patientImageName = bookingParties.getPatient().getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                    String patientImageURL = "@drawable/" +  patientImageName;
                    int patientImageResource = getResources().getIdentifier(patientImageURL, null, getPackageName());


                    //setting the respective text fields wit details
                    txtPatientName.setText(patientName);
                    txtPsychName.setText(psychologistName);

                    //setting the images for the booking parties
                    imgPatient.setImageDrawable(getResources().getDrawable(patientImageResource, getApplicationContext().getTheme()));
                    imgPsychologist.setImageDrawable(getResources().getDrawable(psychImageResource, getApplicationContext().getTheme()));

                }
            }

            @Override
            public void onFailure(Call<BookingParties> call, Throwable t) {

            }
        });


        //cancel booking functionality starts here
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("bookingID", bookingID);

        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

                apiInterface = retrofit.create(APIInterface.class);

                apiInterface.cancelBooking("application/json; charset=utf-8", hashMap).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {
                            Toast.makeText(EditAppointment.this, "Booking cancelled Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(EditAppointment.this, TabbedAppointmentMain.class);
                            i.putExtra("CancelBookingMessage","CancelBooking");
                            startActivity(i);
                        } else {
                            Toast.makeText(EditAppointment.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });

            }
        });


        //button to take the user back to the previous page
        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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
            case "04":
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

}