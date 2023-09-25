package com.customerglu.sdk.Utils;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.RewardInterface;
import com.customerglu.sdk.Modal.RewardModel;

public class CGHelper {

    boolean isCampaignPresent = false;
    private static CGHelper INSTANCE;

    public static CGHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CGHelper();
        }
        return INSTANCE;
    }

    public boolean isCampaignValid(String campaignId) {
        if (CustomerGlu.campaignIdList != null && !CustomerGlu.campaignIdList.isEmpty()) {
            return CustomerGlu.campaignIdList.contains(campaignId);
        } else {
            CustomerGlu.getInstance().retrieveData(CustomerGlu.globalContext, new RewardInterface() {
                @Override
                public void onSuccess(RewardModel rewardModel) {
                    if (CustomerGlu.campaignIdList != null && !CustomerGlu.campaignIdList.isEmpty()) {
                        isCampaignPresent = CustomerGlu.campaignIdList.contains(campaignId);
                    }
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }
        return isCampaignPresent;
    }

}
