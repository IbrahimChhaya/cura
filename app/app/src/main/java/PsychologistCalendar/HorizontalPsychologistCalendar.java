package PsychologistCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.AddBooking;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.MainActivity;
import com.example.mhaprototype.PsychologistCalendarModel;
import com.example.mhaprototype.R;
import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;
import com.github.badoualy.datepicker.TimelineView;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HorizontalPsychologistCalendar extends AppCompatActivity {

    private ListView mListView;
    private String titles[] = {"Hello","2","2","2","2","2","2"};
    private String descriptions[] = {"Hello2","2","2","2","2","2","2"};
    private String Timings[] = {"Hello3","2","2","2","2","2","2"};


    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<String> TypeList = new ArrayList<>();
    private ArrayList<String> TimeList = new ArrayList<>();
    private ArrayList<Integer> calendarIDList = new ArrayList<>();

    private static final String TAG = "Testing";
    CustomCalendar customCaldr;
    APIInterface apiInterface;
    CardView cardView;
    CardView cardView2;
    TableLayout tblPsychLayout;
    int LinkStatus;

    ArrayList<String> mondayBookingList = new ArrayList<>();
    ArrayList<String> tuesdayBookingList = new ArrayList<>();
    ArrayList<String> thursdayBookingList = new ArrayList<>();
    ArrayList<String> wednesdayBookingList = new ArrayList<>();
    ArrayList<String> fridayBookingList = new ArrayList<>();
    ArrayList<String> saturdayBookingList = new ArrayList<>();
    ArrayList<String> sundayBookingList = new ArrayList<>();

    ArrayList<String> mondayList = new ArrayList<>();
    ArrayList<String> tuesdayList = new ArrayList<>();
    ArrayList<String> wednesdayList = new ArrayList<>();
    ArrayList<String> thursdayList = new ArrayList<>();
    ArrayList<String> fridayList = new ArrayList<>();
    ArrayList<String> saturdayList = new ArrayList<>();
    ArrayList<String> sundayList = new ArrayList<>();

    //for the horizontal calendar library
    private MonthView monthView;
    private TimelineView timelineView;
    private TextView selectedDate2;

    String dayOfWeek = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_psychologist_calendar);

        ImageView btnPrev = (ImageView)findViewById(R.id.imgbtnPrev);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.bookToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.textView26);
        heading.setText("Select Date and Time");


        selectedDate2 = (TextView) findViewById(R.id.txtSelectedDate2);
        //instantiating and setting the colours for the calendar
        monthView = (MonthView)findViewById(R.id.mti_month_view);
        timelineView = (TimelineView)findViewById(R.id.mti_timeline);


        monthView.setBackgroundColor(Color.parseColor("#FF9565"));
        monthView.setColorBeforeSelection(Color.WHITE);
        monthView.setDefaultColor(Color.WHITE);
        timelineView.setBackgroundColor(Color.WHITE);

        timelineView.setDayLabelColor(Color.BLACK);
        timelineView.setDateLabelColor(Color.BLACK);
        timelineView.setDateLabelSelectedColor(Color.WHITE);
        timelineView.setLabelColor(Color.WHITE);

        DatePickerTimeline timeline =(DatePickerTimeline)findViewById(R.id.timeline);
        //replace the firstvisible day and date to the current date and day
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        timeline.setFirstVisibleDate(currentYear, currentMonth-1, currentDay);

        timeline.setLastVisibleDate(currentYear+1, Calendar.DECEMBER, 31);

        //checking if the date is in proper format
        String strCurrentMonth = "";
        String strCurrentDay = "";
        if(currentMonth <10)
        {
            strCurrentMonth = "0"+currentMonth;
        }
        if(currentDay<10)
        {
            strCurrentDay = "0"+currentDay;
        }
        String currentDate = currentYear+"-"+strCurrentMonth+"-"+strCurrentDay;
        Date date = null;

        dayOfWeek = getSelectedDay(date,currentDate);
        //setting the calendar on create


