package com.example.mhaprototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(registerActivity.this,LoginRolesActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    //userType 1 = child
    //userType 2 = guardian
    //userType 3 = psych

    public void openChildRegister(View v)
    {
        GlobalVariables.userType = 1;
        Intent intent = new Intent(registerActivity.this, childRegister.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }

    public void openGuardianRegister(View v)
    {
        GlobalVariables.userType = 2;
        Intent intent = new Intent(registerActivity.this, Register.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }

  /*  public void openPsychRegister(View v)
    {
        GlobalVariables.userType = 3;
        Intent intent = new Intent(registerActivity.this, PsychRegister1.class);
        startActivity(intent);
    }*/

    public void redirect(View view)
    {
        Intent intent = new Intent(registerActivity.this, LoginRolesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}