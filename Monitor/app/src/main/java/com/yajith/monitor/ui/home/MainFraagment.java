package com.yajith.monitor.ui.home;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yajith.monitor.R;
import com.yajith.monitor.ui.dashboard.DashboardFragment;
import com.yajith.monitor.ui.notifications.NotificationsFragment;

public class MainFraagment extends Fragment {
    public String value;
    ProgressDialog progressDialog;
    public static TextView textView,textView1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progressDialog=new ProgressDialog(getContext());
        textView=root.findViewById(R.id.text);
        textView1=root.findViewById(R.id.text1);
        ListView listView= DashboardFragment.listView;
        if(listView!=null)
        {
            listView.setVisibility(View.INVISIBLE);
        }
        if(NotificationsFragment.listView!=null)
        {
            listView.setVisibility(View.INVISIBLE);
        }
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Asyncs asyncs=new Asyncs();
        asyncs.execute();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        progressDialog.dismiss();
    }

    Integer i;
    class Asyncs extends AsyncTask<Void,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView.setText(values[0]);
            onPostExecute(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                progressDialog.dismiss();
            }
        }

        String values,values1;
        @Override
        protected String doInBackground(Void... voids) {

            FirebaseDatabase.getInstance().getReference().child("TotalMonitor").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    values=String.valueOf(dataSnapshot.child("value").getValue());
                    FirebaseDatabase.getInstance().getReference().child("NotConnected").child("value").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            values1=String.valueOf(dataSnapshot.getValue());
                            textView1.setText(values1);
                            publishProgress(values);
                            return;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            return values;
        }
    }
}
