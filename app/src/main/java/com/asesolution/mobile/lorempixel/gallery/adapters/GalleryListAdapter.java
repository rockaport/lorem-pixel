package com.asesolution.mobile.lorempixel.gallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asesolution.mobile.lorempixel.MainApplication;
import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.Images;
import com.asesolution.mobile.lorempixel.gallery.PaletteTransformation;
import com.asesolution.mobile.lorempixel.gallery.activities.ImageViewActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {
    int imageSize;
    ArrayList<String> urls;
    ArrayMap<Integer, Boolean> favorites;

    public GalleryListAdapter() {
        this.imageSize = MainApplication.getStaticResources().getDimensionPixelSize(R.dimen.gallery_image_size);
        urls = Images.getShuffledUrls(imageSize, imageSize);
        favorites = new ArrayMap<>(urls.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the URL and parse the category from it
        final String url = urls.get(position);
        String category = Images.parseCategory(url);

        // Load the image view from the url
        Picasso.with(holder.thumbnail.getContext())
                .load(url)
                .transform(PaletteTransformation.instance())
                .into(holder.thumbnail, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        // Taken from Jake's writeup on picasso + palette
                        // Use the bitmaps palette color to set the text background color
                        Bitmap bitmap = ((BitmapDrawable) holder.thumbnail.getDrawable()).getBitmap(); // Ew!
                        Palette palette = PaletteTransformation.getPalette(bitmap);

                        int color = palette.getDarkVibrantColor(Color.parseColor("#757575"));

                        // Add some transparency. This kind of sucks and it's a little cleaner/faster
                        // to just use bitwise operations, but I'm going for a readability here
                        int newColor = Color.argb(255 * 2 / 3, Color.red(color), Color.green(color), Color.blue(color));

                        // Finally update the background color
                        holder.category.setBackgroundColor(newColor);
                    }
                });

        // Update the category text
        holder.category.setText(category);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainApplication.getStaticContext();
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putExtra(ImageViewActivity.EXTRA_URL, urls.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        // Add a checked change listener for the favorite button
        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favorites.put(position, isChecked);
            }
        });

        // Synchronize the state of the favorite button
        if (favorites.containsKey(position)) {
            holder.favorite.setChecked(favorites.get(position));
        } else {
            favorites.put(position, false);
            holder.favorite.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        holder.container.startAnimation(AnimationUtils.loadAnimation(
                holder.container.getContext(), android.R.anim.slide_in_left));
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        // Clear the animation for views that are detached. If the user flings the list.
        holder.container.clearAnimation();
    }

    public void remove(int adapterPosition) {
        urls.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gallery_list_container)
        FrameLayout container;
        @Bind(R.id.gallery_list_thumbnail)
        ImageButton thumbnail;
        @Bind(R.id.gallery_list_category)
        TextView category;
        @Bind(R.id.gallery_list_favorite)
        CheckBox favorite;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
