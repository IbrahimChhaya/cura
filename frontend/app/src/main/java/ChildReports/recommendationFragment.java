package ChildReports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ChildTestSubsystem.AssignmentMain;
import ChildTestSubsystem.ChildTestMain;
import ResourceHub.RHubDescription;
import ResourceHub.ResourceHubModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recommendationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIInterface apiInterface;

    ArrayList<String> RHubProblems = new ArrayList<>();
    ArrayList<Integer> RHubIDs = new ArrayList<>();
    ArrayList<String> RHubImages = new ArrayList<>();
    ArrayList<String> RHubDescription = new ArrayList<>();

    ListView lstRecommendation;

    int ChildID;

    public recommendationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recommendationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recommendationFragment newInstance(String param1, String param2) {
        recommendationFragment fragment = new recommendationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recommendation, container, false);
        lstRecommendation = rootView.findViewById(R.id.recommendationList);

        //getting the child ID
        Bundle bundle = getArguments();
        ChildID = bundle.getInt("ChildID");

        //fetching all the focus points assigned to the child
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.fetchChildFocusPoints(ChildID).enqueue(new Callback<List<ResourceHubModel>>() {
            @Override
            public void onResponse(Call<List<ResourceHubModel>> call, Response<List<ResourceHubModel>> response) {
                if(response.code()==200)
                {
                    for(ResourceHubModel rHub : response.body())
                    {
                        RHubImages.add(rHub.getTitleImage());
                        RHubIDs.add(rHub.getProblemID());
                        RHubProblems.add(rHub.getProblem());
                        RHubDescription.add(rHub.getDescription());
                    }
                    //add the mood details for the last card
                    getAverageMood(RHubImages,RHubIDs,RHubProblems);

                   /* myAdapter adapter = new myAdapter();
                    lstRecommendation.setAdapter(adapter);*/
                }
            }

            @Override
            public void onFailure(Call<List<ResourceHubModel>> call, Throwable t) {

            }
        });



        return rootView;
    }



    //adapter class
    public class myAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return RHubIDs.size();
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
            convertView = getLayoutInflater().inflate(R.layout.child_recommendation_item,parent,false);
            ImageView problemImage = convertView.findViewById(R.id.recImage);
            TextView problemDescription = convertView.findViewById(R.id.txtRecommendationDescription);
            CardView crdRedirect = convertView.findViewById(R.id.crdRedirect);


                String imageUrl = "@drawable/"+RHubImages.get(position).replaceAll(".(png|jpe?g|svg)", "");
                int imageResource = getResources().getIdentifier(imageUrl, null, getActivity().getPackageName());
                problemImage.setImageDrawable(getResources().getDrawable(imageResource, getActivity().getApplicationContext().getTheme()));

                if(RHubIDs.get(position) >0)
                {
                    crdRedirect.setVisibility(View.VISIBLE);
                    problemDescription.setText("We suggest you read through the \n"+RHubProblems.get(position)+" resource hub");
                }else
                {
                    crdRedirect.setVisibility(View.INVISIBLE);
                    problemDescription.setText(RHubProblems.get(position));
                }
                crdRedirect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), RHubDescription.class);
                        intent.putExtra( "probID",RHubIDs.get(position));
                        intent.putExtra("probDescription", RHubDescription.get(position));
                        intent.putExtra("probName", RHubProblems.get(position));
                        startActivity(intent);
                    }
                });

            return convertView;
        }
    }


    //function to get the average mood
    public void getAverageMood(ArrayList<String> RHubImages, ArrayList<Integer>RHubIDs, ArrayList<String> RHubDescription)
    {
        apiInterface.getAverageMoodPerWeek(ChildID).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.code()==200)
                {
                    double value = 0;
                    value = response.body();
                    if(value ==1)
                    {
                        RHubImages.add("blobsob.png");
                        RHubIDs.add(-1);
                        RHubDescription.add("Your child's weekly mood log indicates that they feel Very sad");
                    }else if(value ==2)
                    {
                        RHubImages.add("blobsad.png");
                        RHubIDs.add(-1);
                        RHubDescription.add("Your child's weekly mood log indicates that they feel sad");
                        String imageUrl = "@drawable/blobsad";

                    }else if(value ==3)
                    {
                        RHubImages.add("blobneutral.png");
                        RHubIDs.add(-1);
                        RHubDescription.add("Your child's weekly mood log indicates that they feel normal");

                    }else if(value == 4)
                    {
                        RHubImages.add("blobsmile.png");
                        RHubIDs.add(-1);
                        RHubDescription.add("Your child's weekly mood log indicates that they feel happy");


                    }else if(value ==5)
                    {
                        RHubImages.add("blobgrin.png");
                        RHubIDs.add(-1);
                        RHubDescription.add("Your child's weekly mood log indicates that they feel very happy");

                    }else
                    {
                        RHubImages.add("error.png");
                        RHubIDs.add(-1);
                        RHubDescription.add("No mood was logged by the child in past week");
                    }

                    myAdapter adapter = new myAdapter();
                    lstRecommendation.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }
}