package ChildList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ChildReports.ChildReportsMain;
import ChildTestSubsystem.ChildTestMain;
import PsychologistCalendar.HorizontalPsychologistCalendar;
import StartUpStuff.GuardianChildRegister.GuardianRegistersChild1;
import StartUpStuff.GuardianChildRegister.GuardianRegistersChild3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildListRedesigned extends AppCompatActivity {

    APIInterface apiInterface;

    ListView listview;

    RecyclerView recyclerView;
    myAdapter adapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    int count = 0;

    ArrayList<String> ChildTitles = new ArrayList<>();
    ArrayList<String> ChildContents = new ArrayList<>();
    ArrayList<Integer> childIds = new ArrayList<>();
    ArrayList<Integer> listImages = new ArrayList<>();

    ArrayList<String> childGrade = new ArrayList<>();
    ArrayList<String> childDOB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list_redesigned);

        ImageView imgBackButton = (ImageView) findViewById(R.id.childBackButton);

        listview = findViewById(R.id.childListview);

        //fetching all the psychologists

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.globalToolbar);
        setSupportActionBar(myToolbar);

        // TextView heading = (TextView) myToolbar.findViewById(R.id.globaltoolbarText);
        //heading.setText("Select a Child");

        // toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        navigationView = findViewById(R.id.navi_view);
//        navigationView.setNavigationItemSelectedListener(this);

        // drawer = findViewById(R.id.drawer);
        // toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        //drawer.addDrawerListener(toggle);
        //toggle.setDrawerIndicatorEnabled(true);
        //toggle.syncState();

        ArrayList<Integer> childIDs = new ArrayList<>();
        apiInterface.getLinkedChildren(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.code() == 200) {
                    int count = 0;
                    for (int i : response.body()) {
                        count++;
                        //adding the child IDs that are linked to the arent in a list
                        childIDs.add(i);
                    }
                    //means that no child is linked yet
                    if (count == 0) {

                    }

                    apiInterface.getAllUsers().enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.code() == 200) {
                                for (UserModel usr : response.body()) {
                                    int intusriD = ((int) usr.getUserID());
                                    if (childIDs != null) {
                                        for (Integer i : childIDs) {
                                            //Checking if the IDs obtained via previous call matches a user
                                            if (i == intusriD) {
                                                ChildTitles.add(usr.getName().toString());
                                                ChildContents.add(usr.getUserType());
                                                childIds.add(i);
                                                if(usr.getGrade() != null && !usr.getGrade().equals("0"))
                                                {
                                                    childGrade.add(usr.getGrade());
                                                }else
                                                {
                                                    childGrade.add("Not Specified");
                                                }
                                                if(usr.getDob() != null)
                                                {
                                                    childDOB.add(usr.getDob());
                                                }else
                                                {
                                                    childDOB.add("Not Specified");
                                                }


                                                String ImageAsset = usr.getProfilePicture();

                                                if (ImageAsset.contains(".svg")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".svg", "");
                                                } else if (ImageAsset.contains(".png")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".png", "");
                                                } else if (ImageAsset.contains(".jpg")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".jpg", "");
                                                } else if (ImageAsset.contains(".jpeg")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".jpeg", "");
                                                } else if (ImageAsset.contains(".gif")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".gif", "");
                                                } else if (ImageAsset.contains(".avif")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".avif", "");
                                                } else if (ImageAsset.contains(".jiff")) {
                                                    ImageAsset = usr.getProfilePicture().replace(".jiff", "");
                                                }

                                                String imageLocation = "@drawable/" + ImageAsset;
                                                int imageResource = getResources().getIdentifier(imageLocation, null, getPackageName());
                                                listImages.add(imageResource);
                                                //adapter = new MyAdapter(ChildListRedesigned.this,ChildTitles,listImages);
                                            }
                                        }

                                    }
                                }
                                if (childIDs.size() == 0) {
                                    ChildTitles.add("No Child Linked");
                                  //  ChildContents.add("Please produce a child");
                                }

//                                String[] Childtitles = GetStringArray(ChildTitles);//{"Psychologist 1", "Psychologist 2" , "Psychologist3"};//getResources().getStringArray(R.array.stories_title);
//                                String[] Childcontents = GetStringArray(ChildContents);//{"Description 1", "Description 2" , "Description 3"}; //getResources().getStringArray(R.array.story_content);
//                                String[] ChildIDList = GetStringArray(childIds);
                                //recyclerView = findViewById(R.id.storiesListsView);
                                // recyclerView.setLayoutManager(new LinearLayoutManager(ChildListMain.this));
