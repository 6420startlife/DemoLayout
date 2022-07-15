package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources;

import android.content.Context;

import com.google.gson.Gson;
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User;

public class UserLocalDataSource {
    private static final String PREF_REGISTERED_USER = "PREF_REGISTERED_USER";
    private static UserLocalDataSource Instance;
    private UserSharedPreferences userSharedPreferences;

    public static void init(Context context) {
        Instance = new UserLocalDataSource();
        Instance.userSharedPreferences = new UserSharedPreferences(context);
    }

    public static UserLocalDataSource getInstance() {
        if (Instance != null) {
            return Instance;
        }
        return new UserLocalDataSource();
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        UserLocalDataSource.getInstance()
                .userSharedPreferences.putUserValue(PREF_REGISTERED_USER, jsonUser);
    }

    public static User getUser() {
        String jsonUser = UserLocalDataSource.getInstance()
                .userSharedPreferences.getUserValue(PREF_REGISTERED_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        return user;
    }
}
