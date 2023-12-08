package ChildList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
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
import com.example.mhaprototype.UserModel;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import PsychologistList.Adapter;
import PsychologistList.PsychologistInfoModel;
import PsychologistList.psychologistListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildListMain  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    APIInterface apiInterface;

    RecyclerView recyclerView;
    ChildAdapter adapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    String IncomingMessage;

    int count = 0;

    ArrayList<String> ChildTitles = new ArrayList<>();
    ArrayList<String> ChildContents = new ArrayList<>();
    ArrayList<String> childIds = new ArrayList<>();

    private static final String TAG = "Debugging";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list_main);

        ImageView imgBackButton = (ImageView)findViewById(R.id.gobackglobalbutton);
        Intent i = getIntent();
        if(i.getStringExtra("IncomingFrom") != null)
        {
            IncomingMessage = i.getStringExtra("IncomingFrom");
        }else
        {
            IncomingMessage = "Normal Procedure";
        }
        //fetching all the psychologists

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.globalToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.globaltoolbarText);
        heading.setText("Select a Child");

       // toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
       // toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        //drawer.addDrawerListener(toggle);
        //toggle.setDrawerIndicatorEnabled(true);
        //toggle.syncState();

        ArrayList<Integer> childIDs = new ArrayList<>();
        apiInterface.getLinkedChildren(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if(response.code()==200)
                {
                    int count = 0;
                    for(int i : response.body())
                    {
                        count++;
                        //adding the child IDs that are linked to the arent in a list
                        childIDs.add(i);
                    }
                    //means that no child is linked yet
                    if(count==0)
                    {

                    }

                    apiInterface.getAllUsers().enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if(response.code()==200)
                            {
                                for(UserModel usr: response.body())
                                {
                                    int intusriD = ((int) usr.getUserID());
                                    if(childIDs != null)
                                    {
                                        for (Integer i : childIDs) {
                                            //Checking if the IDs obtained via previous call matches a user
                                            if (i == intusriD) {
                                                ChildTitles.add(usr.getName());
                                                ChildContents.add(usr.getUserType());
                                                childIds.add(String.valueOf(i));
                                            }
                                        }
                                    }
                                }
                                if(childIDs.size()==0)
                                {
                                    ChildTitles.add("No Child Linked");
                                    ChildContents.add("Please produce a child");
                                }

                                String[] Childtitles = GetStringArray(ChildTitles);//{"Psychologist 1", "Psychologist 2" , "Psychologist3"};//getResources().getStringArray(R.array.stories_title);
                                String[] Childcontents = GetStringArray(ChildContents);//{"Description 1", "Description 2" , "Description 3"}; //getResources().getStringArray(R.array.story_content);
                                String[] ChildIDList = GetStringArray(childIds);
                                recyclerView = findViewById(R.id.storiesListsView);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ChildListMain.this));
                                adapter = new ChildAdapter(ChildListMain.this,Childtitles,Childcontents,ChildIDList,IncomingMessage); // our adapter takes two string array
                                recyclerView.setAdapter(adapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

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
            Toast.makeText(this, "Home btn Clicked.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    //function to convert an arraylist to a string array
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