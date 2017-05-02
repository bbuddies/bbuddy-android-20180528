package com.odde.bbuddy.authentication;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticatorTest {

    private final Call mockCall = mock(Call.class);
    RawAuthenticationApi mockRawAuthenticationApi = mock(RawAuthenticationApi.class);
    Authenticator authenticator = new Authenticator(mockRawAuthenticationApi);
    Credentials credentials = new Credentials("abc@gmail.com", "password");
    Runnable afterSuccess = mock(Runnable.class);
    Runnable afterFailed = mock(Runnable.class);

    @Before
    public void givenRawApiWillReturnCall() {
        when(mockRawAuthenticationApi.authenticate(any(Credentials.class))).thenReturn(mockCall);
    }

    @Test
    public void authenticate_with_user_name_and_password() throws JSONException {
        authenticate(credentials);

        verify(mockRawAuthenticationApi).authenticate(credentials);
    }

    @Test
    public void authenticate_successful() {
        givenAuthenticateWillSuccess();

        authenticate(credentials);

        verify(afterSuccess).run();
    }

    @Test
    public void authenticate_failed() {
        givenAuthenticateWillFail();

        authenticate(credentials);

        verify(afterFailed).run();
    }

    private void givenAuthenticateWillSuccess() {
        callWillResponse(Response.success("success"));
    }

    private void givenAuthenticateWillFail() {
        callWillResponse(Response.error(401, mock(ResponseBody.class)));
    }

    private void authenticate(Credentials credentials) {
        authenticator.authenticate(credentials, afterSuccess, afterFailed);
    }

    private void callWillResponse(final Response response) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback callback = invocation.getArgument(0);
                callback.onResponse(mockCall, response);
                return null;
            }
        }).when(mockCall).enqueue(any(Callback.class));
    }

}