package StartUpStuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mhaprototype.R;

public class NewStartUp extends AppCompatActivity {
    Button btnAlreadyHaveAccount;
    Button btnGetStarted;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_start_up);

        btnAlreadyHaveAccount = findViewById(R.id.btnAlreadyHaveAccount);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewStartUp.this,SignInRoles.class);
                i.putExtra("StartStatus","AlreadyRegistered");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewStartUp.this,SignInRoles.class);
                i.putExtra("StartStatus","NotRegistered");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }
}