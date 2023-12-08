package Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatModel {
    @SerializedName("messageID")
    @Expose
    private int messageID;

    @SerializedName("linkID")
    @Expose
    private int linkID;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("read")
    @Expose
    private boolean read;

    @SerializedName("senderID")
    @Expose
    private int senderID;

    public ChatModel(int messageID, int linkID, String message, String date, boolean read, int senderID) {
        this.messageID = messageID;
        this.linkID = linkID;
        this.message = message;
        this.date = date;
        this.read = read;
        this.senderID = senderID;
    }

    public ChatModel(JSONObject object) throws JSONException{
        messageID = object.getInt("messageID");
        linkID = object.getInt("linkID");
        message = object.getString("message");
        date = object.getString("date");
        read = object.getBoolean("read");
        senderID = object.getInt("senderID");
    }

    // Getter Methods

    public int getMessageID() {
        return messageID;
    }

    public int getLinkID() {
        return linkID;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public boolean getRead() {
        return read;
    }

    public int getSenderID() {
        return senderID;
    }

    // Setter Methods

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }
}
