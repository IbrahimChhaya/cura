package PsychologistList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import MapLocation.NearestPsychologistMap;
import MapLocation.psychMapFragment;
import MapLocation.psych_review_fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PsychologistProfile extends AppCompatActivity {



    private static final String TAG = "Testing for coordinates" ;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    APIInterface apiInterface;

    private psychMapFragment psychMap;
    private TabItem aboutItem ;
    private TabItem reviewItem;
    private Toolbar toolBar;

    private psych_review_fragment reviewFragment;
    TextView txtPsychname;
    ImageView btnBackButton;
    ImageView imgProfilePic;

    public static String myAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_profile);

        btnBackButton = findViewById(R.id.imgBackButton);
        viewPager = (ViewPager) findViewById(R.id.psychViewpager);
        tabLayout = (TabLayout) findViewById(R.id.psychTab);
        imgProfilePic= findViewById(R.id.imgPsychProfile);

        txtPsychname = (TextView)findViewById(R.id.txtPsychName);
        TextView txtDegree = (TextView)findViewById(R.id.txtProfession);

        txtPsychname.setText(GlobalVariables.psychologistName);
        txtDegree.setText(GlobalVariables.psychologistDegree);

        //setting profile pic of psychologist


/*        String imageName = GlobalVariables.loggedInUser.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
        String imageUrl = "@drawable/" + imageName;
        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
        imgGuardianProfilePic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));*/




        psychMap = new psychMapFragment();
        reviewFragment = new psych_review_fragment();

        tabLayout.setupWithViewPager(viewPager);
        viewPageAdapter vAdapter = new viewPageAdapter(getSupportFragmentManager(),0);
        vAdapter.addFragment(psychMap, "About");
        vAdapter.addFragment(reviewFragment,"Review");
        viewPager.setAdapter(vAdapter);
        getLocationCoordinates(GlobalVariables.psychAddress);

        Log.d(TAG, "onCreategetpsychologist Coordinates: " + GlobalVariables.Coordinates + GlobalVariables.psychAddress);

        //getting the rating for the psychologist
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        //getting the rating for the psychologist
        apiInterface.GetPsychologistRating(GlobalVariables.psychologistID).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.code()==200)
                {
                    double dblRating = response.body();
                    float fltRating = (float)dblRating;

                    ratingBar.setRating(fltRating);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });


        //getting the psychologist profile
        apiInterface.getAllUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.code()==200)
                {
                    for(UserModel u : response.body())
                    {
                        if(u.getUserID()==GlobalVariables.psychologistID)
                        {
                            String imageName = u.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                            String imageUrl = "@drawable/" + imageName;
                            int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                            imgProfilePic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });

        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



       //  myAdapter adt = new myAdapter();

       //  reviewFragment.setArguments(listGName,listRating);



    }

    public void getLocationCoordinates(String address)
    {
        ArrayList<Double> result = new ArrayList<>();
        GeoLocation geolocation = new GeoLocation();
        geolocation.getAddress(address,getApplicationContext(),new GeoHandler());
    }

    private class GeoHandler extends Handler
    {
        private static final String TAG = " Checking Coordinates" ;
        public String myAddress = "";

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String address;
            switch(msg.what)
            {
                case 1 :
                    Bundle bundle = msg.getData();
                    address= bundle.getString("address");
                    break;
                default:
                    address = null;
            }
            // txtPsychname.setText(address);
             myAddress = address;
            //GlobalVariables.Coordinates = address;
        }
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

//    public class myAdapterReview extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return listGName.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            convertView = getLayoutInflater().inflate(R.layout.review_rating, parent, false);
//            RatingBar rtBar = (RatingBar) convertView.findViewById(R.id.ratingBarReview);
//            TextView guardName = (TextView) convertView.findViewById(R.id.reviewGuardianName);
//
//            CardView card = convertView.findViewById(R.id.childCard);
//
//            rtBar.setRating(Integer.valueOf(listRating.get(position)));
//            guardName.setText(listGName.get(position));
//
//
//           // ListView myview = convertView.findViewById(R.id.childListview);
//
//
//
//            return convertView;
//
//
//
//
//
//        }
//    }


}