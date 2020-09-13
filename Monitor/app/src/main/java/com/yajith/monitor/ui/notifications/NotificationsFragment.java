package com.yajith.monitor.ui.notifications;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.yajith.monitor.R;
import com.yajith.monitor.ui.notifications.CustomAdapter;
import com.yajith.monitor.ui.dashboard.DashboardFragment;
import com.yajith.monitor.ui.home.MainFraagment;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    public static ListView listView;
    ProgressDialog progressDialog;
    Activity activity;
    Map<String,String> map=new HashMap();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        activity=getActivity();
        listView=root.findViewById(R.id.list);
        TextView textView = MainFraagment.textView;
        ListView listView = DashboardFragment.listView;
        if (textView != null) {
            textView.setVisibility(View.INVISIBLE);
        }
        if (listView != null) {
            listView.setVisibility(View.INVISIBLE);
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Notify", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.i("Token",token);

                        // Log and toast
                       /* String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                    }
                });
        Async1 async1=new Async1();
        async1.execute();
        return root;
    }
    class Async1 extends AsyncTask<Void,String,String>
    {
        String count;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(count!=null)
            {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String[] id=new String[Integer.parseInt(values[0])];
            String[] name=new String[Integer.parseInt(values[0])];
            int index=0;
            for(Map.Entry<String,String> m:map.entrySet())
            {
                id[index]=m.getKey();
                name[index]=m.getValue();
                Log.i(id[index],name[index]);
                index++;
            }
            CustomAdapter customAdapter=new CustomAdapter(activity,id,name);
            listView.setAdapter(customAdapter);
            onPostExecute(count);

        }

        @Override
        protected String doInBackground(Void... voids) {
            FirebaseDatabase.getInstance().getReference().child("Msg").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    count=String.valueOf(dataSnapshot.getChildrenCount());
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        String child=dataSnapshot1.getKey();
                        Log.i("child",child);
                        String key=String.valueOf(dataSnapshot.child(child).child("name").getValue());
                        String value=String.valueOf(dataSnapshot.child(child).child("address").getValue());
                        // Log.i(key,value);
                        map.put(key,value);
                    }
                    publishProgress(count);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return count;
        }
    }
}
