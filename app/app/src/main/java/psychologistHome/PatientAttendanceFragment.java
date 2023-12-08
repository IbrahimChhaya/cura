package psychologistHome;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientAttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientAttendanceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PieChart pieChart;
    PieData pieData;
    APIInterface apiInterface;
    TextView txtTotalCancelled;
    TextView txtTotalAttended;
    TextView txtTotalMissed;
    List<PieEntry> pieEntryList = new ArrayList<>();

    public PatientAttendanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientAttendanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientAttendanceFragment newInstance(String param1, String param2) {
        PatientAttendanceFragment fragment = new PatientAttendanceFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_patient_attendance, container, false);
        txtTotalCancelled = rootView.findViewById(R.id.txtTotalCancelled);
        txtTotalAttended = rootView.findViewById(R.id.txtTotalAttended);
        txtTotalMissed = rootView.findViewById(R.id.txtTotalMissed);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getNumEachBookingPerPsych(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<numAttendedCancelledMissed>() {
            @Override
            public void onResponse(Call<numAttendedCancelledMissed> call, Response<numAttendedCancelledMissed> response) {
                if(response.code()==200)
                {
                    txtTotalAttended.setText(String.valueOf(response.body().getAttended()));
                    txtTotalCancelled.setText(String.valueOf(response.body().getCancelled()));
                    txtTotalMissed.setText(String.valueOf(response.body().getMissed()));


                    pieChart = rootView.findViewById(R.id.pieChart);
                    pieChart.setUsePercentValues(true);
                    pieEntryList.add(new PieEntry(response.body().getMissed(),"Missed"));
                    pieEntryList.add(new PieEntry(response.body().getCancelled(),"Cancelled"));
                    pieEntryList.add(new PieEntry(response.body().getAttended(),"Attended"));
                    PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
                    pieChart.getDescription().setEnabled(false);

                    //setting the colours
                    int [] color={ Color.rgb(255,149,101),Color.rgb(244,65,101),Color.rgb(17,214,255)
                    };
                    pieDataSet.setColors(color);
                    pieData = new PieData(pieDataSet);
                    pieData.setValueTextSize(15f);
                    pieData.setValueTextColor(Color.WHITE);
                    pieChart.setData(pieData);

                    pieDataSet.setValueFormatter(new MyValueFormatter());

                    pieChart.setDrawSliceText(false);
                    pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                    pieChart.invalidate();


                }
            }

            @Override
            public void onFailure(Call<numAttendedCancelledMissed> call, Throwable t) {

            }
        });





        return rootView;
    }

    public class MyValueFormatter extends ValueFormatter {
        private final DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + " %"; // e.g. append percentage sign
        }
    }
}