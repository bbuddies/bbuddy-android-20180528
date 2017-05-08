package com.odde.bbuddy.common;

import javax.validation.ConstraintViolation;

public class Validator {

    private final javax.validation.Validator rawValidator;

    public Validator(javax.validation.Validator rawValidator) {
        this.rawValidator = rawValidator;
    }

    public void processEachViolation(Object object, Consumer<String> consumer) {
        for (ConstraintViolation violation : rawValidator.validate(object))
            consumer.accept(errorMessage(violation));
    }

    private String errorMessage(ConstraintViolation violation) {
        return violation.getPropertyPath().iterator().next().getName() + " " + violation.getMessage();
    }

}