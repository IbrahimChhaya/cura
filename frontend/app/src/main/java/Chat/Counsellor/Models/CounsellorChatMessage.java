package Chat.Counsellor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CounsellorChatMessage {
    @SerializedName("chatID")
    @Expose
    private int chatID;

    @SerializedName("counsellorChatID")
    @Expose
    private int counsellorChatID;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("senderID")
    @Expose
    private int senderID;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("read")
    @Expose
    private boolean read;


    public CounsellorChatMessage(int chatID, int counsellorChatID, String date, int senderID, String message, boolean read) {
        this.chatID = chatID;
        this.counsellorChatID = counsellorChatID;
        this.date = date;
        this.senderID = senderID;
        this.message = message;
        this.read = read;
    }

    public CounsellorChatMessage(JSONObject object) throws JSONException {
        chatID = object.getInt("chatID");
        counsellorChatID = object.getInt("counsellorChatID");
        date = object.getString("date");
        senderID = object.getInt("senderID");
        message = object.getString("message");
        read = object.getBoolean("read");
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getCounsellorChatID() {
        return counsellorChatID;
    }

    public void setCounsellorChatID(int counsellorChatID) {
        this.counsellorChatID = counsellorChatID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
