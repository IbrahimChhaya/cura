package psychologistHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Account.UpdateGuardianAccount;
import PsychologistList.PsychologistInfoModel;
import ResourceHub.ResourceHubMain;
import StartUpStuff.NewStartUp;
import StartUpStuff.SignInRoles;
import psychologistHome.PracticeDetails.UpdatePracticeDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class psychologist_home extends AppCompatActivity {

    //declaring variables for card views
    CardView crdLogout;
    CardView crdViewAppointments;
    CardView crdResourceHub;
    CardView crdMyPatients;
    CardView crdReports;
    CardView crdAccount;
    CardView crdPracticeDetails;
    APIInterface apiInterface;
    GridLayout grdPsychHome;



    //psychologist image and name
    ImageView imgPsychProfile;
    TextView txtPsychName;

    int countAppointments = 0;
    int countPatients = 0;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(psychologist_home.this, NewStartUp.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_home);

        //setting up the card view
        crdLogout = findViewById(R.id.psychologist_logout);
        crdMyPatients = findViewById(R.id.psychologist_patients);
        crdReports = findViewById(R.id.psychologist_view_reports);
        crdResourceHub = findViewById(R.id.psychologist_resource_hub);
        crdViewAppointments = findViewById(R.id.psychologist_view_meetings);
        crdAccount = findViewById(R.id.psychologist_personal_info);
        crdPracticeDetails = findViewById(R.id.psychologist_practice_info);
        grdPsychHome = findViewById(R.id.psychologist_home_grid);


        //setting up personal detail xml attributes
        imgPsychProfile = findViewById(R.id.psychologistProfilePic);
        txtPsychName = findViewById(R.id.txtName);

        grdPsychHome.removeAllViews();
        grdPsychHome.setColumnCount(2);
        grdPsychHome.setRowCount(6);

        //setting psychologist image and name
        setDescription();
        getPatients();
        InstantiatePsychologist();

        //setting psychologists home




        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        crdLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(psychologist_home.this, NewStartUp.class);
                startActivity(i);
            }
        });

        crdResourceHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(psychologist_home.this, ResourceHubMain.class);
                i.putExtra("from", "psychHome");
                startActivity(i);
            }
        });

        crdAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(psychologist_home.this, UpdateGuardianAccount.class);
                i.putExtra("from", "psychHome");
                startActivity(i);

            }
        });
        crdReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(psychologist_home.this, GeneralReports.class);
                startActivity(i);

            }
        });

        crdViewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(psychologist_home.this, UpcomingAppointments.class);
                startActivity(i);

            }
        });

        crdMyPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(psychologist_home.this, PsychPatientList.class);
                startActivity(i);
            }
        });

        crdPracticeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(psychologist_home.this, UpdatePracticeDetails.class);
                startActivity(i);
            }
        });


        //setting the home page for the psychologists

        apiInterface.GetUpcomingBookings(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModelPsychologist>>() {
            @Override
            public void onResponse(Call<List<AppointmentModelPsychologist>> call, Response<List<AppointmentModelPsychologist>> response) {
                if(response.code()==200)
                {
                    for(AppointmentModelPsychologist appModel : response.body())
                    {
                        countAppointments++;
                    }

                    //get the patients
                    apiInterface.GetPsychologistPatients(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if(response.code()==200)
                            {
                                for(UserModel usr: response.body())
                                {
                                    countPatients++;
                                }

                                setPsychHome(countAppointments,countPatients);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModelPsychologist>> call, Throwable t) {

            }
        });




        //



    }


    public void setDescription()
    {
        //setting psychologist name and profile picture
        String GuardianName = GlobalVariables.loggedInUser.getName().trim();
        //GuardianName

        txtPsychName.setText(GuardianName);

        //setting the profile pic for the guardian********************************
        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        imgPsychProfile.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
        //setting the profile pic for the guardian end*****************************

    }

    public void getPatients()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.GetUpcomingBookings(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModelPsychologist>>() {
            @Override
            public void onResponse(Call<List<AppointmentModelPsychologist>> call, Response<List<AppointmentModelPsychologist>> response) {
                if(response.code()==200 && response.body()!=null)
                {
                    GlobalVariables.patientImages = new ArrayList<>();
                    GlobalVariables.patientNames = new ArrayList<>();
                    for(AppointmentModelPsychologist bModel: response.body())
                    {
                        //fetching the child
                        int pairID = (int)bModel.getPairID();
                        apiInterface.GetPairChild(pairID).enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                if(response.code()==200)
                                {
                                    GlobalVariables.patientImages.add(response.body().getProfilePicture());
                                    GlobalVariables.patientNames.add(response.body().getName());
                                }
                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModelPsychologist>> call, Throwable t) {

            }
        });
    }


    public void InstantiatePsychologist()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        apiInterface.getAllPsychologists().enqueue(new Callback<List<PsychologistInfoModel>>() {
            @Override
            public void onResponse(Call<List<PsychologistInfoModel>> call, Response<List<PsychologistInfoModel>> response) {
                if(response.code()==200)
                {
                    for(int x = 0 ; x < response.body().size();x++)
                    {
                        if(GlobalVariables.loggedInUser.getUserID() == response.body().get(x).getPsychID())
                        {
                            float fltUserID = (float) GlobalVariables.loggedInUser.getUserID();
                            float fltPsychID = (float) response.body().get(x).getPsychID();
                            GlobalVariables.LoggedInPsychologist = new PsychologistInfoModel(fltUserID,
                                    GlobalVariables.loggedInUser.getName(),GlobalVariables.loggedInUser.getEmail(),GlobalVariables.loggedInUser.getProfilePicture(),
                                    GlobalVariables.loggedInUser.getDateRegistered(),GlobalVariables.loggedInUser.getStatus(),fltPsychID,
                                    response.body().get(x).getAddress(),response.body().get(x).getQualification(),response.body().get(x).getRegNumber(),response.body().get(x).getDescription(),
                                    response.body().get(x).getSpeciality());

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PsychologistInfoModel>> call, Throwable t) {

            }
        });

    }


    public void setPsychHome(int numAppointments, int numPatients)
    {
        if(numAppointments==0 && numPatients==0)
        {
            grdPsychHome.addView(crdReports);
            grdPsychHome.addView(crdResourceHub);
            grdPsychHome.addView(crdAccount);
            grdPsychHome.addView(crdPracticeDetails);
            grdPsychHome.addView(crdLogout);
        }else if(numAppointments==0 && numPatients > 0 )
        {
            grdPsychHome.addView(crdMyPatients);
            grdPsychHome.addView(crdReports);
            grdPsychHome.addView(crdResourceHub);
            grdPsychHome.addView(crdAccount);
            grdPsychHome.addView(crdPracticeDetails);
            grdPsychHome.addView(crdLogout);
        }else if(numAppointments>0 && numPatients >0)
        {
            grdPsychHome.addView(crdViewAppointments);
            grdPsychHome.addView(crdMyPatients);
            grdPsychHome.addView(crdReports);
            grdPsychHome.addView(crdResourceHub);
            grdPsychHome.addView(crdAccount);
            grdPsychHome.addView(crdPracticeDetails);
            grdPsychHome.addView(crdLogout);
        }else
        {
            grdPsychHome.addView(crdViewAppointments);
            grdPsychHome.addView(crdMyPatients);
            grdPsychHome.addView(crdReports);
            grdPsychHome.addView(crdResourceHub);
            grdPsychHome.addView(crdAccount);
            grdPsychHome.addView(crdPracticeDetails);
            grdPsychHome.addView(crdLogout);
        }

    }





}