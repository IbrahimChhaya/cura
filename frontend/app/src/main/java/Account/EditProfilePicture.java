package Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
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

import Account.Child.UpdateChildAccount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfilePicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_picture);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariables.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Button btnSave = findViewById(R.id.btnSavePicture);
        ImageView backButton = findViewById(R.id.imageView19);

        ViewPager2 pictureView = findViewById(R.id.imageSlider);

        //get user type (Child/Parent)
        String userType = GlobalVariables.loggedInUser.getUserType();

        List<SliderItem> sliderItems = new ArrayList<>();

        if(userType.equals("Parent") || userType.equals("Psychologist")){
            sliderItems.add(new SliderItem(R.drawable.adult1));
            sliderItems.add(new SliderItem(R.drawable.adult2));
            sliderItems.add(new SliderItem(R.drawable.adult3));
            sliderItems.add(new SliderItem(R.drawable.adult4));
            sliderItems.add(new SliderItem(R.drawable.adult5));
            sliderItems.add(new SliderItem(R.drawable.adult6));
            sliderItems.add(new SliderItem(R.drawable.adult7));
            sliderItems.add(new SliderItem(R.drawable.adult8));
            sliderItems.add(new SliderItem(R.drawable.adult9));
        }
        else if(userType.equals("Child")){
            sliderItems.add(new SliderItem(R.drawable.child0));
            sliderItems.add(new SliderItem(R.drawable.child1));
            sliderItems.add(new SliderItem(R.drawable.child2));
            sliderItems.add(new SliderItem(R.drawable.child3));
            sliderItems.add(new SliderItem(R.drawable.child4));
            sliderItems.add(new SliderItem(R.drawable.child5));
        }


        pictureView.setAdapter(new SliderAdapter(sliderItems, pictureView));

        pictureView.setClipToPadding(false);
        pictureView.setClipChildren(false);
        pictureView.setOffscreenPageLimit(3);
        pictureView.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer((new MarginPageTransformer(10)));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        pictureView.setPageTransformer(compositePageTransformer);
        PageListener pageListener = new PageListener();
        pictureView.registerOnPageChangeCallback(pageListener);

        //back button
        backButton.setOnClickListener(v -> finish());

        //button onClick listener
        btnSave.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();

            //name email dob profilePicture
            map.put("userID", GlobalVariables.loggedInUser.getUserID());
            map.put("newName", GlobalVariables.loggedInUser.getName());
            map.put("newDOB", GlobalVariables.loggedInUser.getDob());
            map.put("newProfilePicture", GlobalVariables.profilePicture);

            apiInterface.updateAccount("application/json; charset=utf-8", map).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if(response.code() == 200){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));

                            if(jsonObject.getInt("userID") > 0){

                                GlobalVariables.loggedInUser = new UserModel(jsonObject);

                                Intent intent;
                                if(GlobalVariables.loggedInUser.getUserType().equals("Parent")|| GlobalVariables.loggedInUser.getUserType().equals("Psychologist")){
                                    intent = new Intent(EditProfilePicture.this, UpdateGuardianAccount.class);
                                }
                                else{
                                    intent = new Intent(EditProfilePicture.this, UpdateChildAccount.class);
                                }
                                startActivity(intent);

                            }
                            else if(jsonObject.getInt("userID") == 0){
                                Toast.makeText(EditProfilePicture.this, "User not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(EditProfilePicture.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(EditProfilePicture.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(EditProfilePicture.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private static class PageListener extends ViewPager2.OnPageChangeCallback {
        private final String userType = GlobalVariables.loggedInUser.getUserType();

        public void onPageSelected(int position) {

            if(userType.equals("Parent") || userType.equals("Psychologist"))
                GlobalVariables.profilePicture = "adult" + (position + 1) + ".png";
            else if(userType.equals("Child"))
                GlobalVariables.profilePicture = "child" + position + ".png";

        }
    }
}