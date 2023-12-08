package ChildReports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedBackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedBackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<String> feedBackList;
    private APIInterface apiInterface;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //child ID from the bundle
    int ChildID;

    public FeedBackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedBackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedBackFragment newInstance(String param1, String param2) {
        FeedBackFragment fragment = new FeedBackFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_feed_back, container, false);
        ListView lstFeedback = rootView.findViewById(R.id.lstChildFeedback);

        //getting the child ID from the bundle
        Bundle bundle = getArguments();
        ChildID = bundle.getInt("ChildID");

        feedBackList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.fetchAllChildsNotesByID(ChildID).enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                if(response.code()==200)
                {
                    for(Notes n : response.body())
                    {
                        if(n.getType().equals("Feedback"))
                        {
                            feedBackList.add(n.getFeedback());
                        }
                    }
                }

                myAdapter adapter = new myAdapter();
                lstFeedback.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {

            }
        });
  /*      feedBackList.add("<h2 style=\"color: white;\">Appointment on 24 April 2021</h2> <p><span class=\"ql-size-huge\">how it do</span></p><p>d<strong>fefefe<em>effefe<u>fefefe</u><s><u>fefsef</u></s></em></strong></p><ol><li><strong><em><s><u>ok</u></s></em></strong></li><li><strong><em><s><u>f</u></s></em></strong></li></ol> <h5 style=\"color: white; opacity: 0.6;\">Written on 20 July 2021");
        feedBackList.add("hi");
        feedBackList.add("im drowning");
        feedBackList.add("im drowning");
        feedBackList.add("im drowning");
        feedBackList.add("im drowning");*/

/*        myAdapter adapter = new myAdapter();
        lstFeedback.setAdapter(adapter);*/

       /* String textToRead = "<h2 style=\"color: white;\">Appointment on 24 April 2021</h2> <p><span class=\"ql-size-huge\">how it do</span></p><p>d<strong>fefefe<em>effefe<u>fefefe</u><s><u>fefsef</u></s></em></strong></p><ol><li><strong><em><s><u>ok</u></s></em></strong></li><li><strong><em><s><u>f</u></s></em></strong></li></ol> <h5 style=\"color: white; opacity: 0.6;\">Written on 20 July 2021";
        TextView txtHtml = rootView.findViewById(R.id.testingHtml);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtHtml.setText(Html.fromHtml(textToRead, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtHtml.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
        }*/


        return rootView;
    }


    //adapter class for feedback
    public class myAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return feedBackList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.child_feedback_item,parent,false);

            TextView txtFeedback = convertView.findViewById(R.id.txtFeedBackText);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtFeedback.setText(Html.fromHtml(feedBackList.get(position), Html.FROM_HTML_MODE_COMPACT));
            } else {
                txtFeedback.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
            }


            String colourList[] = {"#90EE90", "#CBC3E3","#FFD580","#ffb6c1","#d4cb96",
                    "#aaedfa","#de98e3"};
            Random rand = new Random();
            int upperbound = colourList.length;
            int randNumber = rand.nextInt(upperbound);

            return convertView;
        }
    }
}