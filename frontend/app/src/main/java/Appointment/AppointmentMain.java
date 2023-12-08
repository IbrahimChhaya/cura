package Appointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Appointment.ErrorPages.NoAppointmentPage;
import PsychologistList.PsychologistList2;
import PsychologistList.psychologistListView;
import StartUpStuff.SignInRoles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentMain extends AppCompatActivity {

    private static final String TAG ="testing click" ;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_main);
        Button btnAddBooking = (Button)findViewById(R.id.btnBooking);
        ImageView imgBackButton = (ImageView)findViewById(R.id.backButtonAppointment);


        String userType = GlobalVariables.loggedInUser.getUserType().toString();
        if(GlobalVariables.loggedInUser.getUserType().equals("Child"))
        {
            btnAddBooking.setVisibility(View.INVISIBLE);
        }

        //that whole freaking code
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        //getting the userbookings
       apiInterface.GetUserBookingsForApp(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModel>>() {
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
                       Intent i = new Intent(AppointmentMain.this, NoAppointmentPage.class);
                       startActivity(i);
                   }
                   int myCount = count;
                   RecyclerView rvItem = findViewById(R.id.rv_item);
                   LinearLayoutManager layoutManager = new LinearLayoutManager(AppointmentMain.this);
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

       btnAddBooking.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(AppointmentMain.this, PsychologistList2.class);//ViewPsychologist psychologistListView
               startActivity(intent);
           }
       });

       imgBackButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(GlobalVariables.loggedInUser.getUserType().equals("Parent"))
               {
                   Intent i = new Intent(AppointmentMain.this, Guardian_Home.class);
                   startActivity(i);
               }else if(GlobalVariables.loggedInUser.getUserType().equals("Child"))
               {
                   Intent i = new Intent(AppointmentMain.this, Child_Home.class);
                   startActivity(i);
               }

           }
       });


    /*    RecyclerView rvItem = findViewById(R.id.rv_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AppointmentMain.this);
        ItemAdapter itemAdapter = new ItemAdapter(buildItemList());
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(layoutManager);*/
    }

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

                        //getting the bookingID
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

                        String info1 =  Html.fromHtml("<b>Booking for: </b>").toString();
                        String info2 = Html.fromHtml("<b>Type: </b>").toString();
                        String info3 = Html.fromHtml("<b>Timings: </b>").toString();

                        SubItem subItem = new SubItem(info1+ ap.getPatientName(), info2 + ap.getBookingInfo().getType(), info3 +startTime + " - "+ endTime, bookingID,
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

           // SubItem subItem = new SubItem("Booking for "+ appModel.getPatientName(), "type " + appModel.getBookingInfo().getType()," Start Time" + appModel.getBookingInfo().getTime().getHours());
            //subItemList.add(subItem);

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

    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        if(GlobalVariables.loggedInUser.getUserType().equals("Parent"))
        {
            Intent i = new Intent(AppointmentMain.this, Guardian_Home.class);
            startActivity(i);
        }else if(GlobalVariables.loggedInUser.getUserType().equals("Child"))
        {
            Intent i = new Intent(AppointmentMain.this, Child_Home.class);
            startActivity(i);
        }

    }
}
