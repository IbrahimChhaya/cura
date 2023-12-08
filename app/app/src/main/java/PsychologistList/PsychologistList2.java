package PsychologistList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ChildList.ChildListRedesigned;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PsychologistList2 extends AppCompatActivity {

    ListView lstLinkedPsych;
    APIInterface apiInterface;
    ArrayList<PsychologistClass> psychArrayList;
    ListViewAdapter adapter;
    ImageView imgBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_list2);

        lstLinkedPsych = findViewById(R.id.lstPsychList);
        imgBackButton = findViewById(R.id.imgbtnPrev);
        psychArrayList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getAllPsychologists().enqueue(new Callback<List<PsychologistInfoModel>>() {
            @Override
            public void onResponse(Call<List<PsychologistInfoModel>> call, Response<List<PsychologistInfoModel>> response) {
                if(response.code()==200)
                {

                    for(PsychologistInfoModel mod : response.body())
                    {

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
                                        PsychologistClass psychClass = new PsychologistClass(psyID,mod.getName(),mod.getAddress(),mod.getQualification(),mod.getProfilePicture(),mod.getDescription());
                                        psychArrayList.add(psychClass);
                                    }
                                    adapter = new ListViewAdapter(PsychologistList2.this,psychArrayList);
                                    lstLinkedPsych.setAdapter(adapter);
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PsychologistInfoModel>> call, Throwable t) {

            }
        });

        //back button
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    //adapter class for the psychologist list
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
                    //Toast.makeText(SearchablePsychologistList.this, "You clicked"+psychlist.get(position).getPsychName(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PsychologistList2.this, ChildListRedesigned.class);
                    i.putExtra("ChildListMessage", "PsychologistList");
                    startActivity(i);

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
}