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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditGrade2 extends AppCompatActivity {
    TextInputLayout textInputLayout;
    TextInputEditText txtPassword;
    Button btnSave;
    TextInputEditText txtNewGrade;
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
        setContentView(R.layout.activity_edit_grade2);
        textInputLayout = findViewById(R.id.childGradePassword);
        txtPassword = findViewById(R.id.txtChildTextPassword);
        txtNewGrade = findViewById(R.id.txtNewGrade);
        btnSave = findViewById(R.id.btnSaveGrade);
        backBtn = findViewById(R.id.imageView22);

        backBtn.setOnClickListener(v -> finish());

        txtNewGrade.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);
        changeButtonVisibility();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtPassword.getText().toString();
                if(password.equals(""))
                {
                    textInputLayout.setError("PLease enter your password");
                }else
                {
                    HashMap<String, Object> map = new HashMap<>();

                    //name email dob profilePicture
                    map.put("userID", GlobalVariables.loggedInUser.getUserID());
                    map.put("oldPassword", password);
                    map.put("newName", GlobalVariables.loggedInUser.getName());
                    map.put("newEmail", GlobalVariables.loggedInUser.getEmail());
                    map.put("newDOB", GlobalVariables.loggedInUser.getDob());
                    map.put("newProfilePicture", GlobalVariables.loggedInUser.getProfilePicture());
                    map.put("grade", txtNewGrade.getText().toString().trim());

                    apiInterface.UpdateGuardianEmail("application/json; charset=utf-8", map).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if(response.code() == 200){
                                JSONObject jsonObject;
                                try {
                                    jsonObject = new JSONObject(new Gson().toJson(response.body()));

                                    if(jsonObject.getInt("userID") > 0){
                                        GlobalVariables.loggedInUser = new UserModel(jsonObject);

                                        Intent intent = new Intent(EditGrade2.this, UpdateChildAccount.class);
                                        startActivity(intent);
                                    }
                                    else if(jsonObject.getInt("userID") == -2){
//                                    txtIncorrectPassword.setVisibility(View.VISIBLE);
//                                    errorLayout.setError("Incorrect Password");
                                        Toast.makeText(EditGrade2.this, "Wrong password", Toast.LENGTH_LONG).show();

                                    }
                                    else if(jsonObject.getInt("userID") == 0){
                                        Toast.makeText(EditGrade2.this, "User not found", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(EditGrade2.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(EditGrade2.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(EditGrade2.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });



    }

    private  void changeButtonVisibility(){
        btnSave = findViewById(R.id.btnSaveGrade);
        txtNewGrade= findViewById(R.id.txtNewGrade);
        txtPassword = findViewById(R.id.txtChildTextPassword);
        String strNewGrade = txtNewGrade.getText().toString().trim();
        String strPassword = txtPassword.getText().toString().trim();

        if(!strNewGrade.isEmpty() && !strPassword.isEmpty())
        {
            btnSave.setEnabled(true);
        }else
        {
            btnSave.setEnabled(false);
        }

    }
}