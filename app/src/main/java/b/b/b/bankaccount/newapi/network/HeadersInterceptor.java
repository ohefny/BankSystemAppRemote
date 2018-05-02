package b.b.b.bankaccount.newapi.network;

import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

import b.b.b.bankaccount.newapi.User;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Omar El Hefny on 11/27/2017.
 */
public class HeadersInterceptor implements Interceptor {
    private boolean needsAuthorization = true;
    private boolean hasContentType = true;
    private boolean hasAccept = true;

    private HeadersInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        ArrayList<Pair<String, String>> headers = new ArrayList<>();
        if (hasContentType)
            headers.add(new Pair<>("Content-Type", "application/json"));
        if (hasAccept)
            headers.add(new Pair<>("Accept", "application/json"));
        if (User.getAccount()!=null) {
                headers.add(new Pair<>("account-number", User.getAccount().getAccountNumber()+""));
                headers.add(new Pair<>("password", User.getAccount().getPassword()));

        }
        Request.Builder builder = original.newBuilder();
        for (Pair<String, String> head : headers) {
            Log.d("Header", head.first + ":" + head.second);
            builder.addHeader(head.first, head.second);
        }
        Request request = builder.build();
        //return chain.proceed(original.newBuilder().addHeader("Content-Type","application/json").addHeader("Accept","application/json").build());
        return chain.proceed(request);
    }

    public static class Builder {
        private final Builder builder;
        HeadersInterceptor interceptor = new HeadersInterceptor();

        public Builder() {
            this.builder = this;
        }

        public Builder setHasContentType(boolean hasContent) {
            interceptor.hasContentType = hasContent;
            return builder;
        }

        public Builder setHasAccept(boolean accept) {
            interceptor.hasAccept = accept;
            return builder;
        }

        public Builder setNeedsAuthentication(boolean hasAuth) {
            interceptor.needsAuthorization = hasAuth;
            return builder;
        }

        public HeadersInterceptor build() {
            return interceptor;
        }
    }
}