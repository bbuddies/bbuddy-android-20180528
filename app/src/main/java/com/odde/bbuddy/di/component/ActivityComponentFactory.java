package com.odde.bbuddy.di.component;

import android.app.Activity;

import com.odde.bbuddy.BbuddyApplication;
import com.odde.bbuddy.di.module.activity.ActivityModule;
import com.odde.bbuddy.di.module.activity.RobobindingModule;

public class ActivityComponentFactory {
    public static ActivityComponent createActivityComponentBy(Activity activity) {
        return ((BbuddyApplication)activity.getApplication()).getApplicationComponent().plus(new ActivityModule(activity), new RobobindingModule(activity));
    }
}
