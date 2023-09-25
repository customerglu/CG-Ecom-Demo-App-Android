package com.customerglu.sdk.Utils;

import android.content.Context;

public class SentryHelper {
    private static SentryHelper Instance;

    // Sentry Instance.
    public static synchronized SentryHelper getInstance() {
        if (Instance == null) {
            Instance = new SentryHelper();
        }
        return Instance;
    }

    public void initSentry(Context context, String dsn) {
     /*   if (CustomerGlu.getInstance().isSentryEnabled()) {
            try {
                Gson gson = new Gson();
                SentryAndroid.init(context, options -> {
                    options.setDsn(dsn);
                    options.setBeforeSend((event, hint) -> {
                        if (event.getThrowable() != null
                                && event.getThrowable().getStackTrace().length > 1
                                && event.getThrowable().getStackTrace()[1].toString().contains(BuildConfig.LIBRARY_PACKAGE_NAME)) {
                            if (event.getLevel() == SentryLevel.FATAL) {
                                ArrayList<MetaData> metaData = new ArrayList<>();
                                String stackTrace = gson.toJson(event);
                                metaData.add(new MetaData("stack_trace", stackTrace));
                                CustomerGlu.diagnosticsHelper.sendDiagnosticsReport(CGConstants.CG_CRASH, CGConstants.CG_LOGGING_EVENTS.CRASH, metaData);
                            }

                            return event;
                        }
                        return null;
                    });
                });
                setupSentryContext();
            } catch (Exception e) {

            }
        }*/
    }

    /**
     * @param userId
     * @param clientId
     */
    public void setupUser(String userId, String clientId) {
     /*   if (CustomerGlu.getInstance().isSentryEnabled()) {
            User user = new User();
            user.setId(userId);
            user.setUsername(clientId);
            Sentry.setUser(user);
        }*/
    }


    /**
     * Setup Sentry Context for more info.
     */
    public void setupSentryContext() {
       /* if (CustomerGlu.getInstance().isSentryEnabled()) {
            Sentry.configureScope(scope -> {
                scope.setContexts(CGConstants.CG_PLATFORM_KEY, CGConstants.CG_PLATFORM_VALUE);
                scope.setContexts(CGConstants.CG_SDK_VERSION_KEY, CGConstants.CG_SDK_VERSION_VAL);
            });
        }*/
    }


    /**
     * Logout User from Sentry
     */
    public void logoutSentry() {
      /*  if (CustomerGlu.getInstance().isSentryEnabled()) {
            Sentry.setUser(null);
        }*/
    }


    /**
     * Capture Sentry Exception.
     *
     * @param exception
     */
    public void captureSentryEvent(Exception exception) {
      /*  if (CustomerGlu.getInstance().isSentryEnabled()) {
            try {
                Sentry.captureException(exception);
            } catch (Exception sentryException) {

            }
        }*/
    }

}