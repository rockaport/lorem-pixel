package com.asesolution.mobile.lorempixel;

import com.asesolution.mobile.lorempixel.data.FavoritesRepository;
import com.asesolution.mobile.lorempixel.data.ImagesRepository;
import com.asesolution.mobile.lorempixel.data.LoremPixelFavoritesRepository;
import com.asesolution.mobile.lorempixel.data.LoremPixelRepository;

public class Injection {
    public static ImagesRepository provideImagesRepository() {
        return new LoremPixelRepository();
    }

    public static FavoritesRepository provideFavoritesRepository() {
        return LoremPixelFavoritesRepository.getInstance();
    }
}
