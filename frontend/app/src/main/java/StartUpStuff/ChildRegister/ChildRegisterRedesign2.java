package StartUpStuff.ChildRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

public class ChildRegisterRedesign2 extends AppCompatActivity {
    Button btnNext;
    EditText email;
    ImageView imgPrev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_register_redesign_2);
//        btnNext = findViewById(R.id.btnChildRegisterNext3);
//        email = findViewById(R.id.txtChildEmail2);
//        imgPrev = findViewById(R.id.imgbtnPrev);
//
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*Intent i = new Intent(NewGuardianRegister.this, NewGuardianRegister2.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);*/
//                if(email.getText().toString().equals(""))
//                {
//                    Toast.makeText(ChildRegisterRedesign2.this,"Please enter your email", Toast.LENGTH_LONG).show();
//                }else
//                {
//                    if(isValidEmailAddress(email.getText().toString()) == false){
//                        Toast.makeText(ChildRegisterRedesign2.this,"Please make sure you entered valied email address", Toast.LENGTH_LONG).show();
//                    }else {
//
//
//                        //GlobalVariables.email = email.getText().toString();
//                        //start the second register activity
//                        Intent i = new Intent(ChildRegisterRedesign2.this, ChildRegisterRedesign3.class);
//                        startActivity(i);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    }
//                }
//            }
//        });
//
//        imgPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//            }
//        });

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
