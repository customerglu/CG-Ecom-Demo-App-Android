package com.customerglu.sdk.Modal;

import java.util.ArrayList;

public class CGConfigData {
    CGMobileConfig mobile;

    public CGConfigData(CGMobileConfig mobile) {
        this.mobile = mobile;
    }

    public CGMobileConfig getMobile() {
        return mobile;
    }

    public void setMobile(CGMobileConfig mobile) {
        this.mobile = mobile;
    }

    public static class CGMobileConfig {
        Boolean disableSdk;
        Boolean enableAnalytics;
        Boolean enableEntryPoints;
        Boolean allowUserRegistration;
        Boolean enableSentry = false;
        Boolean forceUserRegistration;
        Boolean enableDarkMode;
        Boolean listenToSystemDarkLightMode;
        Boolean isDiagnosticsEnabled;
        Boolean isMetricsEnabled;
        Boolean isCrashLoggingEnabled;
        String loaderColor;
        String lightBackground;
        String darkBackground;
        String secretKey;
        String loadScreenColor;
        String androidStatusBarColor;
        String androidStatusBarLightColor;
        String androidStatusBarDarkColor;
        String errorMessageForDomain;
        String callbackConfigurationUrl;
        String deeplinkUrl;
        LoaderConfig loaderConfig;
        SentryDsn sentryDsn;
        PlatformList activityIdList;
        PlatformList bannerIds;
        PlatformList embedIds;
        Integer errorCodeForDomain;
        ArrayList<String> whiteListedDomains;
        ArrayList<String> testUserIds;
        Boolean enableMqtt;
        Boolean allowAnonymousRegistration;
        Integer allowedRetryCount;

        public CGMobileConfig(Boolean disableSdk, Integer allowedRetryCount, Boolean allowAnonymousRegistration, Boolean enableAnalytics, Boolean enableEntryPoints, Boolean allowUserRegistration, Boolean enableSentry, Boolean enableMqtt, Boolean forceUserRegistration, Boolean enableDarkMode, Boolean listenToSystemDarkLightMode, Boolean isDiagnosticsEnabled, Boolean isMetricsEnabled, Boolean isCrashLoggingEnabled, String loaderColor, String lightBackground, String darkBackground, String secretKey, String loadScreenColor, String androidStatusBarColor, String androidStatusBarLightColor, String androidStatusBarDarkColor, String errorMessageForDomain, String callbackConfigurationUrl, String deeplinkUrl, LoaderConfig loaderConfig, SentryDsn sentryDsn, PlatformList activityIdList, PlatformList bannerIds, PlatformList embedIds, Integer errorCodeForDomain, ArrayList<String> whiteListedDomains, ArrayList<String> testUserIds) {
            this.disableSdk = disableSdk;
            this.enableAnalytics = enableAnalytics;
            this.enableEntryPoints = enableEntryPoints;
            this.allowUserRegistration = allowUserRegistration;
            this.enableSentry = enableSentry;
            this.enableMqtt = enableMqtt;
            this.forceUserRegistration = forceUserRegistration;
            this.enableDarkMode = enableDarkMode;
            this.listenToSystemDarkLightMode = listenToSystemDarkLightMode;
            this.isDiagnosticsEnabled = isDiagnosticsEnabled;
            this.isMetricsEnabled = isMetricsEnabled;
            this.isCrashLoggingEnabled = isCrashLoggingEnabled;
            this.loaderColor = loaderColor;
            this.lightBackground = lightBackground;
            this.darkBackground = darkBackground;
            this.secretKey = secretKey;
            this.loadScreenColor = loadScreenColor;
            this.androidStatusBarColor = androidStatusBarColor;
            this.androidStatusBarLightColor = androidStatusBarLightColor;
            this.androidStatusBarDarkColor = androidStatusBarDarkColor;
            this.errorMessageForDomain = errorMessageForDomain;
            this.callbackConfigurationUrl = callbackConfigurationUrl;
            this.deeplinkUrl = deeplinkUrl;
            this.loaderConfig = loaderConfig;
            this.sentryDsn = sentryDsn;
            this.activityIdList = activityIdList;
            this.allowAnonymousRegistration = allowAnonymousRegistration;
            this.bannerIds = bannerIds;
            this.embedIds = embedIds;
            this.errorCodeForDomain = errorCodeForDomain;
            this.whiteListedDomains = whiteListedDomains;
            this.testUserIds = testUserIds;
            this.allowedRetryCount = allowedRetryCount;
        }


        public Integer getAllowedRetryCount() {
            return allowedRetryCount;
        }

