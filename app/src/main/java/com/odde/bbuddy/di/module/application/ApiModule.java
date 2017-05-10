package com.odde.bbuddy.di.module.application;

import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.api.RawAccountsApi;
import com.odde.bbuddy.authentication.model.AuthenticationToken;
import com.odde.bbuddy.common.ApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public ApiFactory provideApiFactory(AuthenticationToken authenticationToken) {
        return new ApiFactory(authenticationToken);
    }

    @Provides
    @Singleton
    public AccountsApi provideAccounts(ApiFactory apiFactory) {
        return new AccountsApi(apiFactory.create(RawAccountsApi.class));
    }

}
