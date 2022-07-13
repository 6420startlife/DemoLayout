package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserLocalDataSource.init(getApplicationContext());
    }
}
