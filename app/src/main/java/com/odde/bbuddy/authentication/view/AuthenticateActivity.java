package com.odde.bbuddy.authentication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.odde.bbuddy.R;
import com.odde.bbuddy.authentication.viewmodel.EditableAuthentication;

import org.robobinding.ViewBinder;

import javax.inject.Inject;

import static com.odde.bbuddy.di.component.ActivityComponentFactory.createActivityComponentBy;

public class AuthenticateActivity extends AppCompatActivity {

    @Inject
    EditableAuthentication editableAuthentication;

    @Inject
    ViewBinder viewBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createActivityComponentBy(this).inject(this);

        setContentView(viewBinder.inflateAndBind(R.layout.activity_login, editableAuthentication));
    }
}
