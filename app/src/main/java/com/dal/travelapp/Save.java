package com.dal.travelapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Save {

    public static void save(Context ctx, String value)
    {
        SharedPreferences s = ctx.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        edt.putString("email", value);
        edt.putBoolean("status", true);
        edt.apply();
    }

    public static Boolean readStatus(Context ctx, Boolean default_value)
    {
        SharedPreferences s = ctx.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        return s.getBoolean("status", default_value);
    }

    public static String readEmail(Context ctx)
    {
        SharedPreferences s = ctx.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        return s.getString("email",null);
    }

    public static void delete(Context ctx)
    {
        SharedPreferences s = ctx.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        s.edit().clear().commit();
    }
}
