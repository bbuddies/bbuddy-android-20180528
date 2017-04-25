package com.odde.bbuddy.account.api;

import com.odde.bbuddy.account.viewmodel.Account;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RawAccountsApi {

    String ACCOUNTS = "/accounts";
    String ACCOUNTS_WITH_ID = ACCOUNTS + "/{id}";

    @POST(ACCOUNTS)
    Call<ResponseBody> addAccount(@Body Account account);

    @GET(ACCOUNTS)
    Call<List<Account>> getAllAccounts();

    @PUT(ACCOUNTS_WITH_ID)
    Call<ResponseBody> editAccount(@Path("id") int accountId, @Body Account account);

    @DELETE(ACCOUNTS_WITH_ID)
    Call<ResponseBody> deleteAccount(@Path("id") int accountId);
}
