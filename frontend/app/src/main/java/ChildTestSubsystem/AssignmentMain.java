package ChildTestSubsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ChildMoodTracker.Answers;
import ChildMoodTracker.ChildAnswer;
import ChildMoodTracker.ChildTest;
import ChildMoodTracker.MoodTracker;
import ChildTestSubsystem.Model.AssignedTest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignmentMain extends AppCompatActivity {
    private static final String TAG = "testing this";
    MaterialCardView crdAns1;
    MaterialCardView crdAns2;
    MaterialCardView crdAns3;
    MaterialCardView crdAns4;

    MaterialButton btnSubmitAssignment;

    APIInterface apiInterface;

    TextView txtAns1;
    TextView txtAns2;
    TextView txtAns3;
    TextView txtAns4;
    MaterialTextView txtError;

    TextView txtQuestion;
    MaterialCardView crdBack;
    MaterialCardView crdNext;

    ArrayList<Integer> questionIDs = new ArrayList<>();
    ArrayList<String> questionText = new ArrayList<>();
    ArrayList<Integer> answerIDs = new ArrayList<>();
    ArrayList<String> answerText = new ArrayList<>();


    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();
    int numberOfAnswers;
    int QuestionCounter = 0;
    int AnswerCounter=0;
    int AnswerID=0;
    int numQuestions = 0;
    int numQuestionsBack = 0;
    //linked list that will store the ids of the answers chosen(on backtracking it will keep on updating)
    //ArrayList<Integer> finalAnswerIDs = new ArrayList<>();
    LinkedList<Integer> finalAnswerIDs = new LinkedList<>();
    ArrayList<ChildAnswer> childAnswerList = new ArrayList<>();


    //new arraylists for positions
    ArrayList<Integer> QuestionPosition = new ArrayList<>();

    //progressbar
    ProgressBar progressBarAssignment;
    int progressPerQuestion = 0;
    int progressBackQuestion = 0;
    int testPoints = 0;

    //back button
    ImageView imgBackButton;

    TextView assignmentTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_main);


        //Initializing all the views *****************************************************************
        crdAns1 = findViewById(R.id.crdAns1);
        crdAns2 = findViewById(R.id.crdAns2);
        crdAns3 = findViewById(R.id.crdAns3);
        crdAns4 = findViewById(R.id.crdAns4);
        crdBack = findViewById(R.id.crdBack);
        crdNext = findViewById(R.id.crdNext);

        txtAns1 = findViewById(R.id.txtTestAns1);
        txtAns2 = findViewById(R.id.txtTestAns2);
        txtAns3 = findViewById(R.id.txtTestAns3);
        txtAns4 = findViewById(R.id.txtTestAns4);
        txtError = findViewById(R.id.txtError);
        txtQuestion = findViewById(R.id.txtQuestion);
        assignmentTitle = findViewById(R.id.txtAssignmentMain);
        btnSubmitAssignment = findViewById(R.id.btnSubmitAssignment);
        progressBarAssignment = findViewById(R.id.assignmentProgress);
        imgBackButton = findViewById(R.id.imgBackButton);


        //programming the back button
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Initializing all the views end *****************************************************************

        //getting the test ID and the childTestID from the previous activity
        Intent i = getIntent();
        int testID = i.getIntExtra("testID",0);
        int childTestID = i.getIntExtra("childTestID",0);
        testPoints = i.getIntExtra("testPoints",0);

        //setting the assignment title
        assignmentTitle.setText(i.getStringExtra("assignmentTitle"));

        //getting the test Questions
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        HashMap<String,Object> testMap = new HashMap<>();
        testMap.put("testID", testID);
        apiInterface.fetchCompleteTest("application/json; charset=utf-8",testMap).enqueue(new Callback<TestInfo>() {
            @Override
            public void onResponse(Call<TestInfo> call, Response<TestInfo> response) {
                if(response.code()==200)
                {
                    TestInfo testInfo = response.body();

                    if(testInfo.getTestQuestions() != null && testInfo.getTestAnswers()!=null)
                    {
                        for(Questions q : testInfo.getTestQuestions())
                        {
                            numQuestions++;
                            questionIDs.add(q.getQuestionID());
                            questionText.add(q.getQuestion());
                        }

                        for(Answers ans : testInfo.getTestAnswers())
                        {
                            answerIDs.add(ans.getAnswerID());
                            answerText.add(ans.getAnswer());
                        }

                        txtQuestion.setText(questionText.get(0));
                        txtAns1.setText(answerText.get(0));
                        txtAns2.setText(answerText.get(1));
                        txtAns3.setText(answerText.get(2));
                        txtAns4.setText(answerText.get(3));
                        //setting the initial progressbar
                        progressPerQuestion = 100/numQuestions;
                        progressBackQuestion = 100/numQuestions;
                        progressBarAssignment.setProgress(0);

                    }
                }
            }

            @Override
            public void onFailure(Call<TestInfo> call, Throwable t) {

            }
        });




        //diabling the next and prev button before selection
        crdBack.setVisibility(View.INVISIBLE);
        crdNext.setVisibility(View.VISIBLE);
        crdNext.setCardBackgroundColor(Color.parseColor("#d3d3d3"));
        txtError.setVisibility(View.INVISIBLE);
        btnSubmitAssignment.setVisibility(View.INVISIBLE);

        crdAns1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crdAns1.setElevation(20);
                crdAns2.setElevation(0);
                crdAns3.setElevation(0);
                crdAns4.setElevation(0);
                //set the answerID
                AnswerID = answerIDs.get(AnswerCounter);
                txtError.setVisibility(View.INVISIBLE);
                if(QuestionCounter<questionIDs.size()-1)
                {
                    crdNext.setCardBackgroundColor(Color.parseColor("#FF9565"));
                    crdNext.setVisibility(View.VISIBLE);
                }else if(QuestionCounter<= questionIDs.size()-1)
                {
                    btnSubmitAssignment.setVisibility(View.VISIBLE);
                    crdNext.setVisibility(View.INVISIBLE);
                }

            }
        });

        crdAns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crdAns2.setElevation(20);
                crdAns1.setElevation(0);
                crdAns3.setElevation(0);
                crdAns4.setElevation(0);
                //set the answer ID
                AnswerID = answerIDs.get(AnswerCounter+1);
                txtError.setVisibility(View.INVISIBLE);
                //crdNext.setVisibility(View.VISIBLE);
                if(QuestionCounter<questionIDs.size()-1)
                {
                    crdNext.setCardBackgroundColor(Color.parseColor("#FF9565"));
                    crdNext.setVisibility(View.VISIBLE);
                }else if(QuestionCounter<= questionIDs.size()-1)
                {
                    crdNext.setVisibility(View.INVISIBLE);
                    btnSubmitAssignment.setVisibility(View.VISIBLE);
                }

            }
        });
        crdAns3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crdAns3.setElevation(20);
                crdAns2.setElevation(0);
                crdAns1.setElevation(0);
                crdAns4.setElevation(0);
                //setting the answerID
                AnswerID = answerIDs.get(AnswerCounter+2);
                txtError.setVisibility(View.INVISIBLE);
                //crdNext.setVisibility(View.VISIBLE);
                if(QuestionCounter<questionIDs.size()-1)
                {
                    crdNext.setCardBackgroundColor(Color.parseColor("#FF9565"));
                    crdNext.setVisibility(View.VISIBLE);
                }else if(QuestionCounter<= questionIDs.size()-1)
                {
                    crdNext.setVisibility(View.INVISIBLE);
                    btnSubmitAssignment.setVisibility(View.VISIBLE);
                }


            }
        });

        crdAns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crdAns4.setElevation(20);
                crdAns2.setElevation(0);
                crdAns3.setElevation(0);
                crdAns1.setElevation(0);
                txtError.setVisibility(View.INVISIBLE);
                //setting the answerID
                AnswerID = answerIDs.get(AnswerCounter+3);
                //crdNext.setVisibility(View.VISIBLE);
                if(QuestionCounter<questionIDs.size()-1)
                {
                    crdNext.setCardBackgroundColor(Color.parseColor("#FF9565"));
                    crdNext.setVisibility(View.VISIBLE);
                }else if(QuestionCounter<= questionIDs.size()-1)
                {
                    crdNext.setVisibility(View.INVISIBLE);
                    btnSubmitAssignment.setVisibility(View.VISIBLE);
                }


            }
        });


        crdNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crdAns1.getElevation() == 0 && crdAns2.getElevation()==0 && crdAns3.getElevation()==0
                        && crdAns4.getElevation()==0)
                {
                    txtError.setVisibility(View.VISIBLE);
                }else
                {
                    //setting the elevation of the card to zero
                    crdAns1.setElevation(0);
                    crdAns2.setElevation(0);
                    crdAns3.setElevation(0);
                    crdAns4.setElevation(0);
                    QuestionCounter++;
                    int size = questionText.size();
                    size++;
                    if(QuestionCounter<= questionIDs.size()-1)
                    {
                        //firstly save the answer to the answer List and then update the question******
                        finalAnswerIDs.addFirst(AnswerID);
                        int listSize = finalAnswerIDs.size();
                        //update the qustion with relevant answers
                        txtQuestion.setText(questionText.get(QuestionCounter));
                        AnswerCounter += 4;
                        txtAns1.setText(answerText.get(AnswerCounter));
                        txtAns2.setText(answerText.get(AnswerCounter+1));
                        txtAns3.setText(answerText.get(AnswerCounter+2));
                        txtAns4.setText(answerText.get(AnswerCounter+3));
                        //crdNext.setVisibility(View.INVISIBLE);
                        crdNext.setCardBackgroundColor(Color.parseColor("#d3d3d3"));

                        //set the progress bar
                        progressPerQuestion += progressPerQuestion;
                        progressBarAssignment.setProgress(progressPerQuestion);

                    }
                    //crdNext.setVisibility(View.INVISIBLE);

                    crdBack.setVisibility(View.VISIBLE);

                }
            }
        });

        btnSubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalAnswerIDs.addFirst(AnswerID);
                int size = finalAnswerIDs.size();

                for(Integer i : finalAnswerIDs)
                {

                    ChildAnswer childAnswer = new ChildAnswer(i);
                    childAnswerList.add(childAnswer);
                }

                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

                apiInterface = retrofit.create(APIInterface.class);

                HashMap<String,Object>  assignmentMap =new HashMap<>();

                ChildTest childTest = new ChildTest(childTestID,GlobalVariables.loggedInUser.getUserID(),testID);// test ID to be passed via intent as well as the child ID

                assignmentMap.put("Answers",childAnswerList);
                assignmentMap.put("TestInfo", childTest);

                apiInterface.submitTest("application/json; charset=utf-8",assignmentMap).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code()==200)
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(AssignmentMain.this).create();
                            alertDialog.setTitle("Test Submitted");
                            //alertDialog.setMessage("You got "+ testPoints+ " points!");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Thanks",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            getPoints();
                                        }
                                    });
                            alertDialog.show();

                        } else
                        {
                            Toast.makeText(AssignmentMain.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });


            }
        });

        crdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setting the card elevation to 0
                crdAns1.setElevation(0);
                crdAns2.setElevation(0);
                crdAns3.setElevation(0);
                crdAns4.setElevation(0);
                progressBackQuestion = 100- progressBackQuestion;
                progressPerQuestion -= progressBackQuestion;

                QuestionCounter--;
                if(QuestionCounter>=0)
                {
                    //removing the last answer if the user backtracks
                    int AnswerToRemove = finalAnswerIDs.removeFirst();
                    int listSize = finalAnswerIDs.size();
                    //updating the question with relevant answers
                    txtQuestion.setText(questionText.get(QuestionCounter));
                    AnswerCounter -= 4;
                    txtAns1.setText(answerText.get(AnswerCounter));
                    txtAns2.setText(answerText.get(AnswerCounter+1));
                    txtAns3.setText(answerText.get(AnswerCounter+2));
                    txtAns4.setText(answerText.get(AnswerCounter+3));

                    //setting the progress
                    progressBarAssignment.setProgress(progressBackQuestion);

                }

                if(QuestionCounter==0)
                {
                    crdBack.setVisibility(View.INVISIBLE);
                    progressBarAssignment.setProgress(0);
                }
                crdNext.setVisibility(View.VISIBLE);
                btnSubmitAssignment.setVisibility(View.INVISIBLE);
            }
        });



    }

    public void getPoints()
    {
        apiInterface.getAllChildTestsAssigned(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<AssignedTest>>() {
            @Override
            public void onResponse(Call<List<AssignedTest>> call, Response<List<AssignedTest>> response) {
                if(response.code()==200)
                {
                    Intent i = new Intent(AssignmentMain.this, ChildTestMain.class);
                    startActivity(i);

                }else
                {
                    Intent i = new Intent(AssignmentMain.this, Child_Home.class);
                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<List<AssignedTest>> call, Throwable t) {

            }
        });
    }
}