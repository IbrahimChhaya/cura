package MapLocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mhaprototype.APIInterface;
import com.example.mhaprototype.GlobalVariables;
import com.example.mhaprototype.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.StringTokenizer;

import Appointment.ErrorPages.NoAppointmentPage;
import ChildList.ChildListMain;
import ChildList.ChildListRedesigned;
import ChildList.ErrorPages.NoChildLinkedMain;
import PsychologistList.ErrorPages.NoPsychologistLinkedMain;
import PsychologistList.psychologistListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class psychMapFragment extends Fragment {
    private String coordinates ;
    private APIInterface apiInterface;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            double latitude = 0;
            double longitude = 0;
            if(GlobalVariables.Coordinates != null)
            {
                StringTokenizer token = new StringTokenizer(GlobalVariables.Coordinates," ");
                latitude = Double.parseDouble(token.nextToken());
                longitude = Double.parseDouble(token.nextToken());
            }


            //GlobalVariables.Coordinates = " ";
            LatLng sydney = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Johannesburg"));
           // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            float zoomLevel = 16.0f; //This goes up to 21
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       // return inflater.inflate(R.layout.fragment_psych_map, container, false);
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_psych_map, container, false);
        TextView txtDescription = (TextView) rootView.findViewById(R.id.txtpsychDescription);
        Button btnBooking = (Button) rootView.findViewById(R.id.btnBookMeeting);
        btnBooking.setText("Make an Appointment");
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the any children are linked
                Retrofit retrofit = new Retrofit.Builder().baseUrl(GlobalVariables.url).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                apiInterface.isLinkedToParent(GlobalVariables.loggedInUser.getUserID()).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code()==200)
                        {
                            int ID = response.body();
                            if(ID>0)
                            {
                                //a link is present
                                Intent i = new Intent(getActivity(), ChildListRedesigned.class);
                                i.putExtra("ChildListMessage","PsychologistList");
                                startActivity(i);
                            }else if (ID==0)
                            {
                                //means that no psychologist exists
                                Intent i = new Intent(getActivity(), ChildListRedesigned.class);
                                i.putExtra("ChildListMessage","PsychologistList");
                                startActivity(i);
                            }else if(ID==-1)
                            {
                                Intent i = new Intent(getActivity(), NoChildLinkedMain.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
               /* Intent i = new Intent(getActivity(), ChildListMain.class);
                startActivity(i);*/
            }
        });
        txtDescription.setText(GlobalVariables.psycologistDescription);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);



        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}