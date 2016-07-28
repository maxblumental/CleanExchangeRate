package com.blumental.maxim.cleanexchangerate;

import android.app.Application;

import com.blumental.maxim.cleanexchangerate.di.ApplicationComponent;
import com.blumental.maxim.cleanexchangerate.di.DaggerApplicationComponent;

public class App extends Application {

    public static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    private ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder().build();
    }
}
