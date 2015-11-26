package com.asesolution.mobile.lorempixel.gallery.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.LoremPixelRepository;
import com.asesolution.mobile.lorempixel.gallery.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageViewActivity extends AppCompatActivity {
    public static final String EXTRA_URL = ImageViewActivity.class.getCanonicalName() + "EXTRA_URL";

    @Bind(R.id.image_view)
    ImageView imageView;
    @Bind(R.id.image_view_category)
    TextView category;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ButterKnife.bind(this);

        // Initialize the animation
        fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);

        // Get the window size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Get the url to process and show
        String inputUrl = getIntent().getStringExtra(EXTRA_URL);

        // Process the category and number
        String urlCategory = LoremPixelRepository.parseCategory(inputUrl);
        int number = Integer.parseInt(LoremPixelRepository.parseNumber(inputUrl));

        // Get the actual url with correct 16:9 aspect ratio size
        String url = LoremPixelRepository.getImageUrl(metrics.widthPixels, metrics.widthPixels * 9 / 16, urlCategory, number);

        // Fetch the image via picasso
        Picasso.with(this)
                .load(url)
                .transform(PaletteTransformation.instance())
                .into(imageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        // Taken from Jake's writeup on picasso + palette
                        // Use the bitmaps palette color to set the text background color
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap(); // Ew!
                        Palette palette = PaletteTransformation.getPalette(bitmap);

                        int color = palette.getDarkVibrantColor(Color.parseColor("#757575"));

                        // Add some transparency. This kind of sucks and it's a little cleaner/faster
                        // to just use bitwise operations, but I'm going for a readability here
                        int newColor = Color.argb(255 * 2 / 3, Color.red(color), Color.green(color), Color.blue(color));

                        // Finally update the background color
                        category.setBackgroundColor(newColor);
                        category.setVisibility(View.VISIBLE);
                    }
                });

        // Update the category
        category.setText(urlCategory);
    }
}