        public void setAllowedRetryCount(Integer allowedRetryCount) {
            this.allowedRetryCount = allowedRetryCount;
        }

        public Boolean getAllowAnonymousRegistration() {
            return allowAnonymousRegistration;
        }

        public void setAllowAnonymousRegistration(Boolean allowAnonymousRegistration) {
            this.allowAnonymousRegistration = allowAnonymousRegistration;
        }

        public Boolean getEnableMqtt() {
            return enableMqtt;
        }

        public void setEnableMqtt(Boolean enableMqtt) {
            this.enableMqtt = enableMqtt;
        }

        public String getCallbackConfigurationUrl() {
            return callbackConfigurationUrl;
        }

        public void setCallbackConfigurationUrl(String callbackConfigurationUrl) {
            this.callbackConfigurationUrl = callbackConfigurationUrl;
        }

        public String getDeeplinkUrl() {
            return deeplinkUrl;
        }

        public void setDeeplinkUrl(String deeplinkUrl) {
            this.deeplinkUrl = deeplinkUrl;
        }

        public ArrayList<String> getTestUserIds() {
            return testUserIds;
        }

        public void setTestUserIds(ArrayList<String> testUserIds) {
            this.testUserIds = testUserIds;
        }

        public PlatformList getActivityIdList() {
            return activityIdList;
        }

        public void setActivityIdList(PlatformList activityIdList) {
            this.activityIdList = activityIdList;
        }

        public PlatformList getBannerIds() {
            return bannerIds;
        }

        public void setBannerIds(PlatformList bannerIds) {
            this.bannerIds = bannerIds;
        }

        public PlatformList getEmbedIds() {
            return embedIds;
        }

        public void setEmbedIds(PlatformList embedIds) {
            this.embedIds = embedIds;
        }

        public Boolean getDiagnosticsEnabled() {
            return isDiagnosticsEnabled;
        }

        public void setDiagnosticsEnabled(Boolean diagnosticsEnabled) {
            isDiagnosticsEnabled = diagnosticsEnabled;
        }

        public Boolean getMetricsEnabled() {
            return isMetricsEnabled;
        }

        public void setMetricsEnabled(Boolean metricsEnabled) {
            isMetricsEnabled = metricsEnabled;
        }

        public Boolean getCrashLoggingEnabled() {
            return isCrashLoggingEnabled;
        }

        public void setCrashLoggingEnabled(Boolean crashLoggingEnabled) {
            isCrashLoggingEnabled = crashLoggingEnabled;
        }

        public SentryDsn getSentryDsn() {
            return sentryDsn;
        }

        public void setSentryDsn(SentryDsn sentryDsn) {
            this.sentryDsn = sentryDsn;
        }

        public String getAndroidStatusBarLightColor() {
            return androidStatusBarLightColor;
        }

        public void setAndroidStatusBarLightColor(String androidStatusBarLightColor) {
            this.androidStatusBarLightColor = androidStatusBarLightColor;
        }

        public String getAndroidStatusBarDarkColor() {
            return androidStatusBarDarkColor;
        }

        public void setAndroidStatusBarDarkColor(String androidStatusBarDarkColor) {
            this.androidStatusBarDarkColor = androidStatusBarDarkColor;
        }

        public LoaderConfig getLoaderConfig() {
            return loaderConfig;
        }

        public void setLoaderConfig(LoaderConfig loaderConfig) {
            this.loaderConfig = loaderConfig;
        }

        public Boolean getEnableDarkMode() {
            return enableDarkMode;
        }

        public void setEnableDarkMode(Boolean enableDarkMode) {
            this.enableDarkMode = enableDarkMode;
        }

        public Boolean getListenToSystemDarkLightMode() {
            return listenToSystemDarkLightMode;
        }

        public void setListenToSystemDarkLightMode(Boolean listenToSystemDarkLightMode) {
            this.listenToSystemDarkLightMode = listenToSystemDarkLightMode;
        }

        public String getLightBackground() {
            return lightBackground;
        }

        public void setLightBackground(String lightBackground) {
            this.lightBackground = lightBackground;
        }

        public String getDarkBackground() {
            return darkBackground;
        }

        public void setDarkBackground(String darkBackground) {
            this.darkBackground = darkBackground;
        }


        public Boolean getAllowUserRegistration() {
            return allowUserRegistration;
        }

        public void setAllowUserRegistration(Boolean allowUserRegistration) {
            this.allowUserRegistration = allowUserRegistration;
        }

        public Boolean getEnableSentry() {
            return enableSentry;
        }

        public void setEnableSentry(Boolean enableSentry) {
            this.enableSentry = enableSentry;
        }

