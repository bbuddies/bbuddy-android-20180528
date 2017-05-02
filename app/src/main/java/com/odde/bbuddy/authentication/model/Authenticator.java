package com.odde.bbuddy.authentication.model;

import com.odde.bbuddy.authentication.api.RawAuthenticationApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authenticator {

    private final RawAuthenticationApi rawAuthenticationApi;

    public Authenticator(RawAuthenticationApi rawAuthenticationApi) {
        this.rawAuthenticationApi = rawAuthenticationApi;
    }

    public void authenticate(Credentials credentials, final Runnable afterSuccess, final Runnable afterFailed) {
        rawAuthenticationApi.authenticate(credentials).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                    afterSuccess.run();
                else
                    afterFailed.run();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

}
