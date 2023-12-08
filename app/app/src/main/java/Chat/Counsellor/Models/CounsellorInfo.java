package Chat.Counsellor.Models;

import com.example.mhaprototype.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class CounsellorInfo {
    private UserModel userInfo;
    private Counsellor counsellorInfo;

    public CounsellorInfo(JSONObject object) throws JSONException {
        JSONObject user = object.getJSONObject("userInfo");
        userInfo = new UserModel(user);

        JSONObject counsellor = object.getJSONObject("counsellorInfo");
        counsellorInfo = new Counsellor(counsellor);
    }

    public UserModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserModel userInfo) {
        this.userInfo = userInfo;
    }

    public Counsellor getCounsellorInfo() {
        return counsellorInfo;
    }

    public void setCounsellorInfo(Counsellor counsellorInfo) {
        this.counsellorInfo = counsellorInfo;
    }

    private class Counsellor{
        @SerializedName("CounsellorID")
        @Expose
        private int counsellorID;

        @SerializedName("PracticeNum")
        @Expose
        private String practiceNum;

        @SerializedName("HighestCertificate")
        @Expose
        private String highestCertificate;

        public Counsellor(JSONObject object) throws JSONException {
            counsellorID = object.getInt("counsellorID");
            practiceNum = object.optString("practiceNum");
            highestCertificate = object.optString("highestCertificate");
        }

        public int getCounsellorID() {
            return counsellorID;
        }

        public void setCounsellorID(int counsellorID) {
            this.counsellorID = counsellorID;
        }

        public String getPracticeNum() {
            return practiceNum;
        }

        public void setPracticeNum(String practiceNum) {
            this.practiceNum = practiceNum;
        }

        public String getHighestCertificate() {
            return highestCertificate;
        }

        public void setHighestCertificate(String highestCertificate) {
            this.highestCertificate = highestCertificate;
        }
    }
}


