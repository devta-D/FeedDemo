package com.devta.unlu.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.devta.unlu.di.component.AppComponent;
import com.devta.unlu.di.component.DaggerAppComponent;

/**
 * Created on : Jun, 23, 2020 at 17:55
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class UnluApplication extends MultiDexApplication {

    private AppComponent component;
    private static UnluApplication application;

    public static UnluApplication getInstance(){
        if(application != null) application = new UnluApplication();
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = new UnluApplication();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public AppComponent getComponent() {
        if(component == null)
            component = DaggerAppComponent.builder().build();
        return component;
    }
}
