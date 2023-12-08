package Chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatBox extends AppCompatActivity {
    private RecyclerView messageRecycler;
    private MessageListAdapter messageListAdapter;
    private LinearLayoutManager LLM;
    private ArrayList<ChatModel> messageList;

    private FloatingActionButton btnSend;
    private EditText messageInput;

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
    public void onBackPressed() {
        Intent intent = new Intent(ChatBox.this, ChatPsychList.class);
        startActivity(intent);
    }

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

        Intent i = getIntent();
        ImageView psychPfp = findViewById(R.id.imgChatProfilePic);
        psychPfp.setImageResource(i.getIntExtra("psychPfp", 0));

        TextView psychName = findViewById(R.id.chatPerson);
        psychName.setText(i.getStringExtra("psychName"));

        ImageView backArrow = findViewById(R.id.imgBackButton);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(ChatBox.this, ChatPsychList.class);
            startActivity(intent);
        });

       //code for getting past messages
        if(timer != null)
            timer.cancel();

        timer = new Timer();
        TimerTask myTimerTask = new myTimerTask();
        timer.schedule(myTimerTask, 0, 1000);   //re-fetch message list every 5 seconds

        //code for sending the message
        messageInput = findViewById(R.id.edit_gchat_message);
        messageInput.addTextChangedListener(textWatcher);

        btnSend = findViewById(R.id.button_gchat_send);
        changeButtonVisibility();

        btnSend.setOnClickListener(v -> {
            HashMap<String, Object> map = new HashMap<>();

            map.put("practitionerID", i.getIntExtra("psychID", 0)); //get psychID from intent extra
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("messageContent", messageInput.getText().toString().trim());
            map.put("senderID", GlobalVariables.loggedInUser.getUserID());

            apiInterface.sendMessage("application/json; charset=utf-8", map).enqueue(new Callback<ChatModel>() {
                @Override
                public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("messageID") > 0){
                                ChatModel message = new ChatModel(jsonObject);

                                messageList.add(message);

                                messageRecycler = findViewById(R.id.recycler_gchat);
                                LLM = new LinearLayoutManager(ChatBox.this);
                                messageRecycler.setLayoutManager(LLM);

                                messageListAdapter = new MessageListAdapter(ChatBox.this, messageList);
                                messageRecycler.setAdapter(messageListAdapter);
                                LLM.scrollToPosition(messageList.size() -1);

                                messageInput.setText("");
                            }
                            else{
                                Toast.makeText(ChatBox.this, "Message ID:" + jsonObject.getInt("messageID"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ChatModel> call, Throwable t) {
                    Toast.makeText(ChatBox.this, "Failed to make api call", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    class myTimerTask extends TimerTask{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        @Override
        public void run() {
            Intent i = getIntent();
            HashMap<String, Object> psyPatPair = new HashMap<>();
            psyPatPair.put("ChildID", GlobalVariables.loggedInUser.getUserID());
            psyPatPair.put("PsychID", i.getIntExtra("psychID", 0)); //get from extra that was pushed with the intent creation

            runOnUiThread(() -> {
                messageList.clear();
                apiInterface.getChatHistory("application/json; charset=utf-8", psyPatPair).enqueue(new Callback<List<ChatModel>>() {

                    @Override
                    public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                        if(response.code() == 200){
                            messageList.addAll(response.body());

                            messageRecycler = findViewById(R.id.recycler_gchat);
                            LLM = new LinearLayoutManager(ChatBox.this);
                            messageRecycler.setLayoutManager(LLM);

                            messageListAdapter = new MessageListAdapter(ChatBox.this, messageList);
                            messageRecycler.setAdapter(messageListAdapter);
                            LLM.scrollToPosition(messageList.size() -1);
                        }
                        else{
//                    Toast.makeText(ChatBox.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ChatModel>> call, Throwable t) {

                    }
                });

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
