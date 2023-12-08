package StartUpStuff.GuardianRegister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.example.mhaprototype.Register;
import com.example.mhaprototype.SliderAdapter;
import com.example.mhaprototype.SliderItem;
import com.example.mhaprototype.guardianRegister2;
//import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.tibolte.agendacalendarview.widgets.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import StartUpStuff.SignInRoles;

public class NewGuardianRegister extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    Button btnNext;
    EditText txtGuardianName;
    TextView txtAlreadySigned;
    FloatingActionButton picker;
    ImageView profilePicture;
    ImageView imgPrev;

    //overriding the back button
    @Override
    public void onBackPressed()
    {
        //programming emulator back button go to the signin activity
        Intent i = new Intent(NewGuardianRegister.this, SignInRoles.class);
        i.putExtra("StartStatus","NotRegistered");
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guardian_register);
        btnNext = findViewById(R.id.btnGuardianRegisterNext2);
        txtGuardianName = findViewById(R.id.txtGuardianName2);
        imgPrev = findViewById(R.id.imgbtnPrev);
        txtAlreadySigned = findViewById(R.id.txtAlreadySigned);
        //picker = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        //profilePicture = findViewById(R.id.imgProfilePic);


        txtAlreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewGuardianRegister.this, Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewGuardianRegister.this, SignInRoles.class);
                i.putExtra("StartStatus","NotRegistered");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(NewGuardianRegister.this, NewGuardianRegister2.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);*/
                if(txtGuardianName.getText().toString().equals(""))
                {
                    Toast.makeText(NewGuardianRegister.this,"Please enter your name", Toast.LENGTH_LONG).show();
                }else
                {
                    GlobalVariables.guardianName = txtGuardianName.getText().toString();
                    //start the second register activity
                    Intent i = new Intent(NewGuardianRegister.this, NewGuardianRegister2.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }
            }
        });

        viewPager2 = findViewById(R.id.viewPagerImageSlider2);

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

       /* picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(NewGuardianRegister.this)
                        .galleryOnly()
                        .crop(100,100)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });*/

        //setting up the viewpager
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        String struri = uri.toString();
        Uri uri2 = Uri.parse(struri);
        profilePicture.setImageURI(uri2);
    }*/

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