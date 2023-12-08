package ChildReports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.tabs.TabLayout;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Appointment.TabbedAppointmentMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChildReportsMain extends AppCompatActivity {

    private LineChart mChart;
    CustomCalendar customCaldr;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MoodFragment moodFragment;
    private ScheduleFragment scheduleFragment;
    private FeedBackFragment feedBackFragment;
    private ErrorFragment errorFragment;
    private recommendationFragment recFragment;
    private APIInterface apiInterface;
    int feedBackCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_reports_main);

        ImageView imgBackButton = findViewById(R.id.imgBackButton);

        moodFragment = new MoodFragment();
        scheduleFragment = new ScheduleFragment();
        feedBackFragment = new FeedBackFragment();
        errorFragment = new ErrorFragment();
        recFragment = new recommendationFragment();

        //getting the id from the childListRedesign class
        Intent i = getIntent();
        int ChildID = i.getIntExtra("ChildID",0);

        //sending the ChildID to the fragments
        Bundle b = new Bundle();
        b.putInt("ChildID", ChildID);
        moodFragment.setArguments(b);
        scheduleFragment.setArguments(b);
        feedBackFragment.setArguments(b);
        recFragment.setArguments(b);







        viewPager = (ViewPager) findViewById(R.id.statsViewPager);
        tabLayout = (TabLayout) findViewById(R.id.reportsTab);

        tabLayout.setupWithViewPager(viewPager);
        viewPageAdapter vAdapter = new viewPageAdapter(getSupportFragmentManager(),0);
        vAdapter.addFragment(moodFragment, "Mood");
        vAdapter.addFragment(scheduleFragment,"Attendance");

        //check if feedback exists
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.fetchAllChildsNotesByID(ChildID).enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                if(response.code()==200)
                {
                    for(Notes n : response.body())
                    {
                        if(n.getType().equals("Feedback"))
                        {
                            feedBackCount++;
                        }
                    }
                    if(feedBackCount > 0)
                    {
                        vAdapter.addFragment(feedBackFragment,"Feedback");
                        vAdapter.addFragment(recFragment,"Recommendations");
                    }else
                    {
                        vAdapter.addFragment(errorFragment,"Feedback");
                        vAdapter.addFragment(recFragment,"Recommendations");
                    }

                    viewPager.setAdapter(vAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {

            }
        });




     /*   vAdapter.addFragment(feedBackFragment,"Feedback");

        viewPager.setAdapter(vAdapter);*/


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

}