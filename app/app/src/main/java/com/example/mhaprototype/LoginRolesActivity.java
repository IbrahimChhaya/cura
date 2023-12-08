package com.example.mhaprototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginRolesActivity extends AppCompatActivity {

    @Override
    public void onBackPressed(){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_roles);

        Button btnSignUp = findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginRolesActivity.this,registerActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

    public void openChildLogin(View v)
    {
        GlobalVariables.userType = 1;
        Intent intent = new Intent(LoginRolesActivity.this, ChildLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void openGuardianLogin(View v)
    {
        GlobalVariables.userType = 2;
        Intent intent = new Intent(LoginRolesActivity.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void openPsychLogin(View v)
    {
        GlobalVariables.userType = 3;
        Intent intent = new Intent(LoginRolesActivity.this, Login.class);
        startActivity(intent);
    }

    public void redirect(View view)
    {
        Intent intent = new Intent(LoginRolesActivity.this, registerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }
}
