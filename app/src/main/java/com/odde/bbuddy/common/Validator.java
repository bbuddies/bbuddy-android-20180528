package com.odde.bbuddy.common;

import com.odde.bbuddy.authentication.viewmodel.EditableAuthentication;

import javax.inject.Inject;

public class Validator {

    @Inject
    public Validator() { }

    public void processEachViolation(Object object, Consumer<String> consumer) {
        if (((EditableAuthentication)object).getEmail().isEmpty())
            consumer.accept("email may not be blank");
    }

}
