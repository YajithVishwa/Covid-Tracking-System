package com.yajith.monitor.ui;

import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class SimpleFCM extends FirebaseMessagingService {
    int id;
    Map<String,Object> map=new HashMap<>();
    String name,address;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
         super.onMessageReceived(remoteMessage);
        /*name=remoteMessage.getNotification().getTitle();
        address=remoteMessage.getNotification().getBody();
        Log.i("name",address);
        id=findname(name);
        if(id==0)
        {
           new Timer().schedule(new TimerTask() {
               @Override
               public void run() {
                   Log.i("id",String.valueOf(id));
                    insertin();
               }
           },2000);
        }
        else
        {
            insertin();
        }
    }

    private void insertin() {
        map.put("name",name);
        Log.i("name",name);
        map.put("address",address);
        FirebaseDatabase.getInstance().getReference().child("Msg").child(String.valueOf(id)).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("inserted","firebase");
            }
        });
    }

    private int findname(final String name) {
        FirebaseDatabase.getInstance().getReference().child("ListName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    if(String.valueOf(d.getValue()).equals(name))
                    {
                        Log.i("in",String.valueOf(d.getValue()));
                        id=Integer.parseInt(String.valueOf(d.getKey()));
                        Log.i("idq",String.valueOf(id));
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(id!=0) {
            return id;
        }
        else
        {
            return 0;
        }*/
    }
}
