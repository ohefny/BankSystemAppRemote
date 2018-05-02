package b.b.b.bankaccount.newapi.network;


import b.b.b.bankaccount.newapi.api.UserAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ohefny on 5/1/18.
 */

public class NetworkCall {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(UserAPI.BaseURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClientProvider.getInstance().getClient(true))
                    .build();

        }
        return retrofit;
    }
}
