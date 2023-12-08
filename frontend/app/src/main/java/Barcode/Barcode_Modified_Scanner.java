package Barcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.MainActivity;
import com.example.mhaprototype.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import ChildList.ChildListRedesigned;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Barcode_Modified_Scanner extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    ImageView backButton;
    TextView scanText;
    Dialog dLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_modified_scanner);



        scanText =  findViewById(R.id.QRCode_TextView);
        backButton = findViewById(R.id.QRCodeBackButton);
        dLog = new Dialog(this);

       // onBackPressed();



        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }else{
            startScanning();
        }

//                scannerView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(Build.VERSION.SDK_INT >= 23){
//                            if(getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                                mCodeScanner.startPreview();
////                                openScanner();
//                            }else{
//                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
//                            }
//                        }else{
//                            mCodeScanner.startPreview();
//                       //     openScanner();
//                        }
//                    }
//                });




    }

    private void startScanning(){
        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.startPreview();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(Barcode_Modified_Scanner.this, result.getText(), Toast.LENGTH_SHORT).show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(GlobalVariables.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        APIInterface apiInterface = retrofit.create(APIInterface.class);

                        // userID & Email
                        HashMap<String, Object> map = new HashMap<>();


                        map.put("userId" ,GlobalVariables.loggedInUser.getUserID());
                        map.put("pairCode",result.getText());



                        apiInterface.pair ("application/json; charset=utf-8", map).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == 200){
                                    if(response.body().equals("Paired")){
                                        Toast.makeText(Barcode_Modified_Scanner.this, "Successfully Paired", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Barcode_Modified_Scanner.this, ChildListRedesigned.class); //Barcode_Scanner
                                        startActivity(intent);

                                    }
                                    else if(response.body().equals("The pair code is incorrect")){
                                        Toast.makeText(Barcode_Modified_Scanner.this, "Failed to pair: Invalid pairing code provided", Toast.LENGTH_LONG).show();
                                    }
                                    else{

                                        Toast.makeText(Barcode_Modified_Scanner.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{

                                    Toast.makeText(Barcode_Modified_Scanner.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                                Toast.makeText(Barcode_Modified_Scanner.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                        //scanText.setText(result.getText());
                    }
                });
            }
        });

        //to scan again if clicked onscreen
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    protected void onPause() {
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }

    // *************************************************
    private boolean CheckPermission(String permission){
        int result = ContextCompat.checkSelfPermission(Barcode_Modified_Scanner.this, permission);

        if(result != PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }



    /*
     we will call this method fo opening camera scanner depending on the permission successful or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_REQUEST_CODE){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  // openScanner();
                    Toast.makeText(Barcode_Modified_Scanner.this, "Permission  Granted", Toast.LENGTH_LONG).show();
                    startScanning();
                }else{
                    //permission not granted
                    Intent intent = new Intent(Barcode_Modified_Scanner.this, Guardian_Home.class); //Barcode_Scanner
                    Toast.makeText(Barcode_Modified_Scanner.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
        }
    }



    @Override
    public void onBackPressed() {
        dLog.setContentView(R.layout.activity_pairing_selector);
        dLog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txtClose = dLog.findViewById(R.id.textViewClose);
        TextView txtReScan = dLog.findViewById(R.id.textViewReScan);
        TextView txtEnterCode = dLog.findViewById(R.id.textViewPair);

        ImageView imgReScan = dLog.findViewById(R.id.ImageRescan);
        ImageView imgEnterCode = dLog.findViewById(R.id.ImageEnterCode);
        ImageView imgClose = dLog.findViewById(R.id.ImageClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barcode_Modified_Scanner.this, Guardian_Home.class); //Barcode_Scanner
                startActivity(intent);
            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Barcode_Modified_Scanner.this, Guardian_Home.class); //Barcode_Scanner
                startActivity(intent);
            }
        });

        imgReScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLog.cancel();
            }
        });

        txtReScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Barcode_Modified_Scanner.this, Barcode_Modified_Scanner.class); //Barcode_Scanner
//                startActivity(intent);
                dLog.cancel();
            }
        });

        imgEnterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPairAlertDialog();
            }
        });

        txtEnterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPairAlertDialog();
            }
        });

        dLog.show();
    }



    private void addPairAlertDialog() {
        AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.remove_account_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextInputEditText txtCodeValue = dialogView.findViewById(R.id.txtEmailValue);
        final TextInputLayout codeLayout = dialogView.findViewById(R.id.txtRemoveEmail);

        codeLayout.setHint("Pairing code");

        dialogBuilder.setTitle("Pair with child");
        dialogBuilder.setMessage("Enter your child's pairing code below");
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean[] closeDialog = {false};

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GlobalVariables.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIInterface apiInterface = retrofit.create(APIInterface.class);

                // userID & Email
                HashMap<String, Object> map = new HashMap<>();


                map.put("userId" ,GlobalVariables.loggedInUser.getUserID());
                map.put("pairCode",txtCodeValue.getText().toString().trim());



                apiInterface.pair ("application/json; charset=utf-8", map).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == 200){
                            if(response.body().equals("Paired")){
                                Toast.makeText(Barcode_Modified_Scanner.this, "Successfully Paired", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                                Intent intent = new Intent(Barcode_Modified_Scanner.this, ChildListRedesigned.class); //Barcode_Scanner
                                startActivity(intent);

                            }
                            else if(response.body().equals("The pair code is incorrect")){
                                codeLayout.setError("Failed to pair: Invalid pairing code provided");
                            }
                            else{
                                closeDialog[0] = true;
                                Toast.makeText(Barcode_Modified_Scanner.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            closeDialog[0] = true;
                            Toast.makeText(Barcode_Modified_Scanner.this, response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        closeDialog[0] = true;
                        Toast.makeText(Barcode_Modified_Scanner.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                if(closeDialog[0])
                    alertDialog.dismiss();
            }
        });
    }


}


