package b.b.b.bankaccount.newapi.api;


import java.util.ArrayList;

import b.b.b.bankaccount.newapi.models.Account;
import b.b.b.bankaccount.newapi.models.Activity;
import b.b.b.bankaccount.newapi.models.Transaction;
import b.b.b.bankaccount.newapi.models.Transfer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ohefny on 5/1/18.
 */

public interface AccountAPI {
    String BaseURL="localhost"+":3000"+"/api"+"";

    @POST("account/transfer")
    Call<Account> makeTransfer(@Body Transfer transfer);

    @POST("account/withdraw")
    Call<Account> makeWithdraw(@Body Transaction transaction);

    @POST("account/deposit")
    Call<Account> makeDeposit(@Body Transaction transaction);

    @GET("account/balance/{id}")
    Call<Integer> getBalance(@Path("id") String id);

    @GET("account/activities/{id}")
    Call<ArrayList<Activity>> getActivities(@Path("id") String id);
}
