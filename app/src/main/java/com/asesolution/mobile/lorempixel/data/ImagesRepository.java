package com.asesolution.mobile.lorempixel.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public interface ImagesRepository {
    void getImageUrls(int size, @NonNull LoadImagesCallback callback);

    interface LoadImagesCallback {
        void onImagesLoaded(ArrayList<String> urls);
    }
}
