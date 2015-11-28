package com.asesolution.mobile.lorempixel.favorites.presenters;

import android.support.annotation.NonNull;

import com.asesolution.mobile.lorempixel.data.FavoritesRepository;
import com.asesolution.mobile.lorempixel.favorites.interfaces.FavoritesContract;

import java.util.ArrayList;

public class FavoritesPresenter implements FavoritesContract.UserAction {
    FavoritesRepository favoritesRepository;
    FavoritesContract.View favoritesView;

    public FavoritesPresenter(
            @NonNull FavoritesRepository favoritesRepository,
            @NonNull FavoritesContract.View favoritesView) {
        this.favoritesRepository = favoritesRepository;
        this.favoritesView = favoritesView;
    }

    @Override
    public void showFullScreenImage(@NonNull String url) {
        favoritesView.showFullScreenImageUi(url);
    }

    @Override
    public void loadFavorites(int size) {
        favoritesView.displayProgressIndicator(true);
        favoritesView.showFavorites(favoritesRepository.getFavorites());
        favoritesView.displayProgressIndicator(false);
    }

    @Override
    public void removeFromFavorites(@NonNull String url) {
    }
}
