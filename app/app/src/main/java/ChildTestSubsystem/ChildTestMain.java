package ChildTestSubsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ChildTestSubsystem.Model.AssignedTest;
import ChildTestSubsystem.Model.ChildTest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildTestMain extends AppCompatActivity {

    ListView lstTest;
    private String TestName[] ={"How are you feeling today", "Take your test 2", "Third test yay you have made it",
    "Are you okay", "Ibrahim is dumb"};
    private int testID[] = {1,2,3,4,5};
    APIInterface apiInterface;
    ArrayList<String> testNameList = new ArrayList<>();
    ArrayList<Integer> testIDList = new ArrayList<>();
    ArrayList<Integer> childTestID = new ArrayList<>();
    ArrayList<Integer> childTestPoints = new ArrayList<>();


    private int  numIncompleteTests = 0;
    TextView txtPending;
    TextView txtPoints;

    ImageView imgBackButton;


    @Override
    public void onBackPressed() {

        Intent i = new Intent(ChildTestMain.this, Child_Home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_test_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.testToolBar);
        setSupportActionBar(myToolbar);

        lstTest = (ListView)findViewById(R.id.lstChildTest);
        txtPending = findViewById(R.id.txtPendingTests);
        txtPoints = findViewById(R.id.txtChildPoints);
        imgBackButton = findViewById(R.id.imgBackButton);

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildTestMain.this, Child_Home.class);
                startActivity(i);
            }
        });





        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        //setting the child points
        setChildPoints();

        //getting the child test assigned
        apiInterface.getAllChildTestsAssigned(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AssignedTest>>() {
            @Override
            public void onResponse(Call<List<AssignedTest>> call, Response<List<AssignedTest>> response) {
                if(response.code()==200)
                {
                    for(AssignedTest t : response.body())
                    {
                        numIncompleteTests++;
                        testNameList.add(t.getTestAssigned().getName());
                        testIDList.add(t.getTestAssigned().getTestID());
                        childTestID.add(t.getChildTestID());
                        childTestPoints.add(t.getTestAssigned().getTotal());
                    }

                    txtPending.setText(String.valueOf(numIncompleteTests));
                    myAdapter adapter = new myAdapter();
                    lstTest.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<AssignedTest>> call, Throwable t) {

            }
        });
       /* myAdapter adapter = new myAdapter();
        lstTest.setAdapter(adapter);*/

    }

    //method to set the child points
    public void setChildPoints()
    {
        //GlobalVariables.loggedInUser.getUserID()
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getChildPoints(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200)
                {
                    txtPoints.setText(String.valueOf(response.body()));
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }


    public class myAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return testNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.child_test_item,parent,false);
            MaterialCardView crdTest = convertView.findViewById(R.id.testCard);
            CardView crdStyle = convertView.findViewById(R.id.crdStyle);
            //txtTestID.setTextColor(Color.WHITE);
            TextView txtTestName = convertView.findViewById(R.id.txtTestName);

            String colourList[] = {"#90EE90", "#CBC3E3","#FFD580","#ffb6c1","#d4cb96",
            "#aaedfa","#de98e3"};
            Random rand = new Random();
            int upperbound = colourList.length;
            int randNumber = rand.nextInt(upperbound);
            crdStyle.setCardBackgroundColor(Color.parseColor(colourList[randNumber]));
            //crdTest.setStrokeColor(Color.parseColor(colourList[randNumber]));
            //txtTestName.setTextColor(Color.WHITE);
            crdTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = testIDList.get(position);
                    int childtestID = childTestID.get(position);
                    Intent i = new Intent(ChildTestMain.this, AssignmentMain.class);
                    i.putExtra("testID", testIDList.get(position));
                    i.putExtra("childTestID", childTestID.get(position));
                    i.putExtra("assignmentTitle", testNameList.get(position));
                    i.putExtra("testPoints", childTestPoints.get(position));
                    startActivity(i);
                }
            });
            //txtTestName.setText(TestName[position]);
            txtTestName.setText(testNameList.get(position));
            return convertView;
        }
    }
}