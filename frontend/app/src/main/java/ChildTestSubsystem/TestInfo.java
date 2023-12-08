package ChildTestSubsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ChildMoodTracker.Answers;

public class TestInfo {

    @SerializedName("testDetails")
    @Expose
    private Test testDetails;


    @SerializedName("testQuestions")
    @Expose
    private ArrayList<Questions> testQuestions;

    @SerializedName("testAnswers")
    @Expose
    private ArrayList<Answers> testAnswers;


    public Test getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(Test testDetails) {
        this.testDetails = testDetails;
    }

    public ArrayList<Questions> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(ArrayList<Questions> testQuestions) {
        this.testQuestions = testQuestions;
    }

    public ArrayList<Answers> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(ArrayList<Answers> testAnswers) {
        this.testAnswers = testAnswers;
    }



}

/*
//composition classes
public class Questions {

    @SerializedName("questionID")
    @Expose
    private int questionID;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("testID")
    @Expose
    private int testID;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }
}


//class for answers
public class Answers {

    @SerializedName("answerID")
    @Expose
    private int answerID;

    @SerializedName("answer")
    @Expose
    private String answer;


    @SerializedName("answerValue")
    @Expose
    private int answerValue;


    @SerializedName("questionID")
    @Expose
    private int questionID;


    //getter and setter methods
    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(int answerValue) {
        this.answerValue = answerValue;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}


public class Test
{
    @SerializedName("testID")
    @Expose
    private int testID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("psychID")
    @Expose
    private int psychID;

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPsychID() {
        return psychID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }*/


