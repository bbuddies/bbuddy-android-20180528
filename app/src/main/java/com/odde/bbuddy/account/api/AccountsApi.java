package com.odde.bbuddy.account.api;

import android.support.annotation.NonNull;

import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.common.functional.Consumer;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsApi {

    private final RawAccountsApi rawAccountsApi;

    public AccountsApi(RawAccountsApi rawAccountsApi) {
        this.rawAccountsApi = rawAccountsApi;
    }

    public void processAllAccounts(final Consumer<List<Account>> consumer) {
        rawAccountsApi.getAllAccounts().enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                consumer.accept(response.body());
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {

            }
        });
    }

    public void addAccount(Account account, final Runnable afterSuccess) {
        rawAccountsApi.addAccount(account).enqueue(callbackOfAfterSuccess(afterSuccess));
    }

    public void editAccount(Account account, final Runnable afterSuccess) {
        rawAccountsApi.editAccount(account.getId(), account).enqueue(callbackOfAfterSuccess(afterSuccess));
    }

    public void deleteAccount(Account account, final Runnable afterSuccess) {
        rawAccountsApi.deleteAccount(account.getId()).enqueue(callbackOfAfterSuccess(afterSuccess));
    }

    @NonNull
    private Callback<ResponseBody> callbackOfAfterSuccess(final Runnable afterSuccess) {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                afterSuccess.run();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
    }

}
