package com.odde.bbuddy.di.component;

import com.odde.bbuddy.account.view.AddBudgetActivity;
import com.odde.bbuddy.authentication.view.AuthenticateActivity;
import com.odde.bbuddy.account.view.AccountsActivity;
import com.odde.bbuddy.account.view.AddAccountActivity;
import com.odde.bbuddy.account.view.EditDeleteAccountActivity;
import com.odde.bbuddy.di.module.activity.ActivityModule;
import com.odde.bbuddy.di.module.activity.RobobindingModule;
import com.odde.bbuddy.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class, RobobindingModule.class})
public interface ActivityComponent {
    void inject(AddAccountActivity addAccountActivity);
    void inject(AccountsActivity accountsActivity);
    void inject(EditDeleteAccountActivity editDeleteAccountActivity);
    void inject(AuthenticateActivity authenticateActivity);
    void inject(AddBudgetActivity addBudgetActivity);
}
