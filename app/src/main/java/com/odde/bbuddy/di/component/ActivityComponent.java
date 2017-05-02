package com.odde.bbuddy.di.component;

import com.odde.bbuddy.AuthenticateActivity;
import com.odde.bbuddy.account.view.AccountsActivity;
import com.odde.bbuddy.account.view.AddAccountActivity;
import com.odde.bbuddy.account.view.EditDeleteAccountActivity;
import com.odde.bbuddy.di.module.ActivityModule;
import com.odde.bbuddy.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(AddAccountActivity addAccountActivity);
    void inject(AccountsActivity accountsActivity);
    void inject(EditDeleteAccountActivity editDeleteAccountActivity);
    void inject(AuthenticateActivity authenticateActivity);
}
