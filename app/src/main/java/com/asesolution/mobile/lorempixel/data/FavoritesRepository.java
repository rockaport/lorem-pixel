package com.asesolution.mobile.lorempixel.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public interface FavoritesRepository {
    ArrayList<String> getFavorites();

    void add(@NonNull String url);

    void remove(@NonNull String url);
}
