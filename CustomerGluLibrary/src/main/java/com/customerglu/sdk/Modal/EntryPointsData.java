package com.customerglu.sdk.Modal;

import java.util.Objects;

public class EntryPointsData {

    String status;
    boolean visible;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public MobileData getMobileData() {
        return mobile;
    }

    public void setMobileData(MobileData mobileData) {
        this.mobile = mobileData;
    }

    public EntryPointsData(String status, boolean visible, String _id, String name, String consumer, String client, String createdAt, String updatedAt, String __v, MobileData mobileData) {
        this.status = status;
        this.visible = visible;
        this._id = _id;
        this.name = name;
        this.consumer = consumer;
        this.client = client;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
        this.mobile = mobileData;
    }

    String _id;
    String name;
    String consumer;
    String client;
    String createdAt;
    String updatedAt;
    String __v;
    MobileData mobile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryPointsData)) return false;
        EntryPointsData that = (EntryPointsData) o;
        return getVisible() == that.getVisible() && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(get_id(), that.get_id()) && Objects.equals(getName(), that.getName()) && Objects.equals(getConsumer(), that.getConsumer()) && Objects.equals(getClient(), that.getClient()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt()) && Objects.equals(get__v(), that.get__v()) && Objects.equals(mobile, that.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getVisible(), get_id(), getName(), getConsumer(), getClient(), getCreatedAt(), getUpdatedAt(), get__v(), mobile);
    }
}
