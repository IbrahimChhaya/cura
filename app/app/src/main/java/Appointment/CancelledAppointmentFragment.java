package Appointment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Appointment.ErrorPages.NoAppointmentPage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelledAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledAppointmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG ="Testing Cancelled" ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    APIInterface apiInterface;

    public CancelledAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelledAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelledAppointmentFragment newInstance(String param1, String param2) {
        CancelledAppointmentFragment fragment = new CancelledAppointmentFragment();
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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        //getting the userbookings
        apiInterface.getAllCancelledBookings(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModel>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel>> call, Response<List<AppointmentModel>> response) {
                if(response.code()==200 && response.body()!=null)
                {
                    int count =0;
                    ArrayList<AppointmentModel> appModelList = new ArrayList<>();
                    for(AppointmentModel appModel : response.body())
                    {
                        count++;
                        appModelList.add(appModel);
                        Log.d(TAG, "onTesting Appointment Model: " + appModel.getBookingInfo().getType() + "Child name " + appModel.getPatientName());
                    }
                    //if no appointments show the no appointment page
                    if(count==0)
                    {
                        Intent i = new Intent(getActivity(), NoAppointmentPage.class);
                        startActivity(i);
                    }
                    int myCount = count;
                    RecyclerView rvItem = getView().findViewById(R.id.rv_item);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    ItemAdapter itemAdapter = null;
                    try {
                        itemAdapter = new ItemAdapter(buildItemList(appModelList));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    rvItem.setAdapter(itemAdapter);
                    rvItem.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel>> call, Throwable t) {

            }
        });
        return inflater.inflate(R.layout.fragment_cancelled_appointment, container, false);
    }


    //utility methods
    private List<Item> buildItemList(List<AppointmentModel> appModel) throws ParseException {
        List<Item> itemList = new ArrayList<>();
        ArrayList<String> normalDates = new ArrayList<>();
        ArrayList<String> noDuplicates = new ArrayList<>();

        for(AppointmentModel ap : appModel)
        {
            normalDates.add(ap.getBookingInfo().getDate().substring(0,10));
        }
        for(String s : normalDates)
        {
            if(!noDuplicates.contains(s))
            {
                noDuplicates.add(s);
            }
        }


        int count = 0;
        for (String nd : noDuplicates) {
            List<SubItem> subItemList = new ArrayList<>();
            for (AppointmentModel ap : appModel) {
                if (nd.equals(ap.getBookingInfo().getDate().substring(0,10))) {
                    count++;
                    int startHour = (int) ap.getBookingInfo().getTime().getHours();
                    int minutes = (int) ap.getBookingInfo().time.getMinutes();

                    int bookingID = (int) ap.getBookingInfo().getBookingID();
                    int endHour = startHour+1;
                    String startTime =" ";
                    String endTime="";

                    if (minutes < 10) {
                        startTime = startHour + ":0" + minutes;
                        endTime = endHour +":0" + minutes;

                    } else {
                        startTime = startHour + ":" + minutes;
                        endTime = endHour +":" + minutes;
                    }

                    if(startHour<10)
                    {
                        startTime = "0"+ startHour+":00";
                    }else
                    {
                        startTime = startTime;
                    }

                    if(endHour <10)
                    {
                        endTime = "0"+endHour+":00";
                    }else
                    {
                        endTime = endTime;
                    }

                    String info1 =  Html.fromHtml("<b>Booking for: </b>").toString();
                    String info2 = Html.fromHtml("<b>Type: </b>").toString();
                    String info3 = Html.fromHtml("<b>Timings: </b>").toString();

                    SubItem subItem = new SubItem(info1+ ap.getPatientName(), info2 + ap.getBookingInfo().getType(), info3 +startTime + " - "+ endTime,bookingID,
                            ap.getBookingInfo().getDate().toString(),ap.getBookingInfo().getCancelled(),ap.getBookingInfo().getType());
                    subItemList.add(subItem);

                      /*  Item item = new Item("Item " + nd, buildSubItemList(ap));
                        itemList.add(item);*/
                }
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date mydate = dateFormat.parse(nd.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mydate);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int year = calendar.get(Calendar.YEAR);
            String monthName = getMonthName(month);

            Item item = new Item( dayOfMonth +" "+monthName + " " + year , subItemList);
            itemList.add(item);
        }

        return itemList;
    }

    private List<SubItem> buildSubItemList(AppointmentModel appModel) {
        List<SubItem> subItemList = new ArrayList<>();

        //SubItem subItem = new SubItem("Booking for "+ appModel.getPatientName(), "type " + appModel.getBookingInfo().getType()," Start Time" + appModel.getBookingInfo().getTime().getHours());
        // subItemList.add(subItem);

        return subItemList;
    }

    //method to get the month name
    private static String getMonthName(int month) {

        String monthName = null;
        switch (month) {
            case 0:
                monthName = "January";
                break;
            case 1:
                monthName = "February";
                break;
            case 2:
                monthName = "March";
                break;
            case 3:
                monthName = "April";
                break;
            case 4:
                monthName = "May";
                break;
            case 5:
                monthName = "June";
                break;
            case 6:
                monthName = "July";
                break;
            case 7:
                monthName = "August";
                break;
            case 8:
                monthName = "September";
                break;
            case 9:
                monthName = "October";
                break;
            case 10:
                monthName = "November";
                break;
            case 11:
                monthName = "December";
                break;
        }

        return monthName;
    }
}