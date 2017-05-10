package com.odde.bbuddy.authentication.viewmodel;

import com.odde.bbuddy.R;
import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.authentication.model.Credentials;
import com.odde.bbuddy.authentication.view.AddAccountView;
import com.odde.bbuddy.common.functional.Consumer;
import com.odde.bbuddy.common.StringResources;
import com.odde.bbuddy.common.validation.Validator;
import com.odde.bbuddy.common.functional.ValueCaptor;
import com.odde.bbuddy.common.validation.Violation;
import com.odde.bbuddy.dashboard.view.DashboardNavigation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.hibernate.validator.constraints.NotBlank;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

@PresentationModel
@ActivityScope
public class EditableAuthentication implements HasPresentationModelChangeSupport {

    public static final String EMAIL_FIELD_NAME = "email";
    public static final String PASSWORD_FIELD_NAME = "password";
    private final Authenticator authenticator;
    private final DashboardNavigation dashboardNavigation;
    private final Lazy<PresentationModelChangeSupport> changeSupportLoader;
    private final StringResources stringResources;
    private final Validator validator;
    private final AddAccountView addAccountView;
    private String message;

    @Inject
    public EditableAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader, StringResources stringResources, Validator validator, AddAccountView addAccountView) {
        this.authenticator = authenticator;
        this.dashboardNavigation = dashboardNavigation;
        this.changeSupportLoader = changeSupportLoader;
        this.stringResources = stringResources;
        this.validator = validator;
        this.addAccountView = addAccountView;
    }

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String userName) {
        this.email = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        if (isValid())
            authenticate();
    }

    private void authenticate() {
        authenticator.authenticate(new Credentials(email, password), new Runnable() {
            @Override
            public void run() {
                dashboardNavigation.navigate();
            }
        }, new Runnable() {
            @Override
            public void run() {
                setMessage(stringResources.get(R.string.login_failed));
                refresh();
            }
        });
    }

    private boolean isValid() {
        final ValueCaptor<Boolean> isValid = new ValueCaptor<>(true);
        validator.processEachViolation(this, new Consumer<Violation>() {
            @Override
            public void accept(Violation violation) {
                addAccountView.showError(violation);
                isValid.capture(false);
            }
        });
        return isValid.value();
    }

    private void refresh() {
        getPresentationModelChangeSupport().refreshPresentationModel();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupportLoader.get();
    }
}
