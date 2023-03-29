package com.customerglu.sdk.Interface;


import com.customerglu.sdk.Modal.RegisterModal;

public interface DataListner {

    void onSuccess(RegisterModal registerModal);
    void onFail(String message);

}
