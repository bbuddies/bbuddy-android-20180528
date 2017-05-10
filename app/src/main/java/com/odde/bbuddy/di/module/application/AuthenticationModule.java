package com.odde.bbuddy.di.module.application;

import com.odde.bbuddy.authentication.api.RawAuthenticationApi;
import com.odde.bbuddy.authentication.model.AuthenticationToken;
import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.common.ApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {

    @Provides
    @Singleton
    public AuthenticationToken provideAuthenticationToken() {
        return new AuthenticationToken();
    }

    @Provides
    @Singleton
    public Authenticator provideAuthenticator(ApiFactory apiFactory) {
        return new Authenticator(apiFactory.create(RawAuthenticationApi.class));
    }

}
