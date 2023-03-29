package com.customerglu.sdk.Interface;

import com.customerglu.sdk.Modal.DeepLinkWormholeModel;
import com.customerglu.sdk.Utils.CGConstants;

public interface CGDeepLinkListener {

    void onSuccess(CGConstants.CGSTATE message, DeepLinkWormholeModel.DeepLinkData deepLinkData);

    void onFailure(CGConstants.CGSTATE exceptions);

}
