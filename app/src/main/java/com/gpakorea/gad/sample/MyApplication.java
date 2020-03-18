package com.gpakorea.gad.sample;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

public class MyApplication extends Application {
    
    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
