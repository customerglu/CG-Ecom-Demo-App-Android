package com.customerglu.sdk.Utils;

import android.content.Context;

import com.tozny.crypto.android.AesCbcWithIntegrity;


public class CryptoreUtils {

    public static CryptoreUtils cryptoreUtils;
    private static AesCbcWithIntegrity.SecretKeys keys;
    private static String generatedSalt;
    private static String passwordForEncryption;

    public static CryptoreUtils getCryptoreUtils() {
        if (cryptoreUtils == null) {
            cryptoreUtils = new CryptoreUtils();
        }
        return cryptoreUtils;
    }

    private void initCryptore() {
        try {
            keys = AesCbcWithIntegrity.generateKeyFromPassword(passwordForEncryption, generatedSalt);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getSaltForEncryption(Context context, String passwordKey) {
        try {
            passwordForEncryption = passwordKey;
            if (Prefs.getEncryptionSalt(context).isEmpty()) {
                generatedSalt = AesCbcWithIntegrity.saltString(AesCbcWithIntegrity.generateSalt());
                Prefs.setEncryptionSalt(context, generatedSalt);
            } else {
                generatedSalt = Prefs.getEncryptionSalt(context);
            }
            initCryptore();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String encryptRSA(String plainText) {
        try {
            AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = AesCbcWithIntegrity.encrypt(plainText, keys);
            return cipherTextIvMac.toString();
        } catch (Exception exception) {
            // exception.printStackTrace();
        }
        return "";
    }

    public String decryptRSA(String encryptedStr) {
        try {
            AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(encryptedStr);
            return AesCbcWithIntegrity.decryptString(cipherTextIvMac, keys);
        } catch (Exception e) {
            //      e.printStackTrace();
        }
        return "";
    }
}





