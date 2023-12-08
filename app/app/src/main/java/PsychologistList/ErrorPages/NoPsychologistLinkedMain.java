package PsychologistList.ErrorPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mhaprototype.R;

import PsychologistList.nearbyPsychologistList;

public class NoPsychologistLinkedMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_psychologist_linked_main);

        //if no psychologist is linked to the parent
        Button btnNoPsychologistLinked = (Button)findViewById(R.id.btnFindPsychologist);

        ImageView btnBack = (ImageView) findViewById(R.id.NoPsychLinkBack) ;

        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.NoPsychLinkToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.NoPsychLinktextview);
        heading.setText("Select Psychologist");

        //take to the error page that displays that no psychologist is linked to the user
        btnNoPsychologistLinked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoPsychologistLinkedMain.this, nearbyPsychologistList.class);
                startActivity(i);
            }
        });

        //button to go back to the previous activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}