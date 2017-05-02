package com.odde.bbuddy.authentication.viewmodel;

import com.odde.bbuddy.authentication.Authenticator;
import com.odde.bbuddy.authentication.Credentials;
import com.odde.bbuddy.dashboard.view.DashboardNavigation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

@PresentationModel
@ActivityScope
public class EditableAuthentication implements HasPresentationModelChangeSupport {

    private final Authenticator authenticator;
    private final DashboardNavigation dashboardNavigation;
    private final Lazy<PresentationModelChangeSupport> changeSupportLoader;
    private String message;

    @Inject
    public EditableAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader) {
        this.authenticator = authenticator;
        this.dashboardNavigation = dashboardNavigation;
        this.changeSupportLoader = changeSupportLoader;
    }

    private String email;
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
        authenticator.authenticate(new Credentials(email, password), new Runnable() {
            @Override
            public void run() {
                dashboardNavigation.navigate();
            }
        }, new Runnable() {
            @Override
            public void run() {
                message = "Login failed";
                getPresentationModelChangeSupport().refreshPresentationModel();
            }
        });
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
