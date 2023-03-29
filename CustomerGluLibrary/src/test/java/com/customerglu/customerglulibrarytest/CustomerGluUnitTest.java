package com.customerglu.customerglulibrarytest;

import static org.junit.Assert.assertEquals;

import android.app.Application;
import android.net.Uri;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.DataListner;
import com.customerglu.sdk.Interface.RewardInterface;
import com.customerglu.sdk.Modal.RegisterModal;
import com.customerglu.sdk.Modal.RewardModel;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.Utils.URLHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;


@RunWith(RobolectricTestRunner.class)

public class CustomerGluUnitTest {

    CustomerGlu customerGlu;
    MockWebServer mockWebServer;
    RegisterModal demo_model;


    public Application getApplication() {
        return GetApplicationContext.getApplication();
    }

    @Before
    public void setUp() {

        try {
            System.setProperty("javax.net.ssl.trustStoreType", "JKS");
            Prefs.putEncKey(getApplication(), CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ0ZXN0dXNlci0xOSIsImdsdUlkIjoiOWZlYTk2ZWItZWQ3OC00NDk2LWI1OGUtZjk5ZDc4ODNlZjcwIiwiY2xpZW50IjoiODRhY2YyYWMtYjJlMC00OTI3LTg2NTMtY2JhMmI4MzgxNmMyIiwiZGV2aWNlSWQiOiJkZXZpY2ViIiwiZGV2aWNlVHlwZSI6ImFuZHJvaWQiLCJpYXQiOjE2NDI0MTUyODMsImV4cCI6MTY3Mzk1MTI4M30.j1pV_cB04ZGmm5BiUhCjJo-EXHSwaw3c9Wp-gbfS6HY");
            URLHelper.BaseUrl = "http://localhost:8080/";
            mockWebServer = new MockWebServer();
            mockWebServer.start(8080);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @After
    public void TearDown() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test

    public void get_customerGlu_instance() {
        customerGlu = CustomerGlu.getInstance();
    }

    @Test
    public void sdk_enable() {
        CustomerGlu.getInstance().disableGluSdk(false);
        assertEquals(false, CustomerGlu.sdk_disable);
    }


    @Test
    public void is_registration_successful() {
        final CountDownLatch signal = new CountDownLatch(1);
        try {
            System.out.println("sdfdsdsds");

            mockWebServer.enqueue(new MockResponse().setBody(CGConstants.register_response));

            mockWebServer.url("user/v1/user/sdk?token=true");
            HashMap<String, Object> param = new HashMap<>();
            param.put("userId", "testuser-19");
            demo_model = null;

            CustomerGlu.getInstance().registerDevice(getApplication(), param, new DataListner() {
                @Override
                public void onSuccess(RegisterModal registerModal) {
                    System.out.println("dvds");
                    demo_model = registerModal;

                    //    assertEquals("true",registerModal.success);
                    System.out.println("dvds");
                    signal.countDown();

                }

                @Override
                public void onFail(String message) {
                    signal.countDown();

                }
            });
            try {
                signal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sdfdsdsds");

            if (demo_model == null || !demo_model.success.equalsIgnoreCase("true")) {
                assertEquals("true", "false");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void retrieve_data_by_filter_test_case() {
        final CountDownLatch signal = new CountDownLatch(1);
        try {
            System.out.println("sdfdsdsds");

            mockWebServer.enqueue(new MockResponse().setBody(CGConstants.load_campaign_response));

            mockWebServer.url("reward/v1.1/user");
            HashMap<String, Object> param = new HashMap<>();
            param.put("type", "quiz");

            CustomerGlu.getInstance().retrieveDataByFilter(getApplication(), param, new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    System.out.println(rewardModel.success);
                    assertEquals("true", rewardModel.success);
                    signal.countDown();
                }

                @Override
                public void onFailure(String message) {
                    signal.countDown();

                }
            });

            try {
                signal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sdfdsdsds");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Retrieve_data_success_test_case() {
        final CountDownLatch signal = new CountDownLatch(1);
        try {
            System.out.println("sdfdsdsds");

            mockWebServer.enqueue(new MockResponse().setBody(CGConstants.load_campaign_response));

            mockWebServer.url("reward/v1.1/user");

            CustomerGlu.getInstance().retrieveData(getApplication(), new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    System.out.println(rewardModel.success);
                    assertEquals("true", rewardModel.success);
                    signal.countDown();
                }

                @Override
                public void onFailure(String message) {
                    signal.countDown();

                }
            });

            try {
                signal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sdfdsdsds");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get_refeer_id_from_deep_link_event() {
        String id = "himanshu";
        String url = "https://customerglu.com/?userId=himanshu";
        Uri deeplink = Uri.parse(url);
        String user_id = CustomerGlu.getInstance().getReferralId(deeplink);
        assertEquals(id, user_id);

    }

    @Test
    public void test_setDefaultBannerImage_Method() {
        String banner_url = "https://bannerimage.com/img-2";
        CustomerGlu.getInstance().setDefaultBannerImage(getApplication(), banner_url);

        assertEquals(banner_url, CustomerGlu.configure_banner_default_url);
    }

    @Test
    public void test_configureLoaderColour_Method() {
        String color = "#FF0000";
        CustomerGlu.getInstance().configureLoaderColour(getApplication(), color);

        assertEquals(color, CustomerGlu.configure_loader_color);
    }


//    @Test
//    public void send_crash_reports_to_customer_glu()
//    {
//        try {
//
//            mockWebServer.enqueue(new MockResponse().setBody(Constants.register_response));
//
//
//            mockWebServer.url("api/v1/report");
//
//
//            CustomerGlu.getInstance().sendCrashAnalytics(getApplication(),"Error:");
//
//
//            System.out.println("sdfdsdsds");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void send_events()
//    {
//        mockWebServer.enqueue(new MockResponse().setBody(Constants.load_campaign_response));
//        mockWebServer.url("https://stream.customerglu.com/v3/server");
//        Map<String,Object> event_properties = new HashMap<>();
//        event_properties.put("state","1");
//        CustomerGlu.getInstance().sendEvent(getApplication(),"Purchase",event_properties);
//    }
//
//    @Test
//    public void set_default_image_banner()
//    {
//        CustomerGlu.getInstance().setDefaultBannerImage(getApplication(),"f");
//    }
//    @Test
//    public void set_default_loader_color()
//    {
//        CustomerGlu.getInstance().configureLoaderColour(getApplication(),"#000000");
//    }
//    @Test
//
//    public void open_wallet_case()
//    {
//        CustomerGlu.getInstance().openWallet(getApplication());
//    }
//
//    @Test
//
//    public void load_all_campaigns_case()
//    {
//        CustomerGlu.getInstance().loadAllCampaigns(getApplication());
//    }
//
//    @Test
//
//    public void load_campaign_by_id_case()
//    {
//        CustomerGlu.getInstance().loadCampaignById(getApplication(),"*73bfj-dfbsh03fn-ndfsfb0d");
//    }
//    @Test
//
//    public void load_campaign_by_filter_case()
//    {
//        Map<String,Object> param = new HashMap<>();
//        param.put("type","quiz");
//        param.put("status","pristine");
//        CustomerGlu.getInstance().loadCampaignsByFilter(getApplication(),param);
//    }


    //
//
    @Test
    public void get_customer_glu_push_notifications() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\n" +
                    "    \"type\": \"CustomerGlu\",\n" +
                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
                    "    \"body\": \"ok cool\",\n" +
                    "    \"glu_message_type\":\"in-app\",\n" +
                    "    \"page_type\":\"full-default\",\n" +
                    "    \"image\": \"\",\n" +
                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
                    "}");

            //   CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(),j, R.drawable.ij);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get_customer_glu_in_app_notifications_with_full_screen() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\n" +
                    "    \"type\": \"CustomerGlu\",\n" +
                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
                    "    \"body\": \"ok cool\",\n" +
                    "    \"glu_message_type\":\"in-app\",\n" +
                    "    \"page_type\":\"full-default\",\n" +
                    "    \"image\": \"\",\n" +
                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
                    "}");

            CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(), j, R.drawable.ij);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get_customer_glu_in_app_notifications_with_middle_dialog() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\n" +
                    "    \"type\": \"CustomerGlu\",\n" +
                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
                    "    \"body\": \"ok cool\",\n" +
                    "    \"glu_message_type\":\"in-app\",\n" +
                    "    \"page_type\":\"middle-default\",\n" +
                    "    \"image\": \"\",\n" +
                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
                    "}");

            CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(), j, R.drawable.ij);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get_customer_glu_in_app_notifications_with_bottom_sheet() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\n" +
                    "    \"type\": \"CustomerGlu\",\n" +
                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
                    "    \"body\": \"ok cool\",\n" +
                    "    \"glu_message_type\":\"in-app\",\n" +
                    "    \"page_type\":\"bottom-default\",\n" +
                    "    \"image\": \"\",\n" +
                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
                    "}");

            CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(), j, R.drawable.ij);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get_customer_glu_in_app_notifications_with_bottom_slider() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\n" +
                    "    \"type\": \"CustomerGlu\",\n" +
                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
                    "    \"body\": \"ok cool\",\n" +
                    "    \"glu_message_type\":\"in-app\",\n" +
                    "    \"page_type\":\"bottom-slider\",\n" +
                    "    \"image\": \"\",\n" +
                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
                    "}");

            CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(), j, R.drawable.ij);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void get_customer_glu_in_app_notifications_with_opacity() {
        JSONObject j = null;
        try {
            j = new JSONObject("{\n" +
                    "    \"type\": \"CustomerGlu\",\n" +
                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
                    "    \"body\": \"ok cool\",\n" +
                    "    \"glu_message_type\":\"in-app\",\n" +
                    "    \"page_type\":\"full-default\",\n" +
                    "    \"image\": \"\",\n" +
                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
                    "}");

            CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(), j, R.drawable.ij, 0.6);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
//
//    @Test
//    public void get_customer_glu_in_app_notifications_with_image()
//    {
//        JSONObject j = null;
//        try {
//            j = new JSONObject("{\n" +
//                    "    \"type\": \"CustomerGlu\",\n" +
//                    "    \"title\": \"Congrats! You are 3 steps away\",\n" +
//                    "    \"body\": \"ok cool\",\n" +
//                    "    \"glu_message_type\":\"push\",\n" +
//                    "    \"page_type\":\"full-default\",\n" +
//                    "    \"image\": \"https://assets.customerglu.com\",\n" +
//                    "    \"nudge_url\": \"https://dbtailwi34eql.cloudfront.net/reward/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJnbHV0ZXN0LWg5IiwiZ2x1SWQiOiIxZWY2YTJlNS00YThhLTRiMzEtYjI0ZC1mZjEzNmI2Nzk2ZDQiLCJjbGllbnQiOiI4NGFjZjJhYy1iMmUwLTQ5MjctODY1My1jYmEyYjgzODE2YzIiLCJkZXZpY2VJZCI6ImRldmljZWIiLCJkZXZpY2VUeXBlIjoiYW5kcm9pZCIsImlhdCI6MTYzOTk5ODkxMywiZXhwIjoxNjcxNTM0OTEzfQ.k17Xhzq3set_BJo7hN_92xzSckaaBr9NFoaq8CHFJs8&rewardUserId=22850f99-70c9-4df9-aefa-f04aef7b4024\"\n" +
//                    "}");
//
//         //   CustomerGlu.getInstance().displayCustomerGluNotification(getApplication(),j, R.drawable.ij,0.6);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//
//    @Test
//    public void is_clear_glu_data()
//    {
//        CustomerGlu.getInstance().clearGluData(getApplication());
//    }
//
//
//    @Test
//    public void is_open_wallet()
//    {
//
//        BottomSheet bottomSheet = new BottomSheet();
//
//
//    }

}
