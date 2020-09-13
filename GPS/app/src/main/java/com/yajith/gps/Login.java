package com.yajith.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView textView,textView1;
    public static Button register;
    String Email,password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Retriving Data from DataBase");
        progressDialog.setCancelable(false);
        textView = findViewById(R.id.name);
        textView1 = findViewById(R.id.password);
        register = findViewById(R.id.register);
        SharedPrefer sharedPrefer = new SharedPrefer();
        boolean flag = sharedPrefer.getints(getApplicationContext());
        Log.i("flag",String.valueOf(flag));
        if (flag) {
            Intent intent=new Intent(Login.this,MainActivity.class);
            startActivity(intent);
        } else {

            register.setEnabled(false);
           Toast.makeText(getApplicationContext(),"Wait Retriving Location",Toast.LENGTH_LONG).show();
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Email = textView.getText().toString();
                    password = textView.getText().toString();
                    if (Email.equals("")) {
                        Toast.makeText(Login.this, "User name is empty", Toast.LENGTH_SHORT).show();
                    } else if (password.equals("")) {
                        Toast.makeText(Login.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseDatabase.getInstance().getReference().child("ListName").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                {
                                    Log.i("LOGIN",dataSnapshot1.toString());
                                    if(dataSnapshot1.getValue().equals(Email))
                                    {
                                        UsedId.setId(Integer.parseInt(dataSnapshot1.getKey()));
                                        return;
                                    }
                                }
                                int count=(int)dataSnapshot.getChildrenCount();
                                count++;
                                Map<String,Object> map=new HashMap<>();
                                map.put(String.valueOf(count),Email);
                                FirebaseDatabase.getInstance().getReference().child("ListName").updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.show();
                                        SharedPrefer sharedPrefer = new SharedPrefer();
                                        sharedPrefer.shared(true, getApplicationContext());
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(Login.this,MapsActivity.class));
                                            }
                                        },2000);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }
}
