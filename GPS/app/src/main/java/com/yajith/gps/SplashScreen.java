package com.yajith.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashScreen extends AppCompatActivity {
    Context context;
    static int count=0;
    String TAG = "SplashScreen";
    private JobScheduler jobScheduler;
    private ComponentName componentName;
    private JobInfo jobInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=this;
        checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION, 100);
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 101);
        LocationManager lo=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!lo.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Alert").setMessage("GPS Not turned on").setPositiveButton("Turn ON", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent,0);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //StartBackgroundTask();
                    Intent i=new Intent(SplashScreen.this,dummy.class);
                    startActivity(i);
                }
            }, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(count==1) {
            LocationManager lo1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!lo1.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(context, "Turn on Location to continue", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        count=1;
    }
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(
                SplashScreen.this,permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            SplashScreen.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            StartBackgroundTask();

        }
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
                Toast.makeText(SplashScreen.this,
                        "Location Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
                this.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count=0;
    }
    @SuppressLint("NewApi")
    public void StartBackgroundTask() {
        //MyService myService=new MyService(this);
        jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        componentName = new ComponentName(getApplicationContext(), MyService.class);
        jobInfo = new JobInfo.Builder(1, componentName)
                .setMinimumLatency(10000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setRequiresCharging(false).build();
        jobScheduler.schedule(jobInfo);
    }
}
