package Appointment;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.mhcredesign.R;

import com.example.mhaprototype.R;

import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<SubItem> subItemList;

    SubItemAdapter(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        SubItem subItem = subItemList.get(i);
        subItemViewHolder.tvSubItemTitle.setText(subItem.getSubItemTitle());
        if(subItem.getSubItemDesc().contains("Repeat"))
        {
            subItemViewHolder.tvDescription.setText("Recurring Session");
        }else
        {
            subItemViewHolder.tvDescription.setText("Single Session");
        }

        //subItemViewHolder.tvDescription.setText(subItem.getSubItemDesc());
        subItemViewHolder.txtBookingTime.setText(subItem.getSubItemBookingTime());

        if(subItem.getSubItemDesc().contains("Repeat"))
        {
            subItemViewHolder.imgType.setBackgroundColor(Color.MAGENTA);
        }else
        {
            subItemViewHolder.imgType.setBackgroundColor(Color.GREEN);
        }
        //subItemViewHolder.cardItem.setBackgroundResource(R.drawable.card_border_colour);
        subItemViewHolder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(v.getContext(), "Please i clicked item" + subItemViewHolder.tvSubItemTitle.getText().toString()+
                        "BookingID "+ subItem.getBookingID(),Toast.LENGTH_SHORT).show();*/

                Intent i = new Intent(v.getContext(),EditAppointment.class);
                //Sending data to a new activity //couldve sent a whole object
                i.putExtra("PatientName",subItemViewHolder.tvSubItemTitle.getText().toString());
                i.putExtra("PatientDescription",subItemViewHolder.tvDescription.getText().toString());
                i.putExtra("BookingTime",subItemViewHolder.txtBookingTime.getText().toString());
                i.putExtra("BookingID",subItem.getBookingID());
                i.putExtra("BookingDate",subItem.getSubItemBookingDate().toString());
                i.putExtra("BookingStatus",subItem.getSubItemBookingStatus().toString());
                i.putExtra("BookingType", subItem.getSubItemBookingType());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubItemTitle;
        CardView cardItem;
        TextView tvDescription;
        ImageView imgType;
        TextView txtBookingTime;

        SubItemViewHolder(View itemView) {
            super(itemView);
            tvSubItemTitle = itemView.findViewById(R.id.tv_sub_item_title);
            tvDescription = itemView.findViewById(R.id.description);
            txtBookingTime = itemView.findViewById(R.id.txtBookingTime);
            imgType = itemView.findViewById(R.id.img_sub_item);

            cardItem = itemView.findViewById(R.id.itemCard);
        }
    }

}
