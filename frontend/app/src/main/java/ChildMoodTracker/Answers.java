package ChildMoodTracker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("position")
    @Expose
    private int position;


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
