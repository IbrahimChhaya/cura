package ChildReports;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.card.MaterialCardView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.velli20.materialunixgraph.LineGraph;
import com.velli20.materialunixgraph.LinePoint;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ChildReports.Models.MoodResult;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    APIInterface apiInterface;
    ArrayList<Integer> copyOfArray = new ArrayList<>();
    ArrayList<MoodResult> moodResults = new ArrayList<>();
    ArrayList<Integer> moodValuesList = new ArrayList<>();
    static final int GRAPH_MAX_VERTICAL_VALUE = 120;

    //child ID from the bundle
    int ChildID;

    LineChart mChart;


    public MoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoodFragment newInstance(String param1, String param2) {
        MoodFragment fragment = new MoodFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_mood, container, false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        MaterialCardView crdStart2 = rootView.findViewById(R.id.stat2card);

        //getting the ID from the bundle
        Bundle bundle = getArguments();
        ChildID = bundle.getInt("ChildID");

        //need to pass the child id from previous activity  INTENT
        apiInterface.getWeeklyMoodTrend(ChildID).enqueue(new Callback<List<MoodResult>>() {
            @Override
            public void onResponse(Call<List<MoodResult>> call, Response<List<MoodResult>> response) {

                ArrayList<Integer> moodPoints = new ArrayList<>();
                ArrayList<String> moodDays = new ArrayList<>();

                if (response.code() == 200) {
                    for (MoodResult mr : response.body()) {
                        moodResults.add(mr);

                        moodPoints.add(mr.getMoodValue());

                        String mydate = mr.getDate();
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd");
                        Date mydate1 = null;
                        try {
                            mydate1 = simpleformat.parse(mydate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Format f = new SimpleDateFormat("EEEE");
                        String dayName = f.format(mydate1);
                        dayName = dayName.substring(0,1);
                        moodDays.add(dayName);
                    }

                    LineChartView lineChartView = rootView.findViewById(R.id.chart);
                    String[] axisData =moodDays.toArray(new String[0]);
                    Integer[] yAxisData = moodPoints.toArray(new Integer[0]);
                    int[] yAxisDummyData = {5};

                    List yAxisValues = new ArrayList();
                    List axisValues = new ArrayList();
                    List yAxisDummyValues = new ArrayList();


                    Line line = new Line(yAxisValues).setColor(Color.parseColor("#FF9565")).setCubic(true).setHasPoints(true);
                    Line line2 = new Line(yAxisDummyValues).setColor(Color.parseColor("#FFFFFF")).setHasPoints(false);

                    for (int i = 0; i < axisData.length; i++) {
                        axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                    }

                    for (int i = 0; i < yAxisData.length; i++) {

                            yAxisValues.add(new PointValue(i, yAxisData[i]));
                    }
                    for(int i = 0 ; i < yAxisDummyData.length; i++)
                    {
                        yAxisDummyValues.add(new PointValue(i,yAxisDummyData[i]));
                    }

                    List lines = new ArrayList();
                    lines.add(line);
                    lines.add(line2);



                    LineChartData data = new LineChartData();
                    data.setLines(lines);

                    Axis axis = new Axis();
                    axis.setValues(axisValues);
                    axis.setTextSize(10);
                    axis.setTextColor(Color.parseColor("#000000"));
                    data.setAxisXBottom(axis);

                    Axis yAxis = new Axis();
                   /* yAxis.setName("Sales in millions");*/
                    yAxis.setTextColor(Color.parseColor("#000000"));
                    yAxis.setTextSize(0);
                    data.setAxisYLeft(yAxis);

                    //checking if the mood was logged in the past week or not
                    int  zeroCount = 0;
                    for(Integer i : moodPoints)
                    {
                        if(i==0)
                        {
                            //checking if all the values are 0 in the arraylist
                            zeroCount++;
                        }
                    }

                    if(zeroCount != moodPoints.size())
                    {
                        lineChartView.setLineChartData(data);
                    }

                    /*Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                    viewport.top = 110;
                    lineChartView.setMaximumViewport(viewport);
                    lineChartView.setCurrentViewport(viewport);*/

                }


            }

            @Override
            public void onFailure(Call<List<MoodResult>> call, Throwable t) {

            }
        });


        //updating the number of mood logs card   Need to pass the child ID from the intent.putextra
        TextView txtLogs = rootView.findViewById(R.id.txtTotalLogs);
        updateLoggedMoods(txtLogs);

        //updating the second card need to pass the child ID in the function
        ImageView averageMoodBlob = rootView.findViewById(R.id.imgAverageMood);
        TextView txtAverageMood = rootView.findViewById(R.id.txtAverageMood);
        TextView txtCurrentMood = rootView.findViewById(R.id.txtCurrentMood);

        getAverageMood(averageMoodBlob,txtAverageMood,txtCurrentMood);




        // Inflate the layout for this fragment
        return rootView;//inflater.inflate(R.layout.fragment_mood, container, false);

    }

    public void updateLoggedMoods(TextView txtLogs)
    {
        apiInterface.getTotalMoodLogsPerWeek(ChildID).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200)
                {
                    txtLogs.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }

    public void getAverageMood(ImageView averageMoodBlob, TextView txtAverageMood, TextView txtCurrentMood)
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
                             String imageUrl = "@drawable/blobsob";
                             int imageResource = getResources().getIdentifier(imageUrl, null, getActivity().getPackageName());
                             averageMoodBlob.setImageDrawable(getResources().getDrawable(imageResource, getActivity().getApplicationContext().getTheme()));
                             txtAverageMood.setText("Average Mood");
                             txtCurrentMood.setText("Your child's weekly mood log indicates that they feel Very sad");
                         }else if(value ==2)
                         {
                             String imageUrl = "@drawable/blobsad";
                             int imageResource = getResources().getIdentifier(imageUrl, null, getActivity().getPackageName());
                             averageMoodBlob.setImageDrawable(getResources().getDrawable(imageResource, getActivity().getApplicationContext().getTheme()));
                             txtAverageMood.setText("Average Mood");
                             txtCurrentMood.setText("Sad");
                         }else if(value ==3)
                         {
                             String imageUrl = "@drawable/blobneutral";
                             int imageResource = getResources().getIdentifier(imageUrl, null, getActivity().getPackageName());
                             averageMoodBlob.setImageDrawable(getResources().getDrawable(imageResource, getActivity().getApplicationContext().getTheme()));
                             txtAverageMood.setText("Average Mood");
                             txtCurrentMood.setText("Normal");
                         }else if(value == 4)
                         {
                             String imageUrl = "@drawable/blobsmile";
                             int imageResource = getResources().getIdentifier(imageUrl, null, getActivity().getPackageName());
                             averageMoodBlob.setImageDrawable(getResources().getDrawable(imageResource, getActivity().getApplicationContext().getTheme()));
                             txtAverageMood.setText("Average Mood");
                             txtCurrentMood.setText("Happy");
                         }else if(value ==5)
                         {
                             String imageUrl = "@drawable/blobgrin";
                             int imageResource = getResources().getIdentifier(imageUrl, null, getActivity().getPackageName());
                             averageMoodBlob.setImageDrawable(getResources().getDrawable(imageResource, getActivity().getApplicationContext().getTheme()));
                             txtAverageMood.setText("Average Mood");
                             txtCurrentMood.setText("Very Happy");
                         }else
                         {
                             int id = getResources().getIdentifier("drawable/error", null, null);
                             averageMoodBlob.setImageResource(id);
                             txtAverageMood.setText("No information");
                             txtCurrentMood.setText("N/A");
                         }

                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }


}



