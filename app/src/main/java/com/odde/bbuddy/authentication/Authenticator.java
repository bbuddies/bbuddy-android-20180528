package com.odde.bbuddy.authentication;

import com.odde.bbuddy.common.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authenticator {

    private final RawAuthenticationApi rawAuthenticationApi;

    public Authenticator(RawAuthenticationApi rawAuthenticationApi) {
        this.rawAuthenticationApi = rawAuthenticationApi;
    }

    public void authenticate(Credentials credentials, final Consumer afterSuccess) {
        rawAuthenticationApi.authenticate(credentials).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                    afterSuccess.accept("success");
                else
                    afterSuccess.accept("failed");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

}
