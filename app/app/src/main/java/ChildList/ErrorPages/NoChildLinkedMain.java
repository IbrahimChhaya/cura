package ChildList.ErrorPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.R;

public class NoChildLinkedMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_child_linked_main);

        Button btnNoChildLinked = (Button)findViewById(R.id.btnAddPair2);

        ImageView btnBack = (ImageView) findViewById(R.id.NoLinkBack) ;

        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.NoLinkToolbar);
        setSupportActionBar(myToolbar);

        TextView heading = (TextView) myToolbar.findViewById(R.id.NoLinktextview);
        heading.setText("Add Pair");

        btnNoChildLinked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoChildLinkedMain.this, Guardian_Home.class);
                i.putExtra("PairStatus", "No pair");
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