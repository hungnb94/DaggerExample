package com.example.daggerex.di.component;

import com.example.daggerex.MyApp;
import com.example.daggerex.data.DataManager;
import com.example.daggerex.di.module.ApplicationModule;
import dagger.Component;

import javax.inject.Singleton;


@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(MyApp app);

    DataManager getDataManager();
}
