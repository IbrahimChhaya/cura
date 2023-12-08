package psychologistHome;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientUpcomingAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientUpcomingAppointmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> bookingDateList = new ArrayList<>();
    private ArrayList<String> bookingTimeList = new ArrayList<>();
    private ArrayList<String> bookingTypeList = new ArrayList<>();
    APIInterface apiInterface;

    ListView lstPatientAppointments;

    int ChildID = 0;

    public PatientUpcomingAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientUpcomingAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientUpcomingAppointmentFragment newInstance(String param1, String param2) {
        PatientUpcomingAppointmentFragment fragment = new PatientUpcomingAppointmentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_patient_upcoming_appointment, container, false);
        lstPatientAppointments = rootView.findViewById(R.id.patientListview);

        Bundle bundle = getArguments();
        ChildID = bundle.getInt("ChildID");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getAllUpcomingBookings3(ChildID).enqueue(new Callback<List<AppointmentModelPsychologist2>>() {
            @Override
            public void onResponse(Call<List<AppointmentModelPsychologist2>> call, Response<List<AppointmentModelPsychologist2>> response) {
                if(response.code()==200)
                {
                    for(AppointmentModelPsychologist2 apModel : response.body())
                    {
                        bookingTypeList.add(apModel.getBookingInfo().getType());
                        //adding the date
                        //Adding the time****************************
                        String strDate = apModel.getBookingInfo().getDate().substring(0,10);
                        strDate = getCurrentDate(strDate);
                        int startHour = (int)apModel.getBookingInfo().getTime().getHours();
                        String strHour = "";
                        if(startHour <10)
                        {
                            strHour = "0"+startHour+":00";
                        }else
                        {

                            strHour = String.valueOf(startHour)+":00";
                        }
                        bookingDateList.add(strDate);
                        bookingTimeList.add(strHour);
                    }
                    myAdapter adapter = new myAdapter();
                    lstPatientAppointments.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModelPsychologist2>> call, Throwable t) {

            }
        });





        return rootView;
    }


    public class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bookingTypeList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.patient_upcoming_bookings_item, parent, false);

            TextView txtBookingDate = convertView.findViewById(R.id.txtBookingDate);
            TextView txtBookingType = convertView.findViewById(R.id.txtBookingType);
            TextView txtBookingTime = convertView.findViewById(R.id.txtBookingTime);

            txtBookingDate.setText("Appointment on: " + bookingDateList.get(position));
            txtBookingType.setText(bookingTypeList.get(position));
            txtBookingTime.setText(bookingTimeList.get(position));


            return convertView;
        }
    }

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
}