package Chat.Counsellor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Chat.ChatModel;
import Chat.Counsellor.Models.CounsellorChatMessage;
import Chat.MessageListAdapter;

public class CounsellorMessageListAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_MESSAGE_SENT = 0;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;

    private Context context;
    private List<CounsellorChatMessage> messageList;

    public CounsellorMessageListAdapter(Context context, List<CounsellorChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message_layout, parent, false);
            return new SentMessageHolder(view);
        }
        else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.received_message_layout, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CounsellorChatMessage chatMessage = messageList.get(position);

        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                try {
                    ((SentMessageHolder) holder).bind(chatMessage);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                try {
                    ((ReceivedMessageHolder) holder).bind(chatMessage);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public int getItemViewType(int position){
        CounsellorChatMessage chatMessage = messageList.get(position);

        if(chatMessage.getSenderID() == GlobalVariables.loggedInUser.getUserID()){//(GlobalVariables.loggedInUser.getUserID())){
            return VIEW_TYPE_MESSAGE_SENT;
        }
        else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    private class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        TextView messageContent, messageTime;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.text_gchat_message_other);
            messageTime = itemView.findViewById(R.id.receivedTime);
        }

        void bind(CounsellorChatMessage message) throws ParseException {
            messageContent.setText(message.getMessage());

            //format given time
            String formattedDate = formatDate(message.getDate());

            messageTime.setText(formattedDate);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView messageContent, messageTime;

        public SentMessageHolder(View itemView) {
            super(itemView);

            messageContent = itemView.findViewById(R.id.text_gchat_message_me);
            messageTime = itemView.findViewById(R.id.sentTime);
        }

        void bind(CounsellorChatMessage message) throws ParseException {
            messageContent.setText(message.getMessage());

            //format given time
            String formattedDate = formatDate(message.getDate());

            messageTime.setText(formattedDate);
        }
    }

    //utility method for formatting dates
    String formatDate(String dateToFormat) throws ParseException {
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

        if(calDatePassed.get(Calendar.DAY_OF_YEAR) == calToday.get(Calendar.DAY_OF_YEAR) && calDatePassed.get(Calendar.YEAR) == calToday.get(Calendar.YEAR))
            sdf.applyPattern(todayPattern);
        else
            sdf.applyPattern(datePattern);

        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}


