package com.example.app_template_java.injects.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class MainModule {

}
