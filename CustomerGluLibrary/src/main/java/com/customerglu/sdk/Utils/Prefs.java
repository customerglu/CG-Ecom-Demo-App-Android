package com.customerglu.sdk.Utils;

import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_CAMPAIGN_OBJ;
import static com.customerglu.sdk.Utils.CGConstants.ENCRYPTED_DISMISSED_ENTRY_POINTS;
import static com.customerglu.sdk.Utils.Comman.aesDecrypt;
import static com.customerglu.sdk.Utils.Comman.aesEncrypt;
import static com.customerglu.sdk.Utils.Comman.printDebugLogs;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Prefs {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences encryptedSharedPreferences;
    public static SharedPreferences.Editor editor;

    private static final String DIMISSED_VALUE = "dismiss_entry_points";
    private static String HASH_VALUE = "Campaign_object";
    private static String SHARED_PREFERENCES_SALT = "ENCRYPTION_SALT_KEY";


    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();
    }

    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "");
        return Value;

    }

    public static void putEncKey(Context context, String Key, String Value) {
        try {
            String encKey = Key;
            String encValue = aesEncrypt(context, Value);
            printDebugLogs("encKey " + encKey);
            printDebugLogs("encValue " + encValue);


            sharedPreferences = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(encKey, encValue);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getEncKey(Context contextGetKey, String Key) {
        try {
            String encKey = Key;
            printDebugLogs("encKey get" + encKey);

            sharedPreferences = contextGetKey.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            String Value = sharedPreferences.getString(encKey, "");
            if (Value.isEmpty()) {
                return "";
            }
            String finalValue = aesDecrypt(contextGetKey, Value);
            printDebugLogs("getValue " + finalValue);
            printDebugLogs("EncgetValue " + Value);

            return finalValue;
        } catch (Exception e) {
            printErrorLogs("" + e);
            return "";
        }

    }


//    public static String getOldEncKey(Context contextGetKey, String Key) {
//        try {
//            String encKey = encrypt(contextGetKey, Key);
//            System.out.println("getKey " + encKey);
//
//            sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
//            String Value = sharedPreferences.getString(encKey, "");
//            String finalValue = decrypt(contextGetKey, Value);
//            System.out.println("getoldValue " + finalValue);
//            System.out.println("EncoldgetValue " + Value);
//
//            return finalValue;
//        } catch (Exception e) {
//            printErrorLogs("" + e);
//            return "";
//        }
//
//    }

    public static void removeKey(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
        settings.edit().remove(key).apply();
    }

    public static void putDismissedEntryPoints(Context context, HashMap<String, Integer> Value) {
        try {
            Gson gson = new Gson();
            String json_value = gson.toJson(Value);
            sharedPreferences = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(DIMISSED_VALUE, json_value);
            editor.apply();
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());
        }
    }

    public static HashMap<String, Integer> getDismissedEntryPoints(Context contextGetKey) {
        try {

            sharedPreferences = contextGetKey.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(DIMISSED_VALUE, null);
            // String finalValue = decrypt(contextGetKey, value);

            //  Log.e("CustomerGlu", value);
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            return gson.fromJson(value, type);
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());

        }
        return null;

    }


    public static void putEncDismissedEntryPoints(Context context, HashMap<String, Integer> Value) {
        try {
            Gson gson = new Gson();
            String json_value = gson.toJson(Value);
            String encKey = ENCRYPTED_DISMISSED_ENTRY_POINTS;
            String encValue = aesEncrypt(context, json_value);
            sharedPreferences = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(encKey, encValue);
            editor.apply();
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());
        }
    }

    public static HashMap<String, Integer> getEncDismissedEntryPoints(Context contextGetKey) {
        try {
            String encKey = ENCRYPTED_DISMISSED_ENTRY_POINTS;

            sharedPreferences = contextGetKey.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(encKey, "");

            if (value.isEmpty()) {
                return new HashMap<String, Integer>();
            }
            String finalValue = aesDecrypt(contextGetKey, value);

            //  Log.e("CustomerGlu", value);
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            return gson.fromJson(finalValue, type);
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());

        }
        return null;

    }

//    public static HashMap<String, Integer> getOldEncDismissedEntryPoints(Context contextGetKey) {
//        try {
//            String encKey = encrypt(contextGetKey, ENCRYPTED_DISMISSED_ENTRY_POINTS);
//
//            sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
//            String value = sharedPreferences.getString(encKey, null);
//            String finalValue = decrypt(contextGetKey, value);
//
//            //  Log.e("CustomerGlu", value);
//            Gson gson = new Gson();
//            Type type = new TypeToken<HashMap<String, Integer>>() {
//            }.getType();
//            return gson.fromJson(finalValue, type);
//        } catch (Exception e) {
//            Log.e("Customerglu", e.toString());
//
//        }
//        return null;
//
//    }

    public static void putCampaignIdObject(Context context, HashMap<String, Integer> Value) {
        try {
            Gson gson = new Gson();
            String json_value = gson.toJson(Value);

            sharedPreferences = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(HASH_VALUE, json_value);

            editor.apply();
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());

        }
    }

    public static HashMap<String, Integer> getCampaignIdObject(Context contextGetKey) {
        try {

            sharedPreferences = contextGetKey.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(HASH_VALUE, null);

            //  Log.e("CustomerGlu", value);
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            return gson.fromJson(value, type);
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());

        }
        return null;

    }

    public static void putEncCampaignIdObject(Context context, HashMap<String, Integer> Value) {
        try {
            Gson gson = new Gson();
            String json_value = gson.toJson(Value);
            String encKey = ENCRYPTED_CAMPAIGN_OBJ;
            String encValue = aesEncrypt(context, json_value);
            sharedPreferences = context.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(encKey, encValue);
            editor.apply();
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());

        }
    }

    public static HashMap<String, Integer> getEncCampaignIdObject(Context contextGetKey) {
        try {
            String encKey = ENCRYPTED_CAMPAIGN_OBJ;

            sharedPreferences = contextGetKey.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(encKey, "");
            if (value.isEmpty()) {
                return new HashMap<String, Integer>();
            }
            String finalValue = aesDecrypt(contextGetKey, value);

            //  Log.e("CustomerGlu", value);
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            return gson.fromJson(finalValue, type);
        } catch (Exception e) {
            Log.e("Customerglu", e.toString());

        }
        return null;

    }

//    public static HashMap<String, Integer> getOldEncCampaignIdObject(Context contextGetKey) {
//        try {
//            String encKey = encrypt(contextGetKey, ENCRYPTED_CAMPAIGN_OBJ);
//
//            sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
//            String value = sharedPreferences.getString(encKey, null);
//            String finalValue = decrypt(contextGetKey, value);
//
//            //  Log.e("CustomerGlu", value);
//            Gson gson = new Gson();
//            Type type = new TypeToken<HashMap<String, Integer>>() {
//            }.getType();
//            return gson.fromJson(finalValue, type);
//        } catch (Exception e) {
//            Log.e("Customerglu", e.toString());
//
//        }
//        return null;
//
//    }


    /**
     * Get Encryption salt.
     *
     * @param contextInstance
     * @return
     */
    public static String getEncryptionSalt(Context contextInstance) {
        sharedPreferences = contextInstance.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARED_PREFERENCES_SALT, "");
    }

    /**
     * Set Encryption Salt.
     *
     * @param contextInstance
     * @param saltString
     */
    public static void setEncryptionSalt(Context contextInstance, String saltString) {
        sharedPreferences = contextInstance.getSharedPreferences("CacheNew", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_SALT, saltString).commit();
    }


}
