package com.odde.bbuddy.common;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ValidatorTest {

    javax.validation.Validator stubRawValidator = mock(javax.validation.Validator.class);
    Validator validator = new Validator(stubRawValidator);

    @Test
    public void should_consume_error_message_if_there_is_any_violation() {
        givenRawValidatorWillReturn("email", "error message");

        Consumer mockConsumer = mock(Consumer.class);
        validator.processEachViolation(new Object(), mockConsumer);

        verify(mockConsumer).accept("email error message");
    }

    private void givenRawValidatorWillReturn(String fieldName, String errorMessage) {
        ConstraintViolation<Object> stubConstraintViolation = mock(ConstraintViolation.class);
        Path stubPath = mock(Path.class);
        Iterator<Path.Node> stubIterator = mock(Iterator.class);
        Path.Node stubPathNode = mock(Path.Node.class);
        when(stubRawValidator.validate(any(Object.class))).thenReturn(new HashSet<>(asList(stubConstraintViolation)));
        when(stubConstraintViolation.getMessage()).thenReturn(errorMessage);
        when(stubConstraintViolation.getPropertyPath()).thenReturn(stubPath);
        when(stubPath.iterator()).thenReturn(stubIterator);
        when(stubIterator.next()).thenReturn(stubPathNode);
        when(stubPathNode.getName()).thenReturn(fieldName);
    }

}