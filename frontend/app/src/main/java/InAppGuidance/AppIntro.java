package InAppGuidance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mhaprototype.R;
import com.github.paolorotolo.appintro.AppIntroFragment;

import Appointment.CancelledAppointmentFragment;
import StartUpStuff.NewStartUp;

public class AppIntro extends com.github.paolorotolo.appintro.AppIntro {
private String slideTitle1, slideTitle2, slideTitle3;
private String slideDescription1, slideDescription2, slideDescription3;
private int slideImage1, slideImage2, slideImage3;
private int slideColor1, slideColor2, slideColor3;

SharedPreferences sharedPref;
SharedPreferences.Editor sharedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);
//        AppIntroCustomLayoutFragment.newInstance(R.layout.intro_custom_layout1)


        //slide 1
        slideTitle1 = "Welcome to Cura";
        slideDescription1 = "App for kids with mental issues";
        slideImage1 =  R.drawable.app_icon;
        slideColor1 = ContextCompat.getColor(getApplicationContext(),R.color.App_Intro_Col4 );
        addSlide(AppIntroFragment.newInstance(slideTitle1, slideDescription1,slideImage1, slideColor1));

        //slide 2
        slideTitle2 = "Mood Tracker";
        slideDescription2 = "Record How You Feel Anytime, Anywhere.";
        slideImage2 =  R.drawable.blobsmile;
        slideColor2 = ContextCompat.getColor(getApplicationContext(),R.color.App_Intro_Col2 );
        addSlide(AppIntroFragment.newInstance(slideTitle2, slideDescription2,slideImage2, slideColor2));

        //slide 3
        slideTitle3 = "Get Started";
        slideDescription3 = "Enjoy Your App";
        slideImage3 =  R.drawable.app_icon;
        slideColor3 = ContextCompat.getColor(getApplicationContext(),R.color.App_Intro_Col3 );
        addSlide(AppIntroFragment.newInstance(slideTitle3, slideDescription3,slideImage3, slideColor3));


        showBackButtonWithDone = true;

//        setTransformer(AppIntroPageTransformerType.Fade);
//        setTransformer(AppIntroPageTransformerType.Zoom);
//        setTransformer(AppIntroPageTransformerType.Flow);
//        setTransformer(AppIntroPageTransformerType.SlideOver);
//        setTransformer(AppIntroPageTransformerType.Depth);

        //initialised shared preferences and gave it a name called MyAppPreferences and a private context mode
        sharedPref = getApplicationContext().getSharedPreferences("MyAppPreference", Context.MODE_PRIVATE);

        //initialised editor
        sharedEditor = sharedPref.edit();

        if(sharedPref != null){
            //checking the boolean value and setting the default value to false
            boolean checkShared = sharedPref.getBoolean("checkStatus", false);

            if(checkShared == true){
                Intent intent = new Intent(AppIntro.this, NewStartUp.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onSkipPressed(Fragment currentFragment){
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(AppIntro.this, NewStartUp.class);
        startActivity(intent);

        sharedEditor.putBoolean("checkStatus", false).commit();
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment){
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(AppIntro.this, NewStartUp.class);
        startActivity(intent);

        sharedEditor.putBoolean("checkStatus", true).commit();
        finish();
    }
}