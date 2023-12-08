package ResourceHub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RHubDescriptionModel {

    @SerializedName("imageID")
    @Expose
    public int imageID;

    @SerializedName("fileName")
    @Expose
    public String fileName;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("problemID")
    @Expose
    public int problemID;



    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProblemID() {
        return problemID;
    }

    public void setProblemID(int problemID) {
        this.problemID = problemID;
    }
}
