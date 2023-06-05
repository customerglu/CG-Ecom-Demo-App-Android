package com.customerglu.sdk.ApiServices;

import static com.customerglu.sdk.Utils.URLHelper.BaseUrl;

import com.customerglu.sdk.CustomerGlu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClients {
    public static String bearer_token = "";
    public static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;
    private static OkHttpClient okHttpClient1;


    public static Retrofit getClient_token() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        initOkHttp_token();

        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(okHttpClient1)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit1;
    }

    private static void initOkHttp_token() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(10);
        dispatcher.setMaxRequestsPerHost(1);
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (CustomerGlu.debuggingMode) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .addHeader("cg-sdk-version", CustomerGlu.cg_sdk_version)
                        .addHeader("cg-sdk-platform", CustomerGlu.cg_app_platform);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        httpClient.dispatcher(dispatcher);
        okHttpClient1 = httpClient.build();

    }

}
