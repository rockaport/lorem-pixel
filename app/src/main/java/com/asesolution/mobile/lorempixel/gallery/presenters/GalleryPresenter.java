package com.asesolution.mobile.lorempixel.gallery.presenters;

import android.support.annotation.NonNull;
import android.view.View;

import com.asesolution.mobile.lorempixel.data.FavoritesRepository;
import com.asesolution.mobile.lorempixel.data.ImagesRepository;
import com.asesolution.mobile.lorempixel.gallery.interfaces.GalleryContract;

public class GalleryPresenter implements GalleryContract.UserAction {
    private ImagesRepository imagesRepository;
    private FavoritesRepository favoritesRepository;
    private GalleryContract.View galleryView;

    public GalleryPresenter(
            @NonNull ImagesRepository imagesRepository,
            @NonNull FavoritesRepository favoritesRepository,
            @NonNull GalleryContract.View galleryView) {
        this.imagesRepository = imagesRepository;
        this.favoritesRepository = favoritesRepository;
        this.galleryView = galleryView;
    }

    @Override
    public void showFullScreenImage(@NonNull View view, @NonNull String url) {
        galleryView.showFullScreenImageUi(view, url);
    }

    @Override
    public void loadGallery(int size) {
        galleryView.displayProgressIndicator(true);

        imagesRepository.getImageUrls(size, urls -> {
            galleryView.displayProgressIndicator(false);
            galleryView.showGallery(urls);
        });
    }

    @Override
    public void loadGallery(int size, @NonNull String category) {
        galleryView.displayProgressIndicator(true);

        imagesRepository.getImageUrls(size, category, urls -> {
            galleryView.displayProgressIndicator(false);
            galleryView.showGallery(urls);
        });
    }

    @Override
    public void addToFavorites(@NonNull String url) {
        favoritesRepository.add(url);
    }

    @Override
    public void removeFromFavorites(@NonNull String url) {
        favoritesRepository.remove(url);
    }

    @Override
    public void showCategoriesList() {
        galleryView.displayCategoriesList();
    }
}
