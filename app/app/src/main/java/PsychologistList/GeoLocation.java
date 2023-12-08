package PsychologistList;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.mhaprototype.GlobalVariables;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//Class to fetch the coordinates of a given location
public class GeoLocation {
    public static void getAddress(String locationAddress, Context context, Handler handler)
    {
        //Thread thread = new Thread(){
           // private static final String TAG = "Testing coordinates in geolocation";

           // @Override
            //public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List addressList = geocoder.getFromLocationName(locationAddress,1);
                    if(addressList !=null && addressList.size()>0)
                    {
                        Address address = (Address) addressList.get(0);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(address.getLatitude()).append(" ");
                        stringBuilder.append(address.getLongitude()).append("");
                        result = stringBuilder.toString();
                        GlobalVariables.Coordinates = result;
                       // Log.d(TAG, "testing coordinates in geolocation: " + GlobalVariables.Coordinates);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally
                {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if(result !=  null)
                    {
                        message.what =1 ;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
          //  }
        //};
       // thread.start();

    }

}
