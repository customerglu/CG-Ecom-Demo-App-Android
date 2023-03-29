package com.customerglu.sdk.clienttesting;

import static com.customerglu.sdk.Utils.CGConstants.CALLBACK_HANDLING;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_BANNER_ID_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_EMBED_ID_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_SCREEN_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.NUDGE_HANDLING;
import static com.customerglu.sdk.Utils.CGConstants.ONE_LINK_HANDLING;
import static com.customerglu.sdk.Utils.CGConstants.SDK_INITIALISED;
import static com.customerglu.sdk.Utils.CGConstants.USER_REGISTERED;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customerglu.sdk.Adapters.ClientTestAdapter;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.Prefs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/***
 *  Author - Himanshu Trehan
 *
 *  Created - 16th March 2023
 *  Updated - 26th March 2023
 *
 *
 *
 *  Client Testing Page helps us to check is clients done integration properly
 *  Test Cases -
 *
 *  SDK_INITIALISED
 *  USER_REGISTERED
 *  CALLBACK_HANDLING
 *  NUDGE_HANDLING
 *  ONE_LINK_HANDLING
 *  SENDING_EVENTS
 *  ENTRY_POINTS_SET_UP
 *
 */


public class ClientTestingPage extends Activity {
    RecyclerView recyclerView;
    RecyclerView advanceRecyclerView;
    ClientTestAdapter basicTestingAdapter;
    ClientTestAdapter advancedTestingAdapter;
    ImageView back;
    ProgressBar pg;
    int basicTestIndex = 0;
    int advanceTestIndex = 0;
    public List<ClientTestingModel> basicClientTestList = new ArrayList<>();
    public List<ClientTestingModel> advancedClientTestList = new ArrayList<>();
    boolean isScreenListHasData = false;
    boolean isBannerListHasData = false;
    boolean isEmbedListHasData = false;
    Dialog dialog;
    Handler handler;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_testing_page);


        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                printErrorLogs("Uncaught Exception" + e);
                String s = "Uncaught Exception" + e;

                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), s);

            }
        });
        addTestCases();
        findViews();
        runTestCases(false, SDK_INITIALISED);

    }

    private void addTestCases() {
        basicClientTestList.add(new ClientTestingModel(CGConstants.SDK_INITIALISED, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#initialise-sdk"));
        basicClientTestList.add(new ClientTestingModel(CGConstants.USER_REGISTERED, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#register-user-mandatory"));
        basicClientTestList.add(new ClientTestingModel(CGConstants.CALLBACK_HANDLING, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#handling-events"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.NUDGE_HANDLING, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#handle-customerglu-nudges"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.ONE_LINK_HANDLING, CGConstants.TEST_STATE.IN_PROGRESS, ""));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.ENTRY_POINTS_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#entry-points"));
        advancedClientTestList.add(new ClientTestingModel(ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#setting-up-floating-buttons-and-popups"));
        advancedClientTestList.add(new ClientTestingModel(ENTRY_POINTS_BANNER_ID_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#setting-up-banners"));
        advancedClientTestList.add(new ClientTestingModel(ENTRY_POINTS_EMBED_ID_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, "https://docs.customerglu.com/sdk/mobile-sdks#setting-up-embed-view"));
        //   clientTestList.add(new ClientTestingModel(CGConstants.SENDING_EVENTS, CGConstants.TEST_STATE.IN_PROGRESS));
    }

    int getIndex(String testName, CGConstants.TEST_TYPE test_type) {
        int index = 0;
        if (test_type.equals(CGConstants.TEST_TYPE.BASIC)) {
            for (int i = 0; i < basicClientTestList.size(); i++) {
                if (testName.equalsIgnoreCase(basicClientTestList.get(i).getTestName())) {
                    index = i;
                }
            }
        } else {
            for (int i = 0; i < advancedClientTestList.size(); i++) {
                if (testName.equalsIgnoreCase(advancedClientTestList.get(i).getTestName())) {
                    index = i;
                }
            }
        }
        return index;
    }

    public void runTestCases(boolean retry, String testName) {
        switch (testName) {
            case SDK_INITIALISED:
                checkSDKInitialised(retry, getIndex(testName, CGConstants.TEST_TYPE.BASIC));
                break;
            case USER_REGISTERED:
                checkUserRegistered(retry, getIndex(testName, CGConstants.TEST_TYPE.BASIC));
                break;
            case CALLBACK_HANDLING:
                checkCallBackHandling(retry, getIndex(testName, CGConstants.TEST_TYPE.BASIC));

                break;
            case NUDGE_HANDLING:
                checkNudgeHandling(retry, getIndex(testName, CGConstants.TEST_TYPE.ADVANCED));
                break;
            case ONE_LINK_HANDLING:
                checkOneLinkHandling(retry, getIndex(testName, CGConstants.TEST_TYPE.ADVANCED));
                break;
            case ENTRY_POINTS_SET_UP:
                checkEntryPointsSetup(retry, getIndex(testName, CGConstants.TEST_TYPE.ADVANCED));
                break;
            case ENTRY_POINTS_SCREEN_SET_UP:
                checkEntryPointsScreenSetup(retry, getIndex(testName, CGConstants.TEST_TYPE.ADVANCED));
                break;
            case ENTRY_POINTS_BANNER_ID_SET_UP:
                checkEntryPointsBannerIdSetup(retry, getIndex(testName, CGConstants.TEST_TYPE.ADVANCED));
                break;
            case ENTRY_POINTS_EMBED_ID_SET_UP:
                checkEntryPointsEmbedIdSetup(retry, getIndex(testName, CGConstants.TEST_TYPE.ADVANCED));
                break;

        }
    }

    private void checkEntryPointsEmbedIdSetup(boolean retry, int index) {
        if (CustomerGlu.embedIdList.size() > 0) {
            isEmbedListHasData = true;
            updateListView(getIndex(ENTRY_POINTS_EMBED_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_EMBED_ID_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            updateListView(getIndex(ENTRY_POINTS_EMBED_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_EMBED_ID_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        runTestCases(false, ENTRY_POINTS_SET_UP);
    }

    private void checkEntryPointsBannerIdSetup(boolean retry, int index) {
        if (CustomerGlu.bannerIdList.size() > 0) {
            isBannerListHasData = true;
            updateListView(getIndex(ENTRY_POINTS_BANNER_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_BANNER_ID_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            updateListView(getIndex(ENTRY_POINTS_BANNER_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_BANNER_ID_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        runTestCases(false, ENTRY_POINTS_EMBED_ID_SET_UP);
    }

    private void checkEntryPointsScreenSetup(boolean retry, int index) {

        if (CustomerGlu.screenList.size() > 0) {
            isScreenListHasData = true;
            updateListView(getIndex(ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_SCREEN_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            updateListView(getIndex(ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_SCREEN_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        runTestCases(false, ENTRY_POINTS_BANNER_ID_SET_UP);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateListView(int index, String testName, boolean result, CGConstants.TEST_TYPE test_type) {
        if (test_type.equals(CGConstants.TEST_TYPE.BASIC)) {
            if (result) {
                basicClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.SUCCESS, basicClientTestList.get(getIndex(testName, CGConstants.TEST_TYPE.BASIC)).getUrl()));
            } else {
                basicClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.FAILURE, basicClientTestList.get(getIndex(testName, CGConstants.TEST_TYPE.BASIC)).getUrl()));
            }
            basicTestingAdapter.notifyDataSetChanged();
        } else {
            if (result) {
                advancedClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.SUCCESS, advancedClientTestList.get(getIndex(testName, CGConstants.TEST_TYPE.ADVANCED)).getUrl()));
            } else {
                advancedClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.FAILURE, advancedClientTestList.get(getIndex(testName, CGConstants.TEST_TYPE.ADVANCED)).getUrl()));
            }
            advancedTestingAdapter.notifyDataSetChanged();
        }
    }

    private void checkEntryPointsSetup(boolean retry, int testIndex) {

        if (isBannerListHasData || isScreenListHasData || isEmbedListHasData) {
            updateListView(testIndex, CGConstants.ENTRY_POINTS_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            updateListView(testIndex, CGConstants.ENTRY_POINTS_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }

    }


    private void checkOneLinkHandling(boolean retry, int testIndex) {
        //String deepLinkUrl = "https://edzuv3j.cglu.us/c/2NKhglg15de4FqE5y5IW8XlyPJ0";
        String deepLinkUrl = CustomerGlu.clientDeeplinkUrl;
        if (!deepLinkUrl.isEmpty() && deepLinkUrl.startsWith("http") && deepLinkUrl.startsWith("https")) {
            Uri uri = Uri.parse(deepLinkUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
        showAlertDialog("Are you redirecting to CG Screen?", testIndex, retry, ONE_LINK_HANDLING, CGConstants.ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_TYPE.ADVANCED);

    }

    private void checkNudgeHandling(boolean retry, int testIndex) {
        String token = "Bearer " + Prefs.getEncKey(getApplicationContext(), CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN);
        String userId = Prefs.getEncKey(getApplicationContext(), CGConstants.ENCRYPTED_CUSTOMERGLU_USER_ID);
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        Comman.getApiToken().clientNudgeTest(token, userData).enqueue(new Callback<RewardModel>() {
            @Override
            public void onResponse(Call<RewardModel> call, Response<RewardModel> response) {

                if (response.body() != null) {
                    if (response.body().success != null && response.body().success.equalsIgnoreCase("true")) {

                        showAlertDialog("Do you see nudge", testIndex, retry, CGConstants.NUDGE_HANDLING, ONE_LINK_HANDLING, CGConstants.TEST_TYPE.ADVANCED);
                    }
                }
            }

            @Override
            public void onFailure(Call<RewardModel> call, Throwable t) {
                showAlertDialog("Do you see nudge", testIndex, retry, ONE_LINK_HANDLING, ONE_LINK_HANDLING, CGConstants.TEST_TYPE.ADVANCED);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void checkCallBackHandling(boolean retry, int testIndex) {
        try {

            dialog = new Dialog(this);

            dialog.setContentView(R.layout.callback_dialog_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

            Button callback_btn = dialog.findViewById(R.id.callback_btn);
            TextView deeplink_text = dialog.findViewById(R.id.deep_link_txt);
            deeplink_text.setText("Following button contains " + CustomerGlu.clientDeeplinkUrl + " deeplink");
            callback_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    try {
                        JSONObject dataObject = new JSONObject();
                        dataObject.put("deepLink", CustomerGlu.clientDeeplinkUrl);

                        Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                        intent.putExtra("data", dataObject.toString());
                        sendBroadcast(intent);
                        showAlertDialog("Do you receive callback?", testIndex, retry, CALLBACK_HANDLING, NUDGE_HANDLING, CGConstants.TEST_TYPE.BASIC);

                    } catch (Exception e) {
                        Log.e("CUSTOMERGLU", "" + e);
                    }
                }
            });

            dialog.show();

        } catch (Exception e) {
            Log.e("", "" + e);
        }

    }


    private void showAlertDialog(String message, int testIndex, boolean retry, String testName, String nextTest, CGConstants.TEST_TYPE testType) {
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientTestingPage.this);

                // Set the message show for the Alert time
                builder.setMessage(message);

                // Set Alert Title
                builder.setTitle("CustomerGlu");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    if (testType.equals(CGConstants.TEST_TYPE.BASIC)) {
                        updateListView(testIndex, testName, true, CGConstants.TEST_TYPE.BASIC);
                        if (!retry) {
                            runTestCases(false, nextTest);
                        }
                    } else {
                        updateListView(testIndex, testName, true, CGConstants.TEST_TYPE.ADVANCED);
                        if (!retry) {
                            runTestCases(false, nextTest);
                        }
                    }
                    dialog.cancel();

                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    if (testType.equals(CGConstants.TEST_TYPE.BASIC)) {
                        updateListView(testIndex, testName, false, CGConstants.TEST_TYPE.BASIC);
                        if (!retry) {

                            runTestCases(false, nextTest);
                        }
                    } else {
                        updateListView(testIndex, testName, false, CGConstants.TEST_TYPE.ADVANCED);
                        if (!retry) {
                            advanceTestIndex++;
                            runTestCases(false, nextTest);
                        }
                    }
                    dialog.cancel();
                });

                // Create the Alert dialog
                alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
                    }
                });
                // Show the Alert Dialog box
                alertDialog.show();
                //Do something after 100ms
            }
        }, 3000);

    }

    private void checkUserRegistered(boolean retry, int retryIndex) {
        String token = Prefs.getEncKey(getApplicationContext(), CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN);
        updateListView(retryIndex, CGConstants.USER_REGISTERED, !token.isEmpty(), CGConstants.TEST_TYPE.BASIC);
        if (!retry) {
            runTestCases(false, CALLBACK_HANDLING);

        }
    }

    private void checkSDKInitialised(boolean retry, int retryIndex) {

        updateListView(retryIndex, CGConstants.SDK_INITIALISED, CustomerGlu.isInitialized, CGConstants.TEST_TYPE.BASIC);

        if (!retry) {
            runTestCases(false, USER_REGISTERED);
        }
    }

    private void findViews() {

        back = findViewById(R.id.back);
        pg = findViewById(R.id.pg);
        recyclerView = findViewById(R.id.testingRecyclerView);
        advanceRecyclerView = findViewById(R.id.advanceTestingRecyclerView);
        basicTestingAdapter = new ClientTestAdapter(getApplicationContext(), this, basicClientTestList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(basicTestingAdapter);
        // advance List Adapter
        advancedTestingAdapter = new ClientTestAdapter(getApplicationContext(), this, advancedClientTestList);
        LinearLayoutManager advanceTestingManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        advanceRecyclerView.setLayoutManager(advanceTestingManager);
        advanceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        advanceRecyclerView.setAdapter(advancedTestingAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (alertDialog != null) {
            alertDialog.cancel();
        }
        if (dialog != null) {
            dialog.cancel();
        }

    }
}
