package com.odde.bbuddy.account.api;

import com.odde.bbuddy.account.viewmodel.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RawAccountsApi {

    String ACCOUNTS = "/accounts";

    @POST(ACCOUNTS)
    Call<Account> addAccount(@Body Account account);

    @GET(ACCOUNTS)
    Call<List<Account>> getAllAccounts();

    @PUT("/accounts/{id}")
    Call<Account> editAccount(@Path("id") int accountId, @Body Account account);

    @DELETE("/accounts/{id}")
    Call<Account> deleteAccount(@Path("id") int accountId);
}