/*        timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {

                String strMonth = ""+(month+1);
                String strDay =""+day;
                if((month+1)<=9)
                {
                    strMonth = "0"+(month+1);
                }
                if(day<=9)
                {
                    strDay = "0"+day;
                }
                String  sDate = year+
                        "-" + strMonth +
                        "-" + strDay;

                //Toast.makeText(HorizontalPsychologistCalendar.this,sDate,Toast.LENGTH_SHORT).show();

                //getting the day of the week
                Date date = null;
                String dayOfWeek = "";
                dayOfWeek = getSelectedDay(date,sDate);
                String dateToDisplay = getCurrentDate(sDate);
                selectedDate2.setText(dayOfWeek+","+ dateToDisplay.toString());
                //Toast.makeText(HorizontalPsychologistCalendar.this,dateToDisplay.toString(), Toast.LENGTH_LONG).show();
                //setting the calendars
                if(dayOfWeek.equals("Monday"))
                {
                    setFinalCalendar2(mondayList,mondayBookingList,sDate);

                }else if (dayOfWeek.equals("Thursday"))
                {
                    setFinalCalendar2(thursdayList,thursdayBookingList,sDate);
                }else if(dayOfWeek.equals("Tuesday"))
                {
                    setFinalCalendar2(tuesdayList,tuesdayBookingList,sDate);
                }else if(dayOfWeek.equals("Wednesday"))
                {
                    setFinalCalendar2(wednesdayList,wednesdayBookingList,sDate);

                }else if(dayOfWeek.equals("Friday"))
                {
                    setFinalCalendar2(fridayList,fridayBookingList,sDate);
                }else if(dayOfWeek.equals("Saturday"))
                {
                    setFinalCalendar2(saturdayList,saturdayBookingList,sDate);
                }else if(dayOfWeek.equals("Sunday"))
                {
                    setFinalCalendar2(sundayList,sundayBookingList,sDate);
                }
            }
        });*/

        //api calls
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getPsychCalendar(GlobalVariables.psychologistID).enqueue(new Callback<List<PsychologistCalendarModel>>() {
            @Override
            public void onResponse(Call<List<PsychologistCalendarModel>> call, Response<List<PsychologistCalendarModel>> response) {
                if(response.code()==200)
                {
                    String day = "";

                    for(PsychologistCalendarModel psychMod : response.body())
                    {
                        //method to read all the schedules to a list of a specific day
                        addToScheduleList(psychMod);
                    }
                    //set the initial calendar here
                    setInitialCalendar(currentDate,dayOfWeek);
                    timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
                        @Override
                        public void onDateSelected(int year, int month, int day, int index) {

                            String strMonth = ""+(month+1);
                            String strDay =""+day;
                            if((month+1)<=9)
                            {
                                strMonth = "0"+(month+1);
                            }
                            if(day<=9)
                            {
                                strDay = "0"+day;
                            }
                            String  sDate = year+
                                    "-" + strMonth +
                                    "-" + strDay;

                            //Toast.makeText(HorizontalPsychologistCalendar.this,sDate,Toast.LENGTH_SHORT).show();

                            //getting the day of the week
                            Date date = null;
                            String dayOfWeek = "";
                            dayOfWeek = getSelectedDay(date,sDate);
                            String dateToDisplay = getCurrentDate(sDate);
                            selectedDate2.setText(dayOfWeek+","+ dateToDisplay.toString());
                            //Toast.makeText(HorizontalPsychologistCalendar.this,dateToDisplay.toString(), Toast.LENGTH_LONG).show();
                            //setting the calendars
                            if(dayOfWeek.equals("Monday"))
                            {
                                setFinalCalendar2(mondayList,mondayBookingList,sDate);

                            }else if (dayOfWeek.equals("Thursday"))
                            {
                                setFinalCalendar2(thursdayList,thursdayBookingList,sDate);
                            }else if(dayOfWeek.equals("Tuesday"))
                            {
                                setFinalCalendar2(tuesdayList,tuesdayBookingList,sDate);
                            }else if(dayOfWeek.equals("Wednesday"))
                            {
                                setFinalCalendar2(wednesdayList,wednesdayBookingList,sDate);

                            }else if(dayOfWeek.equals("Friday"))
                            {
                                setFinalCalendar2(fridayList,fridayBookingList,sDate);
                            }else if(dayOfWeek.equals("Saturday"))
                            {
                                setFinalCalendar2(saturdayList,saturdayBookingList,sDate);
                            }else if(dayOfWeek.equals("Sunday"))
                            {
                                setFinalCalendar2(sundayList,sundayBookingList,sDate);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PsychologistCalendarModel>> call, Throwable t) {

            }
        });

        apiInterface.getPsychBookings2(GlobalVariables.psychologistID).enqueue(new Callback<List<BookingModel2>>() {
            @Override
            public void onResponse(Call<List<BookingModel2>> call, Response<List<BookingModel2>> response) {
                if(response.code()==200)
                {
                    for(BookingModel2 mod : response.body())
                    {
                        Date date = null;
                        String day = getSelectedDay(date,mod.getDate());
                        //checking if the booking is cancelled or for future
                        if(mod.getCancelled().equals("Future") || mod.getCancelled().equals("Cancelled"))
                        {
                        if(day.equals("Monday"))
                        {
                            mondayBookingList.add(mod.getDate() + " " + mod.getType()+" "+ mod.getTime().getHours());
                        }else if (day.equals("Tuesday"))
                        {
                            tuesdayBookingList.add(mod.getDate() + " " + mod.getType()+" "+ mod.getTime().getHours());
                        }else if(day.equals("Wednesday"))
                        {
                            wednesdayBookingList.add(mod.getDate() + " " + mod.getType()+" "+ mod.getTime().getHours());
                        }else if(day.equals("Thursday"))
                        {
                            thursdayBookingList.add(mod.getDate() + " " + mod.getType()+" "+ mod.getTime().getHours());
                        }else
                        {
                            fridayBookingList.add(mod.getDate() + " " + mod.getType()+" "+ mod.getTime().getHours());
                        }
                    }
                    }

                    for(String s : mondayBookingList)
                    {
                        Log.d(TAG, "onMondayList: "+ s);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookingModel2>> call, Throwable t) {

            }
        });

        //Getting the status of the link
        HashMap<String, Object> userPsych = new HashMap<>();

        userPsych.put("UserID", GlobalVariables.GuardianChildID);
        userPsych.put("PsychID", GlobalVariables.psychologistID);

        apiInterface.isLinked("application/json; charset=utf-8", userPsych).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200)
                {
                    LinkStatus = response.body();
                    String ID = String.valueOf(LinkStatus);
                  //  Toast.makeText(getApplicationContext(),ID,Toast.LENGTH_SHORT).show();

                }else
                {
                    Log.d(TAG, "onErrorLink: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });


        //dont touch this
        mListView = (ListView)findViewById(R.id.calendarListView);


        //button to go back to the previous activity
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    //utility function to extract the starthour from the dates
    public int extractHour(String startString)
    {
        String stString = startString.toString().replace("T"," ");
        int hourToReturn =0;
        try {
            Date TestDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stString);
            String newString =  new SimpleDateFormat("HH:mm").format(TestDate);
            String ans = newString.replace(":00","");
            hourToReturn = Integer.parseInt(ans);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hourToReturn;

    }

    //utility method to filter out days and their data for he psychologist
    public void addToScheduleList(PsychologistCalendarModel psychMod)
    {
        if(psychMod.isClosed()==false) {
            if (psychMod.getDayOfWeek().equals("Monday")) {
                mondayList.add("Monday" + " " + psychMod.getSingleStart() + " " + psychMod.getSingleEnd()
                        + " " + psychMod.getRepeatStart() + " " + psychMod.getRepeatEnd() + " " + psychMod.getPsychID() + " " +
                        psychMod.getPsychCalendarID() + " " + psychMod.isClosed());

            } else if (psychMod.getDayOfWeek().equals("Tuesday")) {
                tuesdayList.add("Tuesday" + " " + psychMod.getSingleStart() + " " + psychMod.getSingleEnd()
                        + " " + psychMod.getRepeatStart() + " " + psychMod.getRepeatEnd() + " " + psychMod.getPsychID() + " " +
                        psychMod.getPsychCalendarID() + " " + psychMod.isClosed());
            } else if (psychMod.getDayOfWeek().equals("Wednesday")) {
                wednesdayList.add("Wednesday" + " " + psychMod.getSingleStart() + " " + psychMod.getSingleEnd()
                        + " " + psychMod.getRepeatStart() + " " + psychMod.getRepeatEnd() + " " + psychMod.getPsychID() + " " +
                        psychMod.getPsychCalendarID() + " " + psychMod.isClosed());
            } else if (psychMod.getDayOfWeek().equals("Thursday")) {
                thursdayList.add("Thursday" + " " + psychMod.getSingleStart() + " " + psychMod.getSingleEnd()
                        + " " + psychMod.getRepeatStart() + " " + psychMod.getRepeatEnd() + " " + psychMod.getPsychID() + " " +
                        psychMod.getPsychCalendarID() + " " + psychMod.isClosed());
            } else if (psychMod.getDayOfWeek().equals("Friday")) {
                fridayList.add("Friday" + " " + psychMod.getSingleStart() + " " + psychMod.getSingleEnd()
                        + " " + psychMod.getRepeatStart() + " " + psychMod.getRepeatEnd() + " " + psychMod.getPsychID() + " " +
                        psychMod.getPsychCalendarID() + " " + psychMod.isClosed());
            }

        }

    }

    //utility method to set the psychologist calendar
    public void setFinalCalendar2(ArrayList<String> CalendarList, ArrayList<String> bookingList,String sDate)
    {
        int bookingListSize = bookingList.size();
        int calendSize = CalendarList.size();

        mListView.setAdapter(null);
        TimeList = new ArrayList<String>();
        TypeList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        calendarIDList = new ArrayList<Integer>();
        int countSessions = 0;
        for(String s : CalendarList)
        {
            countSessions++;
            StringTokenizer token = new StringTokenizer(s," ");
            String day = token.nextToken();
            String singleStart = token.nextToken();
            String singleEnd = token.nextToken();
            String repeatStart = token.nextToken();
            String repeatEnd = token.nextToken();
            double psychID = Double.parseDouble(token.nextToken());
            double calendarID = Double.parseDouble(token.nextToken());
            boolean closed = Boolean.parseBoolean(token.nextToken());

            int intCalendarID = ((int) calendarID);

            int intEndRepeat = extractHour(repeatEnd);
            int intStartRepeat = extractHour(repeatStart);

            int intEndFirst = extractHour(singleEnd);
            int intStartFirst = extractHour(singleStart);

            int repeatDifference = intEndRepeat - intStartRepeat;
            int startOld = intStartRepeat;

            int firstDifference = intEndFirst - intStartFirst;
            //int startNew = intStartRepeat;
            int startFirstOld = intStartFirst;

            //calendarIDList.add(intCalendarID);

            if(LinkStatus== GlobalVariables.psychologistID)//GlobalVariables.psychologistID)
            {
                for(int x = 0 ; x < repeatDifference ; x++)
                {
                    titleList.add("Available Session");
                    TypeList.add("Recurring Booking Session");
                    if(startOld<10)
                    {
                        TimeList.add("0"+startOld+":00");
                    }else
                    {
                        TimeList.add(startOld+":00");
                    }
                    //+ "-"+ ++startOld + ""+":00");
                    calendarIDList.add(intCalendarID);
                    startOld++;

                    int check = 0;
                    for(String bk : bookingList)
                    {
                        StringTokenizer bookingToken = new StringTokenizer(bk, " ");
                        String bookingDate = bookingToken.nextToken();
                        bookingDate = bookingDate.replace("T00:00:00", "");
                        String bookingType = bookingToken.nextToken();
                        double dblStartHour = Double.parseDouble(bookingToken.nextToken());
                        int intStartHour = ((int) dblStartHour);
                        String strStartHour  = String.valueOf(intStartHour);
                        if(intStartHour < 10)
                            strStartHour = "0"+strStartHour;

                        if(TimeList.contains(strStartHour+":00") && bookingDate.equals(sDate) && TypeList.contains("Single Booking Session") )
                        {
                            TimeList.remove(strStartHour+":00");
                            TypeList.remove("Single Booking Session");
                            titleList.remove("Available Session");
                        }else if(TimeList.contains(strStartHour+":00") && TypeList.contains("Recurring Booking Session")&& bookingDate.equals(sDate))
                        {
                            TimeList.remove(strStartHour+":00");
                            TypeList.remove("Recurring Booking Session");
                            titleList.remove("Available Session");
                        }
                    }

                }


            }else //means that there is no link to current psychologist
            {
                for(int i = 0 ; i < firstDifference; i++)
                {
                    titleList.add("Available Session");
                    TypeList.add("Single Booking Session");

                    if(startFirstOld<10)
                    {
                        TimeList.add("0"+startFirstOld+":00");
                    }else
                    {
                        TimeList.add(startFirstOld+":00");
                    }
                    //TimeList.add(startFirstOld+":00");
                    startFirstOld++;
                    calendarIDList.add(intCalendarID);

                    for (String bk : bookingList) {
                        StringTokenizer bookingToken = new StringTokenizer(bk, " ");
                        String bookingDate = bookingToken.nextToken();
                        bookingDate = bookingDate.replace("T00:00:00", "");
                        String bookingType = bookingToken.nextToken();
                        double dblStartHour = Double.parseDouble(bookingToken.nextToken());
                        int intStartHour = ((int) dblStartHour);
                        String strStartHour  = String.valueOf(intStartHour);
                        if(intStartHour < 10)
                            strStartHour = "0"+strStartHour;

                        if(TimeList.contains(strStartHour+":00") && bookingDate.equals(sDate) && TypeList.contains("Single Booking Session") )
                        {
                            TimeList.remove(strStartHour+":00");
                            TypeList.remove("Single Booking Session");
                            titleList.remove("Available Session");
                        }else if(TimeList.contains(strStartHour+":00") && TypeList.contains("Recurring Booking Session"))
                        {
                            TimeList.remove(strStartHour+":00");
                            TypeList.remove("Recurring Booking Session");
                            titleList.remove("Available Session");
                        }

                    }
                }
            }

        }
        int size = TimeList.size();
        if(size !=0)
        {
            createTimeList(sDate);
            for(int x= 0 ; x < TimeList.size(); x++)
            {
                int startHour = Integer.parseInt(TimeList.get(x).replace(":00",""));
                int endHour = startHour+1;
                String strEndhour="";
                if(endHour < 10)
                {
                    strEndhour = "0"+endHour+":00";
                }else
                {
                    strEndhour = endHour+":00";
                }
                TimeList.set(x,TimeList.get(x)+"-" + strEndhour);//++startHour+":00");
            }
        }
        if(countSessions==0)
        {
            selectedDate2.setText("No appointments available");
        }
        myAdapter adapter = new myAdapter(sDate);
        mListView.setAdapter(adapter);

    }

    //utility method to get the day for a current date selected
    private String getSelectedDay(Date date, String sDate)
    {
        date = null;
        String dayOfWeek = "";
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(sDate);
            dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOfWeek;
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

    public void setInitialCalendar(String currentDate,String dayOfWeek)
    {
        if(dayOfWeek.equals("Monday"))
        {
            setFinalCalendar2(mondayList,mondayBookingList,currentDate);

        }else if (dayOfWeek.equals("Thursday"))
        {
            setFinalCalendar2(thursdayList,thursdayBookingList,currentDate);
        }else if(dayOfWeek.equals("Tuesday"))
        {
            setFinalCalendar2(tuesdayList,tuesdayBookingList,currentDate);
        }else if(dayOfWeek.equals("Wednesday"))
        {
            setFinalCalendar2(wednesdayList,wednesdayBookingList,currentDate);

        }else if(dayOfWeek.equals("Friday"))
        {
            setFinalCalendar2(fridayList,fridayBookingList,currentDate);
        }else if(dayOfWeek.equals("Saturday"))
        {
            setFinalCalendar2(saturdayList,saturdayBookingList,currentDate);
        }else if(dayOfWeek.equals("Sunday"))
        {
            setFinalCalendar2(sundayList,sundayBookingList,currentDate);
        }
    }





    //making the custom adapter class

    public class myAdapter extends BaseAdapter {

        private String sDate;
        public myAdapter(String sDate)
        {
            this.sDate = sDate;
        }

        @Override
        public int getCount() {
            return TimeList.size(); //titles.length;
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
            convertView = getLayoutInflater().inflate(R.layout.calendar_item_psychologist,parent,false);
            ImageView imgCalendar = (ImageView)findViewById(R.id.img_Calendar);
            TextView txtTitle = convertView.findViewById(R.id.cal_Title);
            TextView txtDescription = convertView.findViewById(R.id.calDescription);
            TextView txtBookingTime = convertView.findViewById(R.id.calBookingTime);
            TextView txtCalendarID = convertView.findViewById(R.id.calendarID);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(PsychologistCalendar.this, txtDescription.getText().toString()
                    //  + "CalendarID " + txtCalendarID.getText().toString(),Toast.LENGTH_SHORT).show();

                    GlobalVariables.bookingDate = sDate;
                    GlobalVariables.CalendarID = Integer.parseInt(txtCalendarID.getText().toString());
                    String StartTime = txtBookingTime.getText().toString();
                    StartTime = "T"+ StartTime.substring(0,5)+":00";
                   // Toast.makeText(HorizontalPsychologistCalendar.this,"StartTime" + StartTime + " CalendariD" + calendarIDList.get(position).toString() ,Toast.LENGTH_SHORT).show();

                    GlobalVariables.startTime = StartTime;

                    Intent i = new Intent(HorizontalPsychologistCalendar.this, AddBooking.class);
                    i.putExtra("bookingType",TypeList.get(position));
                    startActivity(i);
                }
            });

           /* txtTitle.setText(titles[position]);
            txtDescription.setText(descriptions[position]);
            txtBookingTime.setText(Timings[position]);*/
            txtTitle.setText(titleList.get(position));
            txtDescription.setText(TypeList.get(position));
            txtBookingTime.setText(TimeList.get(position));
            txtCalendarID.setText(calendarIDList.get(position).toString());

            return convertView;
        }
    }


    private int getCurrentHour()
    {

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        return hour;
    }

    public void createTimeList(String sDate)
    {
        ArrayList<String> tempTimeList = new ArrayList<>();
        int currentHour = getCurrentHour();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());


        Date dateCurrent=null;
        Date dateSelected=null;
        try {
            dateCurrent = sdf.parse(currentDate);
            dateSelected = sdf.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dateSelected.compareTo(dateCurrent) == 0) {
            for (int x = 0; x < TimeList.size(); x++) {
                int startHour = Integer.parseInt(TimeList.get(x).replace(":00", ""));
                if (startHour > currentHour) {
                    tempTimeList.add(TimeList.get(x));
                    TypeList.add(TypeList.get(x));
                    titleList.add("Available Session");
                }
            }
        }
        else {
            tempTimeList = TimeList;
            System.out.println("Same old shit");
        }
        TimeList = tempTimeList;
    }

}