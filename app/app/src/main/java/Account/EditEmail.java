package Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
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

public class EditEmail extends AppCompatActivity {
    Button btnSave;
    TextInputEditText txtNewEmail;

    TextInputLayout txtNewEmailLayout;
    TextInputEditText txtNewEmailConfirm;

    TextInputLayout txtPasswordLayout;
    TextInputEditText txtPassword;
    ImageView backBtn;

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
        setContentView(R.layout.activity_edit_email);

        btnSave = findViewById(R.id.btnSaveEmail);
        TextView txtCurrentEmail = findViewById(R.id.txtCurrentEmail);
        txtNewEmail = findViewById(R.id.txtNewEmailValue);
        txtNewEmailConfirm = findViewById(R.id.txtNewEmailConfirm);
        txtPassword = findViewById(R.id.txtEmailPassword);

        txtNewEmailLayout = findViewById(R.id.txtNewEmailConf);
        txtPasswordLayout = findViewById(R.id.emailPassword);
        backBtn = findViewById(R.id.imageView25);

        txtCurrentEmail.setText(GlobalVariables.loggedInUser.getEmail());

        //add text listeners to edit texts
        txtNewEmail.addTextChangedListener(textWatcher);
        txtNewEmailConfirm.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);

        changeButtonVisibility();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(GlobalVariables.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        backBtn.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {

            Map<String, Object> map = new HashMap<>();

            //name email dob profilePicture
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("oldPassword", txtPassword.getText().toString().trim());
            map.put("newName", GlobalVariables.loggedInUser.getName());
            map.put("newEmail", txtNewEmail.getText().toString().trim());
            map.put("newDOB", GlobalVariables.loggedInUser.getDob());
            map.put("newProfilePicture", GlobalVariables.loggedInUser.getProfilePicture());

            apiInterface.UpdateGuardianEmail("application/json; charset=utf-8", map).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("userID") > 0){
                                GlobalVariables.loggedInUser = new UserModel(jsonObject);

                                Intent intent = new Intent(EditEmail.this, UpdateGuardianAccount.class);
                                startActivity(intent);
                            }
                            else if(jsonObject.getInt("userID") == -2){
                                txtPasswordLayout.setError("Incorrect Password");
                            }
                            else if(jsonObject.getInt("userID") == -3){
                                txtNewEmailLayout.setError("This email address is already in use.");
                            }
                            else if(jsonObject.getInt("userID") == 0){
                                Toast.makeText(EditEmail.this, "User not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(EditEmail.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditEmail.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(EditEmail.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    //Utility method: used to check if input fields are empty - in order to control button availability
    private  void changeButtonVisibility(){
        btnSave = findViewById(R.id.btnSaveEmail);
        txtPassword = findViewById(R.id.txtEmailPassword);
        txtNewEmail = findViewById(R.id.txtNewEmailValue);
        txtNewEmailConfirm = findViewById(R.id.txtNewEmailConfirm);

        txtPasswordLayout = findViewById(R.id.emailPassword);

        txtPasswordLayout.setError(null);
        txtNewEmailLayout.setError(null);
        String strEmail = txtNewEmail.getText().toString().trim();
        String strEmailConf = txtNewEmailConfirm.getText().toString().trim();
        String strPassword = txtPassword.getText().toString().trim();

        if (strEmail.isEmpty() || strEmailConf.isEmpty() || strPassword.isEmpty()) {
            btnSave.setEnabled(false);
        } else {
            if(checkEmailsMatch())
                btnSave.setEnabled(true);
        }
    }

    //utility method: used to ensure that new email and confirmation email matches
    private boolean checkEmailsMatch() {
        txtNewEmailLayout = findViewById(R.id.txtNewEmailConf);
        String strEmail = txtNewEmail.getText().toString().trim();
        String strEmailConf = txtNewEmailConfirm.getText().toString().trim();

        if(!strEmail.isEmpty() && !strEmailConf.isEmpty()){
            if(strEmail.equals(strEmailConf)){
                txtNewEmailLayout.setError(null);
                btnSave.setEnabled(true);
            }
            else{
                txtNewEmailLayout.setError("The confirmation email must match the new email");
                btnSave.setEnabled(false);
            }
        }

        return false;
    }
}