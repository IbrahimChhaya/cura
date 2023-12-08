package psychologistHome;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingsGeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingsGeneralFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    APIInterface apiInterface;

    BarChart barChart;
    TextView txtPatientIncrease;
    TextView txtNewPatients;
    BarDataSet barDataSet1;
    ArrayList barEntries;
    ArrayList<MonthBookingCount> monthCountList = new ArrayList<>();
    String[] days = new String[]{" ","Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    public BookingsGeneralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingsGeneralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingsGeneralFragment newInstance(String param1, String param2) {
        BookingsGeneralFragment fragment = new BookingsGeneralFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_bookings_general, container, false);
        barChart = rootView.findViewById(R.id.barChart_view);
        txtPatientIncrease = rootView.findViewById(R.id.txtPatientIncrease);
        txtNewPatients = rootView.findViewById(R.id.txtNewPatients);

        //setPatientIncrease();
        setNewAppointments();
        setNewPatientPerMonth();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        barDataSet1 = new BarDataSet(getBarEntriesOne(),"");
        barDataSet1.setColor(getActivity().getApplicationContext().getResources().getColor(R.color.purple_200));
        barDataSet1.setColor(Color.parseColor("#C56EFF"));
        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1);
        //formatting the data
        MyDecimalValueFormatter formatter = new MyDecimalValueFormatter();
        data.setValueTextSize(12f);
        data.setValueFormatter(formatter);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);
        barChart.getLegend().setEnabled(false);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(false);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
       // xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
       // xAxis.setGranularityEnabled(true);



        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(12);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.75f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animate();

        // below line is to group bars
        // and add spacing to it.
        //barChart.groupBars(0, groupSpace, barSpace);

        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
        return rootView;

    }

    // array list for first set
    private ArrayList<BarEntry> getBarEntriesOne() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, GlobalVariables.monthBookingCount.get(0).getBookingCount()));
        barEntries.add(new BarEntry(2f, GlobalVariables.monthBookingCount.get(1).getBookingCount()));
        barEntries.add(new BarEntry(3f, GlobalVariables.monthBookingCount.get(2).getBookingCount()));
        barEntries.add(new BarEntry(4f, GlobalVariables.monthBookingCount.get(3).getBookingCount()));
        barEntries.add(new BarEntry(5f, GlobalVariables.monthBookingCount.get(4).getBookingCount()));
        barEntries.add(new BarEntry(6f, GlobalVariables.monthBookingCount.get(5).getBookingCount()));
        barEntries.add(new BarEntry(7f, GlobalVariables.monthBookingCount.get(6).getBookingCount()));
        barEntries.add(new BarEntry(8f, GlobalVariables.monthBookingCount.get(7).getBookingCount()));
        barEntries.add(new BarEntry(9f, GlobalVariables.monthBookingCount.get(8).getBookingCount()));
        barEntries.add(new BarEntry(10f, GlobalVariables.monthBookingCount.get(9).getBookingCount()));
        barEntries.add(new BarEntry(11f, GlobalVariables.monthBookingCount.get(10).getBookingCount()));
        barEntries.add(new BarEntry(12f, GlobalVariables.monthBookingCount.get(11).getBookingCount()));
        return barEntries;
    }


    private void setPatientIncrease()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        apiInterface.getPatientPercentIncrease(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.code()==200)
                {
                    txtPatientIncrease.setText(String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });

    }

    private void setNewPatientPerMonth()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);
        apiInterface.getNumberOfNewPatientsThisMonth(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200)
                {
                    txtNewPatients.setText(String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }

    public void setNewAppointments()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put("dateNow","1999-06-15");
        hashmap.put("psychID",GlobalVariables.loggedInUser.getUserID());

        apiInterface.getNumBookingsThisMonth("application/json; charset=utf-8", hashmap).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200)
                {
                    txtPatientIncrease.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }


    //value  formatter class to remove the decimals
    public class MyDecimalValueFormatter extends ValueFormatter {

        private DecimalFormat mFormat;

        public MyDecimalValueFormatter() {
            mFormat = new DecimalFormat("#");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }
    }



}