package com.example.mhaprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import StartUpStuff.SignInRoles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildLogin extends AppCompatActivity{ //implements View.OnKeyListener {

    private ViewPager2 animalViewPager;
    private ViewPager2 foodViewPager;
    private ViewPager2 colorViewPager;
    private ImageView backBtn;
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

    @Override
    public void onBackPressed()
    {
        //programming emulator back button
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

//    @Override
//    public boolean onKey(View view, int keyCode, KeyEvent event) {
//
////        TextView responseText = (TextView) findViewById(R.id.responseText);
//        EditText myEditText = (EditText) view;
//
//        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
//                keyCode == EditorInfo.IME_ACTION_DONE ||
//                event.getAction() == KeyEvent.ACTION_DOWN &&
//                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//
//            if (!event.isShiftPressed()) {
//                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
//                if (view.getId() == R.id.editEmail) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//                return true;
//            }
//
//        }
//        return false; // pass on to other listeners.
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_login);

        backBtn = findViewById(R.id.childLoginBack);
//        EditText emailText = findViewById(R.id.editEmail);
//        emailText.setOnKeyListener(this);

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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Button btnGo = (Button) findViewById(R.id.btnnext);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtEmail = (EditText) findViewById(R.id.editEmail);
                String password = foodPass + animalPass + colorPass;

                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("email", edtEmail.getText().toString());
                hashMap.put("password", password);

                apiInterface.loginUser("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(edtEmail.getText().toString().equals(""))
                        {
                            Toast.makeText(ChildLogin.this,"Please provide your email address", Toast.LENGTH_LONG).show();
                        }else {

                            if (response.code() == 200) {

                                String getserverusertype = "";
                                int getuserID = 0;

                                JSONObject gsonObject = null;
                                try {
                                    gsonObject = new JSONObject(new Gson().toJson(response.body()));

                                    if (gsonObject.getInt("userID") > 0) {
                                        UserModel usermodel = new UserModel(gsonObject);
                                        GlobalVariables.loggedInUser = usermodel;
                                        Intent intent = new Intent(ChildLogin.this, Child_Home.class);
                                        /*intent.putExtra("userID", getuserID);*/
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ChildLogin.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                                    }

                              /*  getserverusertype = usermodel.getUserType();
                                getuserID = (int) (usermodel.getUserID());

                                GlobalVariables.name = usermodel.getName();
                                GlobalVariables.userID = getuserID;
                                GlobalVariables.typeUser = getserverusertype;
                                GlobalVariables.email = usermodel.getEmail();
                                GlobalVariables.birthDate = usermodel.getDob();*/

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(ChildLogin.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(ChildLogin.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });
    }

    private void setupViewPager(ViewPager2 viewPager2, String images) {

        List<SliderItem> sliderItems = new ArrayList<>();
        if (images.equals("animal")) {
            sliderItems.add(new SliderItem(R.drawable.animal0));
            sliderItems.add(new SliderItem(R.drawable.animal1));
            sliderItems.add(new SliderItem(R.drawable.animal2));
            sliderItems.add(new SliderItem(R.drawable.animal3));
            sliderItems.add(new SliderItem(R.drawable.animal4));
            sliderItems.add(new SliderItem(R.drawable.animal5));
        } else if (images.equals("food")) {
            sliderItems.add(new SliderItem(R.drawable.food0));
            sliderItems.add(new SliderItem(R.drawable.food1));
            sliderItems.add(new SliderItem(R.drawable.food2));
            sliderItems.add(new SliderItem(R.drawable.food3));
            sliderItems.add(new SliderItem(R.drawable.food4));
            sliderItems.add(new SliderItem(R.drawable.food5));
        } else {
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

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            animalViewPager.setCurrentItem(animalViewPager.getCurrentItem() + 1);
            foodViewPager.setCurrentItem(foodViewPager.getCurrentItem() + 1);
            colorViewPager.setCurrentItem(colorViewPager.getCurrentItem() + 1);
        }
    };


    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private int currentPage;
        private String image;

        public PageListener(String image) {
            this.image = image;
        }

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            currentPage = position;
            if (image.equals("animal")) {
                switch (position) {
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
            } else if (image.equals("food")) {
                switch (position) {
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
            } else {
                switch (position) {
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