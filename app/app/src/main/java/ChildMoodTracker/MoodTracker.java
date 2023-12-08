package ChildMoodTracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import ChildTestSubsystem.ChildTestMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoodTracker extends AppCompatActivity {

    APIInterface apiInterface;
    CardView crdGrin;
    CardView crdHappy;
    CardView crdNormal;
    CardView crdSad;
    CardView crdCry;

    ChildTest childTestMain;

    int AnswerValue = 0;
    String Answer = "";
    Button btnSaveMood;
    int AnswerID = 0;
    boolean alreadyDone = false;
    ArrayList<Integer> answerIDList = new ArrayList<>();
    ArrayList<Integer> answerValueList = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();
    TextView txtAns1;
    TextView txtAns2;
    TextView txtAns3;
    TextView txtAns4;
    TextView txtAns5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_tracker);

        txtAns1 = findViewById(R.id.txtAns1);
        txtAns2 = findViewById(R.id.txtAns2);
        txtAns3 = findViewById(R.id.txtAns3);
        txtAns4 = findViewById(R.id.txtAns4);
        txtAns5 = findViewById(R.id.txtAns5);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.moodtoolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.textView15);
        heading.setText("Mood Tracker");

        String strCurrentDay = String.valueOf(currentDay);
        String strCurrentMonth = String.valueOf(currentMonth);
        if(currentDay < 10)
        {
            strCurrentDay = "0"+strCurrentDay;
        }
        if(currentMonth<10)
        {
            strCurrentMonth = "0"+strCurrentMonth;
        }

        String currentDate = currentYear+"-"+strCurrentMonth+"-"+strCurrentDay;

        HashMap<String,Object> childTestMap = new HashMap<>();

        childTestMap.put("date",currentDate);//currentDate);
        childTestMap.put("testID", 1);
        childTestMap.put("childID",GlobalVariables.loggedInUser.getUserID());
        //showing the options
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.checkIfMoodWasLoggedToday("application/json; charset=utf-8",childTestMap).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code()==200)
                {
                    JSONObject gsonObject = null;
                    if(response.body()!=null)
                    {
                        try {
                            gsonObject = new JSONObject(new Gson().toJson(response.body()));
                            childTestMain = new ChildTest(gsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        alreadyDone = true;
                        showAlertDialog();

                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });



        ImageView btnBackButton = (ImageView)findViewById(R.id.imgBackButton);
        crdGrin = (CardView)findViewById(R.id.crdGrin);
        crdHappy = (CardView)findViewById(R.id.crdHappy);
        crdNormal = (CardView)findViewById(R.id.crdNormal);
        crdSad = (CardView)findViewById(R.id.crdSad);
        crdCry = (CardView)findViewById(R.id.crdCry);
        btnSaveMood= (Button)findViewById(R.id.btnSaveMood);


        btnSaveMood.setVisibility(View.INVISIBLE);
//        txtAns1.setText(answerList.get(0));
//        txtAns2.setText(answerList.get(1));
//        txtAns3.setText(answerList.get(2));
//        txtAns4.setText(answerList.get(3));
//        txtAns5.setText(answerList.get(4));


        HashMap<String,Object> testMap = new HashMap<>();
        testMap.put("testID", 1);
        apiInterface.getTestAnswers("application/json; charset=utf-8",testMap).enqueue(new Callback<List<Answers>>() {
            @Override
            public void onResponse(Call<List<Answers>> call, Response<List<Answers>> response) {
                if(response.code()==200)
                {
                    for(Answers a : response.body())
                    {
                        answerIDList.add(a.getAnswerID());
                        answerValueList.add(a.getAnswerValue());
                        answerList.add(a.getAnswer());
                    }
                    //setArrayLists(response.body());

                }
               /* txtAns1.setText(answerList.get(0));
                txtAns2.setText(answerList.get(1));
                txtAns3.setText(answerList.get(2));
                txtAns4.setText(answerList.get(3));
                txtAns5.setText(answerList.get(4));*/
                setAnswers();
            }

            @Override
            public void onFailure(Call<List<Answers>> call, Throwable t) {

            }
        });


        //setting on click listeners
        crdGrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveMood.setVisibility(View.VISIBLE);
                Answer ="";
                AnswerValue = 0;
                Answer =answerList.get(0);//"I feel very happy";
                AnswerValue = answerValueList.get(0); //5;
                AnswerID = answerIDList.get(0);//1;
                crdGrin.setCardBackgroundColor(Color.parseColor("#FFFF33"));
                crdCry.setCardBackgroundColor(Color.WHITE);
                crdHappy.setCardBackgroundColor(Color.WHITE);
                crdNormal.setCardBackgroundColor(Color.WHITE);
                crdSad.setCardBackgroundColor(Color.WHITE);

            }
        });

        crdHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveMood.setVisibility(View.VISIBLE);
                Answer ="";
                AnswerValue = 0;
                Answer =answerList.get(1);//"I feel happy";
                AnswerValue = answerValueList.get(1);//4;
                AnswerID = answerIDList.get(1);//2;
                crdGrin.setCardBackgroundColor(Color.WHITE);
                crdCry.setCardBackgroundColor(Color.WHITE);
                crdHappy.setCardBackgroundColor(Color.parseColor("#62fc03"));
                crdNormal.setCardBackgroundColor(Color.WHITE);
                crdSad.setCardBackgroundColor(Color.WHITE);

            }
        });

        crdNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveMood.setVisibility(View.VISIBLE);
                Answer ="";
                AnswerValue = 0;
                Answer =answerList.get(2);//"I feel normal";
                AnswerValue = answerValueList.get(2);//3;
                AnswerID = answerIDList.get(2);//3;
                crdGrin.setCardBackgroundColor(Color.WHITE);
                crdCry.setCardBackgroundColor(Color.WHITE);
                crdHappy.setCardBackgroundColor(Color.WHITE);
                crdNormal.setCardBackgroundColor(Color.parseColor("#be9d6a"));
                crdSad.setCardBackgroundColor(Color.WHITE);

            }
        });

        crdSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveMood.setVisibility(View.VISIBLE);
                Answer ="";
                AnswerValue = 0;
                Answer =answerList.get(3);//"I feel sad";
                AnswerValue = answerValueList.get(3);//2;
                AnswerID = answerIDList.get(3);//4;
                crdGrin.setCardBackgroundColor(Color.WHITE);
                crdCry.setCardBackgroundColor(Color.WHITE);
                crdHappy.setCardBackgroundColor(Color.WHITE);
                crdNormal.setCardBackgroundColor(Color.WHITE);
                crdSad.setCardBackgroundColor(Color.parseColor("#b4b557"));

            }
        });

        crdCry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveMood.setVisibility(View.VISIBLE);
                Answer ="";
                AnswerValue = 0;
                Answer =answerList.get(4);//"I feel very sad";
                AnswerValue = answerValueList.get(4);//1;
                AnswerID = answerIDList.get(4);//5;
                crdGrin.setCardBackgroundColor(Color.WHITE);
                crdCry.setCardBackgroundColor(Color.parseColor("#0388FC"));
                crdHappy.setCardBackgroundColor(Color.WHITE);
                crdNormal.setCardBackgroundColor(Color.WHITE);
                crdSad.setCardBackgroundColor(Color.WHITE);

            }
        });

        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submitTest
                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

                apiInterface = retrofit.create(APIInterface.class);

                HashMap<String,Object>  moodMap =new HashMap<>();
                ArrayList<ChildAnswer> answerList = new ArrayList<>();
                ChildAnswer childAnswer = new ChildAnswer(AnswerID);

                answerList.add(childAnswer);

                ChildTest childTest = new ChildTest(GlobalVariables.loggedInUser.getUserID(),1);


                moodMap.put("Answers", answerList);

                if(alreadyDone)
                {
                    //int ChildTestID = childTest.getChildTestID();
                    moodMap.put("TestInfo", childTestMain);
                   // moodMap.put("childTestID",childTestMain.getChildTestID());

                    apiInterface.editTest("application/json; charset=utf-8", moodMap).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.code()==200)
                            {
                                if(response.body()==0)
                                {
                                    Intent i = new Intent(MoodTracker.this, Child_Home.class);
                                    startActivity(i);
                                    Toast.makeText(MoodTracker.this, "Mood recorded successfully", Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    Toast.makeText(MoodTracker.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });

                    //update the mood


                }else
                {
                    moodMap.put("TestInfo", childTest);
                apiInterface.submitTest("application/json; charset=utf-8", moodMap).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code()==200)
                        {
                            if(response.body()==0)
                            {
                                Intent i = new Intent(MoodTracker.this, Child_Home.class);
                                startActivity(i);
                                Toast.makeText(MoodTracker.this, "Mood recorded successfully", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(MoodTracker.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
                }

            }
        });

    }

    public void showAlertDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Re submit mood?");
        alertDialog.setMessage("Would you like to edit your mood?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();


    }

    public void setAnswers()
    {
        txtAns1.setText(answerList.get(0));
        txtAns2.setText(answerList.get(1));
        txtAns3.setText(answerList.get(2));
        txtAns4.setText(answerList.get(3));
        txtAns5.setText(answerList.get(4));
    }
    public void setArrayLists(List<Answers> a)
    {
        for(Answers ans : a)
        {
            answerIDList.add(ans.getAnswerID());
            answerValueList.add(ans.getAnswerValue());
            answerList.add(ans.getAnswer());
        }

    }


}