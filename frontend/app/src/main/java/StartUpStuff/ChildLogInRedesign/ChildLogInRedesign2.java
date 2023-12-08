package StartUpStuff.ChildLogInRedesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.ChildLogin;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.SliderAdapter;
import com.example.mhaprototype.SliderItem;
import com.example.mhaprototype.UserModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ResourceHub.RHubDescription;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildLogInRedesign2 extends AppCompatActivity {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_login_redesign2);
        //TextView txtPasswordRedirect = findViewById(R.id.txtPasswordInputRedirect);

        backBtn = findViewById(R.id.imgbtnPrev);

        animalViewPager = (ViewPager2) findViewById(R.id.viewPagerImageSlider2ChildAnimal);
        setupViewPager(animalViewPager, "animal");
        ChildLogInRedesign2.PageListener pageListener = new ChildLogInRedesign2.PageListener("animal");
        animalViewPager.registerOnPageChangeCallback(pageListener);

        foodViewPager = (ViewPager2) findViewById(R.id.viewPagerImageSlider2ChildFood);
        setupViewPager(foodViewPager, "food");
        pageListener = new ChildLogInRedesign2.PageListener("food");
        foodViewPager.registerOnPageChangeCallback(pageListener);

        colorViewPager = (ViewPager2) findViewById(R.id.viewPagerImageSlider2ChildColor);
        setupViewPager(colorViewPager, "color");
        pageListener = new ChildLogInRedesign2.PageListener("color");
        colorViewPager.registerOnPageChangeCallback(pageListener);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Button btnGo = (Button) findViewById(R.id.btnChildRegister);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = foodPass + animalPass + colorPass;

                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("email", GlobalVariables.email);
                hashMap.put("password", password);

                apiInterface.loginUser("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.code() == 200) {

                            String getserverusertype = "";
                            int getuserID = 0;

                            JSONObject gsonObject = null;
                            try {
                                gsonObject = new JSONObject(new Gson().toJson(response.body()));

                                if (gsonObject.getInt("userID") > 0) {
                                    UserModel usermodel = new UserModel(gsonObject);
                                    GlobalVariables.loggedInUser = usermodel;
                                    Intent intent = new Intent(ChildLogInRedesign2.this, Child_Home.class);
                                    intent.putExtra( "childPass", password);
                                    /*intent.putExtra("userID", getuserID);*/
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ChildLogInRedesign2.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(ChildLogInRedesign2.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(ChildLogInRedesign2.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

        //redirect to text password
       /* txtPasswordRedirect.setVisibility(View.INVISIBLE);
        txtPasswordRedirect.setEnabled(false);
        txtPasswordRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildLogInRedesign2.this, ChildTextPasswordSignin.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });*/
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
