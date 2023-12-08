package Account;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Account.Child.UpdateChildAccount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditName extends AppCompatActivity implements View.OnKeyListener {
    Button btnSave;
    TextInputEditText txtName;
    ImageView backBtn;

    //used to watch whether an EditText has been changed
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
                if (view.getId() == R.id.txtEditNameValue) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    View btnView = findViewById(R.id.btnSaveName);
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
        setContentView(R.layout.activity_edit_name);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        txtName = findViewById(R.id.txtEditNameValue);
        btnSave = findViewById(R.id.btnSaveName);
        backBtn = findViewById(R.id.imageView26);

        txtName.setText(GlobalVariables.loggedInUser.getName());

        txtName.addTextChangedListener(textWatcher);
        txtName.setOnKeyListener(this);
        checkFieldsForEmptyValues();

        backBtn.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();

            //name email dob profilePicture
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("newName", txtName.getText().toString().trim());
            map.put("newDOB", GlobalVariables.loggedInUser.getDob());
            map.put("newProfilePicture", GlobalVariables.loggedInUser.getProfilePicture());


            apiInterface.updateAccount("application/json; charset=utf-8", map).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.code() ==200){
                        JSONObject gsonObject;
                        try {
                            gsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(gsonObject.getInt("userID") > 0){
                                GlobalVariables.loggedInUser = new UserModel(gsonObject);

                                if(GlobalVariables.loggedInUser.getUserType().equals("Parent")){
                                    Intent intent = new Intent(EditName.this, UpdateGuardianAccount.class);
                                    startActivity(intent);
                                }
                                else if(GlobalVariables.loggedInUser.getUserType().equals("Child")){
                                    Intent intent = new Intent(EditName.this, UpdateChildAccount.class);
                                    startActivity(intent);
                                }


                            }
                            else if(gsonObject.getInt("userID") == 0){
                                Toast.makeText(EditName.this, "User not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(EditName.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditName.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

        });

    }

    //Utility method: used to check if input fields are empty - in order to control button availability
    private  void checkFieldsForEmptyValues(){
        btnSave = findViewById(R.id.btnSaveName);

        String strName = txtName.getText().toString().trim();

        btnSave.setEnabled(!strName.isEmpty());
    }
}