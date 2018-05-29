package com.odde.bbuddy.di.module.activity;

import android.app.Activity;
import android.content.Context;

import com.odde.bbuddy.account.view.AccountView;
import com.odde.bbuddy.authentication.view.AuthenticationView;
import com.odde.bbuddy.budget.view.BudgetView;
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
    public AuthenticationView provideAuthenticationView() {
        return (AuthenticationView) activity;
    }

    @Provides @ActivityScope
    public AccountView provideAccountView() {
        return (AccountView) activity;
    }

    @Provides @ActivityScope
    public BudgetView provideBudgetView() {
        return (BudgetView) activity;
    }
}
