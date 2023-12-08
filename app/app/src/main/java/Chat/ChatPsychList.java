package Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChatPsychList  extends AppCompatActivity {
    ArrayList<String> psychNames;
    ArrayList<String> lastMessages;
    ArrayList<String> messageDates;
    ArrayList<Integer> psychImages;
    ListView listView;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ChatPsychList.this, Child_Home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_psychologist_list);

        listView = findViewById(R.id.list);

        ImageView backArrow = findViewById(R.id.chatListBackButton);

        backArrow.setOnClickListener(v -> {
            Intent i = new Intent(ChatPsychList.this, Child_Home.class);
            startActivity(i);
        });

        psychNames = new ArrayList<>();
        psychImages = new ArrayList<>();
        lastMessages = new ArrayList<>();
        messageDates = new ArrayList<>();
        ArrayList<Integer> psychIDs = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        apiInterface.getChildPsychologists(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<List<PsychChatListItem>>() {
            @Override
            public void onResponse(Call<List<PsychChatListItem>> call, Response<List<PsychChatListItem>> response) {
                if (response.code() == 200) {
                    for (PsychChatListItem item : response.body()) {
                        psychIDs.add(item.getPsychID());
                        psychNames.add(item.getPsychName());
                        lastMessages.add(item.getLastMessage().getMessage());

                        try {
                            String messageDate = formatDate(item.getLastMessage().getDate());
                            messageDates.add(messageDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        String imageName = item.getProfilePicture().replaceAll(".(png|jpe?g|svg)", "");
                        String imageUrl = "@drawable/" + imageName;


                        int imageResource = getResources().getIdentifier(imageUrl, null, getApplicationContext().getPackageName());

                        psychImages.add(imageResource);
                    }

                    Object[] names = psychNames.toArray();
                    String[] stringArrayNames = Arrays.copyOf(names, names.length, String[].class);
                    Object[] dates = messageDates.toArray();
                    Object[] messages = lastMessages.toArray();
                    Object[] images = psychImages.toArray();

                    ListAdapter listAdapter = new Account.ListAdapter(ChatPsychList.this, stringArrayNames, messages, images, dates);
                    listView.setAdapter(listAdapter);
                }
            }


            @Override
            public void onFailure(Call<List<PsychChatListItem>> call, Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatPsychList.this, ChatBox.class);
                intent.putExtra("psychID", psychIDs.get(position));
                intent.putExtra("psychName", psychNames.get(position));
                intent.putExtra("psychPfp", psychImages.get(position));
                startActivity(intent);
            }
        });


    }

    //utility method for formatting dates
    String formatDate(String dateToFormat) throws ParseException {
        if(dateToFormat.equals("0001-01-01T00:00:00")){
            return " ";
        }

        //format given time
        String datePattern = "HH:mm | MMM dd";
        String todayPattern = "HH:mm";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sdf.parse(dateToFormat);
        Date todayDate = Calendar.getInstance().getTime();

        Calendar calToday = Calendar.getInstance();
        Calendar calDatePassed = Calendar.getInstance();

        calToday.setTime(todayDate);
        calDatePassed.setTime(date);

        if (calDatePassed.get(Calendar.DAY_OF_YEAR) == calToday.get(Calendar.DAY_OF_YEAR) && calDatePassed.get(Calendar.YEAR) == calToday.get(Calendar.YEAR))
            sdf.applyPattern(todayPattern);
        else
            sdf.applyPattern(datePattern);

        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
