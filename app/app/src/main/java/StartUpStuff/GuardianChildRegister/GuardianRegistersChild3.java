package StartUpStuff.GuardianChildRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.example.mhaprototype.SliderAdapter;
import com.example.mhaprototype.SliderItem;
import com.example.mhaprototype.UserModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ChildList.ChildListMain;
import ChildList.ChildListRedesigned;
import StartUpStuff.ChildLogInRedesign.ChildLogInRedesign1;
import StartUpStuff.ChildRegister.ChildRegisterRedesign3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuardianRegistersChild3 extends AppCompatActivity {
    ImageView imgPrev;
   // TextView txtAlreadySigned;
    Button btnSignUp;

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

    TextView txtPasswordRedirect;


    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_registers_child3);

       // txtAlreadySigned = findViewById(R.id.GCRegtxtAlreadySigned);
        imgPrev = findViewById(R.id.GCRegimgbtnPrev);
        btnSignUp = findViewById(R.id.GCRegbtnChildRegister);
        txtPasswordRedirect = findViewById(R.id.txtTextPasswordInstead);


        animalViewPager = (ViewPager2) findViewById(R.id.GCRegviewPagerImageSlider2ChildAnimal);

        setupViewPager(animalViewPager, "animal");
        GuardianRegistersChild3.PageListener pageListener = new GuardianRegistersChild3.PageListener("animal");
        animalViewPager.registerOnPageChangeCallback(pageListener);


        foodViewPager = (ViewPager2) findViewById(R.id.GCRegviewPagerImageSlider2ChildFood);
        setupViewPager(foodViewPager, "food");
        pageListener = new GuardianRegistersChild3.PageListener("food");
        foodViewPager.registerOnPageChangeCallback(pageListener);

        colorViewPager = (ViewPager2) findViewById(R.id.GCRegviewPagerImageSlider2ChildColor);
        setupViewPager(colorViewPager, "color");
        pageListener = new GuardianRegistersChild3.PageListener("color");
        colorViewPager.registerOnPageChangeCallback(pageListener);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String password = foodPass + animalPass +  colorPass;


//                GlobalVariables.grade = "0";
//                GlobalVariables.birthDate = "2019-08-16";

                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = formatter.format(todayDate);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                //String DateOfBirth = GlobalVariables.birthDate + "T00:00:00";
                String DateRegistered = todayString + "T00:00:00";

                Map<String, Object> hashMap = new HashMap<>();
//                Map<String, Object> hashmap2 = new HashMap<>();
//                Map<String, Integer> hashmap3 = new HashMap<>();
//                Map<Object, Object> hashmap4 = new HashMap<>();


                UserModel usrModelChildInfo = new UserModel(GlobalVariables.email, GlobalVariables.name, password, null, "Child", null, GlobalVariables.profilePicture, DateRegistered, "Active",true);

                Model guardianID = new Model(GlobalVariables.loggedInUser.getUserID());
               // ChildInfo childinfo = new ChildInfo( GlobalVariables.email, GlobalVariables.name, password, DateOfBirth, "Child", GlobalVariables.grade, GlobalVariables.profilePicture, DateRegistered, "Active");

                hashMap.put("childInfo", usrModelChildInfo);
                hashMap.put("guardianID", GlobalVariables.loggedInUser.getUserID());

//
//                hashMap.put("Email", GlobalVariables.email);
//                hashMap.put("Name", GlobalVariables.name);
//                hashMap.put("Password", password);
//                hashMap.put("DOB",  GlobalVariables.birthDate + "T00:00:00");
//                hashMap.put("UserType","Child");
//                hashMap.put("Grade", GlobalVariables.grade);
//                hashMap.put("ProfilePicture", GlobalVariables.profilePicture);
//
//                hashMap.put("DateRegistered", todayString + "T00:00:00");
//                hashMap.put("Status", "Active");
//                hashmap3.put("guardianID", GlobalVariables.loggedInUser.getUserID());
//                hashmap2.put("childInfo", hashMap);
//                hashmap4.put(hashmap2, hashmap3);
//                hashmap2.put(3001,hashMap);


                apiInterface.GuardianRegistersChild("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        if (response.code() == 200) {
                            JSONObject gsonObject = null;
                            try {
                                gsonObject = new JSONObject(new Gson().toJson(response.body()));
                                if(gsonObject.getInt("userID")>0)
                                {
                                    Intent intent = new Intent(GuardianRegistersChild3.this, ChildListRedesigned.class);
                                    Toast.makeText(GuardianRegistersChild3.this,"Registered Successfully", Toast.LENGTH_LONG).show();

                                    startActivity(intent);
                                    //intent  = getIntent();
                                    // Toast.makeText(ChildRegisterRedesign3.this,intent.getStringExtra("CReg1"), Toast.LENGTH_LONG).show();
//                                    String mes = intent.getStringExtra("CReg1");
//                                    Toast.makeText(ChildRegisterRedesign3.this,mes, Toast.LENGTH_LONG).show();


                                }else if(gsonObject.getInt("userID")==0)
                                {
                                    Toast.makeText(GuardianRegistersChild3.this, "Email already exists", Toast.LENGTH_LONG).show();
                                }else
                                {
                                    Toast.makeText(GuardianRegistersChild3.this, "Something went wrong: Please try again later", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                           /* Intent intent = new Intent(childRegister2.this, ChildLogin.class);
                            Toast.makeText(childRegister2.this,"Registered Successfully", Toast.LENGTH_LONG).show();
                            startActivity(intent);*/

                        } else {
                            Toast.makeText(GuardianRegistersChild3.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            Log.i("error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(GuardianRegistersChild3.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.i("error", t.getLocalizedMessage());
                    }
                });
            }
        });

//        txtAlreadySigned.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(GuardianRegistersChild3.this, Login.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//            }
//        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        //button to redirect to text password page
        txtPasswordRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuardianRegistersChild3.this, GuardianRegistersChild4.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

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