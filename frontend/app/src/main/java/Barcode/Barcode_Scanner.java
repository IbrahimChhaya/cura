package Barcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.Login;
import com.example.mhaprototype.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Barcode_Scanner extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 101;
    Button Scan_Code;
    TextView txtdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        txtdata = (TextView) findViewById(R.id.data_text);
        //barcode Scanner
        Scan_Code = findViewById(R.id.button_Scan);

        Scan_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 23){
                    if(CheckPermission(Manifest.permission.CAMERA)){
                        openScanner();
                    }else{
                        requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                    }
                }else{
                    openScanner();
                }
            }
        });
    }

    //will read the QR Code
    private void openScanner(){
        new IntentIntegrator(Barcode_Scanner.this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result == null){
            Toast.makeText(Barcode_Scanner.this, "Blank", Toast.LENGTH_LONG).show();
        }else{
            if(result.getContents() == null){
                Toast.makeText(Barcode_Scanner.this, "Blank", Toast.LENGTH_LONG).show();
            }else{
                txtdata.setText("Data: " + result.getContents());

                //getting pair to connect

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GlobalVariables.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIInterface apiInterface = retrofit.create(APIInterface.class);

                // userID & Email
                HashMap<String, Object> map = new HashMap<>();


                map.put("userId" ,GlobalVariables.loggedInUser.getUserID());
                map.put("pairCode",result.getContents());



                apiInterface.pair ("application/json; charset=utf-8", map).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == 200){
                            if(response.body().equals("Paired")){
                                Intent intent = new Intent(Barcode_Scanner.this, Guardian_Home.class);
                                Toast.makeText(Barcode_Scanner.this, "Successfully Paired", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                               // alertDialog.dismiss();

                            }
                            else if(response.body().equals("The pair code is incorrect")){
                               // codeLayout.setError("Failed to pair: Invalid pairing code provided");
                            }
                            else{
                                //closeDialog[0] = true;
                                Toast.makeText(Barcode_Scanner.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            //closeDialog[0] = true;
                            Toast.makeText(Barcode_Scanner.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //closeDialog[0] = true;
                        Toast.makeText(Barcode_Scanner.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                //Ends
            }
        }
    }

    /*
            Method to check the android version >= 6
         */
    private boolean CheckPermission(String permission){
        int result = ContextCompat.checkSelfPermission(Barcode_Scanner.this, permission);

        if(result != PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }

    private void requestPermission(String permission, int code){
        if(ActivityCompat.shouldShowRequestPermissionRationale(Barcode_Scanner.this,permission)){

        }else{
            ActivityCompat.requestPermissions(Barcode_Scanner.this, new String[]{permission},code); //requesting permission
        }
    }

    /*
     we will call this method fo opening camera scanner depending on the permission successful or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case    CAMERA_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openScanner();
                }
        }
    }
}