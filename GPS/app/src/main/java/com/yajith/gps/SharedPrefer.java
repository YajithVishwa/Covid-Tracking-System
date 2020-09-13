package com.yajith.gps;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefer {
    public void shared(boolean value, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("GPS",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("Login",value);
        editor.commit();

    }
    public boolean getints(Context context)
    {
        SharedPreferences sharedPrefer=context.getSharedPreferences("GPS",Context.MODE_PRIVATE);
        boolean f=sharedPrefer.getBoolean("Login",false);
        return f;
    }
}
