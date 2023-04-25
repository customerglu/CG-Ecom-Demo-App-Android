package com.customerglu.sdk.Utils;

import com.customerglu.sdk.CustomerGlu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CGAPIHelper {

    public static final int DEFAULT_RETRIES = CustomerGlu.apiRetryCount;

    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final Callback<T> callback) {
        call.enqueue(new CGRetryableCallback<T>(call, retryCount) {
            @Override
            public void onFinalFailure(Call<T> call, Throwable throwable) {
                callback.onFailure(call, throwable);
            }

            @Override
            public void onFinalResponse(Call<T> call, Response<T> response) {
                //This method returns the time in millis

                callback.onResponse(call, response);

            }
        });
    }

    public static <T> void enqueueWithRetry(Call<T> call, final Callback<T> callback) {
        enqueueWithRetry(call, DEFAULT_RETRIES, callback);
    }

    public static boolean isCallSuccess(Response response) {
        int code = response.code();
        return code != 429 && code != 502 && code != 408;
        // return (code >= 200 && code < 400);
    }

}
