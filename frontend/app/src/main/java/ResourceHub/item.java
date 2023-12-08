package ResourceHub;

public class item {

    private String problemName;
    private String description;
    private int Image;
    private int ID;

    //constructor
    public item(String problemName,String description, int image, int ID) {
        this.problemName = problemName;
        this.description = description;
        Image = image;
        this.ID = ID;
        ;
    }

    public item(String description, int image, int problemID){
        this.description = description;
        this.Image = image;
        this.ID = problemID;
    }

    //getter and setter methods
    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getProblemID() {
        return ID;
    }

    public void setProblemID(int problemID) {
        this.ID = problemID;
    }
}
