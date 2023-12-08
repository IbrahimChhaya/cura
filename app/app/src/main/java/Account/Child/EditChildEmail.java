package Account.Child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.example.mhaprototype.SliderAdapter;
import com.example.mhaprototype.SliderItem;
import com.example.mhaprototype.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditChildEmail extends AppCompatActivity {
    ViewPager2 foodPager;
    ViewPager2 animalPager;
    ViewPager2 colorPager;

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

    Button btnSave;
    TextInputEditText txtNewEmail;
    ImageView backBtn;

    TextInputLayout txtNewEmailLayout;
    TextInputEditText txtNewEmailConfirm;



    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changeButtonVisibility();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child_email);

        foodPager = findViewById(R.id.foodPager);
        setupViewPager(foodPager, "food");
        EditChildEmail.PageListener pageListener = new EditChildEmail.PageListener("food");
        foodPager.registerOnPageChangeCallback(pageListener);

        animalPager = findViewById(R.id.animalPager);
        setupViewPager(animalPager, "animal");
        pageListener = new EditChildEmail.PageListener("animal");
        animalPager.registerOnPageChangeCallback(pageListener);

        colorPager = findViewById(R.id.colorPager);
        setupViewPager(colorPager, "color");
        pageListener = new EditChildEmail.PageListener("color");
        colorPager.registerOnPageChangeCallback(pageListener);

        btnSave = findViewById(R.id.btnSaveChildEmail);
        TextView txtCurrentEmail = findViewById(R.id.txtChildCurrentEmail);
        txtNewEmail = findViewById(R.id.txtNewChildEmailValue);
        txtNewEmailConfirm = findViewById(R.id.txtNewChildEmailConfirm);
        backBtn = findViewById(R.id.imageView20);

        txtNewEmailLayout = findViewById(R.id.childConfEmail);




        txtCurrentEmail.setText(GlobalVariables.loggedInUser.getEmail());

        //add text listeners to edit texts
        txtNewEmail.addTextChangedListener(textWatcher);
        txtNewEmailConfirm.addTextChangedListener(textWatcher);
        changeButtonVisibility();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        btnSave.setOnClickListener(v -> {
            String password = foodPass + animalPass + colorPass;

            HashMap<String, Object> map = new HashMap<>();

            //name email dob profilePicture
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("oldPassword", password);
            map.put("newName", GlobalVariables.loggedInUser.getName());
            map.put("newEmail", txtNewEmail.getText().toString().trim());
            map.put("newDOB", GlobalVariables.loggedInUser.getDob());
            map.put("newProfilePicture", GlobalVariables.loggedInUser.getProfilePicture());
            map.put("grade", GlobalVariables.loggedInUser.getGrade());

            apiInterface.UpdateGuardianEmail("application/json; charset=utf-8", map).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("userID") > 0){
                                GlobalVariables.loggedInUser = new UserModel(jsonObject);
                                Intent intent = new Intent(EditChildEmail.this, UpdateChildAccount.class);
                                startActivity(intent);
                            }
                            else if(jsonObject.getInt("userID") == -2){
//                                    txtIncorrectPassword.setVisibility(View.VISIBLE);
//                                    errorLayout.setError("Incorrect Password");
                                Toast.makeText(EditChildEmail.this, "Wrong password", Toast.LENGTH_LONG).show();

                            }
                            else if(jsonObject.getInt("userID") == -3){
                                txtNewEmailLayout.setError("This email address is already in use.");
                            }
                            else if(jsonObject.getInt("userID") == 0){
                                Toast.makeText(EditChildEmail.this, "User not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(EditChildEmail.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditChildEmail.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(EditChildEmail.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        });

        backBtn.setOnClickListener(v -> finish());
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

    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private static final String TAG = "Peepee";
        private final String image;

        public PageListener(String image) {
            this.image = image;
        }

        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
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
        }
    }

    //Utility method: used to check if input fields are empty - in order to control button availability
    private  void changeButtonVisibility(){
        btnSave = findViewById(R.id.btnSaveChildEmail);
        txtNewEmail = findViewById(R.id.txtNewChildEmailValue);
        txtNewEmailConfirm = findViewById(R.id.txtNewChildEmailConfirm);

        txtNewEmailLayout.setError(null);
        String strEmail = txtNewEmail.getText().toString().trim();
        String strEmailConf = txtNewEmailConfirm.getText().toString().trim();

        if (strEmail.isEmpty() || strEmailConf.isEmpty()) {
            btnSave.setEnabled(false);
        } else {
            if(checkEmailsMatch())
                btnSave.setEnabled(true);
        }
    }

    //utility method: used to ensure that new email and confirmation email matches
    private boolean checkEmailsMatch() {
        txtNewEmailLayout = findViewById(R.id.childConfEmail);
        String strEmail = txtNewEmail.getText().toString().trim();
        String strEmailConf = txtNewEmailConfirm.getText().toString().trim();

        if(!strEmail.isEmpty() && !strEmailConf.isEmpty()){
            if(strEmail.equals(strEmailConf)){
                txtNewEmailLayout.setError(null);
                btnSave.setEnabled(true);
            }
            else{
                txtNewEmailLayout.setError("The confirmation email must match the new email");
                btnSave.setEnabled(false);
            }
        }

        return false;
    }
}