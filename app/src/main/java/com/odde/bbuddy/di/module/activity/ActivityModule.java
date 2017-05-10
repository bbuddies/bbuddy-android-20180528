package com.odde.bbuddy.di.module.activity;

import android.app.Activity;
import android.content.Context;

import com.odde.bbuddy.authentication.view.AddAccountView;
import com.odde.bbuddy.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    public Context provideContext() {
        return activity;
    }

    @Provides @ActivityScope
    public Activity provideActivity() {
        return activity;
    }

    @Provides @ActivityScope
    public AddAccountView provideAddAccountView() {
        return (AddAccountView) activity;
    }

}
