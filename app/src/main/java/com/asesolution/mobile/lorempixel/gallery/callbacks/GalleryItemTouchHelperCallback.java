package com.asesolution.mobile.lorempixel.gallery.callbacks;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.asesolution.mobile.lorempixel.gallery.adapters.GalleryListAdapter;


public class GalleryItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    GalleryListAdapter galleryListAdapter;

    public GalleryItemTouchHelperCallback(GalleryListAdapter galleryListAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.galleryListAdapter = galleryListAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        galleryListAdapter.remove(viewHolder.getAdapterPosition());
    }
}
