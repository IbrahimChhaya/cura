package PsychologistList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.MainActivity;
import com.example.mhaprototype.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import Appointment.AppointmentMain;
import ChildTestSubsystem.AssignmentMain;
import ChildTestSubsystem.ChildTestMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchablePsychologistList extends AppCompatActivity {

    private int testID[] = {1, 2, 3, 4, 5};
    private String randomList[] = {"Abbd", "Bdbibf", "Cwbweiubf", "Drnweo", "Ewubwuirbg"};
    ListView lstPsychList;
    EditText searchView;
    ArrayList<PsychologistClass> psychArrayList;

    ArrayAdapter<PsychologistClass> arrayAdapter;
    ListViewAdapter adapter;
    APIInterface apiInterface;
    ImageView imgBackButton;
    CardView crdSort;
    CardView crdFilter;

    //poppup menu
    PopupMenu popupMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable_psychologist_list);
        searchView = findViewById(R.id.PsychSearch);
        lstPsychList = findViewById(R.id.lstPsychList);
        imgBackButton = findViewById(R.id.imgBack);
        crdSort = findViewById(R.id.crdSort);
        crdFilter = findViewById(R.id.crdFilter);


        psychArrayList = new ArrayList<>();

       /* psychArrayList.add(new PsychologistClass(1,"Sue","bedfordview","bsc bullshit","image"));
        psychArrayList.add(new PsychologistClass(2,"due","bedfordview","bsc bullshit","Image"));
        psychArrayList.add(new PsychologistClass(3,"eue","bedfordview","bsc bullshit", "Image"));
        psychArrayList.add(new PsychologistClass(4,"fue","bedfordview","bsc bullshit", "Image"));
*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        //fetchAll the psychologist
        apiInterface.getAllPsychologists().enqueue(new Callback<List<PsychologistInfoModel>>() {
            @Override
            public void onResponse(Call<List<PsychologistInfoModel>> call, Response<List<PsychologistInfoModel>> response) {
                if(response.code()==200)
                {
                    for(PsychologistInfoModel psychModel : response.body())
                    {
                        int psychID = (int) psychModel.getPsychID();
                        PsychologistClass psychClass = new PsychologistClass(
                                psychID,psychModel.getName(),psychModel.getAddress(),
                                psychModel.getQualification(),psychModel.getProfilePicture(),psychModel.getDescription()
                        );
                        psychArrayList.add(psychClass);
                    }
                    //psychArrayList.add(new PsychologistClass(34,"Idiot","hello","hello","adult9.png", "description"));
                    lstPsychList.setTextFilterEnabled(true);
                    adapter = new ListViewAdapter(SearchablePsychologistList.this,psychArrayList);
                    lstPsychList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PsychologistInfoModel>> call, Throwable t) {

            }
        });

        //sort the arraylist for sort on the psych list
        crdSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sort the arrayList by name
                sort(psychArrayList);
                adapter = new ListViewAdapter(SearchablePsychologistList.this,psychArrayList);
                lstPsychList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        //adding the search feature for the edit text box
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchView.getText().toString().toLowerCase();
                adapter.filter(text);

            }
        });

        //filtering the items
        crdFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(SearchablePsychologistList.this, v);
                popupMenu.getMenu().add("Child Psychologist");
                popupMenu.getMenu().add("School Psychologist");
                popupMenu.getMenu().add("Development Psychologist");
                popupMenu.getMenu().add("School Counsellor");
                popupMenu.getMenu().add("Family Therapist");
                popupMenu.getMenu().add("Show All");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        String itemClicked = item.getTitle().toString();
                        //function that sets up the array of the items according to the item selected
                        filterArray(itemClicked);
                        return true;
                    }
                });
            }
        });



        //button to go to the previous activity
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchablePsychologistList.this,Guardian_Home.class);
                startActivity(i);
            }
        });



    }


    //listview adapter
    public class ListViewAdapter extends BaseAdapter {

        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<PsychologistClass> psychlist = null;
        private ArrayList<PsychologistClass> arraylist;

        public ListViewAdapter(Context context,
                               List<PsychologistClass> psychlist) {
            mContext = context;
            this.psychlist = psychlist;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<PsychologistClass>();
            this.arraylist.addAll(psychlist);
        }

        public class ViewHolder {
            TextView txtRandom;
            TextView txtPsychQualification;
            TextView txtPsychLocation;
            ImageView imgPsychProfile;

        }

        @Override
        public int getCount() {
            return psychlist.size();
        }

        @Override
        public PsychologistClass getItem(int position) {
            return psychlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.psychologist_card_item, null);
                // Locate the TextViews in listview_item.xml
                holder.txtRandom = (TextView) view.findViewById(R.id.txtPsychName);
                holder.imgPsychProfile = (ImageView) view.findViewById(R.id.imgpsychImage);
                holder.txtPsychQualification = (TextView) view.findViewById(R.id.txtPsychQualification);
                holder.txtPsychLocation = (TextView) view.findViewById(R.id.txtpsychLocation);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.txtRandom.setText(psychlist.get(position).getPsychName());
            holder.txtPsychQualification.setText(psychlist.get(position).getQualification());
            holder.txtPsychLocation.setText(psychlist.get(position).getPsychLocation());

            // Set the results into ImageView
            String imageName = psychlist.get(position).getPsychImage().replaceAll(".(png|jpe?g|svg)", "");
            String imageUrl = "@drawable/" + imageName;
            int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
            holder.imgPsychProfile.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));



            // Listen for ListView Item Click
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Send single item click data to SingleItemView Class
                    //sending the relevant data to the psych profile
                    GlobalVariables.psychologistID = 0;
                    GlobalVariables.psychologistID = psychlist.get(position).getPsychID();
                    GlobalVariables.psychologistDegree = psychlist.get(position).getQualification();
                    GlobalVariables.psycologistDescription = psychlist.get(position).getPsychDescription();
                    GlobalVariables.psychologistName = psychlist.get(position).getPsychName();
                    GlobalVariables.psychAddress=" ";
                    GlobalVariables.psychAddress = psychlist.get(position).getPsychLocation();

                    Intent i = new Intent(SearchablePsychologistList.this, PsychologistProfile.class);
                    startActivity(i);
                    //Toast.makeText(SearchablePsychologistList.this, "You clicked"+psychlist.get(position).getPsychName(), Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            psychlist.clear();
            if (charText.length() == 0) {
                psychlist.addAll(arraylist);
            } else {
                for (PsychologistClass wp : arraylist) {
                    if (wp.getPsychName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        psychlist.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed()
    {
        //programming emulator back button
      Intent i = new Intent(SearchablePsychologistList.this,Guardian_Home.class);
      startActivity(i);

    }

    //function to sort the arraylist of psychologist class
    public static void sort(ArrayList<PsychologistClass> list)
    {

        list.sort((o1, o2)
                -> o1.getPsychName().compareTo(
                o2.getPsychName()));
    }

    //function to filter the sychologist array based on their scope
    public void filterArray(String itemClicked)
    {
        ArrayList<PsychologistClass> tempArray = new ArrayList<>();
        for(PsychologistClass psych : psychArrayList)
        {
            if(psych.getQualification().contains(itemClicked))
            {
                tempArray.add(psych);
            }else if(itemClicked.equals("Show All"))
            {
                    tempArray.add(psych);
            }
        }
        adapter = new ListViewAdapter(SearchablePsychologistList.this, tempArray);
        lstPsychList.setAdapter(adapter);

    }


}