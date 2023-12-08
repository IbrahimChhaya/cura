package psychologistHome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ChildReports.ChildReportsMain;
import ChildReports.MoodFragment;
import ChildReports.ScheduleFragment;
import ResourceHub.ResourceHubModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientReport extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MoodFragment moodFragment;
    private ScheduleFragment scheduleFragment;
    private PatientUpcomingAppointmentFragment patientUpcomingAppointmentFragment;
    private ImageView imgProfilePic;
    private TextView patientName;
    private ChipGroup chipGroup;
    private ImageView imgBackButton;
    private NoPatientAppointmentFragment noPatientAppointmentFragment;

    APIInterface apiInterface;
    ArrayList<String> focusPoints = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_report);
        imgProfilePic = findViewById(R.id.imgPatient);
        patientName = findViewById(R.id.txtPatientName);
        chipGroup = findViewById(R.id.chipGroup);
        imgBackButton = findViewById(R.id.reportBackButton);



        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        moodFragment = new MoodFragment();
        scheduleFragment = new ScheduleFragment();
        patientUpcomingAppointmentFragment = new PatientUpcomingAppointmentFragment();
        noPatientAppointmentFragment = new NoPatientAppointmentFragment();


        //getting the id from the childListRedesign class
        Intent i = getIntent();
        int ChildID = i.getIntExtra("ChildID",0);
        String profilePic = i.getStringExtra("ChildImage");

        //setting the profile picture of the patient
        String imageUrl = "@drawable/" + profilePic;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        imgProfilePic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));


        patientName.setText(i.getStringExtra("ChildName"));


        //fetching childs focus points
        apiInterface.fetchChildFocusPoints(ChildID).enqueue(new Callback<List<ResourceHubModel>>() {
            @Override
            public void onResponse(Call<List<ResourceHubModel>> call, Response<List<ResourceHubModel>> response) {
                if(response.code()==200)
                {
                    for(ResourceHubModel rhub : response.body())
                    {
                        Random rnd = new Random();

                        int color = Color.parseColor(rhub.getColour());//Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        Chip chip = new Chip(chipGroup.getContext());
                        chip.setText(rhub.getProblem());
                        chip.setChipStrokeWidth(2f);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(Color.WHITE));
                        chip.setChipStrokeColor(ColorStateList.valueOf(color));
                        chip.setTextColor(ColorStateList.valueOf(color));
                        chipGroup.addView(chip);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResourceHubModel>> call, Throwable t) {

            }
        });



        //sending the ChildID to the fragments
        Bundle b = new Bundle();
        b.putInt("ChildID", ChildID);
        moodFragment.setArguments(b);
        scheduleFragment.setArguments(b);
        patientUpcomingAppointmentFragment.setArguments(b);



        viewPager = (ViewPager) findViewById(R.id.statsViewPager);
        tabLayout = (TabLayout) findViewById(R.id.reportsTab);

        tabLayout.setupWithViewPager(viewPager);
        viewPageAdapter vAdapter = new viewPageAdapter(getSupportFragmentManager(),0);
        vAdapter.addFragment(moodFragment, "Mood");
        vAdapter.addFragment(scheduleFragment,"Attendance");

        apiInterface.getAllUpcomingBookings3(ChildID).enqueue(new Callback<List<AppointmentModelPsychologist2>>() {
            @Override
            public void onResponse(Call<List<AppointmentModelPsychologist2>> call, Response<List<AppointmentModelPsychologist2>> response) {
                if(response.code()==200)
                {
                        vAdapter.addFragment(patientUpcomingAppointmentFragment,"Appointments");
                }else
                {
                    vAdapter.addFragment(noPatientAppointmentFragment,"Appointments");
                }

                viewPager.setAdapter(vAdapter);
            }

            @Override
            public void onFailure(Call<List<AppointmentModelPsychologist2>> call, Throwable t) {

            }
        });








        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    //viewpage adapter
    public class viewPageAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();


        public viewPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment frag, String title)
        {
            fragments.add(frag);
            fragmentTitles.add(title);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}