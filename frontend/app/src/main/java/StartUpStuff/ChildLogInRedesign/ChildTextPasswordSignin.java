package StartUpStuff.ChildLogInRedesign;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildTextPasswordSignin extends AppCompatActivity {
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_text_password_signin);
        TextInputEditText txtChildPassword = findViewById(R.id.txtChildTextPassword);
        Button btnSignIn = findViewById(R.id.btnChildSignin);
        TextInputLayout textInputLayout = findViewById(R.id.PasswordLayout);
        ImageView imgBackButton = findViewById(R.id.imgPrevButtonSignin);


        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtChildPassword.getText().toString().equals(""))
                {
                    textInputLayout.setError("Please enter your password");
                }else
                {
                    Map<String, String> hashMap = new HashMap<>();
                    hashMap.put("email", GlobalVariables.email);
                    hashMap.put("password", txtChildPassword.getText().toString());
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

                    apiInterface = retrofit.create(APIInterface.class);

                    apiInterface.loginUser("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code() == 200) {

                                String getserverusertype = "";
                                int getuserID = 0;

                                JSONObject gsonObject = null;
                                try {
                                    gsonObject = new JSONObject(new Gson().toJson(response.body()));

                                    if (gsonObject.getInt("userID") > 0 && gsonObject.getString("userType").equals("Child")) {
                                        UserModel usermodel = new UserModel(gsonObject);
                                        GlobalVariables.loggedInUser = usermodel;
                                        Intent intent = new Intent(ChildTextPasswordSignin.this, Child_Home.class);
                                        /*intent.putExtra("userID", getuserID);*/
                                        startActivity(intent);
                                    } else if(gsonObject.getInt("userID") > 0 && gsonObject.getString("userType").equals("Parent")){
                                        Toast.makeText(ChildTextPasswordSignin.this, "Sorry the email provided belongs to a parent", Toast.LENGTH_LONG).show();
                                    }else
                                    {
                                        Toast.makeText(ChildTextPasswordSignin.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(ChildTextPasswordSignin.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(ChildTextPasswordSignin.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}