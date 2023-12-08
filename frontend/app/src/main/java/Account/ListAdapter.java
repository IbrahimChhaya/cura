package Account;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mhaprototype.R;

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] mainTitle;
    private final Object[] subtitle;
    private final Object[] images;
    private final Object[] dates;

    public ListAdapter(Activity context, String[] mainTitle, Object[] subtitle, Object[] images, Object[] dates){
        super(context, R.layout.my_list, mainTitle);

        this.context = context;
        this.mainTitle = mainTitle;
        this.subtitle = subtitle;
        this.images = images;
        this.dates = dates;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.my_list, null, true);

        TextView titleText = rowView.findViewById(R.id.title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        ImageView imageView = rowView.findViewById(R.id.psychImage);
        TextView dateText = rowView.findViewById(R.id.messageDate);

        titleText.setText(mainTitle[position]);
        subtitleText.setText(subtitle[position].toString());
        imageView.setImageResource((Integer)images[position]);
        dateText.setText(dates[position].toString());

//        String imageName = images[position].replaceAll(".(png|jpe?g|svg)", "");
//        String imageUrl = "@drawable/" + imageName;
//        int imageResource = getResources().getIdentifier(imageUrl, null, context.getPackageName());
//        profilePicture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), imageResource,getApplicationContext().getTheme()));


        return rowView;

    }
}
