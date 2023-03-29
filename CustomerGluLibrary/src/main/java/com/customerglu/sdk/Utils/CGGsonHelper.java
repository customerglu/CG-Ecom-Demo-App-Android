package com.customerglu.sdk.Utils;

import com.google.gson.Gson;

public class CGGsonHelper {

    static Gson gson;
    private static CGGsonHelper INSTANCE;

    public static CGGsonHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CGGsonHelper();
            gson = new Gson();
        }
        return INSTANCE;
    }

    public Gson getGsonInstance() {
        return gson;
    }

    public String stringToJSONConversion(String jsonString) {
        return gson.toJson(jsonString);
    }

}
