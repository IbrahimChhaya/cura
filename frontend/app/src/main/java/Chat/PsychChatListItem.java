package Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PsychChatListItem {

    @SerializedName("psychID")
    @Expose
    private int psychID;

    @SerializedName("psychName")
    @Expose
    private String psychName;

    @SerializedName("lastMessage")
    @Expose
    private ChatModel lastMessage;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    public PsychChatListItem(int psychID, String psychName, ChatModel lastMessage, String profilePicture) {
        this.psychID = psychID;
        this.psychName = psychName;
        this.lastMessage = lastMessage;
        this.profilePicture = profilePicture;
    }


    public int getPsychID() {
        return psychID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }

    public String getPsychName() {
        return psychName;
    }

    public void setPsychName(String psychName) {
        this.psychName = psychName;
    }

    public ChatModel getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatModel lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


}
