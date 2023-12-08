package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PsychRegister3 extends AppCompatActivity {
    EditText psychEmail, psychPassword, psychConfPassword;
    String getPsychEmail, getPsychPasswordm, getPsychConfPassword;
    Button btnPsychRegister;
    APIInterface apiInterface;

    PsychRegister1 p1 = new PsychRegister1();
    PsychRegister2 p2 = new PsychRegister2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psych_register3);

        psychEmail = (EditText) findViewById(R.id.txtPsychRegEmail);
        psychPassword = (EditText) findViewById(R.id.txtPsychRegPassword);
        psychConfPassword = (EditText) findViewById(R.id.txtPsychRegConfPassword);
        btnPsychRegister = (Button) findViewById(R.id.btnPsychRegister);

        getPsychEmail = psychEmail.getText().toString();
        getPsychPasswordm = psychPassword.getText().toString();
        getPsychConfPassword = psychConfPassword.getText().toString();

        btnPsychRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                Map<String, String> hashMap = new HashMap<>();


                hashMap.put("Name", p1.getPsychFirstName+ " " + p1.getPsychSurname);
                hashMap.put("dob", p1.getPsychRegDate  + "T00:00:00");
                hashMap.put("address", p1.getPsychAddress);
                hashMap.put("description", p1.getPsychDescription);
                hashMap.put("qualification", p2.getPsychHighestQualification);
                hashMap.put("regNumber", p2.getPsychLicenceNumber);
                hashMap.put("speciality", p2.getPsychSpeciality);
                hashMap.put("Email", getPsychEmail);
                hashMap.put("password", getPsychPasswordm);

                apiInterface.RegisterPsychologist("application/json; charset=utf-8",hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.code() == 200) {
                            JSONObject gsonObject = null;
                            try {
                                gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                PsychModel psychModel = new PsychModel(gsonObject);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(PsychRegister3.this, Login.class);
                            Toast.makeText(PsychRegister3.this,"Registered Successfully", Toast.LENGTH_LONG).show();
                            startActivity(intent);

                        } else {
                            Toast.makeText(PsychRegister3.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            Log.i("error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(PsychRegister3.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.i("error", t.getLocalizedMessage());
                    }
                });

            }
        });
    }
}