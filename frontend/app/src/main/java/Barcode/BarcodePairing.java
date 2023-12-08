package Barcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



//import com.google.zxing.MultiFormatWriter;


import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.MainActivity;
import com.example.mhaprototype.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarcodePairing extends AppCompatActivity {
    ImageView barcodeImage;

    TextView txtdata;
    APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_pairing);

        barcodeImage = (ImageView) findViewById(R.id.img_barcode);
        txtdata = (TextView) findViewById(R.id.data_text);

        //will store the pairing code
        String pairingCode = "Hey";

        MultiFormatWriter mfw = new MultiFormatWriter();

        //generating and fetching the paircode for the child
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();

        apiInterface = retrofit.create(APIInterface.class);
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("UserID" , GlobalVariables.loggedInUser.getUserID());

        apiInterface.generatePairCode("application/json; charset=utf-8", hashMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()== 200)
                {
                    try{
                        BitMatrix bMatrix = mfw.encode(response.body(), BarcodeFormat.QR_CODE,200,200); //set barcode height and width
                        txtdata.setText("Data: " + response.body());
                        //creating encoder for barcode
                        BarcodeEncoder bEncoder = new BarcodeEncoder();

                        //create bitmap image of barcode using the barcode encoder
                        Bitmap bMap = bEncoder.createBitmap(bMatrix);

                        //displaying to the screen
                        barcodeImage.setImageBitmap(bMap);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }else
                {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
//        try{
//                        BitMatrix bMatrix = mfw.encode("Whats Up", BarcodeFormat.QR_CODE,200,200); //set barcode height and width
//                        txtdata.setText("Data: " + "Hey");
//                        //creating encoder for barcode
//                        BarcodeEncoder bEncoder = new BarcodeEncoder();
//
//                        //create bitmap image of barcode using the barcode encoder
//                        Bitmap bMap = bEncoder.createBitmap(bMatrix);
//
//                        //displaying to the screen
//                        barcodeImage.setImageBitmap(bMap);
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }

    }


}
