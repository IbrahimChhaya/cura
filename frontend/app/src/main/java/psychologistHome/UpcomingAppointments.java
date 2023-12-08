package psychologistHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.BookingModel;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ChildList.ChildListRedesigned;
import ChildReports.ChildReportsMain;
import PsychologistCalendar.HorizontalPsychologistCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingAppointments extends AppCompatActivity {

    APIInterface apiInterface;
    private ListView patientListView;
    private ArrayList<String> childList = new ArrayList<>();
    private ArrayList<String> bookingTimeList = new ArrayList<>();
    private ArrayList<String> bookingTypeList = new ArrayList<>();

    private ArrayList<String> patientImage = new ArrayList<>();
    private ArrayList<String> patientName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointments);
        patientListView = findViewById(R.id.patientListview);
        ImageView imgBack = findViewById(R.id.appointmentBack);

        childList.add("Hello");
        childList.add("Hello");
        childList.add("Hello");
        childList.add("Hello");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.GetUpcomingBookings(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AppointmentModelPsychologist>>() {
            @Override
            public void onResponse(Call<List<AppointmentModelPsychologist>> call, Response<List<AppointmentModelPsychologist>> response) {
                if(response.code()==200)
                {
                    for(AppointmentModelPsychologist bModel: response.body())
                    {
                        //Adding the time****************************
                        String strDate = bModel.getDate().substring(0,10);
                        strDate = getCurrentDate(strDate);
                        int startHour = (int)bModel.getTime().getHours();
                        String strHour = "";
                        if(startHour <10)
                        {
                            strHour = strDate + "  "+"0"+startHour+":00";
                        }else
                        {
                            strHour =strDate+"  "+ String.valueOf(startHour)+":00";
                        }
                        bookingTimeList.add(strHour);
                        //Adding the time****************************

                        //Adding the type****************************
                        bookingTypeList.add(bModel.getType());
                        //Adding the type****************************
                    }
                    myAdapter adapter = new myAdapter();
                    patientListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModelPsychologist>> call, Throwable t) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
            convertView = getLayoutInflater().inflate(R.layout.psychologist_upcoming_appointment_item, parent, false);

            TextView txtPatientName = convertView.findViewById(R.id.txtPatientName);
            TextView txtBookingType = convertView.findViewById(R.id.txtBookingType);
            TextView txtBookingTime = convertView.findViewById(R.id.txtBookingTime);
            ImageView imgPatientProfilePic = convertView.findViewById(R.id.patientPic);

            txtBookingTime.setText(bookingTimeList.get(position));
            txtBookingType.setText(bookingTypeList.get(position));

            String imageName = GlobalVariables.patientImages.get(position).replaceAll(".(png|jpe?g|svg)", "");
            String imageUrl = "@drawable/" + imageName;
            int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
            imgPatientProfilePic.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));

            txtPatientName.setText(GlobalVariables.patientNames.get(position));

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