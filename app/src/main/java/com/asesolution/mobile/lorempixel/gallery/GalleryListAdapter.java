package com.asesolution.mobile.lorempixel.gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.FavoritesRepository;
import com.asesolution.mobile.lorempixel.data.LoremPixelUtil;
import com.asesolution.mobile.lorempixel.utils.PaletteUtils;
import com.asesolution.mobile.lorempixel.utils.picasso.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {
    ArrayList<String> urls;
    GalleryContract.UserAction userAction;
    FavoritesRepository favoritesRepository;


    public GalleryListAdapter(GalleryContract.UserAction userAction, ArrayList<String> urls, FavoritesRepository favoritesRepository) {
        this.userAction = userAction;
        this.urls = urls;
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_item, parent, false);

        ViewHolder holder = new ViewHolder(view);

        holder.thumbnail.setOnClickListener(v -> userAction.showFullScreenImage(urls.get(holder.getAdapterPosition())));

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the URL and parse the category from it
        String url = urls.get(position);

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

                        // Finally update the background color
                        holder.category.setBackgroundColor(PaletteUtils.getPaletteColor(bitmap));
                    }
                });

        // Update the category text
        holder.category.setText(LoremPixelUtil.parseCategory(url));

        // Clear the listener since we'll be manually setting the checked state and that will fire
        // the listener
        holder.favorite.setOnCheckedChangeListener(null);

        // Synchronize the state of the favorite button
        if (favoritesRepository.contains(url)) {
            holder.favorite.setChecked(true);
        } else {
            holder.favorite.setChecked(false);
        }

        // Add a checked change listener for the favorite button
        holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = holder.getAdapterPosition();

            if (isChecked) {
                userAction.addToFavorites(urls.get(adapterPosition));
            } else {
                userAction.removeFromFavorites(urls.get(adapterPosition));
            }
        });
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
        RelativeLayout container;
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
