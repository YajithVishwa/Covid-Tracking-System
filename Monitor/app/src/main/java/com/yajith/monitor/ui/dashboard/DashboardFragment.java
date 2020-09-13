package com.yajith.monitor.ui.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yajith.monitor.R;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {
    ProgressDialog progressDialog;
    public static ListView listView;
    Activity activity;
    Map<String,String> map=new HashMap();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        activity=getActivity();
        listView=root.findViewById(R.id.list);
        Async1 async1=new Async1();
        async1.execute();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listView.getVisibility()==View.INVISIBLE)
        {
            listView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listView.setVisibility(View.INVISIBLE);
        progressDialog.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
        listView.setVisibility(View.INVISIBLE);
        progressDialog.dismiss();
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
            FirebaseDatabase.getInstance().getReference().child("ListName").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    count=String.valueOf(dataSnapshot.getChildrenCount());
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        String key=dataSnapshot1.getKey();
                        String value=String.valueOf(dataSnapshot1.getValue());
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
