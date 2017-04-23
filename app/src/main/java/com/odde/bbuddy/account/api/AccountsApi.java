package com.odde.bbuddy.account.api;

import android.support.annotation.NonNull;

import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.common.Consumer;

import java.util.List;

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
    private Callback<Account> callbackOfAfterSuccess(final Runnable afterSuccess) {
        return new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                afterSuccess.run();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        };
    }

}
