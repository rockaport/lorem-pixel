package com.asesolution.mobile.lorempixel.utils.picasso;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Transformation;

import java.util.Map;
import java.util.WeakHashMap;

public final class PaletteTransformation implements Transformation {
    private static final PaletteTransformation INSTANCE = new PaletteTransformation();
    private static final Map<Bitmap, Palette> CACHE = new WeakHashMap<>();

    private PaletteTransformation() {
    }

    public static PaletteTransformation instance() {
        return INSTANCE;
    }

    public static Palette getPalette(Bitmap bitmap) {
        return CACHE.get(bitmap);
    }

    @Override
    public Bitmap transform(Bitmap source) {
        CACHE.put(source, new Palette.Builder(source).generate());
        return source;
    }

    @Override
    public String key() {
        return PaletteTransformation.class.getSimpleName();
    }
}