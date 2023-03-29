package com.customerglu.sdk.Interface;

import com.customerglu.sdk.Modal.RewardModel;

public interface RewardInterface {

    void onSuccess(RewardModel rewardModel);
    void onFailure(String message);
}
