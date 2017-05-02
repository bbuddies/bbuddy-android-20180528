package com.odde.bbuddy.dashboard.view;

import android.app.Activity;
import android.content.Intent;

import com.odde.bbuddy.MainActivity;
import com.odde.bbuddy.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class DashboardNavigation {

    private final Activity activity;

    @Inject
    public DashboardNavigation(Activity activity) {
        this.activity = activity;
    }

    public void navigate() {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
