package com.joonhuiwong.mylibrary.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Utils {

    private static Utils instance;

    public static final String ADD_EDIT_BOOK_ACTIVITY_KEY = "add_edit_book_activity";
    public static final String OPEN_BOOK_DETAIL_KEY = "open_book_detail";
    private final FirebaseAnalytics mFirebaseAnalytics;

    private Utils(Context context) {
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        return this.mFirebaseAnalytics;
    }

    public void logItemSelected(String itemName) {
        this.logItemSelected(itemName, null);
    }

    public void logItemSelected(String itemName, Bundle extraParams) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        if (extraParams != null) {
            bundle.putAll(extraParams);
        }
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);
    }

    public void logScreenView(String screenName) {
        this.logScreenView(screenName, null);
    }

    public void logScreenView(String screenName, Bundle extraParams) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        if (extraParams != null) {
            bundle.putAll(extraParams);
        }
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    public void logEvent(String eventName, Bundle bundle) {
        mFirebaseAnalytics.logEvent(eventName, bundle);
    }
}
