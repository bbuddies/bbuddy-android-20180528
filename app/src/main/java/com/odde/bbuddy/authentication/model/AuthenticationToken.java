package com.odde.bbuddy.authentication.model;

import com.odde.bbuddy.common.BiConsumer;
import com.odde.bbuddy.common.Function;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationToken {

    private final static Map<String, String> authenticationHeaders = new HashMap<>();

    public void processEachHeader(BiConsumer<String, String> consumer) {
        for (Map.Entry<String, String> header: authenticationHeaders.entrySet())
            consumer.accept(header.getKey(), header.getValue());
    }

    public void updateEachHeader(Function<String, String> applier) {
        if (applier.apply("access-token") != null ||
                applier.apply("token-type") != null ||
                applier.apply("uid") != null ||
                applier.apply("client") != null ||
                applier.apply("expiry") != null) {
            authenticationHeaders.put("access-token", applier.apply("access-token"));
            authenticationHeaders.put("token-type", applier.apply("token-type"));
            authenticationHeaders.put("uid", applier.apply("uid"));
            authenticationHeaders.put("client", applier.apply("client"));
            authenticationHeaders.put("expiry", applier.apply("expiry"));
        }
    }
}
