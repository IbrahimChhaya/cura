package Appointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import Appointment.ErrorPages.NoAppointmentPage;
import ChildList.ErrorPages.NoChildLinkedMain;
import PsychologistList.ErrorPages.NoPsychologistLinkedMain;
import PsychologistList.PsychologistList2;
import PsychologistList.PsychologistProfile;
import PsychologistList.nearbyPsychologistList;
import PsychologistList.psychologistListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabbedAppointmentMain extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    APIInterface  apiInterface;

    private CurrentAppointmentFragment currentAppointmentFragment;
    private UpcomingAppointmentFragment upcomingAppointmentFragment;
    private CancelledAppointmentFragment cancelledAppointmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_appointment_main);

        ImageView imgbackButton = (ImageView)findViewById(R.id.backButtonAppointment);
        Button btnAddBooking = (Button)findViewById(R.id.btnTabbedAddBooking);
        viewPager = (ViewPager) findViewById(R.id.psychViewpager);
        tabLayout = (TabLayout) findViewById(R.id.psychTab);

        currentAppointmentFragment = new CurrentAppointmentFragment();
        upcomingAppointmentFragment = new UpcomingAppointmentFragment();
        cancelledAppointmentFragment = new CancelledAppointmentFragment();


        tabLayout.setupWithViewPager(viewPager);
        viewPageAdapter vAdapter = new viewPageAdapter(getSupportFragmentManager(),0);
        vAdapter.addFragment(currentAppointmentFragment, "Past");
        vAdapter.addFragment(upcomingAppointmentFragment,"Upcoming");
        vAdapter.addFragment(cancelledAppointmentFragment,"Cancelled");
        viewPager.setAdapter(vAdapter);

        Intent intent = getIntent();
        if(intent.getStringExtra("CancelBookingMessage")!=null)
        {
            if(intent.getStringExtra("CancelBookingMessage").equals("CancelBooking"))
            {
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                tab.select();
            }else
            {
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            }
        }else
        {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }



        if(GlobalVariables.loggedInUser.getUserType().equals("Child"))
        {
            btnAddBooking.setVisibility(View.INVISIBLE);
        }


        imgbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalVariables.loggedInUser.getUserType().equals("Parent"))
                {
                    Intent i = new Intent(TabbedAppointmentMain.this, Guardian_Home.class);
                    startActivity(i);
                }else
                {
                    Intent i = new Intent(TabbedAppointmentMain.this, Child_Home.class);
                    startActivity(i);
                }

            }
        });

        btnAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if any psychologist is linked
                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                apiInterface.isLinkedToParent(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code()==200)
                        {
                            int ID = response.body();
                            if(ID>0)
                            {
                                //a link is present
                                Intent i = new Intent(TabbedAppointmentMain.this, PsychologistList2.class); //psychologistListView
                                startActivity(i);
                            }else if (ID==0)
                            {
                                //means that no psychologist exists
                                Intent i = new Intent(TabbedAppointmentMain.this, nearbyPsychologistList.class);
                                startActivity(i);
                            }else if(ID==-1)
                            {
                                Intent i = new Intent(TabbedAppointmentMain.this, NoChildLinkedMain.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });

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
}