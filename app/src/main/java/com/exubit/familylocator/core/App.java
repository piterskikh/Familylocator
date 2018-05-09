package com.exubit.familylocator.core;

import android.app.Application;

import com.exubit.familylocator.core.dagger.AppComponent;
import com.exubit.familylocator.core.dagger.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .setContext(getApplicationContext())
                .build();

        new BeanStart();
    }


}
