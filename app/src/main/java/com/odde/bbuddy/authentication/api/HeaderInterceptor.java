package com.odde.bbuddy.authentication.api;

import android.support.annotation.NonNull;

import com.odde.bbuddy.authentication.model.AuthenticationToken;
import com.odde.bbuddy.common.BiConsumer;
import com.odde.bbuddy.common.Function;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private final AuthenticationToken authenticationToken;

    public HeaderInterceptor(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return updateAuthenticationHeadersThenResponse(chain, requestWithAuthenticationHeaders(chain.request()));
    }

    private Response updateAuthenticationHeadersThenResponse(Chain chain, Request request) throws IOException {
        final Response response = chain.proceed(request);

        authenticationToken.updateEachHeader(new Function<String, String>() {
            @Override
            public String apply(String header) {
                return response.header(header);
            }
        });

        return response;
    }

    private Request requestWithAuthenticationHeaders(Request originalRequest) {
        return addAuthenticationHeaders(originalRequest)
                .method(originalRequest.method(), originalRequest.body())
                .build();
    }

    @NonNull
    private Request.Builder addAuthenticationHeaders(Request original) {
        final Request.Builder requestBuilder = original.newBuilder();

        authenticationToken.processEachHeader(new BiConsumer<String, String>() {
            @Override
            public void accept(String header, String value) {
                requestBuilder.header(header, value);
            }
        });
        return requestBuilder;
    }
}
