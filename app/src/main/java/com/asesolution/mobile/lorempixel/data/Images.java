package com.asesolution.mobile.lorempixel.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Images {
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
        return String.format(LOREM_URL + "/%d/%d/%s/%d", width, height, category, number);
    }

    public static String getRandomImageUrl(int width, int height, String category) {
        return getImageUrl(width, height, category, getRandomNumber());
    }

    public static String getRandomImageUrl(int width, int height) {
        return getImageUrl(width, height, getRandomCategory(), getRandomNumber());
    }

    public static ArrayList<String> getShuffledUrls(int width, int height) {
        ArrayList<String> urls = new ArrayList<>(categories.length * MAX_PER_CATEGORY);

        int index = 0;
        for (String category : categories) {
            for (int i = 0; i < MAX_PER_CATEGORY; i++) {
                urls.add(index++, getImageUrl(width, height, category, i));
            }
        }

        Collections.shuffle(urls);

        return urls;
    }

    public static String parseCategory(String url) {
        return url.split("/")[5];
    }

    public static String parseNumber(String url) {
        return url.split("/")[6];
    }
}
