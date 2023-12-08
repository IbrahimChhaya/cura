package psychologistHome.PracticeDetails;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.textfield.TextInputEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.HashMap;

import PsychologistList.PsychologistInfoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDescription extends AppCompatActivity implements View.OnKeyListener {
    Button btnSave;
    TextInputEditText txtDescription;
    ImageView backBtn;
    APIInterface apiInterface;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                if (view.getId() == R.id.txtEditQualificationValue) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    View btnView = findViewById(R.id.btnSaveQualification);
                    btnView.performClick();
                }
                return true;
            }

        }
        return false; // pass on to other listeners.
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);

        txtDescription = findViewById(R.id.txtEditDescriptionValue);
        btnSave = findViewById(R.id.btnSaveDescription);
        backBtn = findViewById(R.id.imageView26);

        txtDescription.setText(GlobalVariables.LoggedInPsychologist.getDescription());

        txtDescription.addTextChangedListener(textWatcher);
        txtDescription.setOnKeyListener(this);
        checkFieldsForEmptyValues();

        backBtn.setOnClickListener(v -> finish());



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hashmap = new HashMap<>();
                hashmap.put("psychID", GlobalVariables.loggedInUser.getUserID());
                hashmap.put("address", GlobalVariables.LoggedInPsychologist.getAddress());
                hashmap.put("qualification", GlobalVariables.LoggedInPsychologist.getQualification());
                hashmap.put("regNumber", GlobalVariables.LoggedInPsychologist.getRegNumber());
                hashmap.put("description",txtDescription.getText().toString());
                hashmap.put("speciality",GlobalVariables.LoggedInPsychologist.getSpeciality());
                hashmap.put("status",GlobalVariables.LoggedInPsychologist.getStatus());

                apiInterface.updatePsychologistInfo("application/json; charset=utf-8", hashmap).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code()==200)
                        {
                            float fltID = (float)GlobalVariables.loggedInUser.getUserID();
                            GlobalVariables.LoggedInPsychologist = new PsychologistInfoModel(fltID,
                                    GlobalVariables.loggedInUser.getName(),GlobalVariables.loggedInUser.getEmail(),GlobalVariables.loggedInUser.getProfilePicture(),
                                    GlobalVariables.loggedInUser.getDateRegistered(),GlobalVariables.loggedInUser.getStatus(),fltID,
                                    GlobalVariables.LoggedInPsychologist.getAddress(),GlobalVariables.LoggedInPsychologist.getQualification(),GlobalVariables.LoggedInPsychologist.getRegNumber(),txtDescription.getText().toString(),
                                    GlobalVariables.LoggedInPsychologist.getSpeciality());

                            Intent i = new Intent(EditDescription.this,UpdatePracticeDetails.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }


    private  void checkFieldsForEmptyValues(){
        btnSave = findViewById(R.id.btnSaveDescription);

        String strName = txtDescription.getText().toString().trim();

        btnSave.setEnabled(!strName.isEmpty());
    }
}