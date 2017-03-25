package com.odde.bbuddy.di.module;

import android.app.Application;

import com.odde.bbuddy.account.model.Accounts;
import com.odde.bbuddy.account.model.AccountsApi;
import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.authentication.AuthenticationToken;
import com.odde.bbuddy.authentication.Authenticator;
import com.odde.bbuddy.authentication.Credentials;
import com.odde.bbuddy.budget.model.Budgets;
import com.odde.bbuddy.budget.viewmodel.Budget;
import com.odde.bbuddy.common.ApiFactory;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public JsonBackend provideJsonBackend(AuthenticationToken authenticationToken) {
        return new JsonBackend(application, authenticationToken);
    }

    @Provides @Singleton
    public AuthenticationToken provideAuthenticationToken() {
        return new AuthenticationToken();
    }

    @Provides @Singleton
    public ApiFactory provideApiFactory(AuthenticationToken authenticationToken) {
        return new ApiFactory(authenticationToken);
    }

    @Provides @Singleton
    public Authenticator provideAuthenticator(JsonBackend jsonBackend) {
        return new Authenticator(jsonBackend, new JsonMapper<>(Credentials.class));
    }

    @Provides @Singleton
    public Accounts provideAccounts(JsonBackend jsonBackend, ApiFactory apiFactory) {
        return new Accounts(jsonBackend, new JsonMapper<>(Account.class), apiFactory.create(AccountsApi.class));
    }

    @Provides @Singleton
    public Budgets provideBudgets(JsonBackend jsonBackend) {
        return new Budgets(jsonBackend, new JsonMapper<>(Budget.class));
    }

}