//                                adapter = new myAdapter(ChildListRedesigned.this, ChildTitles, listImages); // our adapter takes two string array
                                // recyclerView.setAdapter(adapter);
                                childIds.add(-1);
                                childDOB.add("2021-01-10");
                                childGrade.add("4");
                                ChildTitles.add("Testing");
                                listImages.add(2131230882);


                                myAdapter adapter = new myAdapter();
                                listview.setAdapter(adapter);

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
                Intent intent = new Intent(ChildListRedesigned.this, Guardian_Home.class);
                startActivity(intent);
            }
        });


        Button btnAddChild = (Button) findViewById(R.id.btnAddChild);



        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildListRedesigned.this, GuardianRegistersChild1.class);
                startActivity(i);
            }
        });



    }

    public class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return childIds.size();
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
            convertView = getLayoutInflater().inflate(R.layout.activity_child_list_redesign_frame, parent, false);
            TextView ChildName = convertView.findViewById(R.id.childName);
            TextView txtChildGrade = convertView.findViewById(R.id.childGrade);
            TextView txtChildDOB = convertView.findViewById(R.id.childDOB);
            ImageView childImage = convertView.findViewById(R.id.childImage);

            CardView childCard = convertView.findViewById(R.id.childCard);

            ChildName.setText(String.valueOf(ChildTitles.get(position)));
            childImage.setImageResource(listImages.get(position));
            txtChildGrade.setText("Grade: "+ childGrade.get(position));

            ListView myview = convertView.findViewById(R.id.childListview);



//
            if(childIds.get(position) == -1){
                childCard.setVisibility(View.INVISIBLE);
                childCard.setEnabled(false);
            }


            //setting the child birthday******************************
            String DOB = childDOB.get(position);
            if(!DOB.equals("Not Specified"))
            {
                DOB = DOB.substring(0,10);
                String childBirthday = getCurrentDate(DOB);
                txtChildDOB.setText("Date of Birth: " + childBirthday);
            }else
            {
                txtChildDOB.setText("Date of Birth: Not Specified");
            }

            //setting the child birthday******************************




            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent getMessage = getIntent();
                    String message = getMessage.getStringExtra("ChildListMessage");
                    if(message.equals("ReportsCard"))
                    {

                        if(childIds.get(position) != -1){
                            Intent i = new Intent(ChildListRedesigned.this, ChildReportsMain.class);
                            i.putExtra("ChildID", childIds.get(position));
                            startActivity(i);
                        }

                    }else if(message.equals("PsychologistList"))
                    {
                        if(childIds.get(position) != -1){
                            Intent i = new Intent(ChildListRedesigned.this, HorizontalPsychologistCalendar.class);
                            GlobalVariables.GuardianChildID = childIds.get(position);
                            startActivity(i);
                        }

                    }

                }
            });
            return convertView;
        }
    }

    //function to get date in a user friendly manner
    public String getCurrentDate(String strDate)
    {
        StringTokenizer token = new StringTokenizer(strDate,"-");
        String MonthName = "";
        String Day = token.nextToken();
        String Month = token.nextToken();
        String Year = token.nextToken();

        switch(Month)
        {
            case "01":
                MonthName = "January";
                break;
            case "02":
                MonthName = "February";
                break;
            case "03":
                MonthName = "March";
                break;
            case "4":
                MonthName = "April";
                break;
            case "05":
                MonthName = "May";
                break;
            case "06":
                MonthName = "June";
                break;
            case "07":
                MonthName = "July";
                break;
            case "08":
                MonthName = "August";
                break;
            case "09":
                MonthName = "September";
                break;
            case "10":
                MonthName = "October";
                break;
            case "11":
                MonthName = "November";
                break;
            case "12":
                MonthName = "December";
                break;

        }
        //Toast.makeText(HorizontalPsychologistCalendar.this,"MonthName: " + MonthName + " Day: " + Day + " Year: " + Year, Toast.LENGTH_LONG).show();
        return " " + Year + " " + MonthName+" "+Day;//Day + " " + MonthName +" " + Year;

    }


//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//        ArrayList<Integer> imgs;
//        ArrayList <String> names;
//
//
//        MyAdapter(Context c, ArrayList <String> n, ArrayList<Integer> img) {
//            super(c, R.layout.activity_child_list_redesign_frame, R.id.childName, n);
//            context = c;
//            this.names = n;
//            this.imgs = img;
//
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//
//            View row = layoutInflater.inflate(R.layout.activity_child_list_redesign_frame, parent, false);
//
//            ImageView images = row.findViewById(R.id.childImage);
//
//            Toolbar myToolbar = (Toolbar) findViewById(R.id.selectChildToolbar);
//            setSupportActionBar(myToolbar);
//
//            myToolbar.setTitle("Select Child");
//
////            TextView illNames = (TextView) myToolbar.findViewById(R.id.toolbarText12);
////
////            illNames.setText(names.get(position));
//
//
//
//            TextView childName = row.findViewById(R.id.childName);
//
//
//            images.setImageResource(imgs.get(position));
//            childName.setText(names.get(position));
//
//
//            return row;
//        }
//
//
//
//    }
//}
}