package com.yajith.monitor.ui.notifications;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yajith.monitor.R;

public class CustomAdapter extends ArrayAdapter<String> {
    String[] address;
    String[] name;
    Activity mContext;
    TextView textView1,textView2;
    public CustomAdapter(@NonNull Activity context, String[] address, String[] name) {
        super(context, R.layout.list_view_model,name);
        this.name=name;
        this.address=address;
        this.mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.notify_view_model, null,true);
       textView1=rowView.findViewById(R.id.count);
       textView2=rowView.findViewById(R.id.textname);
       textView1.setText(address[position]);
       textView2.setText(name[position]);
       return rowView;
    }
}
