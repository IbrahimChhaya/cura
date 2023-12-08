package ChildList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import Account.Child.UpdateChildAccount;
import PsychologistCalendar.HorizontalPsychologistCalendar;

import com.example.mhaprototype.DynamicCalendar;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;

import java.util.Random;

import PsychologistCalendar.PsychologistCalendar;
import PsychologistList.Adapter;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    private static final String TAG ="Testing Child ID" ;
    private LayoutInflater inflater;
    private String[] sTitles;
    private String[] sContent;
    private String[] ChildIDs;
    private String incomingMessage;
    /*private ArrayList<String> sTitles;
    private ArrayList<String> sContent;*/

    ChildAdapter(Context context, String[] titles, String[] contents, String[] ids,String incomingMessage) {
        this.inflater = LayoutInflater.from(context);
        this.sTitles = titles;
        this.sContent = contents;
        this.ChildIDs = ids;
        this.incomingMessage =incomingMessage;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.child_list_custom_view, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder viewHolder, int i) {
        String title = sTitles[i];//sTitles[i];
        String content = sContent[i];
        viewHolder.storyTitle.setText(title);
        viewHolder.storyContent.setText(content);
        viewHolder.number.setText(String.valueOf(title.charAt(0)));
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        // generate random color

        Random r = new Random();
        int red = r.nextInt(255 - 0 + i);
        int green = r.nextInt(255 - i + 1);
        int blue = r.nextInt(255 - 0 + (i + 1));
        shape.setColor(Color.rgb(red, green, blue));
        viewHolder.circle.setBackground(shape);
    }

    @Override
    public int getItemCount() {
        return sTitles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView storyTitle, storyContent, number;
        CardView circle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // implement onClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String myID = ChildIDs[getAdapterPosition()];
                    int childID = Integer.parseInt(myID);
                    Log.d(TAG, "onClickChildID Test: " + childID);
                    GlobalVariables.GuardianChildID = childID;
                    if(incomingMessage.equals("MyChildrenTile"))
                    {
                      //update child account here
                    }else if(incomingMessage.equals("Normal Procedure"))
                    {
                        Intent i = new Intent(v.getContext(), HorizontalPsychologistCalendar.class);//PsychologistCalendar
                        // send story title and contents through recyclerview to detail activity
                    /*i.putExtra("titleOfStory", sTitles[getAdapterPosition()]);
                    i.putExtra("contentOfStory", sContent[getAdapterPosition()]);*/
                        v.getContext().startActivity(i);
                    }
                  /*  Intent i = new Intent(v.getContext(), HorizontalPsychologistCalendar.class);//PsychologistCalendar
                    // send story title and contents through recyclerview to detail activity
                    *//*i.putExtra("titleOfStory", sTitles[getAdapterPosition()]);
                    i.putExtra("contentOfStory", sContent[getAdapterPosition()]);*//*
                    v.getContext().startActivity(i);*/
                }
            });
            storyTitle = itemView.findViewById(R.id.storyTitle);
            storyContent = itemView.findViewById(R.id.storyContent);
            number = itemView.findViewById(R.id.number);
            circle = itemView.findViewById(R.id.circle);


        }
    }

}
