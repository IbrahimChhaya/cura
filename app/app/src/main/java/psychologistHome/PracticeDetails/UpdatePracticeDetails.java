package psychologistHome.PracticeDetails;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import StartUpStuff.NewStartUp;
import psychologistHome.psychologist_home;

public class UpdatePracticeDetails extends AppCompatActivity {

    private TextView txtQualification;
    private TextView txtDescription;
    private TextView txtSpeciality;
    private TextView txtRegNumber;

    private LinearLayout layoutQualification;
    private LinearLayout layoutDescription;
    private LinearLayout layoutSpeciality;
    private LinearLayout layoutRegNumber;
    private ImageView imgBackButton;



    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(UpdatePracticeDetails.this, psychologist_home.class);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_practice_details);

        txtQualification = findViewById(R.id.txtQualification);
        txtDescription = findViewById(R.id.txtDescription);
        txtSpeciality = findViewById(R.id.txtSpeciality);
        txtRegNumber = findViewById(R.id.txtRegNumber);

        layoutDescription = findViewById(R.id.DescriptionLayout);
        layoutQualification = findViewById(R.id.QualificationLayout);
        layoutRegNumber = findViewById(R.id.RegistrationNumberLayout);
        layoutSpeciality = findViewById(R.id.SpecialityLayout);
        imgBackButton = findViewById(R.id.imgBackButton);


        txtQualification.setText(GlobalVariables.LoggedInPsychologist.getQualification().toString());
        txtSpeciality.setText(GlobalVariables.LoggedInPsychologist.getSpeciality().toString());
        txtRegNumber.setText(GlobalVariables.LoggedInPsychologist.getRegNumber());
        txtDescription.setText(GlobalVariables.LoggedInPsychologist.getDescription());


        layoutQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdatePracticeDetails.this,EditQualification.class);
                startActivity(i);
            }
        });
        layoutDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdatePracticeDetails.this,EditDescription.class);
                startActivity(i);
            }
        });

        layoutSpeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdatePracticeDetails.this, EditSpeciality.class);
                startActivity(i);
            }
        });

        layoutRegNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdatePracticeDetails.this, EditRegistrationNumber.class);
                startActivity(i);
            }
        });


        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdatePracticeDetails.this, psychologist_home.class);
                startActivity(i);

            }
        });

    }
}