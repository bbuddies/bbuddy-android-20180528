package com.odde.bbuddy.di.module;

import android.app.Activity;
import android.content.Context;

import com.odde.bbuddy.account.viewmodel.PresentableAccounts;
import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.authentication.viewmodel.AutologinAuthentication;
import com.odde.bbuddy.authentication.viewmodel.EditableAuthentication;
import com.odde.bbuddy.common.StringResources;
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
    Context provideContext() {
        return activity;
    }

    @Provides @ActivityScope
    Activity provideActivity() {
        return activity;
    }

    @Provides @ActivityScope
    ViewBinder provideViewBinder() {
        return new BinderFactoryBuilder().build().createViewBinder(activity);
    }

    @Provides @ActivityScope @Named("accounts")
    PresentationModelChangeSupport providePresentationModelChangeSupportForAccounts(PresentableAccounts presentableAccounts) {
        return new PresentationModelChangeSupport(presentableAccounts);
    }

    @Provides @ActivityScope @Named("editableAuthentication")
    PresentationModelChangeSupport providePresentationModelChangeSupportForEditableAuthentication(EditableAuthentication editableAuthentication) {
        return new PresentationModelChangeSupport(editableAuthentication);
    }

    @Provides @ActivityScope
    EditableAuthentication provideEditableAuthentication(Authenticator authenticator, DashboardNavigation dashboardNavigation, @Named("editableAuthentication") Lazy<PresentationModelChangeSupport> changeSupportLoader, StringResources stringResources) {
        if (AUTO_LOGIN)
            return new AutologinAuthentication(authenticator, dashboardNavigation, changeSupportLoader, stringResources);
        else
            return new EditableAuthentication(authenticator, dashboardNavigation, changeSupportLoader, stringResources);
    }

}
