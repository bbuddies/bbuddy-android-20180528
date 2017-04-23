package com.odde.bbuddy.account.view;

import android.app.Activity;

import com.odde.bbuddy.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class AccountsNavigation {

    private final Activity activity;

    @Inject
    public AccountsNavigation(Activity activity) {
        this.activity = activity;
    }

    public void navigate() {
        activity.finish();
    }
}
