package StartUpStuff.ChildLogInRedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;

import StartUpStuff.ChildRegister.ChildRegisterRedesign;
import StartUpStuff.ChildRegister.ChildRegisterRedesign2;
import StartUpStuff.SignInRoles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildLogInRedesign1 extends AppCompatActivity {
    EditText emailChild;
    Button btnsignIn;
    ImageView backBtn;
    TextView txtAlreadySigned;
    APIInterface apiInterface;
    boolean passwordStatus;

    //back button code
    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        Intent i = new Intent(ChildLogInRedesign1.this, SignInRoles.class);
        i.putExtra("StartStatus","AlreadyRegistered");
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_login_redesign_1);
        emailChild = findViewById(R.id.edtChildEmailLog);
        btnsignIn = findViewById(R.id.btnChildSignin);
        backBtn = findViewById(R.id.backLoginChild);
        txtAlreadySigned = findViewById(R.id.txtNoAccount);

        txtAlreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildLogInRedesign1.this, ChildRegisterRedesign.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailChild.getText().toString().equals("")){
                    Toast.makeText(ChildLogInRedesign1.this,"Please enter your email", Toast.LENGTH_LONG).show();
                }else{
                    if(isValidEmailAddress(emailChild.getText().toString()) == true){
                        GlobalVariables.email = emailChild.getText().toString();
                        //check the password type
                        checkPasswordType(emailChild.getText().toString());



                    }else{
                        Toast.makeText(ChildLogInRedesign1.this,"Please enter correct email format", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildLogInRedesign1.this, SignInRoles.class);
                i.putExtra("StartStatus","AlreadyRegistered");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });


    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void checkPasswordType(String email)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        apiInterface.hasPicturePass(email).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==200)
                {
                    passwordStatus = response.body();
                    if(passwordStatus == true)
                    {
                        Intent i = new Intent(ChildLogInRedesign1.this, ChildLogInRedesign2.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }else if(passwordStatus == false)
                    {
                        Intent i = new Intent(ChildLogInRedesign1.this, ChildTextPasswordSignin.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}

