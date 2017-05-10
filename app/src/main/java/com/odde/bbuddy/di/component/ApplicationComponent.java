package com.odde.bbuddy.di.component;

import com.odde.bbuddy.di.module.activity.ActivityModule;
import com.odde.bbuddy.di.module.application.ApiModule;
import com.odde.bbuddy.di.module.application.ApplicationModule;
import com.odde.bbuddy.di.module.application.AuthenticationModule;
import com.odde.bbuddy.di.module.activity.RobobindingModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AuthenticationModule.class, ApiModule.class})
public interface ApplicationComponent {

    ActivityComponent plus(ActivityModule activityModule, RobobindingModule robobindingModule);

}
