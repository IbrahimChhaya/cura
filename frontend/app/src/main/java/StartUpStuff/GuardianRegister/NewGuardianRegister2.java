package StartUpStuff.GuardianRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
import com.example.mhaprototype.guardianRegister2;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewGuardianRegister2 extends AppCompatActivity {
    TextInputEditText txtGuardianEmail;
    TextInputEditText txtGuardianPassword;
    Button btnGuardianSubmit;
    APIInterface apiInterface;
    TextView txtAlreadySigned;
    ImageView imgPrev;
    TextInputLayout emailInput, passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guardian_register2);
        txtGuardianEmail = findViewById(R.id.txtGuardianEmail2);
        txtGuardianPassword = findViewById(R.id.txtGuardianPassword2);
        btnGuardianSubmit = findViewById(R.id.btnGuardianRegister2);
        txtAlreadySigned = findViewById(R.id.txtAlreadySigned);
        imgPrev = findViewById(R.id.imgbtnPrev);
        emailInput = findViewById(R.id.emailLayout);
        passwordInput = findViewById(R.id.PasswordLayout);


        txtAlreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewGuardianRegister2.this,Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });



        btnGuardianSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = formatter.format(todayDate);
                if(txtGuardianEmail.getText().toString().equals(""))
                {
                    emailInput.setError("Please provide your Email address");
                }else if(txtGuardianPassword.getText().toString().equals(""))
                {
                    passwordInput.setError("Please provide your Password");
                }

                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("name", GlobalVariables.guardianName); //GlobalVariables.guardianName);
                hashMap2.put("email", txtGuardianEmail.getText().toString().trim());
                hashMap2.put("password", txtGuardianPassword.getText().toString().trim());
                hashMap2.put("userType", "Parent");
                hashMap2.put("profilePicture", GlobalVariables.profilePicture + ".png");
                hashMap2.put("dateRegistered",todayString);//"2021-07-16T17:50:42.802Z");//todayString);
                hashMap2.put("status", "Active");

                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                //registering the user
                if (txtGuardianEmail.getText().toString().equals("")) {
                    Toast.makeText(NewGuardianRegister2.this, "Please provide an email", Toast.LENGTH_LONG).show();
                } else if (txtGuardianPassword.getText().toString().equals("")) {
                    Toast.makeText(NewGuardianRegister2.this, "Please provide a password", Toast.LENGTH_LONG).show();
                } else {
                    if(isValidEmailAddress(txtGuardianEmail.getText().toString())  == true){
                        apiInterface.RegisterUser("application/json; charset=utf-8", hashMap2).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.code() == 200) {
                                    JSONObject gsonObject = null;
                                    try {
                                        gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                        UserModel usermodel = new UserModel(gsonObject);
                                        GlobalVariables.loggedInUser = usermodel;
                                        int userID = gsonObject.getInt("userID");
                                        int newUserID = userID;
                                        if(userID>0)
                                        {
                                            Intent intent = new Intent(NewGuardianRegister2.this, Guardian_Home.class);
                                            Toast.makeText(NewGuardianRegister2.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                        }else if(userID==0)
                                        {
                                            Toast.makeText(NewGuardianRegister2.this,"Sorry the email already exists",Toast.LENGTH_SHORT).show();
                                        }else
                                        {
                                            Toast.makeText(NewGuardianRegister2.this,"System error: Please try again later",Toast.LENGTH_SHORT).show();
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
                    }else{
                        Toast.makeText(NewGuardianRegister2.this,"Please enter correct email format", Toast.LENGTH_LONG).show();
                    }


                }

            }
        });


        txtAlreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewGuardianRegister2.this,Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}