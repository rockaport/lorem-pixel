package com.asesolution.mobile.lorempixel.gallery.adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.Images;
import com.asesolution.mobile.lorempixel.gallery.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {
    private static final int NUM_ROWS = Images.MAX_PER_CATEGORY * Images.categories.length;
    ArrayMap<Integer, String> urls = new ArrayMap<>(NUM_ROWS);
    ArrayMap<Integer, Boolean> favorites = new ArrayMap<>(NUM_ROWS);
    int imageSize;

    public GalleryListAdapter(int imageSize) {
        this.imageSize = imageSize;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String category, url;

        if (urls.containsKey(position)) {
            category = urls.get(position).split("/")[5];
            url = urls.get(position);
        } else {
            category = Images.getRandomCategory();
            url = Images.getRandomImageUrl(imageSize, imageSize, category);
            urls.put(position, url);
        }

        holder.category.setText(category);

        Picasso.with(holder.thumbnail.getContext())
                .load(url)
                .transform(PaletteTransformation.instance())
                .into(holder.thumbnail, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) holder.thumbnail.getDrawable()).getBitmap(); // Ew!
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        int color = palette.getDarkVibrantColor(Color.parseColor("#757575"));
                        int newColor = Color.argb(255 * 2 / 3, Color.red(color), Color.green(color), Color.blue(color));
                        holder.category.setBackgroundColor(newColor);
                    }
                });

        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favorites.put(position, isChecked);
            }
        });

        if (favorites.containsKey(position)) {
            holder.favorite.setChecked(favorites.get(position));
        } else {
            favorites.put(position, false);
            holder.favorite.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ROWS;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gallery_list_thumbnail)
        ImageView thumbnail;
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
