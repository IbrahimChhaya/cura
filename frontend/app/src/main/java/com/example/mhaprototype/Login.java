package com.example.mhaprototype;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import StartUpStuff.GuardianRegister.NewGuardianRegister;
import StartUpStuff.GuardianRegister.NewGuardianRegister2;
import StartUpStuff.SignInRoles;
import psychologistHome.psychologist_home;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText Email, Password;
    Spinner usertype;
    String getemail = "";
    String getpassword = "";
    String getserverusertype = "";
    String getServerEmail = "";
    String userName = "";
    String getServerPassword = "";
    String getServerProfilePicture = "";
    int getuserID;
    TextInputLayout emailInput, passwordInput;

    String userStatus;
    APIInterface apiInterface;


    LinearLayout linearLayout;

    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        Intent i = new Intent(Login.this, SignInRoles.class);
        i.putExtra("StartStatus","AlreadyRegistered");
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardian_signin_redesign);//activity_login);



        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

       /* Button btnLog = (Button)findViewById(R.id.button9);
        Button btnReg = (Button)findViewById(R.id.button10);*/

        btnLogin = (Button) findViewById(R.id.btnGuardianSignin);//btnGo);
        Email =  findViewById(R.id.edtGuardianEmail);//txtEmail);
        Password =  findViewById(R.id.edtGuardianPassword);//txtPassword);
        TextView txtDontHaveAccount = (TextView) findViewById(R.id.txtNoAccount);
        ImageView btnPrev = (ImageView) findViewById(R.id.imgPrevButtonSignin);
        emailInput = findViewById(R.id.emailLayout);
        passwordInput = findViewById(R.id.PasswordLayout);

        Intent i = getIntent();
        if(i.getStringExtra("userType").equals("Psychologist"))
        {
            txtDontHaveAccount.setVisibility(View.INVISIBLE);
            txtDontHaveAccount.setEnabled(false);
        }

     /*   linearLayout = (LinearLayout)findViewById(R.id.login_bottom_sheet2);

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
*/
      /*  btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });*/

        txtDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, NewGuardianRegister.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignInRoles.class);
                i.putExtra("StartStatus","AlreadyRegistered");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> hashMap = new HashMap<>();
                getemail = Email.getText().toString();
                getpassword = Password.getText().toString();
                if(getemail.equals(""))
                {
                    emailInput.setError("Please provide your Email address");
                }else if(getpassword.equals(""))
                {
                    passwordInput.setError("Please provide your Password");
                }

                hashMap.put("email", getemail);
                hashMap.put("password", getpassword);

                if (getemail.equals("") || getpassword.equals("")) {
                    Toast.makeText(Login.this, "Please type in your credentials", Toast.LENGTH_LONG).show();
                } else {

                    if(isValidEmailAddress(getemail) == true){
                        apiInterface.loginUser("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.code() == 200) {
                                    JSONObject gsonObject = null;
                                    try {
                                        gsonObject = new JSONObject(new Gson().toJson(response.body()));



                                        if (gsonObject.getInt("userID") > 0) {
                                            UserModel usermodel = new UserModel(gsonObject);
                                            GlobalVariables.loggedInUser = usermodel;
                                            if (GlobalVariables.loggedInUser.getStatus().equals("Active")) {
                                                if (GlobalVariables.loggedInUser.getUserType().equals("Child")) {
                                                    Intent intent = new Intent(Login.this, Child_Home.class);
                                                    intent.putExtra("userID", getuserID);
                                                    startActivity(intent);
                                                } else if (GlobalVariables.loggedInUser.getUserType().equals("Parent")) {
                                                    Intent intent = new Intent(Login.this, Guardian_Home.class);
                                                    intent.putExtra("userID", getuserID);
                                                    startActivity(intent);

                                                }else if(GlobalVariables.loggedInUser.getUserType().equals("Psychologist"))
                                                {
                                                    Intent intent = new Intent(Login.this, psychologist_home.class);
                                                    intent.putExtra("userID", getuserID);
                                                    startActivity(intent);
                                                }
                                            }else
                                            {
                                                Toast.makeText(Login.this, "Sorry your account is no longer active", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(Login.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                                        }

                               /* userName = usermodel.getName();
                                getServerEmail = usermodel.getEmail();
                                getServerPassword = usermodel.getPassword();
                                getserverusertype = usermodel.getUserType();
                                getuserID = (usermodel.getUserID());
                                getServerProfilePicture = usermodel.getProfilePicture();
                                userStatus = usermodel.getStatus();*/



                                /*GlobalVariables.userID = getuserID;
                                GlobalVariables.typeUser = getserverusertype;
                                GlobalVariables.userName = userName;
                                GlobalVariables.profilePicture = getServerProfilePicture;
                                GlobalVariables.name = usermodel.getName();
                                GlobalVariables.birthDate = usermodel.getDob();
                                GlobalVariables.email = getServerEmail;*/
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    Toast.makeText(Login.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                }
                            }


                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(Login.this,"Please enter correct email format", Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void openRegister(View view) {
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
    }
}