package com.asesolution.mobile.lorempixel.favorites.interfaces;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public interface FavoritesContract {
    interface View {
        void displayProgressIndicator(boolean active);

        void showFullScreenImageUi(@NonNull String url);

        void showFavorites(ArrayList<String> urls);
    }

    interface UserAction {
        void showFullScreenImage(@NonNull String url);

        void loadFavorites(int size);

        void removeFromFavorites(@NonNull String url);
    }
}
