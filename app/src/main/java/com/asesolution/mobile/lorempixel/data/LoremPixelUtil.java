package com.asesolution.mobile.lorempixel.data;

public class LoremPixelUtil {
    public static String parseCategory(String url) {
        return url.split("/")[5];
    }

    public static String parseNumber(String url) {
        return url.split("/")[6];
    }
}
