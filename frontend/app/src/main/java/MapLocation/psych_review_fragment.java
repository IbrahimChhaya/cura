package MapLocation;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ChildList.ChildListRedesigned;
import ChildReports.ChildReportsMain;
import PsychologistCalendar.HorizontalPsychologistCalendar;
import PsychologistList.PsychologistProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link psych_review_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class psych_review_fragment extends Fragment {

    private ArrayList<String> listGName = new ArrayList<String>();
    private ArrayList<Integer> listRating = new ArrayList<Integer>();

    private int myRating = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIInterface apiInterface;

    public psych_review_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment psych_review_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static psych_review_fragment newInstance(String param1, String param2) {
        psych_review_fragment fragment = new psych_review_fragment();
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
//
//
//        listGName.add("Name1");
//        listGName.add("Name2");
//        listGName.add("Name3");
//        listGName.add("Name4");
//
//
//        listRating.add(3);
//        listRating.add(4);
//        listRating.add(5);
//        listRating.add(1);
//
//        // Inflate the layout for this fragment
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        View v;
        v =  inflater.inflate(R.layout.fragment_psych_review_fragment, container, false);

        ListView listv = (ListView) v.findViewById(R.id.listReview);
        TextView tx = (TextView) v.findViewById(R.id.reviewGuardianNa);


        RatingBar bar = (RatingBar) v.findViewById(R.id.ratingBarReviewUser);
        Button btnSave = (Button) v.findViewById(R.id.btnSaveRating);

        bar.getNumStars();
       bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               btnSave.setVisibility(View.VISIBLE);
               myRating = (int) ratingBar.getRating();
           }
       });

       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int rat = (int) myRating;
               String strRating = String.valueOf(myRating);


               HashMap<String,Integer> saveRatingHash = new HashMap();
               ModelSavingRating modelRating = new ModelSavingRating(GlobalVariables.loggedInUser.getUserID(), GlobalVariables.psychologistID, rat);

               saveRatingHash.put("GuardianID", GlobalVariables.loggedInUser.getUserID());
               saveRatingHash.put("PsychID", GlobalVariables.psychologistID);
               saveRatingHash.put("Rating", rat);

               apiInterface.SaveRating("application/json; charset=utf-8",saveRatingHash).enqueue(new Callback<ModelSavingRating>() {
                   @Override
                   public void onResponse(Call<ModelSavingRating> call, Response<ModelSavingRating> response) {
                        if(response.code() == 200){
                            //successfull
                        }
                   }

                   @Override
                   public void onFailure(Call<ModelSavingRating> call, Throwable t) {

                   }
               });
           }
       });
       //

//        if( bar.getRating() == 2 || bar.getRating() == 3 || bar.getRating() == 4 || bar.getRating() ==5){
//
//        }
        apiInterface.getAllUserRating(GlobalVariables.psychologistID).enqueue(new Callback<List<Ratings>>() {
            @Override
            public void onResponse(Call<List<Ratings>> call, Response<List<Ratings>> response) {
             //   Ratings rat = new Ratings();
                if(response.code() == 200){
                    for(Ratings r: response.body()){
                        listGName.add(r.getName());
                        listRating.add((int) r.getRating());

                        if(r.getName().equals(GlobalVariables.loggedInUser.getName())){
                            bar.setRating(r.getRating());
                        }
                    }


                    listGName.add("-1");
                    listRating.add(0);

                    myAdapterReview adapt = new myAdapterReview();
                    listv.setAdapter(adapt);


                }
            }

            @Override
            public void onFailure(Call<List<Ratings>> call, Throwable t) {

            }
        });

//        apiInterface.getAllUserRating(GlobalVariables.psychologistID).enqueue(new Callback<List<Ratings>>() {
//            @Override
//            public void onResponse(Call<Ratings> call, Response<Ratings> response) {
//                Ratings rat = new Ratings();
//                if(response.code() == 200){
//                   // for(int r = 0; r < rat.getName().length(); r++){
//                        listGName.add(rat.getName().toString());
//                        listRating.add((int) rat.getRating());
//                    //}
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Ratings> call, Throwable t) {
//
//            }
//        });
//
//        listGName.add("Name1");
//        listGName.add("Name2");
//        listGName.add("Name3");
//        listGName.add("Name4");
//        listGName.add("-1");
//
//
//        listRating.add(3);
//        listRating.add(4);
//        listRating.add(5);
//        listRating.add(1);
//        listRating.add(0);



        return v;
    }

    public class myAdapterReview extends BaseAdapter {

        @Override
        public int getCount() {
            return listGName.size();
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
            convertView = getLayoutInflater().inflate(R.layout.review_rating, parent, false);
            RatingBar rtBar = (RatingBar) convertView.findViewById(R.id.ratingBarReview);
            TextView guardName = (TextView) convertView.findViewById(R.id.reviewGuardianName);

       //     CardView card = convertView.findViewById(R.id.childCard);

            rtBar.setRating(Integer.valueOf(listRating.get(position)));
            guardName.setText(listGName.get(position));


            if(listGName.get(position) == "-1" && listRating.get(position) == 0){
                rtBar.setVisibility(View.INVISIBLE);
                guardName.setVisibility(View.INVISIBLE);
            }

            return convertView;

        }
    }


}