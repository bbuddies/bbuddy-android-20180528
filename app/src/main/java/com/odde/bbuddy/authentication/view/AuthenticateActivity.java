package com.odde.bbuddy.authentication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import com.odde.bbuddy.R;
import com.odde.bbuddy.authentication.viewmodel.EditableAuthentication;
import com.odde.bbuddy.common.validation.Violation;

import org.robobinding.ViewBinder;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.odde.bbuddy.authentication.viewmodel.EditableAuthentication.*;
import static com.odde.bbuddy.di.component.ActivityComponentFactory.createActivityComponentBy;

public class AuthenticateActivity extends AppCompatActivity implements AuthenticationView {

    @Inject
    EditableAuthentication editableAuthentication;

    @Inject
    ViewBinder viewBinder;

    private Map<String, TextInputLayout> bindedTextInputLayouts = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createActivityComponentBy(this).inject(this);

        setContentView(viewBinder.inflateAndBind(R.layout.activity_login, editableAuthentication));

        bindedTextInputLayouts.put(EMAIL_FIELD_NAME, (TextInputLayout) findViewById(R.id.UsernameError));
        bindedTextInputLayouts.put(PASSWORD_FIELD_NAME, (TextInputLayout) findViewById(R.id.PasswordError));
    }

    @Override
    public void showError(Violation violation) {
        bindedTextInputLayouts.get(violation.getFieldName()).setError(violation.getMessage());
    }
}