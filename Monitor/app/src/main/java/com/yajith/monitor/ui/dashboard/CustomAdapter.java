package com.yajith.monitor.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yajith.monitor.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    String[] count;
    String[] name;
    Activity mContext;
    TextView textView1,textView2;
    public CustomAdapter(@NonNull Activity context,String[] count,String[] name) {
        super(context, R.layout.list_view_model,name);
        this.name=name;
        this.count=count;
        this.mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view_model, null,true);
       textView1=rowView.findViewById(R.id.count);
       textView2=rowView.findViewById(R.id.textname);
       textView1.setText(count[position]);
       textView2.setText(name[position]);
       return rowView;
    }
}
