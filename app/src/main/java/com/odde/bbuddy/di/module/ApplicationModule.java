package com.odde.bbuddy.di.module;

import android.app.Application;

import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.api.RawAccountsApi;
import com.odde.bbuddy.authentication.api.RawAuthenticationApi;
import com.odde.bbuddy.authentication.model.AuthenticationToken;
import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.common.ApiFactory;
import com.odde.bbuddy.common.StringResources;

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
    public AuthenticationToken provideAuthenticationToken() {
        return new AuthenticationToken();
    }

    @Provides @Singleton
    public ApiFactory provideApiFactory(AuthenticationToken authenticationToken) {
        return new ApiFactory(authenticationToken);
    }

    @Provides @Singleton
    public Authenticator provideAuthenticator(ApiFactory apiFactory) {
        return new Authenticator(apiFactory.create(RawAuthenticationApi.class));
    }

    @Provides @Singleton
    public AccountsApi provideAccounts(ApiFactory apiFactory) {
        return new AccountsApi(apiFactory.create(RawAccountsApi.class));
    }

    @Provides @Singleton
    public StringResources provideResources() {
        return new StringResources(application.getResources());
    }

}
