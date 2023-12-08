package com.example.mhaprototype;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.shawnlin.numberpicker.NumberPicker;

public class childRegister extends AppCompatActivity {

    private static String TAG = "NumberPicker";
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    ImageView btnBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_register);

        btnBackButton = findViewById(R.id.childRegisterBack);
        Button btn7 = (Button) findViewById(R.id.button7);

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(childRegister.this, "peepeepoopoo", Toast.LENGTH_SHORT).show();
                final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.dateScroll);

                int x, y;
                x = btn7.getLeft();
                y = btn7.getRight();

                Button btn8 = (Button) findViewById(R.id.button5);

                hsv.scrollTo(54, btn8.getTop());
            }
        });

        viewPager2 = findViewById(R.id.viewPagerImageSlider);

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
        compositePageTransformer.addTransformer((new MarginPageTransformer(40)));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        PageListener pageListener = new PageListener();
        viewPager2.registerOnPageChangeCallback(pageListener);

        NumberPicker gradePicker = (NumberPicker) findViewById(R.id.gradePicker);
        gradePicker.setMinValue(1);
        gradePicker.setMaxValue(12);
        gradePicker.setDividerColor(ContextCompat.getColor(this, R.color.colorAccent));
        gradePicker.setDividerDistance(150);
        gradePicker.setFadingEdgeEnabled(true);
        gradePicker.setFadingEdgeStrength((float) 0.15);
        gradePicker.setItemSpacing(25);
        gradePicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        gradePicker.setSelectedTextSize(getResources().getDimension(R.dimen.text_size));
        gradePicker.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        gradePicker.setTextSize(getResources().getDimension(R.dimen.text_size));

        NumberPicker dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        //make it so that the dayPicker's values abide by the month chosen
        NumberPicker monthPicker = (NumberPicker) findViewById(R.id.monthPicker);
        // Using string values
        // IMPORTANT! setMinValue to 1 and call setDisplayedValues after setMinValue and setMaxValue
        String[] data = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(data.length);
        monthPicker.setDisplayedValues(data);
        // OnClickListener
        monthPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG, "Click on current value");
            }
        });
        // OnValueChangeListener
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.w(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
            }
        });
        // OnScrollListener
        monthPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.w(TAG, String.format(Locale.US, "newVal: %d", picker.getValue()));
                }
            }
        });

        String[] year;
        year = new String[20];
        int yearToStart = (Calendar.getInstance().get(Calendar.YEAR) - 6); //6 years ago from current year, the min age
        for(int i = 0; i < year.length; i++)
        {
            year[i] = String.valueOf(yearToStart - i);
        }
        NumberPicker yearPicker = (NumberPicker) findViewById(R.id.yearPicker);
        yearPicker.setMinValue(1);//1996
        yearPicker.setMaxValue(20);//2015
        yearPicker.setDividerColor(ContextCompat.getColor(this, R.color.colorAccent));
        yearPicker.setDividerDistance(150);
        yearPicker.setFadingEdgeEnabled(true);
        yearPicker.setFadingEdgeStrength((float) 0.15);
        yearPicker.setItemSpacing(25);
        yearPicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        yearPicker.setSelectedTextSize(getResources().getDimension(R.dimen.text_size));
        yearPicker.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        yearPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        yearPicker.setOrder(NumberPicker.DESCENDING);
        yearPicker.setDisplayedValues(year);


        Button btnNext = (Button) findViewById(R.id.btnNext);
        EditText txtName = (EditText) findViewById(R.id.txtName);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mont = String.valueOf(monthPicker.getValue());
                String d = String.valueOf(dayPicker.getValue());
                GlobalVariables.name = txtName.getText().toString();
                if(txtName.getText().toString().equals(""))
                {
                    Toast.makeText(childRegister.this,"Please provide your name", Toast.LENGTH_LONG).show();
                }else {
                    if ((monthPicker.getValue()) <= 9) {
                        mont = "0" + (monthPicker.getValue());
                    }
                    if (dayPicker.getValue() <= 9) {
                        d = "0" + dayPicker.getValue();
                    }
                    String y = year[yearPicker.getValue() - 1];
                    String dob = y + "-" + mont + "-" + d;
                /*DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    GlobalVariables.birthDate = format.parse(dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                    GlobalVariables.grade = String.valueOf(gradePicker.getValue());
                    GlobalVariables.birthDate = dob;
                    Intent intent = new Intent(childRegister.this, childRegister2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        //move to previous activity
        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });
    }

    private Runnable sliderRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private int currentPage;

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            currentPage = position;
            GlobalVariables.profilePicture = "child" + position + ".png";
        }
    }
}