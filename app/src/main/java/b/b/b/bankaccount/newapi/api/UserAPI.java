package b.b.b.bankaccount.newapi.api;


import b.b.b.bankaccount.newapi.models.Account;
import b.b.b.bankaccount.newapi.models.Login;
import b.b.b.bankaccount.newapi.models.Register;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ohefny on 5/1/18.
 */

public interface UserAPI {
    String BaseURL="https://polar-retreat-74854.herokuapp.com/api/";


    @POST("user/login")
    Call<Account> login(@Body Login login);

    @POST("user/register")
    Call<Account> register(@Body Register register);
}
