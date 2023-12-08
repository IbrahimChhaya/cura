package Account;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import psychologistHome.psychologist_home;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateGuardianAccount extends AppCompatActivity {
    APIInterface apiInterface;

    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(GlobalVariables.url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


    //new GUI
    ImageView profilePicture;
    LinearLayout nameLayout;
    TextView txtName;

    LinearLayout emailLayout;
    TextView txtEmail;

    LinearLayout dobLayout;
    TextView txtDob;

    LinearLayout passwordLayout;
    LinearLayout logoutLayout;
    LinearLayout removeAccount;

    FloatingActionButton editImage;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        apiInterface = retrofit.create(APIInterface.class);

        profilePicture = findViewById(R.id.imgProfilePic);
        editImage = findViewById(R.id.floatingActionButton);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtDob = findViewById(R.id.txtDOB);

        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        dobLayout = findViewById(R.id.DOBLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        logoutLayout = findViewById(R.id.Logout);
        removeAccount = findViewById(R.id.removeAccount);
        backButton = findViewById(R.id.imgBackButton);

        txtName.setText(GlobalVariables.loggedInUser.getName());
        txtEmail.setText(GlobalVariables.loggedInUser.getEmail());

        Intent i = getIntent();


        //String dob = GlobalVariables.loggedInUser.getDob().substring(0, 10);

        if(GlobalVariables.loggedInUser.getDob() != null)
            txtDob.setText(GlobalVariables.loggedInUser.getDob().substring(0, 10));
        else
            txtDob.setText("Tell us your birth date");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.accounttext);
        heading.setText("My Account");

        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        profilePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), imageResource,getApplicationContext().getTheme()));

        editImage.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateGuardianAccount.this, EditProfilePicture.class);
            startActivity(intent);
        });
        profilePicture.setOnClickListener(v -> {
            //redirect to name changing page
            Intent intent = new Intent(UpdateGuardianAccount.this, EditProfilePicture.class);
            startActivity(intent);
        });
        nameLayout.setOnClickListener(v -> {
            //redirect to name changing page
            Intent intent = new Intent(UpdateGuardianAccount.this, EditName.class);
            startActivity(intent);
        });

        emailLayout.setOnClickListener(v -> {
            //redirect to email changing screen
            Intent intent = new Intent(UpdateGuardianAccount.this, EditEmail.class);
            startActivity(intent);
        });

        dobLayout.setOnClickListener(v -> {
            //redirect to dob changing screen
            Intent intent = new Intent(UpdateGuardianAccount.this, EditDOB.class);
            startActivity(intent);
        });

        passwordLayout.setOnClickListener(v -> {
            //redirect to password changing screen
            Intent intent = new Intent(UpdateGuardianAccount.this, EditPassword.class);
            startActivity(intent);
        });

        logoutLayout.setOnClickListener(v -> {
            GlobalVariables.loggedInUser = null;

            //redirect to login
            Intent intent = new Intent(UpdateGuardianAccount.this, Login.class);
            startActivity(intent);
        });

        removeAccount.setOnClickListener(v -> alertDialog());

        //backButton.setOnClickListener(v -> finish());
        //redirecting back to the home page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i.getStringExtra("from").equals("guardianHome"))
                {
                    Intent i = new Intent(UpdateGuardianAccount.this, Guardian_Home.class);
                    startActivity(i);
                }else if(i.getStringExtra("from").equals("psychHome"))
                {
                    Intent i = new Intent(UpdateGuardianAccount.this, psychologist_home.class);
                    startActivity(i);
                }


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
                            Intent intent = new Intent(UpdateGuardianAccount.this, Login.class);
                            startActivity(intent);
                        }
                        else if(response.body() == -2){
                            emailLayout.setError("Incorrect email address provided");
                        }
                        else{
                            closeDialog[0] = true;
                            Toast.makeText(UpdateGuardianAccount.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(UpdateGuardianAccount.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(UpdateGuardianAccount.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

            if(closeDialog[0])
                alertDialog.dismiss();
        });


    }
}