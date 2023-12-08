package com.example.mhaprototype;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PsychologistList.psychologistListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationPage extends AppCompatActivity {
    Button AddBooking, ViewBooking,generateCode;
    APIInterface apiInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);
        TextView txtHeading = (TextView) findViewById(R.id.txtHeading);
        txtHeading.setTypeface(null, Typeface.BOLD);
        AddBooking = (Button) findViewById(R.id.btnNavigateAddBooking);
        TableLayout tbLayout = (TableLayout)findViewById(R.id.tblBookings);

        if(GlobalVariables.typeUser.equals("Child"))
        {
            AddBooking.setVisibility(View.INVISIBLE);
        }
        //Fetching the bookings for the user
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getuserBookings(GlobalVariables.userID).enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                if(response.code() == 204)
                {
                    TextView txtHeading = (TextView) findViewById(R.id.txtHeading);
                    txtHeading.setText("No bookings");
                }
                if(response.code() == 200)
                {
                    View tableHeading = LayoutInflater.from(NavigationPage.this).inflate(R.layout.table_item,null,false);
                    TextView txtDate = (TextView) tableHeading.findViewById(R.id.txtDate);
                    TextView txtTime = (TextView) tableHeading.findViewById(R.id.txtTime);
                    TextView txtType = (TextView) tableHeading.findViewById(R.id.txtType);
                    //adding some style
                    txtDate.setTypeface(null, Typeface.BOLD);
                    txtTime.setTypeface(null, Typeface.BOLD);
                    txtType.setTypeface(null, Typeface.BOLD);
                    tableHeading.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                   /* txtDate.setText("Date");
                    txtTime.setText("Time");
                    txtType.setText("Type");

                    tbLayout.addView(tableHeading);*/
                     int count = -1;
                    for(BookingModel mod: response.body()) {
                        count++;

                        //

                        View tableRow = LayoutInflater.from(NavigationPage.this).inflate(R.layout.table_item, null, false);
                        if (count % 2 == 0) {
                            tableRow.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                        TextView txtDateView = (TextView) tableRow.findViewById(R.id.txtDate);
                        TextView txtTimeView = (TextView) tableRow.findViewById(R.id.txtTime);
                        TextView txtTypeView = (TextView) tableRow.findViewById(R.id.txtType);

                        String date = mod.getDate().substring(0, 10);

                        GlobalVariables.bookingList.add(mod.getDate().toString() + " " +mod.getType()+" "+ mod.getTime().getHours());
                        txtDateView.setText(date);
                        int hour = (int) mod.getTime().getHours();
                        int minutes = (int) mod.time.getMinutes();
                        if (minutes < 10) {
                            txtTimeView.setText(hour + ":0" + minutes);
                        } else {
                            txtTimeView.setText(hour + ":" + minutes);
                        }
                        txtTypeView.setText(mod.getType());
                        tbLayout.addView(tableRow);
                    }
                }else{
                    Log.d("Output " , response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {
                Toast.makeText(NavigationPage.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        //Fetching the bookings for the user end

        AddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationPage.this, psychologistListView.class);//ViewPsychologist
                startActivity(intent);
            }
        });

    }
}