package com.asesolution.mobile.lorempixel.favorites.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.LoremPixelUtil;
import com.asesolution.mobile.lorempixel.favorites.interfaces.FavoritesContract;
import com.asesolution.mobile.lorempixel.utils.PaletteUtils;
import com.asesolution.mobile.lorempixel.utils.picasso.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {
    int imageSize;
    ArrayList<String> favorites;
    FavoritesContract.UserAction userAction;

    public FavoritesListAdapter(FavoritesContract.UserAction userAction, int imageSize, ArrayList<String> favorites) {
        this.userAction = userAction;
        this.imageSize = imageSize;
        this.favorites = favorites;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the URL and parse the category from it
        String url = favorites.get(position);

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

        holder.thumbnail.setOnClickListener(v -> userAction.showFullScreenImage(favorites.get(position)));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
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
        favorites.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.favorites_list_container)
        RelativeLayout container;
        @Bind(R.id.favorites_list_thumbnail)
        ImageButton thumbnail;
        @Bind(R.id.favorites_list_category)
        TextView category;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
