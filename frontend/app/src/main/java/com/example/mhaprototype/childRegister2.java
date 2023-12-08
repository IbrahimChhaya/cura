package com.example.mhaprototype;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class childRegister2 extends AppCompatActivity {

    private ViewPager2 animalViewPager;
    private ViewPager2 foodViewPager;
    private ViewPager2 colorViewPager;
    private Handler sliderHandler = new Handler();

    static final String Animal0 = "Crocodile";
    static final String Animal1 = "Rabbit";
    static final String Animal2 = "Lion";
    static final String Animal3 = "Elephant";
    static final String Animal4 = "Turtle";
    static final String Animal5 = "Fish";

    static final String Food0 = "Hotdog";
    static final String Food1 = "Pizza";
    static final String Food2 = "Apple";
    static final String Food3 = "Burger";
    static final String Food4 = "Chicken";
    static final String Food5 = "Donut";

    static final String Color0 = "Red";
    static final String Color1 = "Blue";
    static final String Color2 = "Green";
    static final String Color3 = "Yellow";
    static final String Color4 = "Pink";
    static final String Color5 = "Purple";

    static String animalPass;
    static String foodPass;
    static String colorPass;

    ImageView btnBackButton;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_register2);

        btnBackButton = findViewById(R.id.childRegisterBack);
        animalViewPager = (ViewPager2) findViewById(R.id.animalViewPager);

        setupViewPager(animalViewPager, "animal");
        PageListener pageListener = new PageListener("animal");
        animalViewPager.registerOnPageChangeCallback(pageListener);




        foodViewPager = (ViewPager2) findViewById(R.id.foodViewPager);
        setupViewPager(foodViewPager, "food");
        pageListener = new PageListener("food");
        foodViewPager.registerOnPageChangeCallback(pageListener);

        colorViewPager = (ViewPager2) findViewById(R.id.colorViewPager);
        setupViewPager(colorViewPager, "color");
        pageListener = new PageListener("color");
        colorViewPager.registerOnPageChangeCallback(pageListener);

        Button btnnext = (Button) findViewById(R.id.btnnext);
        EditText edtEmail = (EditText) findViewById(R.id.editEmail);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();

                String password = foodPass + animalPass +  colorPass;


                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                Map<String, Object> hashMap = new HashMap<>();

                hashMap.put("Email", email);
                hashMap.put("Name", GlobalVariables.name);
                hashMap.put("Password", password);
                hashMap.put("DOB", GlobalVariables.birthDate + "T00:00:00");
                hashMap.put("UserType","Child");
                hashMap.put("Grade", GlobalVariables.grade);
                hashMap.put("ProfilePicture", GlobalVariables.profilePicture);


                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = formatter.format(todayDate);

                hashMap.put("DateRegistered", todayString + "T00:00:00");
                hashMap.put("Status", "Active");

                apiInterface.RegisterUser("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(edtEmail.getText().toString().equals(""))
                        {
                            Toast.makeText(childRegister2.this,"Please provide your email address", Toast.LENGTH_LONG).show();
                        }else {


                            if (response.code() == 200) {
                                JSONObject gsonObject = null;
                                try {
                                    gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                    if (gsonObject.getInt("userID") > 0) {
                                        Intent intent = new Intent(childRegister2.this, ChildLogin.class);
                                        Toast.makeText(childRegister2.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                    } else if (gsonObject.getInt("userID") == 0) {
                                        Toast.makeText(childRegister2.this, "Email already exists", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(childRegister2.this, "Something went wrong: Please try again later", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                           /* Intent intent = new Intent(childRegister2.this, ChildLogin.class);
                            Toast.makeText(childRegister2.this,"Registered Successfully", Toast.LENGTH_LONG).show();
                            startActivity(intent);*/

                            } else {
                                Toast.makeText(childRegister2.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                Log.i("error", response.errorBody().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(childRegister2.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.i("error", t.getLocalizedMessage());
                    }
                });
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

    private void setupViewPager(ViewPager2 viewPager2, String images) {

        List<SliderItem> sliderItems = new ArrayList<>();
        if(images.equals("animal")) {
            sliderItems.add(new SliderItem(R.drawable.animal0));
            sliderItems.add(new SliderItem(R.drawable.animal1));
            sliderItems.add(new SliderItem(R.drawable.animal2));
            sliderItems.add(new SliderItem(R.drawable.animal3));
            sliderItems.add(new SliderItem(R.drawable.animal4));
            sliderItems.add(new SliderItem(R.drawable.animal5));
        }
        else if(images.equals("food"))
        {
            sliderItems.add(new SliderItem(R.drawable.food0));
            sliderItems.add(new SliderItem(R.drawable.food1));
            sliderItems.add(new SliderItem(R.drawable.food2));
            sliderItems.add(new SliderItem(R.drawable.food3));
            sliderItems.add(new SliderItem(R.drawable.food4));
            sliderItems.add(new SliderItem(R.drawable.food5));
        }
        else
        {
            sliderItems.add(new SliderItem(R.drawable.color0));
            sliderItems.add(new SliderItem(R.drawable.color1));
            sliderItems.add(new SliderItem(R.drawable.color2));
            sliderItems.add(new SliderItem(R.drawable.color3));
            sliderItems.add(new SliderItem(R.drawable.color4));
            sliderItems.add(new SliderItem(R.drawable.color5));
        }
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
    }

    private Runnable sliderRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            animalViewPager.setCurrentItem(animalViewPager.getCurrentItem() + 1);
            foodViewPager.setCurrentItem(foodViewPager.getCurrentItem() + 1);
            colorViewPager.setCurrentItem(colorViewPager.getCurrentItem() + 1);
        }
    };

    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private int currentPage;
        private String image;

        public PageListener(String image)
        {
            this.image = image;
        }

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            currentPage = position;
            if(image.equals("animal"))
            {
                switch(position)
                {
                    case 0:
                        animalPass = Animal0;
                        break;
                    case 1:
                        animalPass = Animal1;
                        break;
                    case 2:
                        animalPass = Animal2;
                        break;
                    case 3:
                        animalPass = Animal3;
                        break;
                    case 4:
                        animalPass = Animal4;
                        break;
                    case 5:
                        animalPass = Animal5;
                        break;
                }
            }
            else if(image.equals("food"))
            {
                switch(position)
                {
                    case 0:
                        foodPass = Food0;
                        break;
                    case 1:
                        foodPass = Food1;
                        break;
                    case 2:
                        foodPass = Food2;
                        break;
                    case 3:
                        foodPass = Food3;
                        break;
                    case 4:
                        foodPass = Food4;
                        break;
                    case 5:
                        foodPass = Food5;
                        break;
                }
            }
            else
            {
                switch(position)
                {
                    case 0:
                        colorPass = Color0;
                        break;
                    case 1:
                        colorPass = Color1;
                        break;
                    case 2:
                        colorPass = Color2;
                        break;
                    case 3:
                        colorPass = Color3;
                        break;
                    case 4:
                        colorPass = Color4;
                        break;
                    case 5:
                        colorPass = Color5;
                        break;
                }
            }

            //GlobalVariables.profilePicture = "child" + position;
        }
    }
}