package Appointment.ErrorPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import ChildList.ErrorPages.NoChildLinkedMain;
import PsychologistList.ErrorPages.NoPsychologistLinkedMain;
import PsychologistList.psychologistListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoAppointmentPage extends AppCompatActivity {

    APIInterface apiInterface;
    Button btnAddBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_appointment_page);

        btnAddBooking = (Button)findViewById(R.id.btnAddBooking2);
        ImageView btnBack = (ImageView) findViewById(R.id.NoappBack);

        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.NoAppToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.NoApptextview);
        heading.setText("My Appointments");

        if(GlobalVariables.loggedInUser.getUserType().equals("Child"))
        {
            btnAddBooking.setVisibility(View.INVISIBLE);
        }

        //button to go back to the previous activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                apiInterface.isLinkedToParent(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code()==200)
                        {
                            int ID = response.body();
                            if(ID>0)
                            {
                                //a link is present
                                Intent i = new Intent(NoAppointmentPage.this, psychologistListView.class);
                                startActivity(i);
                            }else if (ID==0)
                            {
                                //means that no psychologist exists
                                Intent i = new Intent(NoAppointmentPage.this, NoPsychologistLinkedMain.class);
                                startActivity(i);
                            }else if(ID==-1)
                            {
                                Intent i = new Intent(NoAppointmentPage.this, NoChildLinkedMain.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });

            }
        });
    }
}