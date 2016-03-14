package com.asesolution.mobile.lorempixel.favorites;

import android.support.annotation.NonNull;

import com.asesolution.mobile.lorempixel.data.FavoritesRepository;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesPresenter implements FavoritesContract.UserAction {
    FavoritesContract.View favoritesView;
    FavoritesRepository favoritesRepository;

    public FavoritesPresenter(
            @NonNull FavoritesContract.View favoritesView,
            @NonNull FavoritesRepository favoritesRepository) {
        this.favoritesView = favoritesView;
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public void showFullScreenImage(@NonNull String url) {
        favoritesView.displayFullScreenImage(url);
    }

    @Override
    public void loadFavorites(int size) {
        favoritesView.displayProgressIndicator(true);

        favoritesRepository.getFavorites()
                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {
                        favoritesView.displayProgressIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<String> strings) {
                        favoritesView.showFavorites(strings);
                    }
                });
    }

    @Override
    public void removeFromFavorites(@NonNull String url) {
    }
}
