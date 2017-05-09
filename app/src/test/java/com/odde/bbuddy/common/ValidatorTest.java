package com.odde.bbuddy.common;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ValidatorTest {

    javax.validation.Validator stubRawValidator = mock(javax.validation.Validator.class);
    Validator validator = new Validator(stubRawValidator);
    Consumer mockConsumer = mock(Consumer.class);

    @Test
    public void should_consume_error_message_if_there_is_any_violation() {
        givenRawValidatorWillReturn("email", "may not be blank");

        validator.processEachViolation(new Object(), mockConsumer);

        verifyConsumerCalledWithViolation("email", "email may not be blank");
    }

    private void verifyConsumerCalledWithViolation(String fieldName, String message) {
        ArgumentCaptor<Violation> captor = ArgumentCaptor.forClass(Violation.class);
        verify(mockConsumer).accept(captor.capture());
        assertThat(captor.getValue().getFieldName()).isEqualTo(fieldName);
        assertThat(captor.getValue().getMessage()).isEqualTo(message);
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