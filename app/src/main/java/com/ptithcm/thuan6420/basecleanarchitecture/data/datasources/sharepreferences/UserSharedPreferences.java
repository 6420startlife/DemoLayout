package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {
    private static final String USER_SHARED_PREFERENCES = "USER_SHARED_PREFERENCES";
    private Context mContext;

    public UserSharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putUserValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getUserValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(USER_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
