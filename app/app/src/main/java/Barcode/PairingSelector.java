package Barcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mhaprototype.R;

public class PairingSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing_selector);

        alertDialog();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);

        dialog.setTitle("Pairing Code");
        dialog.setMessage("Would you like to");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}