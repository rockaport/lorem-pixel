package com.asesolution.mobile.lorempixel.data;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import rx.Observable;
import rx.schedulers.Schedulers;

public class LoremPixelRepository implements ImagesRepository {
    public static final String LOREM_URL = "http://lorempixel.com";
    public static final int MAX_PER_CATEGORY = 11;
    public static final String[] categories = {
            "abstract",
            "animals",
            "business",
            "cats",
            "city",
            "food",
            "nightlife",
            "fashion",
            "people",
            "nature",
            "sports",
            "technics",
            "transport"
    };
    private static final Random random = new Random();

    public static String getRandomCategory() {
        return categories[random.nextInt(categories.length)];
    }

    public static int getRandomNumber() {
        return random.nextInt(MAX_PER_CATEGORY);
    }

    public static String getImageUrl(int width, int height, String category, int number) {
        return String.format(Locale.ENGLISH, LOREM_URL + "/%d/%d/%s/%d", width, height, category, number);
    }

    boolean isValidCategory(String category) {
        for (String c : categories) {
            if (c.equals(category)) {
                return true;
            }
        }

        return false;
    }

    ArrayList<String> getShuffledUrls(int width, int height, String category) {
        ArrayList<String> urls;
        if (TextUtils.isEmpty(category) || !isValidCategory(category)) {
            urls = new ArrayList<>(categories.length * MAX_PER_CATEGORY);

            int index = 0;
            for (String cat : categories) {
                for (int i = 0; i < MAX_PER_CATEGORY; i++) {
                    urls.add(index++, getImageUrl(width, height, cat, i));
                }
            }
        } else {
            urls = new ArrayList<>(MAX_PER_CATEGORY);

            for (int i = 0; i < MAX_PER_CATEGORY; i++) {
                urls.add(i, getImageUrl(width, height, category, i));
            }
        }

        Collections.shuffle(urls);

        return urls;
    }

    ArrayList<String> getShuffledUrls(int imageSize) {
        return getShuffledUrls(imageSize, imageSize, null);
    }

    ArrayList<String> getShuffledUrls(int imageSize, String category) {
        return getShuffledUrls(imageSize, imageSize, category);
    }

    @Override
    public Observable<ArrayList<String>> getImageUrls(int imageSize) {
        return Observable.just(getShuffledUrls(imageSize)).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ArrayList<String>> getImageUrls(int imageSize, @NonNull String category) {
        return Observable.just(getShuffledUrls(imageSize, category)).subscribeOn(Schedulers.io());
    }
}
