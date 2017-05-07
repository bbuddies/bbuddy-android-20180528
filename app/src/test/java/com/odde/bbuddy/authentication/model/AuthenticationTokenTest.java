package com.odde.bbuddy.authentication.model;

import com.odde.bbuddy.common.BiConsumer;
import com.odde.bbuddy.common.Function;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthenticationTokenTest {

    AuthenticationToken token = new AuthenticationToken();
    Map<String, String> authenticationTokens = new HashMap<String, String>() {{
        put("access-token", "access-token");
        put("token-type", "token-type");
        put("uid", "uid");
        put("client", "client");
        put("expiry", "expiry");
    }};
    Map<String, String> headersWithoutAuthenticationToken = new HashMap<>();

    @Test
    public void update_token_and_get_headers() {
        updateByHeaders(authenticationTokens);

        verifyCanProcessHeaders(authenticationTokens);
    }

    @Test
    public void will_not_update_token_when_authentication_token_headers_not_exist() {
        given_token_already_updated_with_headers(authenticationTokens);

        updateByHeaders(headersWithoutAuthenticationToken);

        verifyCanProcessHeaders(authenticationTokens);
    }

    private void given_token_already_updated_with_headers(Map<String, String> headersWithAuthenticationTokens) {
        updateByHeaders(headersWithAuthenticationTokens);
    }

    private void verifyCanProcessHeaders(Map<String, String> headersWithAuthenticationTokens) {
        BiConsumer mockBiConsumer = mock(BiConsumer.class);
        token.processEachHeader(mockBiConsumer);

        for (Map.Entry<String, String> header : headersWithAuthenticationTokens.entrySet())
            verify(mockBiConsumer).accept(header.getKey(), header.getValue());
    }

    private void updateByHeaders(final Map<String, String> headers) {
        token.updateEachHeader(new Function<String, String>() {
            @Override
            public String apply(String key) {
                return headers.get(key);
            }
        });
    }

}