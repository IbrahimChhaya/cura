package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PsychRegister2 extends AppCompatActivity {
    EditText highestQualification, LicenceNumber, Speciality;
    public String getPsychHighestQualification, getPsychLicenceNumber, getPsychSpeciality;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psych_register2);

        highestQualification = (EditText) findViewById(R.id.txtPsychHighestQualification);
        LicenceNumber = (EditText) findViewById(R.id.txtPsychLicenceNumber);
        Speciality = (EditText) findViewById(R.id.txtRegPsychSpeciality);

        getPsychHighestQualification = highestQualification.getText().toString();
        getPsychLicenceNumber = LicenceNumber.getText().toString();
        getPsychSpeciality = Speciality.getText().toString();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getPsychHighestQualification.equals("") || getPsychLicenceNumber.equals("") || getPsychSpeciality.equals("")){
                    Toast.makeText(PsychRegister2.this, "Please type in your credentials", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(PsychRegister2.this, PsychRegister3.class);
                    startActivity(intent);
                }

            }
        });

    }
}