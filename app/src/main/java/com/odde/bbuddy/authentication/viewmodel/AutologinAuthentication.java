package com.odde.bbuddy.authentication.viewmodel;

import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.dashboard.view.DashboardNavigation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

@PresentationModel
@ActivityScope
public class AutologinAuthentication extends EditableAuthentication {

    @Inject
    public AutologinAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader) {
        super(authenticator, dashboardNavigation, changeSupportLoader);
        setEmail("admin@bbuddy.com");
        setPassword("123456");
        login();
    }
}
