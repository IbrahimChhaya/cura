package Account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Account.Child.UpdateChildAccount;
import ResourceHub.RHubDescription;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditDOB extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();
    TextInputEditText txtDob;
    ProgressBar progressBar;
    TextView proCompleted;

    Button btnSave;
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
        setContentView(R.layout.activity_edit_dob);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        txtDob = findViewById(R.id.txtEditBirthday);
        btnSave = findViewById(R.id.btnSaveDOB);
        backBtn = findViewById(R.id.imageView24);

        progressBar = findViewById(R.id.profileCompletenessprogressBar2);
        proCompleted = findViewById(R.id.profileCompleteness);

        txtDob.setText("Select your birth date");
        if(GlobalVariables.loggedInUser.getDob() != null)
            txtDob.setText(GlobalVariables.loggedInUser.getDob().substring(0, 10));

        txtDob.addTextChangedListener(textWatcher);
        changeButtonVisibility();

        txtDob.setInputType(InputType.TYPE_NULL);
        txtDob.setKeyListener(null);

        //String strDob = GlobalVariables.loggedInUser.getDob().substring(0, 10).replace("-", "/");
        Date dob = null;
        try {
            String strDob = " ";
            if(GlobalVariables.loggedInUser.getDob() != null)
                strDob = GlobalVariables.loggedInUser.getDob().substring(0, 10).replace("-", "/");

            Date todayDate = Calendar.getInstance().getTime();


            if(strDob != " ")
                dob = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(strDob);
            else{
                dob = todayDate;
            }

            myCalendar.setTime(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //setting up the date picker
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        //allowing the date picker to pop up on edit text click
        txtDob.setOnClickListener(v -> new DatePickerDialog(EditDOB.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        txtDob.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                // Show your calender here
                new DatePickerDialog(EditDOB.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            } else {
                // Hide your calender here
                new DatePickerDialog(EditDOB.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).hide();
            }
        });

        btnSave.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();

            //name email dob profilePicture
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("newName", GlobalVariables.loggedInUser.getName());
            map.put("newDOB", txtDob.getText().toString().trim());
            map.put("newProfilePicture", GlobalVariables.loggedInUser.getProfilePicture());

            apiInterface.updateAccount("application/json; charset=utf-8", map).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("userID") > 0){
                                GlobalVariables.loggedInUser = new UserModel(jsonObject);

                                if(GlobalVariables.loggedInUser.getUserType().equals("Parent")){
                                    Intent intent = new Intent(EditDOB.this, UpdateGuardianAccount.class);
                                    startActivity(intent);
                                }
                                else if(GlobalVariables.loggedInUser.getUserType().equals("Child")){
                                    Intent intent = new Intent(EditDOB.this, UpdateChildAccount.class);
                                    intent.putExtra( "dob", txtDob.getText().toString());
                                    startActivity(intent);
                                }
                            }
                            else if(jsonObject.getInt("userID") == 0){
                                Toast.makeText(EditDOB.this, "User not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(EditDOB.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditDOB.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(EditDOB.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        backBtn.setOnClickListener(v -> finish());
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String formattedDate = sdf.format(myCalendar.getTime());
        String finalDate = formattedDate.replace("/","-");
        txtDob.setText(finalDate);
    }

    //Utility method: used to check if input fields are empty - in order to control button availability
    private void changeButtonVisibility(){
        btnSave = findViewById(R.id.btnSaveDOB);
        txtDob = findViewById(R.id.txtEditBirthday);

        String strEmail = txtDob.getText().toString().trim();

        btnSave.setEnabled(!strEmail.isEmpty());
    }
}