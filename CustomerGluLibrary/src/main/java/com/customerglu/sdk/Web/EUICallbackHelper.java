package com.customerglu.sdk.Web;

import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN;
import static com.customerglu.sdk.Utils.CGConstants.PROGRAM_DATA;
import static com.customerglu.sdk.Utils.CGConstants.REWARD_DATA;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.webkit.WebView;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.ProgramFilter;
import com.customerglu.sdk.Modal.ProgramFilterModel;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.Prefs;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EUICallbackHelper {

    public static EUICallbackHelper INSTANCE;
    public String programData;
    public String rewardData;


    public static synchronized EUICallbackHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EUICallbackHelper();
        }

        return INSTANCE;
    }

    private ProgramFilterModel addFilterCampaign() {
        Map<String, Object> campaignData = new HashMap<>();
        for (String campaignId : CustomerGlu.campaignIdList) {
            campaignData.put(campaignId, true);
        }

        ProgramFilter programFilter = new ProgramFilter(campaignData);
        return new ProgramFilterModel(programFilter, 50, 1);
    }

    public void getProgramData() {
        String token = Prefs.getEncKey(CustomerGlu.globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN);
        ProgramFilterModel programFilterModel = addFilterCampaign();
        Gson gson = new Gson();
        String json = gson.toJson(programFilterModel);
        printErrorLogs(json);
        Comman.getApiToken().getPrograms("Bearer " + token, programFilterModel).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.body() != null) {
                    JsonObject jsonObject = response.body();
                    programData = jsonObject.toString();
                    Prefs.putEncKey(CustomerGlu.globalContext, CGConstants.PROGRAM_DATA, programData);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }

    public void getRewardData() {
        String token = Prefs.getEncKey(CustomerGlu.globalContext, ENCRYPTED_CUSTOMERGLU_TOKEN);
        ProgramFilterModel programFilterModel = addFilterCampaign();
        Gson gson = new Gson();
        String json = gson.toJson(programFilterModel);
        printErrorLogs(json);
        Comman.getApiToken().getRewards("Bearer " + token, programFilterModel).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    JsonObject jsonObject = response.body();

                    rewardData = jsonObject.toString();
                    Prefs.putKey(CustomerGlu.globalContext, CGConstants.REWARD_DATA, rewardData);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }

    public void callJavaScriptFunction(WebView webView, JSONObject jsonObject) {
        try {
            String eventName = jsonObject.getString("eventName");
            JSONObject eventData = jsonObject.getJSONObject("data");
            JSONObject responseDataObject = new JSONObject();
            if (eventName.equalsIgnoreCase(CGConstants.REQUEST_API_DATA)) {
                responseDataObject = createApiRequestObject(eventData);
            } else if (eventName.equalsIgnoreCase(CGConstants.REFRESH_API_DATA)) {
                responseDataObject = createRefreshResultData(eventData);
            }
            JSONObject finalResponseDataObject = responseDataObject;
            Observable.fromCallable(new Callable<String>() {
                        @Override
                        public String call() throws Exception {

                            if (webView != null) {

//                                printDebugLogs("Start Posting Data");
//                                printDebugLogs(finalResponseDataObject.toString());
                                String jsFunctionCall =
                                        CGConstants.SDK_CALLBACK + "(" + finalResponseDataObject + ");";
                                webView.evaluateJavascript(jsFunctionCall, null);
                            }
                            return "";
                        }
                    }).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    })
                    .subscribe();


        } catch (Exception e) {
            printErrorLogs("JS method callback failure" + e);
        }

    }

    private JSONObject createApiRequestObject(JSONObject jsonObject) {

        JSONObject result = new JSONObject();
        if (rewardData == null) {
            rewardData = Prefs.getEncKey(CustomerGlu.globalContext, REWARD_DATA);
        }
        if (programData == null) {
            programData = Prefs.getEncKey(CustomerGlu.globalContext, PROGRAM_DATA);
        }

        try {
            String requestId = jsonObject.getString("requestId");
            JSONObject data = new JSONObject();
            data.put("requestId", requestId);
            if (rewardData != null && !rewardData.isEmpty()) {
                data.put("rewardsResponse", new JSONObject(rewardData));
            }
            if (programData != null && !programData.isEmpty()) {
                data.put("programsResponse", new JSONObject(programData));
            }
            result.put("eventName", CGConstants.REQUEST_API_RESULT);
            result.put("data", data);

        } catch (Exception e) {
            printErrorLogs("createApiRequestObject " + e);
        }

        return result;

    }

    private JSONObject createRefreshResultData(JSONObject jsonObject) {

        JSONObject result = new JSONObject();

        try {
            String requestId = jsonObject.getString("requestId");
            JSONObject data = new JSONObject();
            data.put("requestId", requestId);
            result.put("eventName", CGConstants.REFRESH_API_DATA_RESULT);
            result.put("data", data);

        } catch (Exception e) {
            printErrorLogs("" + e);
        }

        return result;

    }


}
