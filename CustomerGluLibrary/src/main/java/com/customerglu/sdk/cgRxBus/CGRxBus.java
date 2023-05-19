package com.customerglu.sdk.cgRxBus;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CGRxBus {

    private static CGRxBus instance;

    private final PublishSubject<Object> subject = PublishSubject.create();

    public static CGRxBus instanceOf() {
        if (instance == null) {
            instance = new CGRxBus();
        }
        return instance;
    }

    /**
     * Pass any event down to event listeners.
     */
    public void postEvent(Object object) {
        subject.onNext(object);
    }

    /**
     * Subscribe to this Observable. On event, do something
     * e.g. replace a fragment
     */
    public Observable<Object> getEvent() {
        return subject;
    }
}