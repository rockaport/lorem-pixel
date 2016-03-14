package com.asesolution.mobile.lorempixel.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;

public interface ImagesRepository {
    Observable<ArrayList<String>> getImageUrls(int size);

    Observable<ArrayList<String>> getImageUrls(int size, @NonNull String category);
}
