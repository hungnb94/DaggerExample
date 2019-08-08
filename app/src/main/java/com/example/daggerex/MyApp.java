package com.example.daggerex;

import android.app.Application;
import com.example.daggerex.data.DataManager;
import com.example.daggerex.di.component.ApplicationComponent;
import com.example.daggerex.di.component.DaggerApplicationComponent;
import com.example.daggerex.di.module.ApplicationModule;

import javax.inject.Inject;

public class MyApp extends Application {

    @Inject
    DataManager dataManager;

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
