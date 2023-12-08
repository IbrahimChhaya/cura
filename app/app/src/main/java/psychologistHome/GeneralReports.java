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
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ChildReports.ChildReportsMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeneralReports extends AppCompatActivity {
    private APIInterface apiInterface;
    private BookingsGeneralFragment bookingsFragment;
    private PatientAttendanceFragment patientAttendanceFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imgBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_reports);
        GlobalVariables.monthBookingCount = new ArrayList<>();
        imgBackButton = findViewById(R.id.imgBackButton);


        bookingsFragment = new BookingsGeneralFragment();
        patientAttendanceFragment = new PatientAttendanceFragment();


        viewPager = (ViewPager) findViewById(R.id.statsViewPager);
        tabLayout = (TabLayout) findViewById(R.id.reportsTab);

        tabLayout.setupWithViewPager(viewPager);
        viewPageAdapter vAdapter = new viewPageAdapter(getSupportFragmentManager(),0);
        vAdapter.addFragment(bookingsFragment, "Monthly Bookings");
        vAdapter.addFragment(patientAttendanceFragment,"Monthly Attendance");


        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getBookingsCountsPerMonth(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<MonthBookingCount>>() {
            @Override
            public void onResponse(Call<List<MonthBookingCount>> call, Response<List<MonthBookingCount>> response) {
                if(response.code()==200)
                {
                    for(MonthBookingCount mnth : response.body())
                    {

                        GlobalVariables.monthBookingCount.add(mnth);
                    }

                    viewPager.setAdapter(vAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<MonthBookingCount>> call, Throwable t) {

            }
        });
        getPirChartData();


        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


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


    public void getPirChartData()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getNumEachBookingPerPsych(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<numAttendedCancelledMissed>() {
            @Override
            public void onResponse(Call<numAttendedCancelledMissed> call, Response<numAttendedCancelledMissed> response) {
                if(response.code()==200)
                {
                     GlobalVariables.globalMissed = response.body().getMissed();
                    GlobalVariables.globalCancelled = response.body().getCancelled();
                    GlobalVariables.globalAttended = response.body().getAttended();

                }
            }

            @Override
            public void onFailure(Call<numAttendedCancelledMissed> call, Throwable t) {

            }
        });
    }
}