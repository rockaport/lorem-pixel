package com.asesolution.mobile.lorempixel.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;
import rx.schedulers.Schedulers;

public class LoremPixelFavoritesRepository implements FavoritesRepository {
    private static LoremPixelFavoritesRepository instance = null;

    private static ArrayList<String> favorites = new ArrayList<>();

    private LoremPixelFavoritesRepository() {
    }

    public static LoremPixelFavoritesRepository getInstance() {
        if (instance == null) {
            instance = new LoremPixelFavoritesRepository();
        }

        return instance;
    }

    @Override
    public Observable<ArrayList<String>> getFavorites() {
        return Observable.just(favorites).subscribeOn(Schedulers.io());
    }

    @Override
    public void add(@NonNull String url) {
        favorites.add(url);
    }

    @Override
    public void remove(@NonNull String url) {
        favorites.remove(url);
    }

    @Override
    public boolean contains(@NonNull String url) {
        return favorites.contains(url);
    }
}
