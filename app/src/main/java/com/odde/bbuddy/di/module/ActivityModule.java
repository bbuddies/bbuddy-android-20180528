package com.odde.bbuddy.di.module;

import android.app.Activity;
import android.content.Context;

import com.odde.bbuddy.account.viewmodel.PresentableAccounts;
import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.authentication.view.AddAccountView;
import com.odde.bbuddy.authentication.viewmodel.AutologinAuthentication;
import com.odde.bbuddy.authentication.viewmodel.EditableAuthentication;
import com.odde.bbuddy.common.StringResources;
import com.odde.bbuddy.common.validation.Validator;
import com.odde.bbuddy.dashboard.view.DashboardNavigation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

import static com.odde.bbuddy.BuildConfig.AUTO_LOGIN;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    public Context provideContext() {
        return activity;
    }

    @Provides @ActivityScope
    public Activity provideActivity() {
        return activity;
    }

    @Provides @ActivityScope
    public AddAccountView provideAddAccountView() {
        return (AddAccountView) activity;
    }

    @Provides @ActivityScope
    public ViewBinder provideViewBinder() {
        return new BinderFactoryBuilder().build().createViewBinder(activity);
    }

    @Provides @ActivityScope @Named("accounts")
    public PresentationModelChangeSupport providePresentationModelChangeSupportForAccounts(PresentableAccounts presentableAccounts) {
        return new PresentationModelChangeSupport(presentableAccounts);
    }

    @Provides @ActivityScope @Named("editableAuthentication")
    public PresentationModelChangeSupport providePresentationModelChangeSupportForEditableAuthentication(EditableAuthentication editableAuthentication) {
        return new PresentationModelChangeSupport(editableAuthentication);
    }

    @Provides @ActivityScope
    public EditableAuthentication provideEditableAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader, StringResources stringResources, Validator validator, AddAccountView addAccountView) {
        if (AUTO_LOGIN)
            return new AutologinAuthentication(authenticator, dashboardNavigation, changeSupportLoader, stringResources, validator, addAccountView);
        else
            return new EditableAuthentication(authenticator, dashboardNavigation, changeSupportLoader, stringResources, validator, addAccountView);
    }

}
