package com.gpakorea.gad.sample;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import com.gad.cashtalktalk.CashTalkTalk;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CashTalkTalk.initialize(this, "{미디어키}");
    }

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
