package Account.Child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.ChildLogin;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import Account.EditDOB;
import Account.EditName;
import Account.EditProfilePicture;
import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateChildAccount extends AppCompatActivity {

    LinearLayout nameLayout;
    LinearLayout emailLayout;
    LinearLayout gradeLayout;
    LinearLayout dobLayout;
    LinearLayout passwordLayout;
    LinearLayout logoutLayout;
    LinearLayout removeLayout;

    TextView txtName;
    TextView txtEmail;
    TextView txtGrade;
    TextView txtDob;

    ImageView imgProfilePicture;
    FloatingActionButton editImage;
    ImageView backBtn;

    ProgressBar progressBar;
    TextView profileCompleted;
    APIInterface apiInterface;

    boolean passwordStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_child_account);

        nameLayout = findViewById(R.id.childNameLayout);
        emailLayout = findViewById(R.id.childEmailLayout);
        gradeLayout = findViewById(R.id.gradeLayout);
        dobLayout = findViewById(R.id.DOBLayout);
        passwordLayout = findViewById(R.id.childPasswordLayout);
        logoutLayout = findViewById(R.id.childLogout);
        removeLayout = findViewById(R.id.childRemoveAccount);

        progressBar = findViewById(R.id.profileCompletenessprogressBar2);
        profileCompleted = findViewById(R.id.profileCompleteness);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtGrade = findViewById(R.id.txtGrade);
        txtDob = findViewById(R.id.txtDOB);

        imgProfilePicture = findViewById(R.id.childProfilePic);
        editImage = findViewById(R.id.childFloatingActionButton);
        backBtn = findViewById(R.id.imageView23);

        txtName.setText(GlobalVariables.loggedInUser.getName());
        txtEmail.setText(GlobalVariables.loggedInUser.getEmail());

        String receivedPassword;
        Intent inten = getIntent();
        receivedPassword  = inten.getStringExtra("childPassHome");



      //  Intent intent2 = getIntent();

        if(GlobalVariables.loggedInUser.getDob() == null){
            txtDob.setText("Tell us when your birthday is");
        }else{
            txtDob.setText(GlobalVariables.loggedInUser.getDob().substring(0, 10));
        }

        if(GlobalVariables.loggedInUser.getGrade() == null){
            txtGrade.setText("Tell us what grade you're in");
        }else{
            txtGrade.setText(GlobalVariables.loggedInUser.getGrade()); //GlobalVariables.loggedInUser.getGrade()
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.accounttext);
        heading.setText("My Account");

        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        imgProfilePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), imageResource,getApplicationContext().getTheme()));

        imgProfilePicture.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateChildAccount.this, EditProfilePicture.class);
            startActivity(intent);
        });

        editImage.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateChildAccount.this, EditProfilePicture.class);
            startActivity(intent);
        });

        nameLayout.setOnClickListener(v -> {
            //redirect to name changing page
            Intent intent = new Intent(UpdateChildAccount.this, EditName.class);
            startActivity(intent);
        });

        emailLayout.setOnClickListener(v -> {
            //redirect to email changing screen
            //check if the user has text password or picture password
            Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
            apiInterface = retrofit.create(APIInterface.class);
            apiInterface.hasPicturePass(GlobalVariables.loggedInUser.getEmail()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.code()==200)
                    {
                        passwordStatus = response.body();
                        if(passwordStatus==false)
                        {
                            Intent intent = new Intent(UpdateChildAccount.this, EditChildEmail2.class);
                            startActivity(intent);
                        }else
                        {
                            Intent intent = new Intent(UpdateChildAccount.this, EditChildEmail.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });

        });

        gradeLayout.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
            apiInterface = retrofit.create(APIInterface.class);
            apiInterface.hasPicturePass(GlobalVariables.loggedInUser.getEmail()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.code()==200)
                    {
                        passwordStatus = response.body();
                        if(passwordStatus==false)
                        {
                            Intent intent = new Intent(UpdateChildAccount.this, EditGrade2.class);
                            startActivity(intent);
                        }else
                        {
                            Intent intent = new Intent(UpdateChildAccount.this, EditGrade.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });

        });

        dobLayout.setOnClickListener(v -> {
            //redirect to dob changing screen
            Intent intent = new Intent(UpdateChildAccount.this, EditDOB.class);
            startActivity(intent);
        });

        passwordLayout.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
            apiInterface = retrofit.create(APIInterface.class);
            apiInterface.hasPicturePass(GlobalVariables.loggedInUser.getEmail()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.code()==200)
                    {
                        passwordStatus = response.body();
                        if(passwordStatus==false)
                        {
                            Intent intent = new Intent(UpdateChildAccount.this, EditChildPassword2.class);
                            startActivity(intent);
                        }else
                        {
                            Intent intent = new Intent(UpdateChildAccount.this, EditChildPassword.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });

        logoutLayout.setOnClickListener(v -> {
            GlobalVariables.loggedInUser = null;

            //redirect to login
            Intent intent = new Intent(UpdateChildAccount.this, ChildLogInRedesign1.class);
            startActivity(intent);
        });



        /* Should kids be allowed to delete their accounts??? */
        removeLayout.setOnClickListener(v -> alertDialog());

        if((txtDob.getText().toString().equals("") || txtDob.getText().toString().equals("0"))&& (txtGrade.getText().toString().equals("") || txtGrade.getText().toString().equals("0"))){
            progressBar.setProgress(30);
        }else if((txtDob.getText().toString().equals("") || txtDob.getText().toString().equals("0")) && txtGrade.getText().toString() != ""){
            progressBar.setProgress(40);
        }else if((txtGrade.getText().toString().equals("") || txtGrade.getText().toString().equals("0"))&& txtDob.getText().toString() != ""){
            progressBar.setProgress(40);
        }else{
            progressBar.setProgress(50);
        }

        profileCompleted.setText("Profile " + (progressBar.getProgress() * 2) + "%");

        //backBtn.setOnClickListener(v -> finish());
        //redirecting back to child home
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateChildAccount.this, Child_Home.class);
                startActivity(i);
            }
        });

    }

    private void alertDialog() {
        AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.remove_account_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextInputEditText txtEmailValue = dialogView.findViewById(R.id.txtEmailValue);
        final TextInputLayout emailLayout = dialogView.findViewById(R.id.txtRemoveEmail);


        dialogBuilder.setTitle("Are you sure you want to delete your account?");
//        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton("OK",
                (dialog, which) -> {

                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            final Boolean[] closeDialog = {false};

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalVariables.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIInterface apiInterface = retrofit.create(APIInterface.class);

            // userID & Email
            HashMap<String, Object> map = new HashMap<>();

            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("email", txtEmailValue.getText().toString().trim());

            apiInterface.removeAccount("application/json; charset=utf-8", map).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.code() == 200){
                        if(response.body() == 0){
                            GlobalVariables.loggedInUser = null;

                            closeDialog[0] = true;
                            //redirect to login
                            Intent intent = new Intent(UpdateChildAccount.this, ChildLogin.class);
                            startActivity(intent);
                        }
                        else if(response.body() == -2){
                            emailLayout.setError("Incorrect email address provided");
                        }
                        else{
                            closeDialog[0] = true;
                            Toast.makeText(UpdateChildAccount.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        closeDialog[0] = true;
                        Toast.makeText(UpdateChildAccount.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    closeDialog[0] = true;
                    Toast.makeText(UpdateChildAccount.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

            if(closeDialog[0])
                alertDialog.dismiss();
        });
    }

    //function to check Password type
    public boolean checkPasswordType(String email)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        apiInterface.hasPicturePass(email).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==200)
                {
                    passwordStatus = response.body();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return passwordStatus;
    }


}