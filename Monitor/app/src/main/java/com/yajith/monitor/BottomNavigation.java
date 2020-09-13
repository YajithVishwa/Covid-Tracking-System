package com.yajith.monitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yajith.monitor.ui.SimpleFCM;
import com.yajith.monitor.ui.dashboard.DashboardFragment;
import com.yajith.monitor.ui.home.MainFraagment;
import com.yajith.monitor.ui.notifications.NotificationsFragment;

public class BottomNavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        startService(new Intent(this, SimpleFCM.class));
        frameLayout=findViewById(R.id.frame);
        bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        MainFraagment homeFragment=new MainFraagment();
                        FragmentManager fragmentManager=getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.frame,homeFragment).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        DashboardFragment dashboardFragment=new DashboardFragment();
                        FragmentManager fragmentManager1=getSupportFragmentManager();
                        fragmentManager1.beginTransaction().add(R.id.frame,dashboardFragment).commit();
                        return true;
                    case R.id.navigation_notifications:
                        NotificationsFragment notificationsFragment=new NotificationsFragment();
                        FragmentManager fragmentManager2=getSupportFragmentManager();
                        fragmentManager2.beginTransaction().add(R.id.frame,notificationsFragment).commit();
                        return true;
                }

                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        MainFraagment homeFragment=new MainFraagment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame,homeFragment).commit();
    }
}
