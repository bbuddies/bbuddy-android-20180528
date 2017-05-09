package com.odde.bbuddy.authentication.viewmodel;

import com.odde.bbuddy.R;
import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.authentication.model.Credentials;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.StringResources;
import com.odde.bbuddy.common.Validator;
import com.odde.bbuddy.dashboard.view.DashboardNavigation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.hibernate.validator.constraints.NotBlank;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.util.Joiner;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

@PresentationModel
@ActivityScope
public class EditableAuthentication implements HasPresentationModelChangeSupport {

    private final Authenticator authenticator;
    private final DashboardNavigation dashboardNavigation;
    private final Lazy<PresentationModelChangeSupport> changeSupportLoader;
    private final StringResources stringResources;
    private final Validator validator;
    private String message;

    @Inject
    public EditableAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader, StringResources stringResources, Validator validator) {
        this.authenticator = authenticator;
        this.dashboardNavigation = dashboardNavigation;
        this.changeSupportLoader = changeSupportLoader;
        this.stringResources = stringResources;
        this.validator = validator;
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
        final ArrayList<String> violationMessages = new ArrayList<>();
        validator.processEachViolation(this, new Consumer<String>() {
            @Override
            public void accept(String violationMessage) {
                violationMessages.add(violationMessage);
            }
        });
        if (!violationMessages.isEmpty()) {
            setMessage(allViolationMessages(violationMessages));
            refresh();
        }
        return violationMessages.isEmpty();
    }

    private String allViolationMessages(ArrayList<String> violationMessages) {
        return Joiner.on(System.getProperty("line.separator")).join(violationMessages);
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
