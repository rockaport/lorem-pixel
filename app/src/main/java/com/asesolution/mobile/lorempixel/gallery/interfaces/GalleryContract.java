package com.asesolution.mobile.lorempixel.gallery.interfaces;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public interface GalleryContract {
    interface View {
        void displayProgressIndicator(boolean active);

        void showFullScreenImageUi(@NonNull String url);

        void showGallery(ArrayList<String> urls);

        void displayCategoriesList();
    }

    interface UserAction {
        void showFullScreenImage(@NonNull String url);

        void loadGallery(int size);

        void loadGallery(int size, @NonNull String category);

        void addToFavorites(@NonNull String url);

        void removeFromFavorites(@NonNull String url);

        void showCategoriesList();
    }
}
