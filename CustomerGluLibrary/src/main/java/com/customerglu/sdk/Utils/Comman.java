package com.customerglu.sdk.Utils;

import static com.customerglu.sdk.Utils.CGConstants.ERROR_URL;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebResourceResponse;

import com.customerglu.sdk.ApiServices.ApiClients;
import com.customerglu.sdk.ApiServices.ApiInterface;
import com.customerglu.sdk.CustomerGlu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Comman {

    static String TAG = "CUSTOMERGLU";
    private final static int GCM_IV_LENGTH = 12;
    private final static int GCM_TAG_LENGTH = 16;

    private enum Alias {
        RSA("CIPHER_RSA"),
        AES("CIPHER_AES");

        String value;

        Alias(String value) {
            this.value = value;
        }
    }

    public static ApiInterface getApiToken() {
        return ApiClients.getClient_token().create(ApiInterface.class);
    }

    public static ApiInterface getApiStringBuilder() {
        return ApiClients.getStringBuilder().create(ApiInterface.class);
    }

    public static String storeZip(Context context) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File zip = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(zip, "CustomerGlu");
        return file.getPath();
    }


    public static String getZip(Context context, String name) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File zip = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(zip, name);
        return file.getPath();
    }

    @TargetApi(21)
    public static WebResourceResponse build() {
        final SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy kk:mm:ss", Locale.US);
        Date date = new Date();
        final String dateString = formatter.format(date);

        Map<String, String> headers = new HashMap<String, String>() {{
            put("Connection", "close");
            put("Content-Type", "text/plain");
            put("Date", dateString + " GMT");
            put("Access-Control-Allow-Origin", "*");
            put("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            put("Access-Control-Max-Age", "600");
            put("Access-Control-Allow-Credentials", "true");
            put("Access-Control-Allow-Headers", "accept, authorization, Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,X-Glu-Auth");
            put("Via", "1.1 vegur");
        }};

        return new WebResourceResponse("text/plain", "UTF-8", 200, "OK", headers, null);
    }

    public static void printDebugLogs(String message) {
        if (CustomerGlu.debuggingMode) {
            Log.d(TAG, message);
        }
    }

    public static void printErrorLogs(String message) {
        if (CustomerGlu.debuggingMode) {
            Log.e(TAG, message);
        }
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkAnonymousUser(Context context) {
        if (!Prefs.getEncKey(context, CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN).isEmpty()) {
            try {
                String anonymous = "";
                String userId = "";
                String JWTEncoded = Prefs.getEncKey(context, CGConstants.ENCRYPTED_CUSTOMERGLU_TOKEN);
                String[] split = JWTEncoded.split("\\.");
                JSONObject jsonObject = new JSONObject(getJson(split[1]));
                if (jsonObject.has("anonymousId")) {
                    anonymous = jsonObject.getString("anonymousId");
                }
                if (jsonObject.has("userId")) {
                    userId = jsonObject.getString("userId");
                }
                if (!userId.isEmpty()) {
                    if (!userId.equalsIgnoreCase(anonymous)) {
                        return true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static boolean
    isValidURL(String url) {
        // Regex to check valid URL


        // Compile the ReGex
        Pattern p = Pattern.compile(CGConstants.URL_REGEX);

        // If the string is empty
        // return false
        if (url == null) {
            return false;
        }

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(url);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    public static boolean isValidColor(String str) {
        // Regex to check valid hexadecimal color code.
        String regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given string
        // and regular expression.
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }
//    public static boolean isValidColor(final String colorCode) {
//        final String HEX_WEBCOLOR_PATTERN
//                = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
//
//        final Pattern pattern = Pattern.compile(HEX_WEBCOLOR_PATTERN);
//        Matcher matcher = pattern.matcher(colorCode);
//        return matcher.matches();
//    }

    public static String
    validateURL(String url) {
        // Regex to check valid URL


        // Compile the ReGex
        if (url != null) {

            try {
                URL myUrl = new URL(url);
                String host = myUrl.getHost();

                if (host.endsWith("customerglu.com")) {
                    printDebugLogs("valid url");
                    printDebugLogs(url);
                    return url;
                }
                if (CustomerGlu.whiteListDomain != null && CustomerGlu.whiteListDomain.size() > 0) {
                    for (int i = 0; i < CustomerGlu.whiteListDomain.size(); i++) {
                        if (CustomerGlu.whiteListDomain.get(i) != null) {
                            if (!CustomerGlu.whiteListDomain.get(i).isEmpty() && host.endsWith(CustomerGlu.whiteListDomain.get(i))) {
                                return url;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        int errorCode = CustomerGlu.errorCodeForDomain;
        String errorMessage = CustomerGlu.errorMessageForDomain;
        return ERROR_URL;

    }

    public static String arrayListStringConvertor(ArrayList<String> data) {

        StringBuffer sb = new StringBuffer();

        for (String s : data) {
            sb.append(s);
            sb.append(" ");
        }

        return sb.toString();
    }

    private static SecretKey generateAESSecretKey(String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(bytes, 0, bytes.length);
            byte[] key = messageDigest.digest();
            SecretKey spec = new SecretKeySpec(key, "AES");
            return spec;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }


    public static void handleDeepLinkUri(Uri uri) {

        if (uri != null && uri.getHost() != null && !uri.getHost().isEmpty() && uri.getHost().contains(CGConstants.CGLU)) {
            String url = uri.toString();
            String[] urltype = url.split("/");
            String deepLinkType = urltype[urltype.length - 2];
            String deepLinkKey = url.substring(url.lastIndexOf("/")).replace("/", "");
            printDebugLogs(url);
            printDebugLogs(deepLinkKey);
            printDebugLogs(deepLinkType);
            CustomerGlu.getInstance().validateDeepLinkKey(deepLinkKey, deepLinkType);
        }
    }


    public static String aesEncrypt(Context context, String privateString) {
        return CryptoreUtils.getCryptoreUtils().encryptRSA(privateString);
    }

    public static String aesDecrypt(Context context, String encrypted) {
        return CryptoreUtils.getCryptoreUtils().decryptRSA(encrypted);
    }


    public static byte[] getSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
