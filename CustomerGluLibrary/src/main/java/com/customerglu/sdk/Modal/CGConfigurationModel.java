package com.customerglu.sdk.Modal;

public class CGConfigurationModel {
    Boolean success;
    CGConfigData data;

    public CGConfigurationModel(Boolean success, CGConfigData data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CGConfigData getData() {
        return data;
    }

    public void setData(CGConfigData data) {
        this.data = data;
    }
    

}
