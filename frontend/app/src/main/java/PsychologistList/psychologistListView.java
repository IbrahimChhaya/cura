package PsychologistList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ChildList.ErrorPages.NoChildLinkedMain;
import PsychologistList.ErrorPages.NoPsychologistLinkedMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class psychologistListView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    APIInterface apiInterface;

    RecyclerView recyclerView;
    Adapter adapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    String[] titles;
    String[] contents;
    int count = 0;

    ArrayList<String> psychTitles = new ArrayList<>();
    ArrayList<String> psychContents = new ArrayList<>();
    ArrayList<String> psychIds  = new ArrayList<>();

    private static final String TAG = "Debugging";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_list_view);

        ImageView imgBackButton = (ImageView)findViewById(R.id.gobackglobalbutton);

        //fetching all the psychologists

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.globalToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.globaltoolbarText);
        heading.setText("Select a Psychologist");


        TextView toolbar = (TextView)findViewById(R.id.toolbar);


        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
       // toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
       // drawer.addDrawerListener(toggle);
      //  toggle.setDrawerIndicatorEnabled(true);
      //  toggle.syncState();

        apiInterface.getAllPsychologists().enqueue(new Callback<List<PsychologistInfoModel>>() {
            @Override
            public void onResponse(Call<List<PsychologistInfoModel>> call, Response<List<PsychologistInfoModel>> response) {
                if(response.code()==200)
                {

                    Log.d(TAG, "onResponsecode: " + response.code());
                    for(PsychologistInfoModel mod : response.body())
                    {

                        //put Globalvariables.useriD
                        apiInterface.isLinkedToParent(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if(response.code()==200)
                                {
                                    int ID = response.body();

                                   // Toast.makeText(psychologistListView.this, "Home btn Clicked." + ID, Toast.LENGTH_SHORT).show();
                                    int psyID = ((int) mod.getPsychID());

                                    if(ID==psyID)
                                    {
                                        psychTitles.add(mod.getName());
                                        psychContents.add(mod.getQualification());
                                        psychIds.add(String.valueOf(psyID));
                                    }/*else if(ID ==0)
                                    {
                                        //means that no psychologist is linked to the guardian
                                        Intent i = new Intent(psychologistListView.this, NoPsychologistLinkedMain.class);
                                        startActivity(i);
                                    }else if(ID==-1)
                                    {
                                        //No pair has been made therefore redirect to a page where the guardian can make a pair
                                        Intent j = new Intent(psychologistListView.this, NoChildLinkedMain.class);
                                        startActivity(j);
                                    }*/
                                    String[] titles = GetStringArray(psychTitles);//{"Psychologist 1", "Psychologist 2" , "Psychologist3"};//getResources().getStringArray(R.array.stories_title);
                                    String[] contents = GetStringArray(psychContents);//{"Description 1", "Description 2" , "Description 3"}; //getResources().getStringArray(R.array.story_content);
                                    String[] IDs = GetStringArray(psychIds);
                                    recyclerView = findViewById(R.id.storiesListsView);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(psychologistListView.this));
                                    adapter = new Adapter(psychologistListView.this,titles,contents,IDs); // our adapter takes two string array
                                    recyclerView.setAdapter(adapter);
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });

                    }

                    /*String[] titles = GetStringArray(psychTitles);//{"Psychologist 1", "Psychologist 2" , "Psychologist3"};//getResources().getStringArray(R.array.stories_title);
                    String[] contents = GetStringArray(psychContents);//{"Description 1", "Description 2" , "Description 3"}; //getResources().getStringArray(R.array.story_content);
                    String[] IDs = GetStringArray(psychIds);
                    recyclerView = findViewById(R.id.storiesListsView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(psychologistListView.this));
                    adapter = new Adapter(psychologistListView.this,titles,contents,IDs); // our adapter takes two string array
                    recyclerView.setAdapter(adapter);*/

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
        if(item.getItemId() == R.id.home){
           // Toast.makeText(this, "Home btn Clicked.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public static String[] GetStringArray(ArrayList<String> arr)
    {

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