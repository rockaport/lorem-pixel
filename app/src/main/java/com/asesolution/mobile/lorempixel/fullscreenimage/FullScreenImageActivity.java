package com.asesolution.mobile.lorempixel.fullscreenimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.LoremPixelRepository;
import com.asesolution.mobile.lorempixel.data.LoremPixelUtil;
import com.asesolution.mobile.lorempixel.utils.PaletteUtils;
import com.asesolution.mobile.lorempixel.utils.picasso.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullScreenImageActivity extends AppCompatActivity {
    public static final String EXTRA_URL = FullScreenImageActivity.class.getCanonicalName() + "EXTRA_URL";

    @Bind(R.id.image_view)
    ImageView imageView;
    @Bind(R.id.image_view_category)
    TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ButterKnife.bind(this);

        // Get the window size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Get the url to process and show
        String inputUrl = getIntent().getStringExtra(EXTRA_URL);

        // Process the category and number
        String urlCategory = LoremPixelUtil.parseCategory(inputUrl);
        int number = Integer.parseInt(LoremPixelUtil.parseNumber(inputUrl));

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

                        // Finally update the background color
                        category.setBackgroundColor(PaletteUtils.getPaletteColor(bitmap));
                        category.setVisibility(View.VISIBLE);
                    }
                });

        // Update the category
        category.setText(urlCategory);
    }
}
