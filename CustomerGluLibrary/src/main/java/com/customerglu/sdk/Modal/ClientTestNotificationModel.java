package com.customerglu.sdk.Modal;

public class ClientTestNotificationModel {

    Boolean success;
    FirebaseData data;

    public ClientTestNotificationModel(Boolean success, FirebaseData data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public FirebaseData getData() {
        return data;
    }

    public void setData(FirebaseData data) {
        this.data = data;
    }

    public class FirebaseData {
        Boolean apnsDeviceToken;
        Boolean firebaseToken;
        Boolean privateKeyApns;
        Boolean privateKeyFirebase;

        public FirebaseData(Boolean apnsDeviceToken, Boolean firebaseToken, Boolean privateKeyApns, Boolean privateKeyFirebase) {
            this.apnsDeviceToken = apnsDeviceToken;
            this.firebaseToken = firebaseToken;
            this.privateKeyApns = privateKeyApns;
            this.privateKeyFirebase = privateKeyFirebase;
        }

        public Boolean getApnsDeviceToken() {
            return apnsDeviceToken;
        }

        public void setApnsDeviceToken(Boolean apnsDeviceToken) {
            this.apnsDeviceToken = apnsDeviceToken;
        }

        public Boolean getFirebaseToken() {
            return firebaseToken;
        }

        public void setFirebaseToken(Boolean firebaseToken) {
            this.firebaseToken = firebaseToken;
        }

        public Boolean getPrivateKeyApns() {
            return privateKeyApns;
        }

        public void setPrivateKeyApns(Boolean privateKeyApns) {
            this.privateKeyApns = privateKeyApns;
        }

        public Boolean getPrivateKeyFirebase() {
            return privateKeyFirebase;
        }

        public void setPrivateKeyFirebase(Boolean privateKeyFirebase) {
            this.privateKeyFirebase = privateKeyFirebase;
        }
    }
}
