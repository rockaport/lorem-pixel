package com.asesolution.mobile.lorempixel.logging;

import android.util.Log;

import timber.log.Timber;

/**
 * Release version of timber that only logs messages of warning or greater (e.g. error and assert)
 */
public class TimberReleaseTree extends Timber.DebugTree {
    @Override
    protected boolean isLoggable(int priority) {
        return priority >= Log.WARN;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (!isLoggable(priority)) {
            return;
        }

        super.log(priority, tag, message, t);
    }
}
