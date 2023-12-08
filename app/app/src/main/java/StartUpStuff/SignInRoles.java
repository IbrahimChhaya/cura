package StartUpStuff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mhaprototype.ChildLogin;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.example.mhaprototype.Register;
import com.example.mhaprototype.childRegister;

import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign2;
import StartUpStuff.ChildRegister.ChildRegisterRedesign;
import StartUpStuff.GuardianRegister.NewGuardianRegister;

public class SignInRoles extends AppCompatActivity {
    ImageView backButton;
    CardView childCardView;
    CardView guardianCardView;
    CardView psychologistCardView;
    ProgressBar progressBar;
    ImageView imgPsych;

    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        Intent i = new Intent(SignInRoles.this, NewStartUp.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_roles);

        imgPsych = findViewById(R.id.psychImage);
        backButton = findViewById(R.id.arrowBack);
        childCardView = findViewById(R.id.childCardView);
        guardianCardView = findViewById(R.id.guardianCardView);
        psychologistCardView = findViewById(R.id.psychologistCardView);
        progressBar = findViewById(R.id.progressBar2);
        Intent i = getIntent();
        String userStatus = i.getStringExtra("StartStatus");

        if(userStatus != null)
        {
            if(userStatus.equals("AlreadyRegistered"))
            {
                progressBar.setVisibility(View.INVISIBLE);
                psychologistCardView.setCardBackgroundColor(Color.parseColor("#e2cbfe"));
            }else
            {
                psychologistCardView.setEnabled(false);
                psychologistCardView.setCardBackgroundColor(Color.GRAY);
                String imageUrl = "@drawable/adult9_greyed";
                int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                imgPsych.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
            }
        }



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInRoles.this, NewStartUp.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        childCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userStatus.equals("AlreadyRegistered"))
                {
                    Intent i = new Intent(SignInRoles.this, ChildLogInRedesign1.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }else if(userStatus.equals("NotRegistered"))
                {
                    Intent i = new Intent(SignInRoles.this, ChildRegisterRedesign.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }

            }
        });

        guardianCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userStatus.equals("AlreadyRegistered"))
                {
                    Intent i = new Intent(SignInRoles.this, Login.class);
                    i.putExtra("userType","Parent");
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }else if(userStatus.equals("NotRegistered"))
                {
                    Intent i = new Intent(SignInRoles.this, NewGuardianRegister.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });

        psychologistCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignInRoles.this, Login.class);
                i.putExtra("userType","Psychologist");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
    }
}