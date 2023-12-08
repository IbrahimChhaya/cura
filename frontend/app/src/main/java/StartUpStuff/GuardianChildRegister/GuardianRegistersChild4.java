package StartUpStuff.GuardianChildRegister;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
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

import ChildList.ChildListRedesigned;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuardianRegistersChild4 extends AppCompatActivity {
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_registers_child4);
        TextInputLayout textInputLayout = findViewById(R.id.PasswordLayout);
        TextInputEditText txtPassword = findViewById(R.id.txtChildTextPassword);
        Button btnSignUp = findViewById(R.id.btnChildSignup);
        ImageView backButton = findViewById(R.id.imgPrevButtonSignin);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassword.getText().toString().equals(""))
                {
                    textInputLayout.setError("Please provide a password");
                }else
                {
                    String password = txtPassword.getText().toString();
//                    GlobalVariables.grade = "0";
//                    GlobalVariables.birthDate = "2019-08-16";

                    Date todayDate = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String todayString = formatter.format(todayDate);

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                    apiInterface = retrofit.create(APIInterface.class);

//                    String DateOfBirth = GlobalVariables.birthDate + "T00:00:00";
                    String DateRegistered = todayString + "T00:00:00";

                    Map<String, Object> hashMap = new HashMap<>();

                    UserModel usrModelChildInfo = new UserModel(GlobalVariables.email, GlobalVariables.name, password, null, "Child", null, GlobalVariables.profilePicture, DateRegistered, "Active",false);

                    Model guardianID = new Model(GlobalVariables.loggedInUser.getUserID());
                    // ChildInfo childinfo = new ChildInfo( GlobalVariables.email, GlobalVariables.name, password, DateOfBirth, "Child", GlobalVariables.grade, GlobalVariables.profilePicture, DateRegistered, "Active");

                    hashMap.put("childInfo", usrModelChildInfo);
                    hashMap.put("guardianID", GlobalVariables.loggedInUser.getUserID());
                    apiInterface.GuardianRegistersChild("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {

                            if (response.code() == 200) {
                                JSONObject gsonObject = null;
                                try {
                                    gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                    if(gsonObject.getInt("userID")>0)
                                    {
                                        Intent intent = new Intent(GuardianRegistersChild4.this, ChildListRedesigned.class);
                                        Toast.makeText(GuardianRegistersChild4.this,"Registered Successfully", Toast.LENGTH_LONG).show();

                                        startActivity(intent);

                                    }else if(gsonObject.getInt("userID")==0)
                                    {
                                        Toast.makeText(GuardianRegistersChild4.this, "Email already exists", Toast.LENGTH_LONG).show();
                                    }else
                                    {
                                        Toast.makeText(GuardianRegistersChild4.this, "Something went wrong: Please try again later", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(GuardianRegistersChild4.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                Log.i("error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(GuardianRegistersChild4.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            Log.i("error", t.getLocalizedMessage());
                        }
                    });
                }



            }
        });
    }
}