        public Boolean getForceUserRegistration() {
            return forceUserRegistration;
        }

        public void setForceUserRegistration(Boolean forceUserRegistration) {
            this.forceUserRegistration = forceUserRegistration;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public Boolean getDisableSdk() {
            return disableSdk;
        }

        public void setDisableSdk(Boolean disableSdk) {
            this.disableSdk = disableSdk;
        }

        public Boolean getEnableAnalytics() {
            return enableAnalytics;
        }

        public void setEnableAnalytics(Boolean enableAnalytics) {
            this.enableAnalytics = enableAnalytics;
        }

        public Boolean getEnableEntryPoints() {
            return enableEntryPoints;
        }

        public void setEnableEntryPoints(Boolean enableEntryPoints) {
            this.enableEntryPoints = enableEntryPoints;
        }

        public String getLoaderColor() {
            return loaderColor;
        }

        public void setLoaderColor(String loaderColor) {
            this.loaderColor = loaderColor;
        }

        public String getLoadScreenColor() {
            return loadScreenColor;
        }

        public void setLoadScreenColor(String loadScreenColor) {
            this.loadScreenColor = loadScreenColor;
        }

        public String getAndroidStatusBarColor() {
            return androidStatusBarColor;
        }

        public void setAndroidStatusBarColor(String androidStatusBarColor) {
            this.androidStatusBarColor = androidStatusBarColor;
        }

        public String getErrorMessageForDomain() {
            return errorMessageForDomain;
        }

        public void setErrorMessageForDomain(String errorMessageForDomain) {
            this.errorMessageForDomain = errorMessageForDomain;
        }

        public Integer getErrorCodeForDomain() {
            return errorCodeForDomain;
        }

        public void setErrorCodeForDomain(Integer errorCodeForDomain) {
            this.errorCodeForDomain = errorCodeForDomain;
        }

        public ArrayList<String> getWhiteListedDomains() {
            return whiteListedDomains;
        }

        public void setWhiteListedDomains(ArrayList<String> whiteListedDomains) {
            this.whiteListedDomains = whiteListedDomains;
        }

        public static class LoaderConfig {
            LoaderURL loaderURL;
            LoaderURL embedLoaderURL;

            public LoaderConfig(LoaderURL loaderURL, LoaderURL embedLoaderURL) {
                this.loaderURL = loaderURL;
                this.embedLoaderURL = embedLoaderURL;
            }

            public LoaderURL getLoaderURL() {
                return loaderURL;
            }

            public void setLoaderURL(LoaderURL loaderURL) {
                this.loaderURL = loaderURL;
            }

            public LoaderURL getEmbedLoaderURL() {
                return embedLoaderURL;
            }

            public void setEmbedLoaderURL(LoaderURL embedLoaderURL) {
                this.embedLoaderURL = embedLoaderURL;
            }

            public static class LoaderURL {
                String light;
                String dark;

                public String getLight() {
                    return light;
                }

                public void setLight(String light) {
                    this.light = light;
                }

                public String getDark() {
                    return dark;
                }

                public void setDark(String dark) {
                    this.dark = dark;
                }

                public LoaderURL(String light, String dark) {
                    this.light = light;
                    this.dark = dark;
                }

            }
        }

        public static class SentryDsn {
            String Android;
            String iOS;
            String Flutter;
            String ReactNative;

            public SentryDsn(String android, String iOS, String flutter, String reactNative) {
                Android = android;
                this.iOS = iOS;
                Flutter = flutter;
                ReactNative = reactNative;
            }

            public String getAndroid() {
                return Android;
            }

            public void setAndroid(String android) {
                Android = android;
            }

            public String getiOS() {
                return iOS;
            }

            public void setiOS(String iOS) {
                this.iOS = iOS;
            }

            public String getFlutter() {
                return Flutter;
            }

            public void setFlutter(String flutter) {
                Flutter = flutter;
            }

            public String getReactNative() {
                return ReactNative;
            }

            public void setReactNative(String reactNative) {
                ReactNative = reactNative;
            }
        }

        public static class PlatformList {
            ArrayList<String> android;
            ArrayList<String> ios;

            public PlatformList(ArrayList<String> android, ArrayList<String> ios) {
                this.android = android;
                this.ios = ios;
            }

            public ArrayList<String> getAndroid() {
                return android;
            }

            public void setAndroid(ArrayList<String> android) {
                this.android = android;
            }

            public ArrayList<String> getIos() {
                return ios;
            }

            public void setIos(ArrayList<String> ios) {
                this.ios = ios;
            }
        }
    }

}
