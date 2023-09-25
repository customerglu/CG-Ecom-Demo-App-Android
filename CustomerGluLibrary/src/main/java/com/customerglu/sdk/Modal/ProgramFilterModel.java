package com.customerglu.sdk.Modal;

public class ProgramFilterModel {
    ProgramFilter filter;
    int limit;
    int page;

    public ProgramFilterModel() {

    }

    public ProgramFilterModel(ProgramFilter filter, int limit, int page) {
        this.filter = filter;
        this.limit = limit;
        this.page = page;
    }

    public ProgramFilter getFilter() {
        return filter;
    }

    public void setFilter(ProgramFilter filter) {
        this.filter = filter;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
