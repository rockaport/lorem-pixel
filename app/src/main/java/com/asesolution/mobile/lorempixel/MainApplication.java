package com.asesolution.mobile.lorempixel;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.asesolution.mobile.lorempixel.logging.TimberReleaseTree;

import timber.log.Timber;

public class MainApplication extends Application {
    private static Context context;
    private static Resources resources;

    public static Resources getStaticResources() {
        return resources;
    }

    public static Context getStaticContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (context == null) {
            context = getApplicationContext();
        }

        if (resources == null) {
            resources = getResources();
        }

        // Plant the right timber tree
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new TimberReleaseTree());
        }
    }
}
