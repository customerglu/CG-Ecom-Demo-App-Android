package com.customerglu.sdk.Utils;

import static com.customerglu.sdk.Utils.Comman.printDebugLogs;

import android.os.Handler;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CGRetryableCallback<T> implements Callback<T> {

    private int totalRetries = 3;
    private static final String TAG = CGRetryableCallback.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0;

    public CGRetryableCallback(Call<T> call, int totalRetries) {
        this.call = call;
        this.totalRetries = totalRetries;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!CGAPIHelper.isCallSuccess(response)) {
            if (retryCount++ < totalRetries) {
                retry();
            } else {
                onFinalResponse(call, response);
            }
        } else {
            onFinalResponse(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (retryCount++ < totalRetries) {
            retry();
        } else {
            onFinalFailure(call, t);
        }
    }

    public void onFinalResponse(Call<T> call, Response<T> response) {


        printDebugLogs("onFinalResponse");
    }

    public void onFinalFailure(Call<T> call, Throwable t) {
        printDebugLogs("onFinalResponse");
    }

    private void retry() {
        new Handler().postDelayed(() -> call.clone().enqueue(this), generateRandomRetryInterval());
    }

    private int generateRandomRetryInterval() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(300) + 100;
    }
}
