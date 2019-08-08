package com.example.daggerex.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.daggerex.MyApp;
import com.example.daggerex.di.annotation.ApplicationContext;
import com.example.daggerex.di.annotation.DatabaseInfo;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private MyApp app;

    public ApplicationModule(MyApp app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        return "dagger-database";
    }

    @Provides
    @DatabaseInfo
    public int provideDatabaseVersion() {
        return 1;
    }

    @Provides
    public SharedPreferences provideSharePreference() {
        return app.getSharedPreferences("dagger-preferences", Context.MODE_PRIVATE);
    }
}
