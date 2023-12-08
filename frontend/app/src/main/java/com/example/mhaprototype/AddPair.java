package com.example.mhaprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPair extends AppCompatActivity {

    APIInterface apiInterface;
    Button btnPair;
    EditText txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pair);
        btnPair = (Button) findViewById(R.id.btnAddPair);
        txtCode = (EditText) findViewById(R.id.txtPairCode);


        btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> hashMap = new HashMap<>();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);
                hashMap.put("userId" ,GlobalVariables.loggedInUser.getUserID());
                hashMap.put("pairCode",txtCode.getText().toString());

                apiInterface.pair("application/json; charset=utf-8", hashMap).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.code()==200)
                        {

                            Toast.makeText(AddPair.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                            if(response.body().toString().equals("Paired")){
                                openHome();

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
        });

    }

    public void openHome(){
        Intent intent = new Intent(this, Guardian_Home.class);
        startActivity(intent);
    }
}