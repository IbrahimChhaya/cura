package StartUpStuff.ChildRegister;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.example.mhaprototype.Register;
import com.example.mhaprototype.SliderAdapter;
import com.example.mhaprototype.SliderItem;
import com.github.tibolte.agendacalendarview.widgets.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import StartUpStuff.GuardianRegister.NewGuardianRegister;
import StartUpStuff.GuardianRegister.NewGuardianRegister2;
import StartUpStuff.SignInRoles;

public class ChildRegisterRedesign extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    Button btnNext;
    EditText txtChildName;
    EditText DOB;
    EditText email;
    TextView txtAlreadySigned;
    FloatingActionButton picker;
    ImageView profilePicture;
    ImageView imgPrev;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onBackPressed()
    {
        //programming emulator back button go to the signin activity
        Intent i = new Intent(ChildRegisterRedesign.this, SignInRoles.class);
        i.putExtra("StartStatus","NotRegistered");
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_register_redesign_1);
        btnNext = findViewById(R.id.btnChildRegisterNext2);
        txtChildName = findViewById(R.id.txtChildName2);
        imgPrev = findViewById(R.id.imgbtnPrev);
        txtAlreadySigned = findViewById(R.id.txtAlreadySigned);
        email = findViewById(R.id.txtChildEmail);
       // DOB = findViewById(R.id.txtChildDOB);
        //picker = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        //profilePicture = findViewById(R.id.imgProfilePic);


        txtAlreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildRegisterRedesign.this, ChildLogInRedesign1.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildRegisterRedesign.this, SignInRoles.class);
                i.putExtra("StartStatus","NotRegistered");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        //setting up the date picker
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        //allowing the datepicker to pop up on edit text click
//        DOB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ChildRegisterRedesign.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(NewGuardianRegister.this, NewGuardianRegister2.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);*/
                if(txtChildName.getText().toString().equals("") || email.getText().toString().equals(""))
                {
                    Toast.makeText(ChildRegisterRedesign.this,"Make sure all entries are filled", Toast.LENGTH_LONG).show();
                }else
                {
                    if(isValidEmailAddress(email.getText().toString()) == false){
                        Toast.makeText(ChildRegisterRedesign.this,"Please make sure you entered valied email address", Toast.LENGTH_LONG).show();
                    }else {
                        GlobalVariables.name = txtChildName.getText().toString();
                        GlobalVariables.email = email.getText().toString();

                        //start the second register activity
                        Intent i = new Intent(ChildRegisterRedesign.this, ChildRegisterRedesign3.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }
            }
        });

        viewPager2 = findViewById(R.id.viewPagerImageSlider2Child);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.child0));
        sliderItems.add(new SliderItem(R.drawable.child1));
        sliderItems.add(new SliderItem(R.drawable.child2));
        sliderItems.add(new SliderItem(R.drawable.child3));
        sliderItems.add(new SliderItem(R.drawable.child4));
        sliderItems.add(new SliderItem(R.drawable.child5));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer((new MarginPageTransformer(5)));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        ChildRegisterRedesign.PageListener pageListener =  new ChildRegisterRedesign.PageListener();
        viewPager2.registerOnPageChangeCallback(pageListener);

    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formatedDate = sdf.format(myCalendar.getTime());
        String finalDate = formatedDate.replace("/","-");
        DOB.setText(finalDate);
    }

    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private int currentPage;

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            currentPage = position;
           // position = position+1;
            GlobalVariables.profilePicture = "child" + position + ".png";
        }
    }

    private Runnable sliderRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
