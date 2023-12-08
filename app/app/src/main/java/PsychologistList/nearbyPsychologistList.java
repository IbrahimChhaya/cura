package PsychologistList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class nearbyPsychologistList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;

    RecyclerView recyclerView;
    nearbyPsychAdapter adapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    String[] titles;
    String[] contents;
    int count = 0;

    ArrayList<String> psychTitles = new ArrayList<>();
    ArrayList<String> psychContents = new ArrayList<>();
    ArrayList<String> psychIds = new ArrayList<>();
    ArrayList<String> psychDescription = new ArrayList<>();
    ArrayList<String> psychAddress = new ArrayList<>();

    private static final String TAG = "Debugging";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_psychologist_list);

        ImageView imgBackButton = (ImageView)findViewById(R.id.gobackglobalbutton);



        //fetching all the psychologists

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.globalToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.globaltoolbarText);
        heading.setText("Select a Psychologist");


        //toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
       // toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
       // drawer.addDrawerListener(toggle);
       // toggle.setDrawerIndicatorEnabled(true);
       // toggle.syncState();

        apiInterface.getAllPsychologists().enqueue(new Callback<List<PsychologistInfoModel>>() {
            @Override
            public void onResponse(Call<List<PsychologistInfoModel>> call, Response<List<PsychologistInfoModel>> response) {
                if (response.code() == 200) {

                    Log.d(TAG, "onResponsecode: " + response.code());
                    for (PsychologistInfoModel mod : response.body()) {

                        psychTitles.add(mod.getName());
                        psychContents.add(mod.getQualification());
                        int psyID = ((int) mod.getPsychID());
                        psychIds.add(String.valueOf(psyID));
                        psychDescription.add(mod.getDescription());
                        psychAddress.add(mod.getAddress());

                    }

                   // Toast.makeText(nearbyPsychologistList.this,"itemsize" + psychTitles.size(),Toast.LENGTH_SHORT).show();
                    String[] titles = GetStringArray(psychTitles);//{"Psychologist 1", "Psychologist 2" , "Psychologist3"};//getResources().getStringArray(R.array.stories_title);
                    String[] contents = GetStringArray(psychContents);//{"Description 1", "Description 2" , "Description 3"}; //getResources().getStringArray(R.array.story_content);
                    String[] IDs = GetStringArray(psychIds);
                    String[] Descriptions = GetStringArray(psychDescription);
                    String[] Addresses = GetStringArray(psychAddress);
                    recyclerView = findViewById(R.id.storiesListsView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(nearbyPsychologistList.this));
                    adapter = new nearbyPsychAdapter(nearbyPsychologistList.this, titles, contents, IDs,Descriptions,Addresses); // our adapter takes two string array
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<PsychologistInfoModel>> call, Throwable t) {

            }
        });

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* String[] titles = GetStringArray(psychContents);//{"Psychologist 1", "Psychologist 2" , "Psychologist3"};//getResources().getStringArray(R.array.stories_title);
        String[] contents = GetStringArray(psychTitles);//{"Description 1", "Description 2" , "Description 3"}; //getResources().getStringArray(R.array.story_content);

        recyclerView = findViewById(R.id.storiesListsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,titles,contents); // our adapter takes two string array
        recyclerView.setAdapter(adapter);*/


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
          //  Toast.makeText(this, "Home btn Clicked.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public static String[] GetStringArray(ArrayList<String> arr) {

        // declaration and initialise String Array
        int size = arr.size();
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }
}