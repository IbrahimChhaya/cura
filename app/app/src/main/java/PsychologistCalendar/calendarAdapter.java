package PsychologistCalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mhaprototype.R;

import java.util.List;

public class calendarAdapter extends RecyclerView.Adapter<calendarAdapter.calendarItemViewHolder> {

    public List<calendarItem> calItem;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public calendarAdapter(List<calendarItem> calItem)
    {
        this.calItem = calItem;
    }

    @NonNull
    @Override
    public calendarItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_item_psychologist, viewGroup, false);
        return new calendarItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull calendarItemViewHolder holder, int position) {
        calendarItem calendItem = calItem.get(position);
        holder.calTitle.setText(calendItem.getCalItemTitle());
        holder.calBookingTime.setText(calendItem.getCalItemBookingTime());
        holder.calDescription.setText(calendItem.getCalItemDesc());

    }

    @Override
    public int getItemCount() {
        return calItem.size();
    }

    class calendarItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imgCalendar;
        TextView calTitle;
        TextView calDescription;
        TextView calBookingTime;

        calendarItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCalendar = itemView.findViewById(R.id.img_Calendar);
            calTitle = itemView.findViewById(R.id.cal_Title);
            calDescription = itemView.findViewById(R.id.calDescription);
            calBookingTime = itemView.findViewById(R.id.calBookingTime);
        }
    }

}
