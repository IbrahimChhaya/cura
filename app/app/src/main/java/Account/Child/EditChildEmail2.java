package Account.Child;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditChildEmail2 extends AppCompatActivity {
    Button btnSave;
    TextInputEditText txtNewEmail;
    ImageView backBtn;

    TextInputLayout txtNewEmailLayout , txtChildEmailPassword;
    TextInputEditText txtNewEmailConfirm;
    TextInputEditText txtPassword;
    APIInterface apiInterface;

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
        setContentView(R.layout.activity_edit_child_email2);

        btnSave = findViewById(R.id.btnSaveChildEmail);
        TextView txtCurrentEmail = findViewById(R.id.txtChildCurrentEmail);
        txtNewEmail = findViewById(R.id.txtNewChildEmailValue);
        txtNewEmailConfirm = findViewById(R.id.txtNewChildEmailConfirm);
        backBtn = findViewById(R.id.imageView20);
        txtPassword = findViewById(R.id.txtChildTextPassword);
        txtChildEmailPassword = findViewById(R.id.childEmailPassword);

        txtNewEmailLayout = findViewById(R.id.childConfEmail);

        txtCurrentEmail.setText(GlobalVariables.loggedInUser.getEmail());

        //add text listeners to edit texts
        txtNewEmail.addTextChangedListener(textWatcher);
        txtNewEmailConfirm.addTextChangedListener(textWatcher);
        changeButtonVisibility();


        //back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtPassword.getText().toString();
                if(password.equals(""))
                {
                    txtChildEmailPassword.setError("Please enter your password");
                }else
                {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(GlobalVariables.url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APIInterface apiInterface = retrofit.create(APIInterface.class);

                    HashMap<String, Object> map = new HashMap<>();

                    //name email dob profilePicture
                    map.put("userID", GlobalVariables.loggedInUser.getUserID());
                    map.put("oldPassword", password);
                    map.put("newName", GlobalVariables.loggedInUser.getName());
                    map.put("newEmail", txtNewEmail.getText().toString().trim());
                    map.put("newDOB", GlobalVariables.loggedInUser.getDob());
                    map.put("newProfilePicture", GlobalVariables.loggedInUser.getProfilePicture());
                    map.put("grade", GlobalVariables.loggedInUser.getGrade());

                    apiInterface.UpdateGuardianEmail("application/json; charset=utf-8", map).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if(response.code() == 200){
                                JSONObject jsonObject;
                                try {
                                    jsonObject = new JSONObject(new Gson().toJson(response.body()));

                                    if(jsonObject.getInt("userID") > 0){
                                        GlobalVariables.loggedInUser = new UserModel(jsonObject);
                                        Intent intent = new Intent(EditChildEmail2.this, UpdateChildAccount.class);
                                        startActivity(intent);
                                    }
                                    else if(jsonObject.getInt("userID") == -2){
//                                    txtIncorrectPassword.setVisibility(View.VISIBLE);
//                                    errorLayout.setError("Incorrect Password");
                                        Toast.makeText(EditChildEmail2.this, "Wrong password", Toast.LENGTH_LONG).show();

                                    }
                                    else if(jsonObject.getInt("userID") == -3){
                                        txtNewEmailLayout.setError("This email address is already in use.");
                                    }
                                    else if(jsonObject.getInt("userID") == 0){
                                        Toast.makeText(EditChildEmail2.this, "User not found", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(EditChildEmail2.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(EditChildEmail2.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(EditChildEmail2.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }


            }
        });



    }


    private  void changeButtonVisibility(){
        btnSave = findViewById(R.id.btnSaveChildEmail);
        txtNewEmail = findViewById(R.id.txtNewChildEmailValue);
        txtNewEmailConfirm = findViewById(R.id.txtNewChildEmailConfirm);

        txtNewEmailLayout.setError(null);
        String strEmail = txtNewEmail.getText().toString().trim();
        String strEmailConf = txtNewEmailConfirm.getText().toString().trim();

        if (strEmail.isEmpty() || strEmailConf.isEmpty()) {
            btnSave.setEnabled(false);
        } else {
            if(checkEmailsMatch())
                btnSave.setEnabled(true);
        }
    }

    private boolean checkEmailsMatch() {
        txtNewEmailLayout = findViewById(R.id.childConfEmail);
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