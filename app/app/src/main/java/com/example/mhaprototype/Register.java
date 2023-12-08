package com.example.mhaprototype;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Register extends AppCompatActivity {
    DatePickerDialog datePicker;
    EditText regName, regSurname, regEmail, regPassword, dateofbirth,regconfpassword;
    Button register;
    String getregName, getRegSurname, getRegEmail, getRegPassword, getRegDOB, getUserType,getRegDate,getRegConfPassword;
    APIInterface apiInterface;

    //new register
    EditText edtDateOfBirth;
    EditText guardianName;
    ImageButton prevButton;
    final Calendar myCalendar = Calendar.getInstance();
    TextView alreadySigned;
    Button guardianRegisterNext;
    CardView startcard;

    //for image selection
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardian_registeration_form); //activity_register2

        prevButton = (ImageButton)findViewById(R.id.imgPrevButton);
        alreadySigned = (TextView)findViewById(R.id.txtAlreadySigned);
        guardianRegisterNext = (Button) findViewById(R.id.btnGuardianRegisterNext);
        guardianName = (EditText)findViewById(R.id.txtGuardianName);
        edtDateOfBirth = (EditText)findViewById(R.id.edtDOB);
        startcard = (CardView) findViewById(R.id.startCard);
        startcard.setBackgroundResource(R.drawable.rounded_top_corner_card);

   /*     CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
               CardView.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 200, 0, 0);
        startcard.setLayoutParams(params);*/

        //setting up the view pager for profile picture selection
        viewPager2 = findViewById(R.id.viewPagerImageSlider);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.adult1));
        sliderItems.add(new SliderItem(R.drawable.adult2));
        sliderItems.add(new SliderItem(R.drawable.adult3));
        sliderItems.add(new SliderItem(R.drawable.adult4));
        sliderItems.add(new SliderItem(R.drawable.adult5));
        sliderItems.add(new SliderItem(R.drawable.adult6));

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
        PageListener pageListener =  new PageListener();
        viewPager2.registerOnPageChangeCallback(pageListener);


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
        edtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //setting up the button to go to the previous activity
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });

        //redirecting back to sign in if text view clicked
        alreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        //btn to got to the second part of the registration activity for guardian
        guardianRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guardianName.getText().toString().equals(""))
                {
                    Toast.makeText(Register.this,"Please provide your name", Toast.LENGTH_LONG).show();
                }else if(edtDateOfBirth.getText().toString().equals(""))
                {
                    Toast.makeText(Register.this,"Please provide your date of birth", Toast.LENGTH_LONG).show();
                }else
                {
                    GlobalVariables.guardianName = guardianName.getText().toString();
                    GlobalVariables.guardianDOB = edtDateOfBirth.getText().toString();
                    //start the second register activity
                    Intent i = new Intent(Register.this, guardianRegister2.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }

            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formatedDate = sdf.format(myCalendar.getTime());
        String finalDate = formatedDate.replace("/","-");
        edtDateOfBirth.setText(finalDate);
    }

    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private int currentPage;

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            currentPage = position;
            position = position+1;
            GlobalVariables.profilePicture = "adult" + position;
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

}