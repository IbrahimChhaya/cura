package Chat.Counsellor.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class CounsellorChat {
    private int counsellorChatID;
    private int counsellorID;
    private int childID;
    private int focus;

    public CounsellorChat(int counsellorChatID, int counsellorID, int childID, int focus) {
        this.counsellorChatID = counsellorChatID;
        this.counsellorID = counsellorID;
        this.childID = childID;
        this.focus = focus;
    }

    public CounsellorChat(JSONObject object) throws JSONException {
        counsellorChatID = object.getInt("counsellorChatID");
        counsellorID = object.getInt("counsellorID");
        childID = object.getInt("childID");
        focus = object.optInt("focus");
    }

    public int getCounsellorChatID() {
        return counsellorChatID;
    }

    public void setCounsellorChatID(int counsellorChatID) {
        this.counsellorChatID = counsellorChatID;
    }

    public int getCounsellorID() {
        return counsellorID;
    }

    public void setCounsellorID(int counsellorID) {
        this.counsellorID = counsellorID;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }
}
