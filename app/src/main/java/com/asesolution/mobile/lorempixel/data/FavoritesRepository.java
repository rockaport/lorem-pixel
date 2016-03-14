package com.asesolution.mobile.lorempixel.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;

public interface FavoritesRepository {
    Observable<ArrayList<String>> getFavorites();

    void add(@NonNull String url);

    void remove(@NonNull String url);

    boolean contains(@NonNull String url);
}
