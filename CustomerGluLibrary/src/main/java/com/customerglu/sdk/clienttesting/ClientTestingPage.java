package com.customerglu.sdk.clienttesting;

import static com.customerglu.sdk.Utils.CGConstants.CALLBACK_HANDLING;
import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_CUSTOMERGLU_USER_ID;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_BANNER_ID_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_EMBED_ID_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_SCREEN_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.ENTRY_POINTS_SET_UP;
import static com.customerglu.sdk.Utils.CGConstants.FIREBASE_PRIVATE_SEVER_KEY;
import static com.customerglu.sdk.Utils.CGConstants.FIREBASE_SETUP;
import static com.customerglu.sdk.Utils.CGConstants.FIREBASE_TOKEN;
import static com.customerglu.sdk.Utils.CGConstants.NUDGE_HANDLING;
import static com.customerglu.sdk.Utils.CGConstants.ONE_LINK_HANDLING;
import static com.customerglu.sdk.Utils.CGConstants.SDK_INITIALISED;
import static com.customerglu.sdk.Utils.CGConstants.USER_REGISTERED;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.customerglu.sdk.Modal.ClientTestNotificationModel;
import com.customerglu.sdk.Modal.ClientTestPostDataModel;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.cgRxBus.event.ClientTestPageTestCaseEvent;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/***
 *  Author - Himanshu Trehan
 *
 *  Created - 16th March 2023
 *  Updated - 26th April 2023
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
    public List<ClientTestingModel> basicClientTestList = new ArrayList<>();
    public List<ClientTestingModel> advancedClientTestList = new ArrayList<>();
    boolean isScreenListHasData = false;
    boolean isBannerListHasData = false;
    boolean isEmbedListHasData = false;
    ClientTestNotificationModel clientTestNotificationModel;
    boolean hasPrivateKey = false;
    boolean hasFirebaseToken = false;
    ArrayList<ClientTestPostDataModel.TestData> testResultList;
    Handler handler;
    Runnable runnable;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_testing_page);
        CustomerGlu.cgrxBus.getEvent().observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof ClientTestPageTestCaseEvent) {

                    runTestCases(((ClientTestPageTestCaseEvent) o).isRetry(), ((ClientTestPageTestCaseEvent) o).getTestName());
                }
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                printErrorLogs("Uncaught Exception" + e);
                String s = "Uncaught Exception" + e;

                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), s);

            }
        });
        getNudgeConfigurationDetails();
        addTestCases();
        findViews();
        runTestCases(false, SDK_INITIALISED);

    }

    private void addTestCases() {
        basicClientTestList.add(new ClientTestingModel(CGConstants.SDK_INITIALISED, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.valueViewType, "https://docs.customerglu.com/sdk/mobile-sdks#initialise-sdk"));
        basicClientTestList.add(new ClientTestingModel(CGConstants.USER_REGISTERED, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.valueViewType, "https://docs.customerglu.com/sdk/mobile-sdks#register-user-mandatory"));
        basicClientTestList.add(new ClientTestingModel(CGConstants.CALLBACK_HANDLING, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.valueViewType, "https://docs.customerglu.com/sdk/mobile-sdks#handling-events"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.FIREBASE_SETUP, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.headingViewType, ""));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.FIREBASE_PRIVATE_SEVER_KEY, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.subHeadingViewType, "https://docs.customerglu.com/advanced-topics/notifications#firebase-cloud-messaging-fcm"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.FIREBASE_TOKEN, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.subHeadingViewType, "https://docs.customerglu.com/sdk/mobile-sdks#register-user-mandatory"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.NUDGE_HANDLING, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.valueViewType, "https://docs.customerglu.com/sdk/mobile-sdks#handle-customerglu-nudges"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.ONE_LINK_HANDLING, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.valueViewType, "https://docs.customerglu.com/sdk/mobile-sdks#handling-customerglu-deeplinks"));
        advancedClientTestList.add(new ClientTestingModel(CGConstants.ENTRY_POINTS_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.headingViewType, ""));
        advancedClientTestList.add(new ClientTestingModel(ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.subHeadingViewType, "https://docs.customerglu.com/sdk/mobile-sdks#setting-up-floating-buttons-and-popups"));
        advancedClientTestList.add(new ClientTestingModel(ENTRY_POINTS_BANNER_ID_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.subHeadingViewType, "https://docs.customerglu.com/sdk/mobile-sdks#setting-up-banners"));
        advancedClientTestList.add(new ClientTestingModel(ENTRY_POINTS_EMBED_ID_SET_UP, CGConstants.TEST_STATE.IN_PROGRESS, CGConstants.subHeadingViewType, "https://docs.customerglu.com/sdk/mobile-sdks#setting-up-embed-view"));
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
            case FIREBASE_PRIVATE_SEVER_KEY:
                checkFirebaseServerKey(retry, getIndex(testName, CGConstants.TEST_TYPE.BASIC));
                break;
            case FIREBASE_TOKEN:
                checkFirebaseToken(retry, getIndex(testName, CGConstants.TEST_TYPE.BASIC));
                break;
            case FIREBASE_SETUP:
                checkFirebaseSetup(retry, getIndex(testName, CGConstants.TEST_TYPE.BASIC));
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

    private void checkFirebaseSetup(boolean retry, int index) {
        if (hasFirebaseToken && hasPrivateKey) {
            updateListView(getIndex(FIREBASE_SETUP, CGConstants.TEST_TYPE.ADVANCED), FIREBASE_SETUP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            updateListView(getIndex(FIREBASE_SETUP, CGConstants.TEST_TYPE.ADVANCED), FIREBASE_SETUP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        retryOrPostData(retry, NUDGE_HANDLING);

    }


    private void checkFirebaseToken(boolean retry, int index) {
        if (clientTestNotificationModel != null && clientTestNotificationModel.getData() != null && clientTestNotificationModel.getData().getFirebaseToken() != null && clientTestNotificationModel.getData().getFirebaseToken()) {
            hasFirebaseToken = true;
            addTestResults(CGConstants.TEST_NAME_ENUM.FIREBASE_TOKEN_SHARED, true);
            updateListView(getIndex(FIREBASE_TOKEN, CGConstants.TEST_TYPE.ADVANCED), FIREBASE_TOKEN, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            addTestResults(CGConstants.TEST_NAME_ENUM.FIREBASE_TOKEN_SHARED, false);
            updateListView(getIndex(FIREBASE_TOKEN, CGConstants.TEST_TYPE.ADVANCED), FIREBASE_TOKEN, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        retryOrPostData(retry, FIREBASE_SETUP);


    }


    private void checkFirebaseServerKey(boolean retry, int index) {
        if (clientTestNotificationModel != null && clientTestNotificationModel.getData() != null && clientTestNotificationModel.getData().getPrivateKeyFirebase() != null && clientTestNotificationModel.getData().getPrivateKeyFirebase()) {
            hasPrivateKey = true;
            addTestResults(CGConstants.TEST_NAME_ENUM.FIREBASE_PRIVATE_KEY, true);
            updateListView(getIndex(FIREBASE_PRIVATE_SEVER_KEY, CGConstants.TEST_TYPE.ADVANCED), FIREBASE_PRIVATE_SEVER_KEY, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            addTestResults(CGConstants.TEST_NAME_ENUM.FIREBASE_PRIVATE_KEY, false);
            updateListView(getIndex(FIREBASE_PRIVATE_SEVER_KEY, CGConstants.TEST_TYPE.ADVANCED), FIREBASE_PRIVATE_SEVER_KEY, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        retryOrPostData(retry, FIREBASE_TOKEN);


    }

    private void checkEntryPointsEmbedIdSetup(boolean retry, int index) {
        if (CustomerGlu.embedIdList.size() > 0) {
            isEmbedListHasData = true;
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_EMBEDID_SET_UP, true);
            updateListView(getIndex(ENTRY_POINTS_EMBED_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_EMBED_ID_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_EMBEDID_SET_UP, false);
            updateListView(getIndex(ENTRY_POINTS_EMBED_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_EMBED_ID_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        retryOrPostData(retry, ENTRY_POINTS_SET_UP);

    }

    private void checkEntryPointsBannerIdSetup(boolean retry, int index) {
        if (CustomerGlu.bannerIdList.size() > 0) {
            isBannerListHasData = true;
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_BANNERID_SET_UP, true);
            updateListView(getIndex(ENTRY_POINTS_BANNER_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_BANNER_ID_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_BANNERID_SET_UP, false);
            updateListView(getIndex(ENTRY_POINTS_BANNER_ID_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_BANNER_ID_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        retryOrPostData(retry, ENTRY_POINTS_EMBED_ID_SET_UP);

    }

    private void checkEntryPointsScreenSetup(boolean retry, int index) {

        if (CustomerGlu.screenList.size() > 0) {
            isScreenListHasData = true;
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_SCREENNAME_SET_UP, true);
            updateListView(getIndex(ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_SCREEN_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_SCREENNAME_SET_UP, false);
            updateListView(getIndex(ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_TYPE.ADVANCED), ENTRY_POINTS_SCREEN_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        retryOrPostData(retry, ENTRY_POINTS_BANNER_ID_SET_UP);


    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateListView(int index, String testName, boolean result, CGConstants.TEST_TYPE test_type) {
        if (test_type.equals(CGConstants.TEST_TYPE.BASIC)) {
            if (result) {
                basicClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.SUCCESS, basicClientTestList.get(index).viewType, basicClientTestList.get(index).getUrl()));
            } else {
                basicClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.FAILURE, basicClientTestList.get(index).viewType, basicClientTestList.get(index).getUrl()));
            }
            basicTestingAdapter.notifyDataSetChanged();
        } else {
            if (result) {
                advancedClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.SUCCESS, advancedClientTestList.get(index).viewType, advancedClientTestList.get(index).getUrl()));
            } else {
                advancedClientTestList.set(index, new ClientTestingModel(testName, CGConstants.TEST_STATE.FAILURE, advancedClientTestList.get(index).viewType, advancedClientTestList.get(index).getUrl()));
            }
            advancedTestingAdapter.notifyDataSetChanged();
        }
    }

    private void checkEntryPointsSetup(boolean retry, int testIndex) {

        if (isBannerListHasData || isScreenListHasData || isEmbedListHasData) {
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_SET_UP, true);
            updateListView(testIndex, CGConstants.ENTRY_POINTS_SET_UP, true, CGConstants.TEST_TYPE.ADVANCED);
        } else {
            addTestResults(CGConstants.TEST_NAME_ENUM.ENTRYPOINTS_SET_UP, false);
            updateListView(testIndex, CGConstants.ENTRY_POINTS_SET_UP, false, CGConstants.TEST_TYPE.ADVANCED);
        }
        postClientTestingData();

    }


    private void checkOneLinkHandling(boolean retry, int testIndex) {
        String deepLinkUrl = CustomerGlu.clientDeeplinkUrl;
        if (deepLinkUrl.startsWith("http") && deepLinkUrl.startsWith("https")) {
            Uri uri = Uri.parse(deepLinkUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
        showAlertDialog("Are you redirecting to CG Screen?", testIndex, retry, ONE_LINK_HANDLING, CGConstants.ENTRY_POINTS_SCREEN_SET_UP, CGConstants.TEST_TYPE.ADVANCED, CGConstants.TEST_NAME_ENUM.ONELINK_HANDLING);

    }

    private void checkNudgeHandling(boolean retry, int testIndex) {
        String token = "Bearer " + Prefs.getEncKey(getApplicationContext(), CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN);
        String userId = Prefs.getEncKey(getApplicationContext(), ENCRYPTED_CUSTOMERGLU_USER_ID);
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        Comman.getApiToken().clientNudgeTest(token, userData).enqueue(new Callback<RewardModel>() {
            @Override
            public void onResponse(Call<RewardModel> call, Response<RewardModel> response) {
                if (response.code() == 200 || response.code() == 201) {
                    if (response.body() != null) {
                        if (response.body().success != null && response.body().success.equalsIgnoreCase("true")) {

                            showAlertDialog("Did you see nudge?", testIndex, retry, CGConstants.NUDGE_HANDLING, ONE_LINK_HANDLING, CGConstants.TEST_TYPE.ADVANCED, CGConstants.TEST_NAME_ENUM.NUDGE_HANDLING);
                        }
                    }
                } else {
                    showAlertDialog("Did you see nudge?", testIndex, retry, CGConstants.NUDGE_HANDLING, ONE_LINK_HANDLING, CGConstants.TEST_TYPE.ADVANCED, CGConstants.TEST_NAME_ENUM.NUDGE_HANDLING);

                }
            }

            @Override
            public void onFailure(Call<RewardModel> call, Throwable t) {
                showAlertDialog("Did you see nudge?", testIndex, retry, ONE_LINK_HANDLING, ONE_LINK_HANDLING, CGConstants.TEST_TYPE.ADVANCED, CGConstants.TEST_NAME_ENUM.NUDGE_HANDLING);
            }
        });

    }

    private void checkCallBackHandling(boolean retry, int testIndex) {
        try {

            Dialog dialog = new Dialog(this);

            dialog.setContentView(R.layout.callback_dialog_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

            Button callback_btn = dialog.findViewById(R.id.callback_btn);
            TextView deeplink_text = dialog.findViewById(R.id.deep_link_txt);
            deeplink_text.setText("Following button contains " + CustomerGlu.callbackConfigurationUrl);
            callback_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    try {
                        JSONObject dataObject = new JSONObject();
                        dataObject.put("deepLink", CustomerGlu.callbackConfigurationUrl);

                        Intent intent = new Intent("CUSTOMERGLU_DEEPLINK_EVENT");
                        intent.putExtra("data", dataObject.toString());
                        sendBroadcast(intent);
                        showAlertDialog("Did you receive callback?", testIndex, retry, CALLBACK_HANDLING, FIREBASE_PRIVATE_SEVER_KEY, CGConstants.TEST_TYPE.BASIC, CGConstants.TEST_NAME_ENUM.CALLBACK_HANDLING);

                    } catch (Exception e) {
                        printErrorLogs("" + e);
                    }
                }
            });

            dialog.show();

        } catch (Exception e) {
            printErrorLogs("" + e);
        }

    }


    private void showAlertDialog(String message, int testIndex, boolean retry, String testName, String nextTest, CGConstants.TEST_TYPE testType, CGConstants.TEST_NAME_ENUM testNameEnum) {
        handler = new Handler(Looper.getMainLooper());
        runnable = () -> {
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
                    updateListView(getIndex(testName, CGConstants.TEST_TYPE.BASIC), testName, true, CGConstants.TEST_TYPE.BASIC);
                    addTestResults(testNameEnum, true);
                    retryOrPostData(retry, nextTest);

                } else {
                    updateListView(getIndex(testName, CGConstants.TEST_TYPE.ADVANCED), testName, true, CGConstants.TEST_TYPE.ADVANCED);
                    addTestResults(testNameEnum, true);
                    retryOrPostData(retry, nextTest);

                }
                dialog.cancel();

            });

            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                // If user click no then dialog box is canceled.
                if (testType.equals(CGConstants.TEST_TYPE.BASIC)) {
                    updateListView(testIndex, testName, false, CGConstants.TEST_TYPE.BASIC);
                    addTestResults(testNameEnum, false);
                    retryOrPostData(retry, nextTest);

                } else {
                    updateListView(testIndex, testName, false, CGConstants.TEST_TYPE.ADVANCED);
                    addTestResults(testNameEnum, false);
                    retryOrPostData(retry, nextTest);

                }
                dialog.cancel();
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
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
        };
        handler.postDelayed(runnable
                , 3000);

    }


    private void checkUserRegistered(boolean retry, int retryIndex) {
        String token = Prefs.getEncKey(getApplicationContext(), CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN);
        addTestResults(CGConstants.TEST_NAME_ENUM.USER_REGISTERED, CustomerGlu.isInitialized);

        updateListView(retryIndex, CGConstants.USER_REGISTERED, !token.isEmpty(), CGConstants.TEST_TYPE.BASIC);
        retryOrPostData(retry, CALLBACK_HANDLING);

    }

    private void checkSDKInitialised(boolean retry, int retryIndex) {

        updateListView(retryIndex, CGConstants.SDK_INITIALISED, CustomerGlu.isInitialized, CGConstants.TEST_TYPE.BASIC);

        addTestResults(CGConstants.TEST_NAME_ENUM.SDK_INITIALIZED, CustomerGlu.isInitialized);

        retryOrPostData(retry, USER_REGISTERED);
    }

    private void retryOrPostData(boolean retry, String nextTestCase) {
        if (!retry) {
            runTestCases(false, nextTestCase);
        } else {
            postClientTestingData();
        }
    }

    private void findViews() {
        back = findViewById(R.id.back);
        pg = findViewById(R.id.pg);
        testResultList = new ArrayList<>();
        recyclerView = findViewById(R.id.testingRecyclerView);
        advanceRecyclerView = findViewById(R.id.advanceTestingRecyclerView);
        basicTestingAdapter = new ClientTestAdapter(getApplicationContext(), basicClientTestList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(basicTestingAdapter);
        // advance List Adapter
        advancedTestingAdapter = new ClientTestAdapter(getApplicationContext(), advancedClientTestList);
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

    private void getNudgeConfigurationDetails() {
        String writeKey = CustomerGlu.getWriteKey(getApplicationContext());
        String userId = Prefs.getEncKey(getApplicationContext(), ENCRYPTED_CUSTOMERGLU_USER_ID);
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        Comman.getApiToken().checkFirebaseConfiguration(writeKey, userData).enqueue(new Callback<ClientTestNotificationModel>() {
            @Override
            public void onResponse(Call<ClientTestNotificationModel> call, Response<ClientTestNotificationModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getSuccess() != null && response.body().getSuccess()) {
                        clientTestNotificationModel = response.body();
                        if (response.body().getData().getApnsDeviceToken() != null && response.body().getData().getApnsDeviceToken()) {
                            addTestResults(CGConstants.TEST_NAME_ENUM.APNS_TOKEN_SHARED, true);
                        } else {
                            addTestResults(CGConstants.TEST_NAME_ENUM.APNS_TOKEN_SHARED, false);

                        }
                        if (response.body().getData().getPrivateKeyApns() != null && response.body().getData().getPrivateKeyApns()) {
                            addTestResults(CGConstants.TEST_NAME_ENUM.APNS_PRIVATE_KEY, true);

                        } else {
                            addTestResults(CGConstants.TEST_NAME_ENUM.APNS_PRIVATE_KEY, false);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ClientTestNotificationModel> call, Throwable t) {

            }
        });
    }

    private void addTestResults(CGConstants.TEST_NAME_ENUM testName, boolean result) {
        // Check if test is already added in List if yes remove old result from list
        if (testResultList.size() > 0) {
            boolean isPresent = false;
            int removeIndex = 0;
            for (int i = 0; i < testResultList.size(); i++) {
                if (testResultList.get(i).getName().equals(testName)) {
                    isPresent = true;
                    removeIndex = i;
                }
            }
            if (isPresent) {
                testResultList.remove(removeIndex);
            }
        }
        String status = "FAILURE";
        if (result) {
            status = "SUCCESS";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CGConstants.DATE_FORMAT_PATTERN, Locale.getDefault());
        String timeStamp = sdf.format(new Date());
        testResultList.add(new ClientTestPostDataModel.TestData(testName, status, timeStamp));
    }

    private void postClientTestingData() {
        ClientTestPostDataModel clientTestPostDataModel = new ClientTestPostDataModel();
        String writeKey = CustomerGlu.getWriteKey(getApplicationContext());
        String userId = Prefs.getEncKey(getApplicationContext(), ENCRYPTED_CUSTOMERGLU_USER_ID);
        PackageInfo pInfo = null;
        try {
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            String os_version = String.valueOf(Build.VERSION.RELEASE);
            clientTestPostDataModel.setApp_version(version);
            clientTestPostDataModel.setOs_version(os_version);
            clientTestPostDataModel.setPlatform("android");
            clientTestPostDataModel.setCg_sdk_platform(CustomerGlu.cg_app_platform);
            clientTestPostDataModel.setCg_sdk_version(CustomerGlu.cg_sdk_version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        clientTestPostDataModel.setUser_id(userId);
        clientTestPostDataModel.setModel(Build.MODEL);
        clientTestPostDataModel.setManufacturer(Build.MANUFACTURER);
        clientTestPostDataModel.setData(testResultList);
        Comman.getApiToken().postClientTestingData(writeKey, clientTestPostDataModel).enqueue(new Callback<ClientTestNotificationModel>() {
            @Override
            public void onResponse(Call<ClientTestNotificationModel> call, Response<ClientTestNotificationModel> response) {

                if (response.body() != null && response.body().getSuccess() != null) {
                    printDebugLogs("Test Completed");
                }
            }

            @Override
            public void onFailure(Call<ClientTestNotificationModel> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();

    }


}
