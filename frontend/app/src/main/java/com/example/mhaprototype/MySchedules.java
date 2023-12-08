package com.example.mhaprototype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MySchedules extends AppCompatActivity {
    APIInterface apiInterface;
    SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedules);
        Intent intent = getIntent();
        int ID = GlobalVariables.userID;





        TextView date1 = (TextView) findViewById(R.id.lblDate);
        TextView time1 = (TextView) findViewById(R.id.lbltime1);
        TextView time2 = (TextView) findViewById(R.id.lbltime2);
        TextView appointmentreason = (TextView) findViewById(R.id.lblappointment);
        TextView appcount = (TextView) findViewById(R.id.lblappointmentcount);
        Button button = (Button) findViewById(R.id.eighteenapril);
        Button button2 = (Button) findViewById(R.id.nineteenapril);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getuserBookings(ID).enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                if(response.code() == 200){
                    String res = "";
                    String hour = "";
                    String type = "";

                    for(BookingModel mod: response.body()){
                        res = mod.getDate();
                        hour = mod.getTime().getHours() + ":" + mod.getTime().getMinutes();
                        type = mod.getType();

                    }
                    appcount.setText("1 Appointment");
                    date1.setText(res);
                    time1.setText("09:00");
                    time2.setText("10:00");

                    appointmentreason.setText(type);
                }else{
                    Log.d("Output " , response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {
                Toast.makeText(MySchedules.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

}