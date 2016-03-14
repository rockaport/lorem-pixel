package com.asesolution.mobile.lorempixel.gallery;

import android.support.annotation.NonNull;

import com.asesolution.mobile.lorempixel.data.FavoritesRepository;
import com.asesolution.mobile.lorempixel.data.ImagesRepository;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class GalleryPresenter implements GalleryContract.UserAction {
    GalleryContract.View galleryView;
    ImagesRepository imagesRepository;
    FavoritesRepository favoritesRepository;

    public GalleryPresenter(
            @NonNull GalleryContract.View galleryView,
            @NonNull ImagesRepository imagesRepository,
            @NonNull FavoritesRepository favoritesRepository) {
        this.galleryView = galleryView;
        this.imagesRepository = imagesRepository;
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public void showFullScreenImage(@NonNull String url) {
        galleryView.displayFullScreenImage(url);
    }

    @Override
    public void loadGallery(int size) {
        galleryView.displayProgressIndicator(true);

        imagesRepository.getImageUrls(size)
                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> galleryView.showGallery(strings),
                        throwable -> {
                        },
                        () -> galleryView.displayProgressIndicator(false));
    }

    @Override
    public void loadGallery(int size, @NonNull String category) {
        galleryView.displayProgressIndicator(true);
        imagesRepository.getImageUrls(size, category)
                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> galleryView.showGallery(strings),
                        throwable -> {
                        },
                        () -> galleryView.displayProgressIndicator(false));
    }

    @Override
    public void addToFavorites(@NonNull String url) {
        favoritesRepository.add(url);
        Timber.d("Image added to favorites %s", url);
    }

    @Override
    public void removeFromFavorites(@NonNull String url) {
        favoritesRepository.remove(url);
        Timber.d("Image removed from favorites %s", url);
    }

    @Override
    public void showCategoriesList() {
        galleryView.displayCategoriesList();
    }
}
