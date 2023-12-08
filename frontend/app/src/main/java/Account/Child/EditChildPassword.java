package Account.Child;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.SliderAdapter;
import com.example.mhaprototype.SliderItem;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditChildPassword extends AppCompatActivity {

    static final String[] Animals = {"Crocodile", "Rabbit", "Lion", "Elephant", "Turtle", "Fish"};

    static final String[] Foods = {"Hotdog", "Pizza", "Apple", "Burger", "Chicken", "Donut"};

    static final String[] Colors = {"Red", "Blue", "Green", "Yellow", "Pink", "Purple"};

    ViewPager2 currFoodPager;
    ViewPager2 currAnimalPager;
    ViewPager2 currColorPager;

    ViewPager2 newFoodPager;
    ViewPager2 newAnimalPager;
    ViewPager2 newColorPager;

    ViewPager2 confFoodPager;
    ViewPager2 confAnimalPager;
    ViewPager2 confColorPager;

    Button btnSave;
    TextView txtMatchError;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child_password);

        //current password picker
        currFoodPager = findViewById(R.id.currentFoodPager);
        setupViewPager(currFoodPager, "food");

        currAnimalPager = findViewById(R.id.currentAnimalPager);
        setupViewPager(currAnimalPager, "animal");

        currColorPager = findViewById(R.id.currentColorPager);
        setupViewPager(currColorPager, "color");

        //new password picker
        newFoodPager = findViewById(R.id.newFoodPager);
        setupViewPager(newFoodPager, "food");

        newAnimalPager = findViewById(R.id.newAnimalPager);
        setupViewPager(newAnimalPager, "animal");

        newColorPager = findViewById(R.id.newColorPager);
        setupViewPager(newColorPager, "color");

        //Confirmation password picker
//        confFoodPager = findViewById(R.id.confFoodPager);
//        setupViewPager(confFoodPager, "food");
//
//        confAnimalPager = findViewById(R.id.confAnimalPager);
//        setupViewPager(confAnimalPager, "animal");
//
//        confColorPager = findViewById(R.id.confColorPager);
//        setupViewPager(confColorPager, "color");

        txtMatchError = findViewById(R.id.matchError);
        backBtn = findViewById(R.id.imageView21);

        backBtn.setOnClickListener(v -> finish());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);


        btnSave = findViewById(R.id.btnSaveChildPassword);
        btnSave.setOnClickListener(v -> {
//            txtMatchError.setVisibility(View.INVISIBLE);
//            txtMatchError.setText("New password must match Confirmation password");

            String oldPassword = getItemChosen(currFoodPager.getCurrentItem(), "food")
                    + getItemChosen(currAnimalPager.getCurrentItem(), "animal")
                    + getItemChosen(currColorPager.getCurrentItem(), "color");

            String newPassword = getItemChosen(newFoodPager.getCurrentItem(), "food")
                    + getItemChosen(newAnimalPager.getCurrentItem(), "animal")
                    + getItemChosen(newColorPager.getCurrentItem(), "color");

//            String confPassword = getItemChosen(confFoodPager.getCurrentItem(), "food")
//                    + getItemChosen(confAnimalPager.getCurrentItem(), "animal")
//                    + getItemChosen(confColorPager.getCurrentItem(), "color");

//            if(!newPassword.equals(confPassword)){
              // txtMatchError.setVisibility(View.VISIBLE);
//                return;
//            }
//            else{
//                txtMatchError.setVisibility(View.INVISIBLE);
//            }

            Map<String, Object> map = new HashMap<>();

            //userID, oldPassword, newPassword
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("oldPassword", oldPassword);
            map.put("newPassword", newPassword);

            apiInterface.changePassword("application/json; charset=utf-8", map).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("userID") > 0){
                                Toast.makeText(EditChildPassword.this, "Password Successfully changed", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(EditChildPassword.this, UpdateChildAccount.class);
                                startActivity(intent);
                            }
                            else if(jsonObject.getInt("userID") == -2){
                                txtMatchError.setText("Incorrect Old Password provided");
                                txtMatchError.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(EditChildPassword.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditChildPassword.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(EditChildPassword.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });


        });

    }

    //get current item chosen from view pager
    private String getItemChosen(int position, String type){
        String chosen;
        switch(type){
            case "animal":
                chosen =  Animals[position];
                break;
            case "food":
                chosen = Foods[position];
                break;
            case "color":
                chosen = Colors[position];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return chosen;
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
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
    }

}