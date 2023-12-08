package StartUpStuff.GuardianChildRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mhaprototype.R;

public class GuardianRegistersChild2 extends AppCompatActivity {
    Button btnNext;
    EditText email;
    ImageView imgPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_registers_child2);
    }
}