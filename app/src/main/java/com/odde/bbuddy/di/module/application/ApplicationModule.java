package com.odde.bbuddy.di.module.application;

import android.app.Application;

import com.odde.bbuddy.common.StringResources;
import com.odde.bbuddy.common.validation.Validator;

import javax.inject.Singleton;
import javax.validation.Validation;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public StringResources provideResources() {
        return new StringResources(application.getResources());
    }

    @Provides
    @Singleton
    public Validator provideValidator() {
        return new Validator(Validation
                .byDefaultProvider()
                .configure()
                .ignoreXmlConfiguration()
                .buildValidatorFactory().getValidator());
    }

}
