package Account.Child;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
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

public class EditChildPassword2 extends AppCompatActivity {
    TextView txtMatchError;
    ImageView backBtn;
    Button btnSave;

    TextInputEditText txtCurrentPassword;
    TextInputEditText txtNewPassword;
    TextInputEditText TxtNewPasswordConf;

    TextInputLayout txtCurrentLayout;
    TextInputLayout txtNewPasswordLayout;
    TextInputLayout TxtNewPasswordLayoutConf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child_password2);
        txtMatchError = findViewById(R.id.matchError);
        backBtn = findViewById(R.id.imageView21);
        txtCurrentPassword = findViewById(R.id.txtChildCurrentTextPassword);
        txtNewPassword = findViewById(R.id.txtChildNewTextPassword);
        TxtNewPasswordConf = findViewById(R.id.txtChildNewTextPasswordConf);

        txtCurrentLayout = findViewById(R.id.childCurrentPassword);
        txtNewPasswordLayout = findViewById(R.id.childNewPassword);
        TxtNewPasswordLayoutConf = findViewById(R.id.childNewPasswordConf);




        backBtn.setOnClickListener(v -> finish());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);


        btnSave = findViewById(R.id.btnSaveChildPassword);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMatchError.setVisibility(View.INVISIBLE);
                txtMatchError.setText("New password must match Confirmation password");

                if(txtCurrentPassword.getText().toString().equals(""))
                {
                    txtCurrentLayout.setError("Please provide your current password");
                }else if(txtNewPassword.getText().toString().equals(""))
                {
                    txtNewPasswordLayout.setError("Please provide a new password");
                }else if(TxtNewPasswordConf.getText().toString().equals(""))
                {
                    TxtNewPasswordLayoutConf.setError("Please confirm your new password");
                }
                else if(txtNewPassword.getText().toString().equals("")||!txtNewPassword.getText().toString().equals(TxtNewPasswordConf.getText().toString())){
                    txtMatchError.setVisibility(View.VISIBLE);
                }
                else{
                    txtMatchError.setVisibility(View.INVISIBLE);
                    Map<String, Object> map = new HashMap<>();

                    //userID, oldPassword, newPassword
                    map.put("userID", GlobalVariables.loggedInUser.getUserID());
                    map.put("oldPassword", txtCurrentPassword.getText().toString());
                    map.put("newPassword", txtNewPassword.getText().toString());

                    apiInterface.changePassword("application/json; charset=utf-8", map).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if(response.code() == 200){
                                JSONObject jsonObject;
                                try {
                                    jsonObject = new JSONObject(new Gson().toJson(response.body()));

                                    if(jsonObject.getInt("userID") > 0){
                                        Toast.makeText(EditChildPassword2.this, "Password Successfully changed", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(EditChildPassword2.this, UpdateChildAccount.class);
                                        startActivity(intent);
                                    }
                                    else if(jsonObject.getInt("userID") == -2){
                                        txtMatchError.setText("Incorrect Old Password provided");
                                        txtMatchError.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        Toast.makeText(EditChildPassword2.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(EditChildPassword2.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(EditChildPassword2.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

               // txtCurrentPassword.setText(GlobalVariables.loggedInUser.getPassword().toString());


            }
        });
    }
}