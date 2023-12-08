package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PsychRegister1 extends AppCompatActivity {
    EditText firstName, surname, address, description,dateofbirth;
    Button btnNext;
    DatePickerDialog datePicker;
    public String getPsychFirstName, getPsychSurname, getPsychRegDate, getPsychAddress, getPsychDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psych_register1);

        firstName = (EditText) findViewById(R.id.txtRegPsychName);
        surname = (EditText) findViewById(R.id.txtRegPsychSurname);
        dateofbirth = (EditText) findViewById(R.id.txtPsychRegDOB);
        address = (EditText) findViewById(R.id.txtRegPsychAddress);
        description = (EditText) findViewById(R.id.txtRegPsychDescription);
        btnNext = (Button) findViewById(R.id.btnPsychRegisterNext1);

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date = dateFormat.format(calendar.getTime());

        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(PsychRegister1.this, date,Toast.LENGTH_LONG).show();

                // date picker dialog

                datePicker = new DatePickerDialog(PsychRegister1.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String mont = "" + (monthOfYear + 1);
                        String d = "" +  dayOfMonth;
                        if((monthOfYear + 1) <= 9){
                            mont = "0" + (monthOfYear);
                        }
                        if(dayOfMonth <= 9){
                            d = "0" + dayOfMonth;
                        }
                        getPsychRegDate = year + "-" + (mont) + "-" + d;
                        dateofbirth.setText(year + "-" + (mont) + "-" + d);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        getPsychFirstName = firstName.getText().toString();
        getPsychSurname = surname.getText().toString();
        getPsychAddress = address.getText().toString();
        getPsychDescription = description.getText().toString();

      /*  btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPsychFirstName.equals("") || getPsychSurname.equals("") || getPsychRegDate.equals("") || getPsychAddress.equals("") || getPsychDescription.equals("")) {
                    Toast.makeText(PsychRegister1.this, "Please type in your credentials", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(PsychRegister1.this, PsychRegister2.class);
                    startActivity(intent);
                }
            }
        });*/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPsychFirstName.equals("") || getPsychSurname.equals("") || getPsychRegDate.equals("") || getPsychAddress.equals("") || getPsychDescription.equals("")) {
                    Toast.makeText(PsychRegister1.this, "Please type in your credentials", Toast.LENGTH_LONG).show();
                }else
                {
                    Intent intent = new Intent(PsychRegister1.this, PsychRegister2.class);
                    startActivity(intent);
                }
            }
        });

    }
}