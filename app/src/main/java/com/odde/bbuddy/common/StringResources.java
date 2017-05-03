package com.odde.bbuddy.common;

import android.content.res.Resources;

public class StringResources {
    private final Resources resources;

    public StringResources(Resources resources) {
        this.resources = resources;
    }

    public String get(int key) {
        return resources.getString(key);
    }
}
