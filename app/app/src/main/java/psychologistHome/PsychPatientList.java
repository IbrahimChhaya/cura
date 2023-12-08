package psychologistHome;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.UserModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import PsychologistList.PsychologistClass;
import PsychologistList.PsychologistProfile;
import PsychologistList.SearchablePsychologistList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PsychPatientList extends AppCompatActivity {

    EditText edtSearch;
    ImageView imgBackButton;
    ListView lstPsychList;

    ArrayList<PatientDetails> patientArrayList;
    ListViewAdapter adapter;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psych_patient_list);

        edtSearch = findViewById(R.id.PsychSearch);
        imgBackButton = findViewById(R.id.imgBack);
        lstPsychList = findViewById(R.id.lstPsychList);

        patientArrayList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.GetPsychologistPatients(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.code()==200)
                {
                    for(UserModel usr : response.body())
                    {
                        PatientDetails pDetails = new PatientDetails(usr.getUserID(),usr.getName(),usr.getDob(),
                                usr.getProfilePicture(),usr.getGrade());
                        patientArrayList.add(pDetails);
                    }

                    lstPsychList.setTextFilterEnabled(true);
                    adapter = new ListViewAdapter(PsychPatientList.this,patientArrayList);
                    lstPsychList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });


        //adding the search capability
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = edtSearch.getText().toString().toLowerCase();
                adapter.filter(text);

            }
        });








        //button for back
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //listview adapter class
    //listview adapter
    public class ListViewAdapter extends BaseAdapter {

        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<PatientDetails> patientList = null;
        private ArrayList<PatientDetails> arraylist;

        public ListViewAdapter(Context context,
                               List<PatientDetails> patientList) {
            mContext = context;
            this.patientList = patientList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<PatientDetails>();
            this.arraylist.addAll(patientList);
        }

        public class ViewHolder {
            TextView txtRandom;
            TextView txtPsychQualification;
            TextView txtPsychLocation;
            ImageView imgPsychProfile;

        }

        @Override
        public int getCount() {
            return patientList.size();
        }

        @Override
        public PatientDetails getItem(int position) {
            return patientList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ListViewAdapter.ViewHolder holder;
            if (view == null) {
                holder = new ListViewAdapter.ViewHolder();
                view = inflater.inflate(R.layout.psychologist_card_item, null);
                // Locate the TextViews in listview_item.xml
                holder.txtRandom = (TextView) view.findViewById(R.id.txtPsychName);
                holder.imgPsychProfile = (ImageView) view.findViewById(R.id.imgpsychImage);
                holder.txtPsychQualification = (TextView) view.findViewById(R.id.txtPsychQualification);
                holder.txtPsychLocation = (TextView) view.findViewById(R.id.txtpsychLocation);
                view.setTag(holder);
            } else {
                holder = (ListViewAdapter.ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.txtRandom.setText(patientList.get(position).getChildName());

            if(patientList.get(position).getChildGrade() !=null)
            {
                if(patientList.get(position).getChildGrade().equals("0"))
                {
                    holder.txtPsychQualification.setText("Grade: Not Specified");
                }
                holder.txtPsychQualification.setText("Grade: "+patientList.get(position).getChildGrade());
            }else
            {
                holder.txtPsychQualification.setText("Grade: Not Specified");
            }


            if(patientList.get(position).getChildDOB() != null)
            {
                String dob = patientList.get(position).getChildDOB().substring(0,10);
                dob = getCurrentDate(dob);
                holder.txtPsychLocation.setText("Date of Birth: "+dob);
            }else
            {
                holder.txtPsychLocation.setText("Not Specified");
            }


            // Set the results into ImageView
            String imageName = patientList.get(position).getChildImage().replaceAll(".(png|jpe?g|svg)", "");
            String imageUrl = "@drawable/" + imageName;
            int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
            holder.imgPsychProfile.setImageDrawable(getResources().getDrawable(imageResource, getApplicationContext().getTheme()));



            // Listen for ListView Item Click
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent i = new Intent(PsychPatientList.this,PatientReport.class);
                    i.putExtra("ChildID", patientList.get(position).getChildID());
                    i.putExtra("ChildImage", imageName);
                    i.putExtra("ChildName",patientList.get(position).getChildName());
                    startActivity(i);
                    // Send single item click data to SingleItemView Class
                    //sending the relevant data to the psych profile
                    //Toast.makeText(SearchablePsychologistList.this, "You clicked"+psychlist.get(position).getPsychName(), Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            patientList.clear();
            if (charText.length() == 0) {
                patientList.addAll(arraylist);
            } else {
                for (PatientDetails wp : arraylist) {
                    if (wp.getChildName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        patientList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

    //function to format the birthday
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