package b.b.b.bankaccount.newapi.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Omar El Hefny on 11/1/2017.
 */
//Use this provider if your request apply to the common setting
//No Node , WriteTimeOut= , ConnectTimeOut= , ReadTimeOut=
public class OkHttpClientProvider {
    //todo test if the client can work when asking for more than one request at the same time
    private static final OkHttpClientProvider ourInstance = new OkHttpClientProvider();
    // private OkHttpClient okHttpClient=httpBuilder.build();
    private final int READ_TIMEOUT = 60;
    private final int WRITE_TIMEOUT = 20;
    private final int CONNECT_TIMEOUT = 5;
    private OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

    private OkHttpClientProvider() {
    }

    public static OkHttpClientProvider getInstance() {
        return ourInstance;
    }

    public OkHttpClient getClient(boolean needsAuth) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //todo:: replace it with the required values
        httpBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.connectionPool(new ConnectionPool(3, 5L, TimeUnit.MINUTES));
        httpBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.retryOnConnectionFailure(false);
        // for shallow logging on network requests (no retry or redirects are showing) however caching can be intercepted
        //httpBuilder.addInterceptor(new LoggingInterceptor()); //Application Interceptor
        //for deeper logging on network requests
        //notice this adds authorization header if needed by service and other default headers
        // httpBuilder.addInterceptor(new LoggingInterceptor());
        httpBuilder.addInterceptor(logging);
        httpBuilder.addInterceptor(new HeadersInterceptor.Builder().setNeedsAuthentication(true).build());



        OkHttpClient client = httpBuilder.build();
        Log.d("OKhttpClient", client.readTimeoutMillis() + ":" + client.connectTimeoutMillis() + ":" + client.interceptors());
        return client;
    }

}
