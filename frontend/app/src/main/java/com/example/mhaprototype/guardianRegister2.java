package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class guardianRegister2 extends AppCompatActivity {

    TextView txtHeading2;
    EditText txtGuardianEmail;
    EditText txtGuardianPassword;
    TextView txtAlreadySigned2;
    Button btnSignUp;
    ImageButton btnPrev2;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_register2);

        txtHeading2 = (TextView) findViewById(R.id.txtHeading2);
        txtGuardianEmail = (EditText) findViewById(R.id.txtGuardianEmail);
        txtGuardianPassword = (EditText) findViewById(R.id.txtGuardianPassword);
        txtAlreadySigned2 = (TextView) findViewById(R.id.txtAlreadySigned2);
        btnSignUp = (Button) findViewById(R.id.btnGuardianRegister);
        btnPrev2 = (ImageButton) findViewById(R.id.imgPrevButton2);

        //setting up retrofit library and the api interface
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);


        //setting image button to take to previous page
        btnPrev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes the user back to the previous page
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });

        //taking the user back to the login page if already signed up
        txtAlreadySigned2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(guardianRegister2.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remm to add the profile picture

                //get the current date
                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = formatter.format(todayDate);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Name", GlobalVariables.guardianName); //GlobalVariables.guardianName);
                hashMap.put("Email", txtGuardianEmail.getText().toString().trim());
                hashMap.put("Password", txtGuardianPassword.getText().toString().trim());
                hashMap.put("DOB", GlobalVariables.guardianDOB);// + "T17:50:42.802Z");
                hashMap.put("UserType", "Parent");
                hashMap.put("ProfilePicture", GlobalVariables.profilePicture + ".png");
                hashMap.put("DateRegistered",todayString);//"2021-07-16T17:50:42.802Z");//todayString);
                hashMap.put("Status", "Active");

                String email = txtGuardianEmail.getText().toString();
                String Password = txtGuardianPassword.getText().toString();

                if (txtGuardianEmail.getText().toString().equals("")) {
                    Toast.makeText(guardianRegister2.this, "Please provide an email", Toast.LENGTH_LONG).show();
                } else if (txtGuardianPassword.getText().toString().equals("")) {
                    Toast.makeText(guardianRegister2.this, "Please provide a password", Toast.LENGTH_LONG).show();
                } else {
                    apiInterface.RegisterUser("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code() == 200) {
                                JSONObject gsonObject = null;
                                try {
                                    gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                    int userID = gsonObject.getInt("userID");
                                    int newUserID = userID;
                                    if(userID>0)
                                    {
                                        Intent intent = new Intent(guardianRegister2.this, Login.class);
                                        Toast.makeText(guardianRegister2.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                    }else if(userID==0)
                                    {
                                        Toast.makeText(guardianRegister2.this,"Sorry the email already exists",Toast.LENGTH_SHORT).show();
                                    }else
                                    {
                                        Toast.makeText(guardianRegister2.this,"System error: Please try again later",Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });

                }
            }
        });
    }
}