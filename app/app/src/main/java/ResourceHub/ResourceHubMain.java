package ResourceHub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;



import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.Child_Home;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.Guardian_Home;
import com.example.mhaprototype.R;

import java.util.ArrayList;
import java.util.List;

import psychologistHome.psychologist_home;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResourceHubMain extends AppCompatActivity {

    APIInterface apiInterface;
    GridView gridView;
    CustomAdapter customAdapter;

    //Contents
    //random images for testing purpose
    int images[] = {R.drawable.child1,R.drawable.child1,R.drawable.child2,R.drawable.child3,R.drawable.child5};
    String names[] = {"Problem 1","Droblem 2","Croblem 3","Efoblem 4","Goblem 5"};

    String descriptions[] = {"Description 1","Description 2","Description 3","Description 4","Description 5","description6","description7"};

    ArrayList<String> listNames = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>();
    ArrayList<String> listDescription = new ArrayList<>();
    ArrayList<Integer> problemIDList = new ArrayList<>();
    List<item> itemList = new ArrayList<>();
    String homeMessage = "";

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        homeMessage = intent.getStringExtra("from");
        if(from.equals("childHome")){
            Intent i = new Intent(ResourceHubMain.this, Child_Home.class);
            startActivity(i);
        }
        else if(from.equals("guardianHome")){
            Intent i = new Intent(ResourceHubMain.this, Guardian_Home.class);
            startActivity(i);
        }else
        {
            Intent i = new Intent(ResourceHubMain.this, psychologist_home.class);
            startActivity(i);
        }
    }

    //programming the back button of the action bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_hub_main);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        homeMessage = intent.getStringExtra("from");


        //getting the grid view form layout
        gridView = (GridView)findViewById(R.id.resourceHub_GridView);

        //testing with API
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        apiInterface.fetchAllFocusPoints().enqueue(new Callback<List<ResourceHubModel>>() {
            @Override
            public void onResponse(Call<List<ResourceHubModel>> call, Response<List<ResourceHubModel>> response) {
                if(response.code()==200)
                {
                    for(ResourceHubModel rModel : response.body())
                    {
                        listDescription.add(rModel.getDescription().toString());
                        listNames.add(rModel.getProblem());
                        problemIDList.add(rModel.getProblemID());
                        String ImageAsset = rModel.getTitleImage(); //blobsmile.png
                        if(ImageAsset.contains(".svg"))
                        {
                            ImageAsset = rModel.getTitleImage().replace(".svg","");
                        }else if(ImageAsset.contains(".png"))
                        {
                            ImageAsset = rModel.getTitleImage().replace(".png",""); //blobsmile
                        }else if(ImageAsset.contains(".jpg"))
                        {
                            ImageAsset = rModel.getTitleImage().replace(".jpg","");
                        }
                        //String imageUrl = "@drawable/"+rModel.getTitleImage().replace(".png","");

                        String imageUrl = "@drawable/"+ImageAsset;
                        int imageResource = getResources().getIdentifier(imageUrl, null, getPackageName());
                        imageList.add(imageResource);
                    }

                    for(int i = 0 ; i < listNames.size() ;i++)
                    {
                        item itemObject = new item(listNames.get(i),listDescription.get(i),imageList.get(i),problemIDList.get(i));//images[i]);
                        itemList.add(itemObject);
                    }
                    customAdapter = new CustomAdapter(itemList,ResourceHubMain.this);
                    gridView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ResourceHubModel>> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.resource_hub_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.resource_search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.resource_search_view)
        {
            return true;
        }else if (id == android.R.id.home)
        {
            if(homeMessage.equals("childHome"))
            {
                Intent i = new Intent(ResourceHubMain.this,Child_Home.class);
                startActivity(i);
            }else if(homeMessage.equals("guardianHome"))
            {
                Intent i = new Intent(ResourceHubMain.this,Guardian_Home.class);
                startActivity(i);
            }else
            {
                Intent i = new Intent(ResourceHubMain.this,psychologist_home.class);
                startActivity(i);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends BaseAdapter implements Filterable
    {

        private List<item> itemList;
        private List<item> filteredItemList;
        private Context context;

        public CustomAdapter(List<item> itemList, Context context) {
            this.itemList = itemList;
            this.filteredItemList = itemList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return filteredItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.resource_hub_row_items,null);

            ImageView imageView = view.findViewById(R.id.problemImage);
            TextView txtName = view.findViewById(R.id.txtProblemName);


            imageView.setImageDrawable(getResources().getDrawable(filteredItemList.get(position).getImage(),getApplicationContext().getTheme()));
            txtName.setText(filteredItemList.get(position).getProblemName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), RHubDescription.class);
                    intent.putExtra( "probID",filteredItemList.get(position).getProblemID());
                    intent.putExtra("probDescription", filteredItemList.get(position).getDescription());
                    intent.putExtra("probName", filteredItemList.get(position).getProblemName());
                    startActivity(intent);
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();

                    if(constraint==null || constraint.length()==0)
                    {
                        filterResults.count = itemList.size();
                        filterResults.values = itemList;
                    }else
                    {
                        String searchStr = constraint.toString().toLowerCase();
                        List<item> resultData = new ArrayList<>();
                        for(item i : itemList)
                        {
                            if(i.getProblemName().toLowerCase().contains(searchStr) || i.getDescription().toLowerCase().contains(searchStr))
                            {
                                resultData.add(i);

                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;

                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    filteredItemList = (List<item>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

}