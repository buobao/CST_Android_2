package com.joint.turman.app.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dqf on 2016/1/11.
 */
public class RxUtils {
    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }
        return subscription;
    }

}
