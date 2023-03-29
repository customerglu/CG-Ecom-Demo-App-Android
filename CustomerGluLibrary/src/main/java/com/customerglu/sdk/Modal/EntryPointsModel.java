package com.customerglu.sdk.Modal;

import java.util.List;

public class EntryPointsModel {
    Boolean success;
    List<EntryPointsData> data;

    public EntryPointsModel(boolean success, List<EntryPointsData> data) {
        this.success = success;
        this.data = data;
    }

    public EntryPointsModel() {

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<EntryPointsData> getEntryPointsData() {
        return data;
    }

    public void setEntryPointsData(List<EntryPointsData> entryPointsData) {
        this.data = entryPointsData;
    }


}
