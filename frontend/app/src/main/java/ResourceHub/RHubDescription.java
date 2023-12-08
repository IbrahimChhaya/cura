package ResourceHub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.MainActivity;
import com.example.mhaprototype.NavigationPage;
import com.example.mhaprototype.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RHubDescription extends AppCompatActivity {
    ListView listview;
//    int illnessImage = R.drawable.adult4;
//    String[] description = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy te"};

    ArrayList<Integer> listImages = new ArrayList<>();
    ArrayList<String> listDescription = new ArrayList<>();
    ArrayList<Integer> listProblemID = new ArrayList<>();

    List<item> listItem = new ArrayList<>();

    ImageView myImageView;

//    String[] arrayDescription;

    int clickedProblemID;
    String RhubProblemTableDescription;
    String RhubProblemName;
    ArrayList<String> addRhubDescription = new ArrayList<>();
    ArrayList<String> addRhubName = new ArrayList<>();

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rhubdescription);

        listview = findViewById(R.id.rhubdescriptionlistviewID);
        myImageView = (ImageView) findViewById(R.id.desriptionbackbutton);
        //backButton = (ImageButton) findViewById(R.id.backRhubmain);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Intent intent = getIntent();
        clickedProblemID = intent.getIntExtra("probID", 0);
        RhubProblemTableDescription  = intent.getStringExtra("probDescription");
        RhubProblemName = intent.getStringExtra("probName");
        addRhubDescription.add(RhubProblemTableDescription);
        addRhubName.add(RhubProblemName);

//        backButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Do something in response to button click
//                Intent intent = new Intent(RHubDescription.this, ResourceHubMain.class);
//                startActivity(intent);
//            }
//        });

        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        apiInterface.getResourceHubImages(clickedProblemID).enqueue(new Callback<List<RHubDescriptionModel>>() {
            @Override
            public void onResponse(Call<List<RHubDescriptionModel>> call, Response<List<RHubDescriptionModel>> response) {
                if(response.code() == 200){
                    for(RHubDescriptionModel descModel : response.body()){


                        listDescription.add(descModel.getDescription().toString());
                        listProblemID.add(descModel.getProblemID());



                        String ImageAsset = descModel.getFileName();

                        if(ImageAsset.contains(".svg")){
                            ImageAsset = descModel.getFileName().replace(".svg", "");
                        }else if(ImageAsset.contains(".png")){
                            ImageAsset = descModel.getFileName().replace(".png", "");
                        }else if(ImageAsset.contains(".jpg")){
                            ImageAsset = descModel.getFileName().replace(".jpg", "");
                        }else if(ImageAsset.contains(".jpeg")){
                            ImageAsset = descModel.getFileName().replace(".jpeg", "");
                        }else if(ImageAsset.contains(".gif")){
                            ImageAsset = descModel.getFileName().replace(".gif", "");
                        }else if(ImageAsset.contains(".avif")){
                            ImageAsset = descModel.getFileName().replace(".avif", "");
                        }else if(ImageAsset.contains(".jiff")){
                            ImageAsset = descModel.getFileName().replace(".jiff", "");
                        }

                        String imageLocation = "@drawable/" + ImageAsset;
                        int imageResource = getResources().getIdentifier(imageLocation, null , getPackageName());
                        listImages.add(imageResource);
                    }
                }



                for(int i = 0; i < listProblemID.size(); i++ ){
                    item itemObject = new item(listDescription.get(i), listImages.get(i), listProblemID.get(i));
                    listItem.add(itemObject);
                }

                MyAdapter adapter = null; 

                if(GlobalVariables.loggedInUser.getUserType().equals("Child")){
                    String[] myArray = new String[addRhubDescription.size()];
                    addRhubDescription.toArray(myArray);

                    String subString = null;

                    for(int i = 0; i < myArray.length; i++) {
                        int firstOccurence = myArray[i].indexOf(".");

                        if(firstOccurence != -1) {
                            subString = myArray[i].substring(0, firstOccurence) + ".";
                        }
                    }

                    ArrayList<String> newDescriptionList = new ArrayList<>(Arrays.asList(subString));



                    adapter = new MyAdapter(RHubDescription.this, addRhubName, listImages, newDescriptionList);

                }else if(GlobalVariables.loggedInUser.getUserType().equals("Parent")||GlobalVariables.loggedInUser.getUserType().equals("Psychologist") ){
                    adapter = new MyAdapter(RHubDescription.this, addRhubName, listImages, addRhubDescription);
                }
                 

                listview.setAdapter(adapter);
            }




            @Override
            public void onFailure(Call<List<RHubDescriptionModel>> call, Throwable t) {

            }
        });
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

