package com.odde.bbuddy.authentication.viewmodel;

import com.odde.bbuddy.authentication.model.Authenticator;
import com.odde.bbuddy.authentication.model.Credentials;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.StringResources;
import com.odde.bbuddy.common.Validator;
import com.odde.bbuddy.dashboard.view.DashboardNavigation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import dagger.Lazy;

import static com.odde.bbuddy.common.CallbackInvoker.callRunnableAtIndex;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditableAuthenticationTest {

    Authenticator mockAuthenticator = mock(Authenticator.class);
    DashboardNavigation mockDashboardNavigation = mock(DashboardNavigation.class);
    Lazy<PresentationModelChangeSupport> stubChangeSupportLoader = mock(Lazy.class);
    PresentationModelChangeSupport mockPresentationModelChangeSupport = mock(PresentationModelChangeSupport.class);
    StringResources stubStringResources = mock(StringResources.class);
    Validator stubValidator = mock(Validator.class);
    EditableAuthentication editableAuthentication = new EditableAuthentication(mockAuthenticator, mockDashboardNavigation, stubChangeSupportLoader, stubStringResources, stubValidator);

    @Before
    public void enableChangeSupport() {
        when(stubChangeSupportLoader.get()).thenReturn(mockPresentationModelChangeSupport);
    }

    @Test
    public void should_correctly_authenticate() {
        login("email", "password");

        verifyAuthenticateCalledWith("email", "password");
    }

    @Test
    public void should_show_dashboard_after_login_successfully() {
        givenAuthenticateWillSuccess();

        login("email", "password");

        verify(mockDashboardNavigation).navigate();
    }

    @Test
    public void should_show_some_message_after_login_failed() {
        givenAuthenticateWillFailed();
        givenLoginFailedMessage("a login failed message");

        login("email", "wrong_password");

        assertThat(editableAuthentication.getMessage()).isEqualTo("a login failed message");
        verify(mockPresentationModelChangeSupport).refreshPresentationModel();
    }

    @Test
    public void should_show_error_message_if_email_is_empty() {
        givenCredentialViolatedWithMessage("email may not be blank");

        login("", "password");

        assertThat(editableAuthentication.getMessage()).isEqualTo("email may not be blank");
        verify(mockPresentationModelChangeSupport).refreshPresentationModel();
    }

    @Test
    public void should_not_call_authenticate_if_email_is_empty() {
        givenCredentialViolatedWithMessage("email may not be blank");

        login("", "password");

        verifyAuthenticationNotCalled();
    }

    private void verifyAuthenticationNotCalled() {
        verify(mockAuthenticator, never()).authenticate(any(Credentials.class), any(Runnable.class), any(Runnable.class));
    }

    private void givenCredentialViolatedWithMessage(final String message) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(1);
                consumer.accept(message);
                return null;
            }
        }).when(stubValidator).processEachViolation(any(EditableAuthentication.class), any(Consumer.class));
    }

    private void givenLoginFailedMessage(String message) {
        when(stubStringResources.get(anyInt())).thenReturn(message);
    }

    private void verifyAuthenticateCalledWith(String email, String password) {
        ArgumentCaptor<Credentials> captor = ArgumentCaptor.forClass(Credentials.class);
        verify(mockAuthenticator).authenticate(captor.capture(), any(Runnable.class), any(Runnable.class));
        assertThat(captor.getValue().getEmail()).isEqualTo(email);
        assertThat(captor.getValue().getPassword()).isEqualTo(password);
    }

    private void login(String email, String password) {
        editableAuthentication.setEmail(email);
        editableAuthentication.setPassword(password);
        editableAuthentication.login();
    }

    private void givenAuthenticateWillFailed() {
        callRunnableAtIndex(2).when(mockAuthenticator).authenticate(any(Credentials.class), any(Runnable.class), any(Runnable.class));
    }

    private void givenAuthenticateWillSuccess() {
        callRunnableAtIndex(1).when(mockAuthenticator).authenticate(any(Credentials.class), any(Runnable.class), any(Runnable.class));
    }

}