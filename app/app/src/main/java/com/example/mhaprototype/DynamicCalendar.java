package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.time.temporal.TemporalAdjusters;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DynamicCalendar extends AppCompatActivity implements OnNavigationButtonClickedListener {
    private static final String TAG = "Testing";
    CustomCalendar customCaldr;
    APIInterface apiInterface;
    CardView cardView;
    CardView cardView2;
    TableLayout tblPsychLayout;
    TextView txtCalendarHeading;
    int LinkStatus;

    ArrayList<String> mondayBookingList = new ArrayList<>();
    ArrayList<String> tuesdayBookingList = new ArrayList<>();
    ArrayList<String> thursdayBookingList = new ArrayList<>();
    ArrayList<String> wednesdayBookingList = new ArrayList<>();
    ArrayList<String> fridayBookingList = new ArrayList<>();
    ArrayList<String> mondayList = new ArrayList<>();
    ArrayList<String> tuesdayList = new ArrayList<>();
    ArrayList<String> wednesdayList = new ArrayList<>();
    ArrayList<String> thursdayList = new ArrayList<>();
    ArrayList<String> fridayList = new ArrayList<>();

    //Declaring the row elements

    //declaring hashmaps for the data
    HashMap<String,Object> mondayHashMap = new HashMap<>();
    HashMap<String,Object> tuesdayHashMap = new HashMap<>();
    HashMap<String,Object> wednesdayHashMap = new HashMap<>();
    HashMap<String,Object> thursdayHashMap = new HashMap<>();
    HashMap<String,Object> fridayHashMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_calendar);


       /* Object day = mondayhashMap.get("day");
        Log.d(TAG, "onCreateMyLife: " + day.toString());*/

        customCaldr = findViewById(R.id.customCalendar);
        //initializing description hashmap
        HashMap<Object, Property> descHashmap = new HashMap<>();

        //initialize default property
        Property defaultProperty = new Property();

        defaultProperty.layoutResource = R.layout.default_view;

        //initialize and assign variables
        defaultProperty.dateTextViewResource = R.id.text_view;

        //put object and property
        descHashmap.put("default",defaultProperty);

        //for current date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("current", currentProperty);

        //for present date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("present", presentProperty);

        //For absent property

        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("absent", absentProperty);

        //set description hashmap to custom calendar
        customCaldr.setMapDescToProp(descHashmap);

        HashMap<Integer, Object> mapDateToDesc = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        mapDateToDesc.put(2, "default");
        mapDateToDesc.put(5, "default");
        mapDateToDesc.put(10, "default"); //You don't need to explicitly mention "default" description dates.
        mapDateToDesc.put(11, "default");
        mapDateToDesc.put(19, "default");
        mapDateToDesc.put(20, "default");
        mapDateToDesc.put(24, "default");
        customCaldr.setDate(calendar, mapDateToDesc);

        customCaldr.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCaldr.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);


        customCaldr.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
               /* String  sDate = selectedDate.get(Calendar.YEAR)+
                        "-" + (selectedDate.get(Calendar.MONTH)+1) +
                        "-" + selectedDate.get(Calendar.DAY_OF_MONTH);*/
                String strMonth = ""+(selectedDate.get(Calendar.MONTH)+1);
                String strDay =""+selectedDate.get(Calendar.DAY_OF_MONTH);

                //Adding 0s to the date of the calendar
                if((selectedDate.get(Calendar.MONTH)+1)<=9)
                {
                    strMonth = "0"+(selectedDate.get(Calendar.MONTH)+1);
                }
                if(selectedDate.get(Calendar.DAY_OF_MONTH)<=9)
                {
                    strDay = "0"+selectedDate.get(Calendar.DAY_OF_MONTH);
                }
                String  sDate = selectedDate.get(Calendar.YEAR)+
                        "-" + strMonth +
                        "-" + strDay;

                Date date = null;
                String dayOfWeek = "";
                dayOfWeek = getSelectedDay(date,sDate);
                String dateToDisplay = getCurrentDate(sDate);
                //displaying in a toast
                Toast.makeText(getApplicationContext(),sDate+" "+dayOfWeek,Toast.LENGTH_SHORT).show();

                //Setting up the table for the user
                tblPsychLayout =  (TableLayout)findViewById(R.id.tblpsychCalendar);
                txtCalendarHeading =  (TextView) findViewById(R.id.txtCalendarHeading);

                View tableHeading = LayoutInflater.from(DynamicCalendar.this).inflate(R.layout.psych_table_item,null,false);
                TextView txtDay = (TextView)findViewById(R.id.txtDay);
                TextView txtType = (TextView)findViewById(R.id.txtTypeBooking);
                TextView txtStartHour = (TextView)tableHeading.findViewById(R.id.txtStartHour);
                TextView txtBreakHour = (TextView)tableHeading.findViewById(R.id.txtBreakHour);
                TextView txtEndHour = (TextView)tableHeading.findViewById(R.id.txtEndHour);

                tableHeading.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                //statement to display the events of the selected date
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
                }
            }
        });


        //Get the list of all the calendars related to the psych ID
        //loop through them and check the type
        //if the type is repeat colour blue else green for first time
        //
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
                }
            }

            @Override
            public void onFailure(Call<List<PsychologistCalendarModel>> call, Throwable t) {

            }
        });

        apiInterface.getPsychBookings(GlobalVariables.psychologistID).enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                if(response.code()==200)
                {
                    for(BookingModel mod : response.body())
                    {
                        Date date = null;
                        String day = getSelectedDay(date,mod.getDate());
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

                    for(String s : mondayBookingList)
                    {
                        Log.d(TAG, "onMondayList: "+ s);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {

            }
        });

        //Getting the status of the link
//        apiInterface.isLinked(GlobalVariables.GuardianChildID).enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if(response.code()==200)
//                {
//                    LinkStatus = response.body();
//                    String ID = String.valueOf(LinkStatus);
//                    Toast.makeText(getApplicationContext(),ID,Toast.LENGTH_SHORT).show();
//
//                }else
//                {
//                    Log.d(TAG, "onErrorLink: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//
//            }
//        });

    }

    //Utility Methods start here****************************************************************************************************************

    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        Map<Integer, Object>[] arr = new Map[2];
        int weeks = newMonth.getWeeksInWeekYear();

        arr[0] = new HashMap<>();
        int z = 0;
            switch(newMonth.get(Calendar.MONTH))
            {
                case Calendar.AUGUST:
                    arr = setColour(Month.AUGUST);
                    break;

                case Calendar.SEPTEMBER:
                    arr = setColour(Month.SEPTEMBER);
                    break;

                case Calendar.JULY:
                    arr = setColour(Month.JULY);
                    break;

                case Calendar.JANUARY:
                    arr = setColour(Month.JANUARY);
                    break;

                case Calendar.FEBRUARY:
                    arr = setColour(Month.FEBRUARY);
                    break;

                case Calendar.MARCH:
                    arr = setColour(Month.MARCH);
                    break;

                case Calendar.APRIL:
                    int day = newMonth.getFirstDayOfWeek();
                    arr = setColour(Month.APRIL);
                    break;

                case Calendar.MAY:
                    arr = setColour(Month.MAY);
                    break;

                case Calendar.JUNE:
                    arr = setColour(Month.JUNE);
                    break;

                case Calendar.OCTOBER:
                    arr = setColour(Month.OCTOBER);
                    break;

                case Calendar.NOVEMBER:
                    arr = setColour(Month.NOVEMBER);
                    break;

                case Calendar.DECEMBER:
                    arr = setColour(Month.DECEMBER);
                    break;

            }
        return arr;
    }

    //utility method to set colours

    public Map<Integer, Object>[] setColour(Month month)
    {
        LocalDate date = null;
        int monthnumber = month.getValue();

        if(monthnumber==1)
        {
            date = LocalDate.of(2021, month.JANUARY, 16);   
        }else if(monthnumber==2)
        {
            date = LocalDate.of(2021, month.FEBRUARY, 16);
        }else if(monthnumber==3)
        {
            date = LocalDate.of(2021, month.MARCH, 16);
        }else if(monthnumber==4)
        {
            date = LocalDate.of(2021, month.APRIL, 16);
        }else if(monthnumber==5)
        {
            date = LocalDate.of(2021, month.MAY, 16);
        }else if(monthnumber==6)
        {
            date = LocalDate.of(2021, month.JUNE, 16);
        }else if(monthnumber==7)
        {
            date = LocalDate.of(2021, month.JULY, 16);
        }else if(monthnumber==8)
        {
            date = LocalDate.of(2021, month.AUGUST, 16);
        }else if(monthnumber==9)
        {
            date = LocalDate.of(2021, month.SEPTEMBER, 16);
        }else if(monthnumber==10)
        {
            date = LocalDate.of(2021, month.OCTOBER, 16);
        }else if(monthnumber==11)
        {
            date = LocalDate.of(2021, month.NOVEMBER, 16);
        }else if(monthnumber==12)
        {
            date = LocalDate.of(2021, month.DECEMBER, 16);
        }
        
        int z = 0;
        Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();
        //LocalDate date = LocalDate.now();
        LocalDate dateOfFirstMonday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)); //returns 2016-07-16
        String day = dateOfFirstMonday.toString();
        StringTokenizer token = new StringTokenizer(day,"-");
        int yearFEB = Integer.parseInt(token.nextToken());
        int monthFEB = Integer.parseInt(token.nextToken());
        //get the first Monday
        int firstMonday = Integer.parseInt(token.nextToken()); //return 16 //first sunday
        int firstTuesday = firstMonday+1; //first sunday
        int firstWednesday = firstTuesday+1;
        int firstThursday = firstWednesday+1;
        int firstFriday = firstThursday+1;

        for(int x = 0 ; x < 5 ; x++)
        {
            arr[0].put(firstMonday+z,"current");
            arr[0].put(firstTuesday+z,"current");
           /* arr[0].put(firstWednesday+z,"current");
            arr[0].put(firstThursday+z,"current");
            arr[0].put(firstFriday+z,"current");*/
            z+=7;
        }

        return arr;
    }

    //utility method to get the current date on selected date
    public String getCurrentDate(String strDate)
    {
        StringTokenizer token = new StringTokenizer(strDate,"-");
        String MonthName = "";
        String Day = token.nextToken();
        String Month = token.nextToken();
        String Year = token.nextToken();

        switch(Month)
        {
            case "1":
                MonthName = "January";
                break;
            case "2":
                MonthName = "February";
                break;
            case "3":
                MonthName = "March";
                break;
            case "4":
                MonthName = "April";
                break;
            case "5":
                MonthName = "May";
                break;
            case "6":
                MonthName = "June";
                break;
            case "7":
                MonthName = "July";
                break;
            case "8":
                MonthName = "August";
                break;
            case "9":
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

        return Day + " " + MonthName +" " + Year;

    }

    //utility method to filter out days and their data for he psychologist
    public void addToScheduleList(PsychologistCalendarModel psychMod)
    {
      /*  String finalStart = " ";
        int hour = (int) psychMod.startHour.getHours();
        int minutes = (int) psychMod.startHour.getMinutes();
        //remember to add the calendariD and the psychologist ID

        if (minutes < 10) {
            finalStart = hour + ":0" + minutes;
        } else {
            finalStart = hour + ":" + minutes;
        }*/

        if(psychMod.getDayOfWeek().equals("Monday"))
        {
            mondayList.add("Monday"+" "+ psychMod.getSingleStart()+" "+psychMod.getSingleEnd()
                    +" "+psychMod.getRepeatStart()+" "+ psychMod.getRepeatEnd() +" "+psychMod.getPsychID() + " "+
                    psychMod.getPsychCalendarID()+" "+ psychMod.isClosed());
           /* mondayHashMap.put("day" , "Monday");
            mondayHashMap.put("singleStart", psychMod.getSingleStart());
            mondayHashMap.put("singleEnd", psychMod.getSingleEnd());
            mondayHashMap.put("repeatStart",psychMod.getRepeatStart());
            mondayHashMap.put("repeatEnd",psychMod.getRepeatEnd());
            mondayHashMap.put("psychID", psychMod.getPsychID());
            mondayHashMap.put("psychCalendarID", psychMod.getPsychCalendarID());
            mondayHashMap.put("isClosed", psychMod.isClosed());*/

        }else if(psychMod.getDayOfWeek().equals("Tuesday"))
        {
            tuesdayList.add("Tuesday"+" "+ psychMod.getSingleStart()+" "+psychMod.getSingleEnd()
                    +" "+psychMod.getRepeatStart()+" "+ psychMod.getRepeatEnd() +" "+psychMod.getPsychID() + " "+
                    psychMod.getPsychCalendarID()+" "+ psychMod.isClosed());
        }
        else if(psychMod.getDayOfWeek().equals("Wednesday"))
        {
            wednesdayList.add("Wednesday"+" "+ psychMod.getSingleStart()+" "+psychMod.getSingleEnd()
                    +" "+psychMod.getRepeatStart()+" "+ psychMod.getRepeatEnd() +" "+psychMod.getPsychID() + " "+
                    psychMod.getPsychCalendarID()+" "+ psychMod.isClosed());
        }else if(psychMod.getDayOfWeek().equals("Thursday"))
        {
            thursdayList.add("Thursday"+" "+ psychMod.getSingleStart()+" "+psychMod.getSingleEnd()
                    +" "+psychMod.getRepeatStart()+" "+ psychMod.getRepeatEnd() +" "+psychMod.getPsychID() + " "+
                    psychMod.getPsychCalendarID()+" "+ psychMod.isClosed());
        }else if(psychMod.getDayOfWeek().equals("Friday"))
        {
            fridayList.add("Friday"+" "+ psychMod.getSingleStart()+" "+psychMod.getSingleEnd()
                    +" "+psychMod.getRepeatStart()+" "+ psychMod.getRepeatEnd() +" "+psychMod.getPsychID() + " "+
                    psychMod.getPsychCalendarID()+" "+ psychMod.isClosed());
        }

    }


    //utility method to get the day for a current date selected
    private String getSelectedDay(Date date,String sDate)
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


    private void alertDialog(String message) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);

        dialog.setTitle("Booking Details");
        dialog.setMessage(message);
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    //Setting bacground colour for each row based on the booking type
    public void setbackColour(String type, View tablerow)
    {
        if(type.equals("Repeat"))
        {
            tablerow.setBackgroundColor(getResources().getColor(R.color.purple_200));
        }else if (type.equals("First"))
        {
            tablerow.setBackgroundColor(getResources().getColor(R.color.light_green));
        }
    }

    //Setting up the final calendar
    public void setFinalCalendar(ArrayList<String> CalendarList, ArrayList<String> bookingList,String sDate)
    {
        tblPsychLayout.removeViews(0, Math.max(0, tblPsychLayout.getChildCount()));
        for(String s : CalendarList)
        {
            StringTokenizer token = new StringTokenizer(s," ");
            String day = token.nextToken();
            String type = token.nextToken();
            String startHour = token.nextToken();
            String breakHour = token.nextToken();
            String endHour = token.nextToken();
            double psychID = Double.parseDouble(token.nextToken());
            double calendarId = Double.parseDouble(token.nextToken());

            int intCalendarID = ((int) calendarId);


            double startH = Double.parseDouble(startHour.toString());
            int intstartH = ((int) startH);

            double breakH =  Double.parseDouble(breakHour.toString());
            int intbreakH = ((int) breakH);

            double endH =  Double.parseDouble(endHour.toString());
            int intendH = ((int) endH);

            int difference = intendH-intstartH;
            int startOld = intstartH;
            int startNew = intstartH;
            int rowCount = 0;
            for(int x = 1 ; x <= difference ; x++)
            {
                rowCount +=1;
                int breakNext = intbreakH+1;
                View tableRow = LayoutInflater.from(DynamicCalendar.this).inflate(R.layout.psych_table_item, null, false);
                TextView dayView = (TextView) tableRow.findViewById(R.id.txtDay);
                TextView typeView = (TextView) tableRow.findViewById(R.id.txtTypeBooking);
                TextView startView = (TextView) tableRow.findViewById(R.id.txtStartHour);
                TextView breakView = (TextView) tableRow.findViewById(R.id.txtBreakHour);
                TextView endView = (TextView) tableRow.findViewById(R.id.txtEndHour);

                if(!(startNew==intbreakH)) //9
                {
                    dayView.setText(day);
                    typeView.setText(type);
                    startNew = intstartH + x;
                    startView.setText(startOld+":00"); //"8.0"
                    breakView.setText(intbreakH+"-" + breakNext);//"12.0"
                    endView.setText(startNew+":00");
                }else
                {
                    dayView.setText("");
                    typeView.setText("");
                    startNew = intstartH + x;
                    startView.setText("BREAK"); //"8.0"
                    breakView.setText("");//"12.0"
                    endView.setText("");
                }

                typeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(typeView.getText().equals("Repeat"))
                        {
                           // alertDialog("Day :" + dayView.getText() + "\n" + startView.getText()+"-"+ endView.getText());
                            String calID = String.valueOf(intCalendarID);
                            GlobalVariables.bookingDate = sDate;
                            GlobalVariables.CalendarID = intCalendarID;
                            GlobalVariables.startTime = "T"+startView.getText().toString()+":00";
                            GlobalVariables.endTime = "T"+endView.getText().toString()+":00";
                            //redirect to addbooking page
                            Intent intent = new Intent(DynamicCalendar.this,AddBooking.class);
                            startActivity(intent);
                           // Toast.makeText(getApplicationContext(),sDate+" "+"Repeat Clicked"+ "cal ID"+ calID,Toast.LENGTH_SHORT).show();
                        }else
                        {
                            //alertDialog("Day :" + dayView.getText() + "\n" + startView.getText()+"-"+ endView.getText());
                            String calID = String.valueOf(intCalendarID);
                            GlobalVariables.bookingDate = sDate;
                            GlobalVariables.CalendarID = intCalendarID;
                            GlobalVariables.startTime = "T"+startView.getText().toString()+":00";
                            GlobalVariables.endTime = "T"+endView.getText().toString()+":00";
                            //redirect to booking page
                            Intent intent = new Intent(DynamicCalendar.this,AddBooking.class);
                            startActivity(intent);
                            //Toast.makeText(getApplicationContext(),sDate+" "+"First Clicked"+"cal ID"+ calID,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                for (String bk : bookingList) {
                    StringTokenizer bookingToken = new StringTokenizer(bk, " ");
                    String bookingDate = bookingToken.nextToken();
                    bookingDate = bookingDate.replace("T00:00:00", "");
                    String bookingType = bookingToken.nextToken();
                    double dblStartHour = Double.parseDouble(bookingToken.nextToken());
                    int intStartHour = ((int) dblStartHour);
                    String strStartHour  = String.valueOf(intStartHour);
                    if(startView.getText().equals(strStartHour+":00") && bookingDate.equals(sDate) && typeView.getText().equals("First"))
                    {
                        startView.setText("Booked");
                        endView.setText("Booked");
                    }else if(startView.getText().equals(strStartHour+":00")&& typeView.getText().equals("Repeat"))
                    {
                        startView.setText("Booked");
                        endView.setText("Booked");
                    }
                }
                startOld++;


                //4 is going to be replaced with Global.psychID that we get from previous page
                if(LinkStatus == 4) {
                    //we gonna pass global psychID here
                    if(typeView.getText().toString().equals("Repeat"))//||startView.getText().toString().equals("BREAK"))
                    {
                        tblPsychLayout.addView(tableRow);
                    }
                }else
                {
                    if(typeView.getText().toString().equals("First")) //|| startView.getText().toString().equals("BREAK"))
                    {
                        tblPsychLayout.addView(tableRow);
                    }

                }

                setbackColour(typeView.getText().toString(), tableRow);
            }

        }
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

    public void setFinalCalendar2(ArrayList<String> CalendarList, ArrayList<String> bookingList,String sDate)
    {

        tblPsychLayout.removeViews(0, Math.max(0, tblPsychLayout.getChildCount()));

        for(String s : CalendarList)
        {
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

            if(LinkStatus==GlobalVariables.psychologistID)
            {
                for(int x = 0 ; x < repeatDifference ; x++)
                {
                    View tableRow = LayoutInflater.from(DynamicCalendar.this).inflate(R.layout.psych_table_item, null, false);
                    TextView dayView = (TextView) tableRow.findViewById(R.id.txtDay);
                    TextView typeView = (TextView) tableRow.findViewById(R.id.txtTypeBooking);
                    TextView startView = (TextView) tableRow.findViewById(R.id.txtStartHour);
                    TextView breakView = (TextView) tableRow.findViewById(R.id.txtBreakHour);
                    TextView endView = (TextView) tableRow.findViewById(R.id.txtEndHour);

                    dayView.setText(day);
                    typeView.setText("Repeat");
                    //startNew = intStartRepeat + x;
                    startView.setText(startOld+":00"); //"8.0"
                    // breakView.setText(intbreakH+"-" + breakNext);//"12.0"
                    startOld++;
                    endView.setText(startOld+":00");

                    typeView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog("Day :" + dayView.getText() + "\n" + startView.getText()+"-"+ endView.getText());
                            String calID = String.valueOf(intCalendarID);
                            GlobalVariables.bookingDate = sDate;
                            GlobalVariables.CalendarID = intCalendarID;
                            GlobalVariables.startTime = "T"+startView.getText().toString()+":00";
                            GlobalVariables.endTime = "T"+endView.getText().toString()+":00";
                            //redirect to addbooking page
                            Intent intent = new Intent(DynamicCalendar.this,AddBooking.class);
                            startActivity(intent);
                        }
                    });
                    for (String bk : bookingList) {
                        StringTokenizer bookingToken = new StringTokenizer(bk, " ");
                        String bookingDate = bookingToken.nextToken();
                        bookingDate = bookingDate.replace("T00:00:00", "");
                        String bookingType = bookingToken.nextToken();
                        double dblStartHour = Double.parseDouble(bookingToken.nextToken());
                        int intStartHour = ((int) dblStartHour);
                        String strStartHour  = String.valueOf(intStartHour);
                        if(startView.getText().equals(strStartHour+":00") && bookingDate.equals(sDate) && typeView.getText().equals("First"))
                        {
                            startView.setText("Booked");
                            endView.setText("Booked");
                        }else if(startView.getText().equals(strStartHour+":00")&& typeView.getText().equals("Repeat"))
                        {
                            startView.setText("Booked");
                            endView.setText("Booked");
                        }
                    }

                    if(closed == false)
                    {
                        tblPsychLayout.addView(tableRow);
                        setbackColour(typeView.getText().toString(), tableRow);
                    }

                }

            }else //means that there is no link to current psychologist
            {
                for(int i = 0 ; i < firstDifference; i++)
                {
                    View tableRow = LayoutInflater.from(DynamicCalendar.this).inflate(R.layout.psych_table_item, null, false);
                    TextView dayView = (TextView) tableRow.findViewById(R.id.txtDay);
                    TextView typeView = (TextView) tableRow.findViewById(R.id.txtTypeBooking);
                    TextView startView = (TextView) tableRow.findViewById(R.id.txtStartHour);
                    TextView breakView = (TextView) tableRow.findViewById(R.id.txtBreakHour);
                    TextView endView = (TextView) tableRow.findViewById(R.id.txtEndHour);

                    dayView.setText(day);
                    typeView.setText("First");
                    //startNew = intStartRepeat + x;
                    startView.setText(startFirstOld+":00"); //"8.0"
                    // breakView.setText(intbreakH+"-" + breakNext);//"12.0"
                    startFirstOld++;
                    endView.setText(startFirstOld+":00");

                    typeView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog("Day :" + dayView.getText() + "\n" + startView.getText()+"-"+ endView.getText());
                            String calID = String.valueOf(intCalendarID);
                            GlobalVariables.bookingDate = sDate;
                            GlobalVariables.CalendarID = intCalendarID;
                            GlobalVariables.startTime = "T"+startView.getText().toString()+":00";
                            GlobalVariables.endTime = "T"+endView.getText().toString()+":00";
                            //redirect to addbooking page
                            Intent intent = new Intent(DynamicCalendar.this,AddBooking.class);
                            startActivity(intent);
                        }
                    });
                    for (String bk : bookingList) {
                        StringTokenizer bookingToken = new StringTokenizer(bk, " ");
                        String bookingDate = bookingToken.nextToken();
                        bookingDate = bookingDate.replace("T00:00:00", "");
                        String bookingType = bookingToken.nextToken();
                        double dblStartHour = Double.parseDouble(bookingToken.nextToken());
                        int intStartHour = ((int) dblStartHour);
                        String strStartHour  = String.valueOf(intStartHour);
                        if(startView.getText().equals(strStartHour+":00") && bookingDate.equals(sDate) && typeView.getText().equals("First"))
                        {
                            startView.setText("Booked");
                            endView.setText("Booked");
                        }else if(startView.getText().equals(strStartHour+":00")&& typeView.getText().equals("Repeat"))
                        {
                            startView.setText("Booked");
                            endView.setText("Booked");
                        }
                    }

                    if(closed==false)
                    {
                        tblPsychLayout.addView(tableRow);
                        setbackColour(typeView.getText().toString(), tableRow);
                    }


                }
            }

        }

    }
}