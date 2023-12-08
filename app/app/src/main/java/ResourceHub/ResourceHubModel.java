package ResourceHub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceHubModel {

    @SerializedName("problemID")
    @Expose
    private int problemID;

    @SerializedName("problem")
    @Expose
    private String problem;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("titleImage")
    @Expose
    private String titleImage;

    @SerializedName("colour")
    @Expose
    private String colour;




    //getter and setter methods


    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getProblemID() {
        return problemID;
    }

    public void setProblemID(int problemID) {
        this.problemID = problemID;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }
}
