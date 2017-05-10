package com.odde.bbuddy.common.validation;

import com.odde.bbuddy.common.functional.Consumer;

import javax.validation.ConstraintViolation;

public class Validator {

    private final javax.validation.Validator rawValidator;

    public Validator(javax.validation.Validator rawValidator) {
        this.rawValidator = rawValidator;
    }

    public void processEachViolation(Object object, Consumer<Violation> consumer) {
        for (ConstraintViolation violation : rawValidator.validate(object))
            consumer.accept(new Violation(fieldName(violation), fieldName(violation) + " " + violation.getMessage()));
    }

    private String fieldName(ConstraintViolation violation) {
        return violation.getPropertyPath().iterator().next().getName();
    }

}