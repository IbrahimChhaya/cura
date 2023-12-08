package ChildReports;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.model.EventDay;
import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import Appointment.AppointmentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CustomCalendar customCaldr;
    APIInterface apiInterface;

    //child ID from the bundle
    int ChildID;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        CompactCalendarView compactCalendarView;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
        TextView txtMonthName = rootView.findViewById(R.id.txtMonthName);
        TextView txtCancelled = rootView.findViewById(R.id.txtCancelled);
        TextView txtMissed = rootView.findViewById(R.id.txtMissed);
        TextView txtMissedDetail = rootView.findViewById(R.id.txtMissedDetail);
        TextView txtCancelledDetail = rootView.findViewById(R.id.txtCancelledDetail);


        compactCalendarView = rootView.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //getting the child ID from the bundle
        Bundle bundle = getArguments();
        ChildID = bundle.getInt("ChildID");

        //getting the current month and year
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDate = formatter.format(date);
        String monthToDisplay = getCurrentMonth(currentDate);
        txtMonthName.setText(monthToDisplay);





        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        //setting the missed and cancelled session on create
        apiInterface.GetUserBookingsForApp2(ChildID).enqueue(new Callback<List<AppointmentModel1>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel1>> call, Response<List<AppointmentModel1>> response) {
                int CountCancelled = 0;
                int CountMissed = 0;
                if(response.code()==200)
                {
                    for(AppointmentModel1 ap1 : response.body())
                    {
                        String appointmentDate = ap1.getBookingInfo().getDate().substring(0,10);
                        String appointmentDateConfirm = getCurrentDate(appointmentDate);

                        if(ap1.getBookingInfo().getCancelled().equals("Cancelled") && monthToDisplay.equals(appointmentDateConfirm)  )
                        {
                            CountCancelled++;
                        }else if(ap1.getBookingInfo().getCancelled().equals("Missed") && monthToDisplay.equals(appointmentDateConfirm))
                        {
                            CountMissed++;
                        }
                    }
                    //updating the report cards on month swipe
                    txtCancelled.setText(String.valueOf(CountCancelled));
                    txtMissed.setText(String.valueOf(CountMissed));
                    txtCancelledDetail.setText("For "+ monthToDisplay);
                    txtMissedDetail.setText("For "+ monthToDisplay);
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel1>> call, Throwable t) {

            }
        });



        apiInterface.GetUserBookingsForApp2(ChildID).enqueue(new Callback<List<AppointmentModel1>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel1>> call, Response<List<AppointmentModel1>> response) {
                if(response.code()==200)
                {
                    String strDate = "";
                    for(AppointmentModel1 ap1 : response.body())
                    {
                        addEvents(strDate,compactCalendarView,ap1);
                    }
                    compactCalendarView.setEventIndicatorStyle(2); // hella important
                    compactCalendarView.setCurrentSelectedDayTextColor(Color.WHITE);
                    compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                        @Override
                        public void onDayClick(Date dateClicked) {

                        }


                        @Override
                        public void onMonthScroll(Date firstDayOfNewMonth) {
                            txtMonthName.setText(simpleDateFormat.format(firstDayOfNewMonth).toString().replace("-",""));
                            String monthDate = simpleDateFormat.format(firstDayOfNewMonth).toString().replace("-","");


                            apiInterface.GetUserBookingsForApp2(ChildID).enqueue(new Callback<List<AppointmentModel1>>() {
                                @Override
                                public void onResponse(Call<List<AppointmentModel1>> call, Response<List<AppointmentModel1>> response) {
                                    int CountCancelled = 0;
                                    int CountMissed = 0;
                                    if(response.code()==200)
                                    {
                                        for(AppointmentModel1 ap1 : response.body())
                                        {
                                            String appointmentDate = ap1.getBookingInfo().getDate().substring(0,10);
                                            String appointmentDateConfirm = getCurrentDate(appointmentDate);

                                            if(ap1.getBookingInfo().getCancelled().equals("Cancelled") && monthDate.equals(appointmentDateConfirm)  )
                                            {
                                                CountCancelled++;
                                            }else if(ap1.getBookingInfo().getCancelled().equals("Missed") && monthDate.equals(appointmentDateConfirm))
                                            {
                                                CountMissed++;
                                            }
                                        }
                                        //updating the report cards on month swipe
                                        txtCancelled.setText(String.valueOf(CountCancelled));
                                        txtMissed.setText(String.valueOf(CountMissed));
                                        txtCancelledDetail.setText("For "+ monthDate);
                                        txtMissedDetail.setText("For "+ monthDate);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<AppointmentModel1>> call, Throwable t) {

                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel1>> call, Throwable t) {

            }
        });

        /*apiInterface.getAllUpcomingBookings2(1).enqueue(new Callback<List<AppointmentModel1>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel1>> call, Response<List<AppointmentModel1>> response) {
                if(response.code()==200)
                {
                    String strDate = "";
                    for(AppointmentModel1 ap1 : response.body())
                    {
                        addEvents(strDate,compactCalendarView,ap1);

                    }

                    compactCalendarView.setEventIndicatorStyle(2); // hella important
                    compactCalendarView.setCurrentSelectedDayTextColor(Color.WHITE);
                    compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                        @Override
                        public void onDayClick(Date dateClicked) {

                        }

                        @Override
                        public void onMonthScroll(Date firstDayOfNewMonth) {
                            txtMonthName.setText(simpleDateFormat.format(firstDayOfNewMonth).toString());

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel1>> call, Throwable t) {

            }
        });

        apiInterface.getAllCancelledBookings2(1).enqueue(new Callback<List<AppointmentModel1>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel1>> call, Response<List<AppointmentModel1>> response) {
                if(response.code()==200)
                {
                    if(response.body()!= null)
                    {
                        String strDate = "";
                        for(AppointmentModel1 ap2 : response.body())
                        {
                            addEvents(strDate,compactCalendarView,ap2);
                        }

                        compactCalendarView.setEventIndicatorStyle(2); // hella important
                        compactCalendarView.setCurrentSelectedDayTextColor(Color.WHITE);
                        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                            @Override
                            public void onDayClick(Date dateClicked) {

                            }

                            @Override
                            public void onMonthScroll(Date firstDayOfNewMonth) {
                                txtMonthName.setText(simpleDateFormat.format(firstDayOfNewMonth).toString());

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel1>> call, Throwable t) {

            }
        });*/

        return rootView;//inflater.inflate(R.layout.fragment_schedule, container, false);
    }



    public void addEvents(String strDate ,CompactCalendarView compactCalendarView, AppointmentModel1 ap1)
    {
        strDate = ap1.getBookingInfo().getDate().substring(0,10);
        //convert that date to a long
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        //second step to convert the date to a long epoch
        SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
        String currentTime = crunchifyFormat.format(date1);
        long epochTime = 0;
        try {
            Date date = crunchifyFormat.parse(currentTime);
            epochTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strEpochTime = String.valueOf(epochTime);
        long myValue = Long.parseLong(strEpochTime);

        //comparing the date to current day
        //getting the current date
        Date date = new Date();

        //create the event   assign colours accordingly
        if(ap1.getBookingInfo().getCancelled().equals("Future"))
        {
            if(date1.compareTo(date) > 0)
            {
                Event ev1 = new Event(Color.GREEN, myValue);
                compactCalendarView.addEvent(ev1);
            }

        }else if(ap1.getBookingInfo().getCancelled().equals("Cancelled") || ap1.getBookingInfo().getCancelled().equals("Missed"))
        {
            Event ev1 = new Event(Color.RED, myValue);
            compactCalendarView.addEvent(ev1);
        }else if(ap1.getBookingInfo().getCancelled().equals("Attended"))
        {
            Event ev1 = new Event(Color.parseColor("#ADD8E6"), myValue);
            compactCalendarView.addEvent(ev1);
        }


    }

    public String getCurrentMonth(String strDate)
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
            case "04":
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
        return  MonthName+" "+Year;//Day + " " + MonthName +" " + Year;

    }


    //getting the current date

    public String getCurrentDate(String strDate)
    {
        StringTokenizer token = new StringTokenizer(strDate,"-");
        String MonthName = "";
        String Year = token.nextToken();
        String Month = token.nextToken();
        String Day = token.nextToken();



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
            case "04":
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
        return  MonthName +" " + Year;

    }

}