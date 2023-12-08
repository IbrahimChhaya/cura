package StartUpStuff.ChildRegister;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.RegisterModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildRegisterTextPassword extends AppCompatActivity {
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_register_text_password);

        TextInputEditText txtPassword = findViewById(R.id.txtChildTextPassword);
        Button btnChildSignIn = findViewById(R.id.btnChildSignin);
        ImageView imgPrevButton = findViewById(R.id.imgPrevButtonSignin);
        TextInputLayout txtInputLayout = findViewById(R.id.PasswordLayout);


        //button to go back to previous activity
        imgPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        btnChildSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassword.getText().equals(""))
                {
                    txtInputLayout.setError("Please provide a password");
                }else
                {
                    //register the child
//                    GlobalVariables.grade = "0";
//                    GlobalVariables.birthDate = "2019-08-16";

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                    apiInterface = retrofit.create(APIInterface.class);

                    Map<String, Object> hashMap = new HashMap<>();

                    Date todayDate = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String todayString = formatter.format(todayDate);

                    hashMap.put("Email", GlobalVariables.email);
                    hashMap.put("Name", GlobalVariables.name);
                    hashMap.put("Password", txtPassword.getText().toString().trim());
                    //hashMap.put("DOB",  GlobalVariables.birthDate + "T00:00:00");
                    hashMap.put("UserType","Child");
                    //hashMap.put("Grade", GlobalVariables.grade);
                    hashMap.put("ProfilePicture", GlobalVariables.profilePicture);
                    //need to add another field to put the status of the password

                    hashMap.put("DateRegistered", todayString + "T00:00:00");
                    hashMap.put("Status", "Active");
                    hashMap.put("PicturePass", false);

                    apiInterface.RegisterUser("application/json; charset=utf-8", hashMap).enqueue(new Callback<RegisterModel>() {
                        @Override
                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                            if(response.code()==200)
                            {
                                JSONObject gsonObject = null;
                                try {
                                    gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                    if(gsonObject.getInt("userID")>0)
                                    {
                                        Intent intent = new Intent(ChildRegisterTextPassword.this, ChildLogInRedesign1.class);
                                        Toast.makeText(ChildRegisterTextPassword.this,"Registered Successfully", Toast.LENGTH_LONG).show();

                                        startActivity(intent);


                                    }else if(gsonObject.getInt("userID")==0)
                                    {
                                        Toast.makeText(ChildRegisterTextPassword.this, "Email already exists", Toast.LENGTH_LONG).show();
                                    }else
                                    {
                                        Toast.makeText(ChildRegisterTextPassword.this, "Something went wrong: Please try again later", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else
                            {
                                Toast.makeText(ChildRegisterTextPassword.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                Log.i("error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterModel> call, Throwable t) {

                        }
                    });
                }
            }
        });






    }
}