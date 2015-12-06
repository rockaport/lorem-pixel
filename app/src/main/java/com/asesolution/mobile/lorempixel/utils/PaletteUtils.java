package com.asesolution.mobile.lorempixel.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.asesolution.mobile.lorempixel.MainApplication;
import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.utils.picasso.PaletteTransformation;

public class PaletteUtils {
    private static final int DEFAULT_COLOR = ContextCompat.getColor(MainApplication.getStaticContext(), R.color.md_grey_600);
    private static final int DEFAULT_ALPHA = 170; // 255 * 2 / 3

    public static int getPaletteColor(Bitmap bitmap) {
        int color = PaletteTransformation.getPalette(bitmap).getDarkVibrantColor(DEFAULT_COLOR);

        // Add some transparency. This kind of sucks and it's a little cleaner/faster
        // to just use bitwise operations, but I'm going for a readability here
        // return (color & 0x00FFFFFF) | (DEFAULT_ALPHA << 24);
        return Color.argb(DEFAULT_ALPHA, Color.red(color), Color.green(color), Color.blue(color));
    }
}
