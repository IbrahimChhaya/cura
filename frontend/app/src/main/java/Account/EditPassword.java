package Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPassword extends AppCompatActivity {

    Button btnSave;
    ImageView backBtn;
    TextInputEditText txtCurrentPassword;
    TextInputEditText txtNewPassword;
    TextInputEditText txtConfirmPassword;
    TextInputLayout txtEditError;
    TextInputLayout txtPasswordError;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changeButtonVisibility();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        btnSave = findViewById(R.id.btnSavePassword);
        backBtn = findViewById(R.id.imageView27);
        txtCurrentPassword= findViewById(R.id.txtPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtEditError = findViewById(R.id.LastInput);
        txtPasswordError = findViewById(R.id.currentPass);

        //add text listeners for all the text fields
        txtCurrentPassword.addTextChangedListener(textWatcher);
        txtNewPassword.addTextChangedListener(textWatcher);
        txtConfirmPassword.addTextChangedListener(textWatcher);

        changeButtonVisibility();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(GlobalVariables.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        backBtn.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {

            Map<String, Object> map = new HashMap<>();

            //userID, oldPassword, newPassword
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("oldPassword", txtCurrentPassword.getText().toString().trim());
            map.put("newPassword", txtNewPassword.getText().toString().trim());

            apiInterface.changePassword("application/json; charset=utf-8", map).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("userID") > 0){
                                Toast.makeText(EditPassword.this, "Password Successfully changed", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(EditPassword.this, UpdateGuardianAccount.class);
                                startActivity(intent);
                            }
                            else if(jsonObject.getInt("userID") == -2){
                                txtEditError.setError("Incorrect Password provided");
                            }
                            else{
                                Toast.makeText(EditPassword.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditPassword.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(EditPassword.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });


        });
    }

    //utility method: control whether button is enabled or not
    private void changeButtonVisibility() {
        btnSave = findViewById(R.id.btnSavePassword);
        txtPasswordError = findViewById(R.id.currentPass);

        txtPasswordError.setError(null);
        String password = txtCurrentPassword.getText().toString().trim();
        String newPassword = txtNewPassword.getText().toString().trim();
        String confirmPass = txtConfirmPassword.getText().toString().trim();

        if(password.isEmpty() || newPassword.isEmpty() || confirmPass.isEmpty()){
            btnSave.setEnabled(false);  //if any of the fields are empty, disable the button
        }
        else{
            if(checkPasswordsMatch()){
                btnSave.setEnabled(true);
            }
        }
    }

    //utility method to check if new password and confirmation password match
    private boolean checkPasswordsMatch() {
        String newPassword = txtNewPassword.getText().toString().trim();
        String confirmPass = txtConfirmPassword.getText().toString().trim();
        txtEditError = findViewById(R.id.LastInput);

        if(!newPassword.isEmpty() && !confirmPass.isEmpty()){
            if(newPassword.equals(confirmPass)){
                txtEditError.setError(null);
                return true;
            }
            else{
                txtEditError.setError("New Password and Confirm password need to match");
                return false;
            }
        }

        return false;
    }

}