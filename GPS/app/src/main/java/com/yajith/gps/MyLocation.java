package com.yajith.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocation implements LocationListener {
    public static Location location1;
    Context context;
    MyLocation(Context context)
    {
        this.context=context;
    }
    @Override
    public void onLocationChanged(Location location) {
        String loc=String.valueOf(location.getLatitude())+"  "+String.valueOf(location.getLatitude());
        Log.i("loc",loc);
        location1=location;

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(context, "Gps status changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(context, "Gps is enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(context, "Gps is disabled", Toast.LENGTH_SHORT).show();
    }
}
