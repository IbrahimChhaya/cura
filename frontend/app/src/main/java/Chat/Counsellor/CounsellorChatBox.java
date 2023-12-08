package Chat.Counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Chat.Counsellor.Models.CounsellorChatMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CounsellorChatBox extends AppCompatActivity {
    private RecyclerView messageRecycler;
    private CounsellorMessageListAdapter messageListAdapter;
    private LinearLayoutManager LLM;
    private ArrayList<CounsellorChatMessage> messageList;
    private int assignedCounsellorID;
    private int chattingID = 0;
    private ImageView counsellorImage;
    private TextView counsellorName;

    private EditText messageInput;
    private FloatingActionButton btnSend;

    private Timer timer;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changeButtonVisibility();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        messageList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        //ui elements
        counsellorImage = findViewById((R.id.imgChatProfilePic));
        counsellorName = findViewById(R.id.chatPerson);

        messageInput = findViewById(R.id.edit_gchat_message);
        messageInput.addTextChangedListener(textWatcher);

        btnSend = findViewById(R.id.button_gchat_send);
        ImageView backArrow = findViewById(R.id.imgBackButton);
        backArrow.setOnClickListener(v -> finish());

        //get child's counsellor
        getAssignedCounsellor(new AssignedCounsellorIDCallback() {
            @Override
            public void onSuccess(@NonNull @NotNull int value) {
                assignedCounsellorID = value;
                setAssignedCounsellorID(value);
            }

            @Override
            public void onError(@NonNull @NotNull Throwable throwable) {

            }
        });

        //mark any unread counsellor messages as read on activity start

        //get list of messages
        if(timer != null)
            timer.cancel();

        timer = new Timer();
        TimerTask myTimerTask = new myTimerTask();
        timer.schedule(myTimerTask, 0, 1000);   //re-fetch message list every 5 seconds


        // onclick for sending a message
        btnSend.setOnClickListener(v -> {
            //check if typed anything
            if(messageInput.getText().toString().trim().isEmpty())
                return;

            //getting chat ID
            if(messageList.size() > 0){
                chattingID = messageList.get(0).getCounsellorChatID();
            }

            String message = messageInput.getText().toString().trim();

            HashMap<String, Object> map = new HashMap<>();

            map.put("counsellorChatID", chattingID);
            map.put("counsellorID", assignedCounsellorID);
            map.put("childID", GlobalVariables.loggedInUser.getUserID());
            map.put("senderID", GlobalVariables.loggedInUser.getUserID());
            map.put("message", message);

            apiInterface.saveCounsellorChatMessage("application/json; charset=utf-8", map).enqueue(new Callback<CounsellorChatMessage>() {
                @Override
                public void onResponse(Call<CounsellorChatMessage> call, Response<CounsellorChatMessage> response) {
                    if(response.code() == 200){
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            CounsellorChatMessage newMessage = new CounsellorChatMessage(jsonObject);

                            if(newMessage.getChatID() > 0){
                                messageList.add(newMessage);

                                messageRecycler = findViewById(R.id.recycler_gchat);
                                LLM = new LinearLayoutManager(CounsellorChatBox.this);
                                messageRecycler.setLayoutManager(LLM);

                                messageListAdapter = new CounsellorMessageListAdapter(CounsellorChatBox.this, messageList);
                                messageRecycler.setAdapter(messageListAdapter);
                                LLM.scrollToPosition(messageList.size() -1);

                                messageInput.setText("");
                            }
                            else{
                                Toast.makeText(CounsellorChatBox.this, "Message ID:" + jsonObject.getInt("messageID"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CounsellorChatMessage> call, Throwable t) {

                }
            });
        });

    }

    private void setAssignedCounsellorID(int id){
        assignedCounsellorID = id;
        GlobalVariables.counsellorAssignedForChat = id;
    }

    public interface AssignedCounsellorIDCallback {
        void onSuccess(@NonNull int value);

        void onError(@NonNull Throwable throwable);
    }

    public void getAssignedCounsellor(AssignedCounsellorIDCallback callbacks){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        apiInterface.fetchChildsCounsellor(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 200){
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        int counsellorId = jsonObject.getInt("userID");

                        counsellorName.setText(jsonObject.getString("name"));

                        String imageName = jsonObject.getString("profilePicture").replaceAll(".(png|jpe?g|svg)", "");
                        String imageUrl = "@drawable/" + imageName;
                        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                        counsellorImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), imageResource,getApplicationContext().getTheme()));

                        if(callbacks != null)
                            callbacks.onSuccess(counsellorId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(CounsellorChatBox.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(CounsellorChatBox.this, "Something went wrong assigning counsellor", Toast.LENGTH_SHORT).show();

                if(callbacks != null)
                    callbacks.onError(t);
            }
        });
    }

    public void getChatMessages(int counsellorID){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        HashMap<String, Object> chatPartiesMap = new HashMap<>();

        chatPartiesMap.put("CounsellorID", counsellorID);
        chatPartiesMap.put("ChildID", GlobalVariables.loggedInUser.getUserID());

        apiInterface.getCounsellingChatMessages("application/json; charset=utf-8", chatPartiesMap).enqueue(new Callback<List<CounsellorChatMessage>>() {
            @Override
            public void onResponse(Call<List<CounsellorChatMessage>> call, Response<List<CounsellorChatMessage>> response) {
                if(response.code() == 200){
                    messageList.addAll(response.body());

                    messageRecycler = findViewById(R.id.recycler_gchat);
                    LLM = new LinearLayoutManager(CounsellorChatBox.this);
                    messageRecycler.setLayoutManager(LLM);

                    messageListAdapter = new CounsellorMessageListAdapter(CounsellorChatBox.this, messageList);
                    messageRecycler.setAdapter(messageListAdapter);
                    LLM.scrollToPosition(messageList.size() -1);

                }
            }

            @Override
            public void onFailure(Call<List<CounsellorChatMessage>> call, Throwable t) {

            }
        });
    }

    class myTimerTask extends TimerTask {
        @Override
        public void run() {
            Intent i = getIntent();
            HashMap<String, Object> psyPatPair = new HashMap<>();
            psyPatPair.put("ChildID", GlobalVariables.loggedInUser.getUserID());
            psyPatPair.put("PsychID", i.getIntExtra("psychID", 0)); //get from extra that was pushed with the intent creation

            runOnUiThread(() -> {
                messageList.clear();
                getChatMessages(assignedCounsellorID);
            });
        }
    }

    //Utility method: used to check if input fields are empty - in order to control button availability
    private  void changeButtonVisibility(){
        messageInput = findViewById(R.id.edit_gchat_message);
        btnSend = findViewById(R.id.button_gchat_send);

        String strMessage = messageInput.getText().toString().trim();

        btnSend.setEnabled(!strMessage.isEmpty());
    }
}
