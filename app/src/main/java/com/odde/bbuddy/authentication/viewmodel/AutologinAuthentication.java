package com.odde.bbuddy.authentication.viewmodel;

import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.authentication.view.AddAccountView;
import com.odde.bbuddy.common.StringResources;
import com.odde.bbuddy.common.Validator;
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
    public AutologinAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader, StringResources stringResources, Validator validator, AddAccountView addAccountView) {
        super(authenticator, dashboardNavigation, changeSupportLoader, stringResources, validator, addAccountView);
        setEmail("admin@bbuddy.com");
        setPassword("123456");
        login();
    }
}