//        backButton.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });







    }



//            backButton.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View v) {
//            Intent i = new Intent(RHubDescription.this, ResourceHubMain.class);
//            startActivity(i);
//        }
//    });

//    public void GoBack(View v){
////        Intent i = new Intent(RHubDescription.this, ResourceHubMain.class);
////        startActivity(i);
//        finish();
//    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<Integer> imgs;
        ArrayList <String> names;
        ArrayList<String> adaptdesc;

        MyAdapter(Context c, ArrayList <String> n, ArrayList<Integer> img, ArrayList<String> desc) {
            super(c, R.layout.resourcehub_row, R.id.text2, desc);
            context = c;
            this.names = n;
            this.imgs = img;
            this.adaptdesc = desc;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View row = layoutInflater.inflate(R.layout.resourcehub_row, parent, false);

            ImageView images = row.findViewById(R.id.illnessImage);

            Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar12);
            setSupportActionBar(myToolbar);

            TextView illNames = (TextView) myToolbar.findViewById(R.id.toolbarText12);

            illNames.setText(names.get(position));



            TextView illDesc = row.findViewById(R.id.illnessDescription);

           // myToolbar.setTitle(names.get(position));
            //illNames.setText(names.get(position));
            images.setImageResource(imgs.get(position));
            illDesc.setText(adaptdesc.get(position));



//            if(myToolbar.getTitle().length() == 3){
//                myToolbar.setTitleMarginStart(500);
//            }else if(myToolbar.getTitle().length() == 10){
//                myToolbar.setTitleMarginStart(420);
//            }else if(myToolbar.getTitle().length() == 21){
//                myToolbar.setTitleMarginStart(311);
//            }else if(myToolbar.getTitle().length() == 16){
//                myToolbar.setTitleMarginStart(373);
//            }else if(myToolbar.getTitle().length() == 8){
//                myToolbar.setTitleMarginStart(454);
//            }else if(myToolbar.getTitle().length() == 4){
//                myToolbar.setTitleMarginStart(480);
//            }else if(myToolbar.getTitle().length() == 9){
//                myToolbar.setTitleMarginStart(436);
//            }else if(myToolbar.getTitle().length() == 13){
//                myToolbar.setTitleMarginStart(380);
//            }else if(myToolbar.getTitle().length() == 5){
//                myToolbar.setTitleMarginStart(463);
//            }else if(myToolbar.getTitle().length() == 26){
//                myToolbar.setTitleMarginStart(271);
//            }

            return row;
        }


//
//        public void GoBack(View v){
//            Intent i = new Intent(RHubDescription.this, ResourceHubMain.class);
//            startActivity(i);
//        }

        //       @NonNull
//       @Override
//        public View getView(int position, View convertView, ViewGroup parent){
//            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View row = layoutInflater.inflate(R.layout.row, parent,false);
//            ImageView images = (ImageView) findViewById(R.id.img1);
//            TextView illNames = (TextView) findViewById(R.id.text1);
//            TextView  illDesc = (TextView) findViewById(R.id.text2);
//
//           images.setImageResource(illnessImage[position]);
//            illNames.setText(illnessName[position]);
//            illDesc.setText(description[position]);
//
//            return row;
//        }
    }





}
