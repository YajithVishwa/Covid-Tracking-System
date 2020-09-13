package com.yajith.gps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    HashMap map=new HashMap<String,String>();
    HashMap red=new HashMap<Double,Double>();
    Button button;
    LatLng sydney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        button=findViewById(R.id.button);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Map<String,Object> map1=new HashMap<>();
                map1.put("home",MyService.frnd.getLongitude()+","+MyService.frnd.getLatitude());
                FirebaseDatabase.getInstance().getReference().child("UserStore").child("1").updateChildren(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i=new Intent(MapsActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        });

    }

    void runlist() {
        MyLocation myLocation = new MyLocation(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, myLocation);
        mythread mythread = new mythread();
        mythread.start();
    }

    class mythread extends Thread {
        public void run() {
            while (true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (MyLocation.location1 != null) {
                            mMap.clear();
                            sydney = new LatLng(MyLocation.location1.getLatitude(), MyLocation.location1.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(sydney).title("You are here"));
                            map.put("Lat", String.valueOf(MyLocation.location1.getLatitude()));
                            map.put("Lon", String.valueOf(MyLocation.location1.getLongitude()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
                        }
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager1 = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
          //  ActivityCompat
          //          .requestPermissions(
            //                MapsActivity.this,new String[] { Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION },100);
            return;
        }
        List <String> provider=locationManager1.getProviders(true);
        Location location =null;
        for(String lo:provider)
        {
            Location l=locationManager1.getLastKnownLocation(lo);
            if(l==null){continue;}
            if(location==null||l.getAccuracy()<location.getAccuracy())
            {
                location=l;
            }
        }
            LatLng s=new LatLng(MyService.lastLocation.getLatitude(),MyService.lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(s).title("you are here"));
            //sydney = new LatLng(location.getLatitude(), location.getLongitude());
            //mMap.addMarker(new MarkerOptions().position(sydney).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(s, 17));
        runlist();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == 100) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            else {
                Toast.makeText(MapsActivity.this,
                        "Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
                this.finish();
            }
        }
    }
}
