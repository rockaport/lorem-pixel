package com.asesolution.mobile.lorempixel.gallery.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.gallery.adapters.GalleryListAdapter;
import com.asesolution.mobile.lorempixel.gallery.callbacks.GalleryItemTouchHelperCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment {
    @Bind(R.id.gallery_list)
    RecyclerView recyclerView;

    GalleryListAdapter galleryListAdapter;
    GalleryItemTouchHelperCallback galleryItemTouchHelperCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        int width = getResources().getDisplayMetrics().widthPixels;
        int imageSize = getResources().getDimensionPixelSize(R.dimen.gallery_image_size);
        int spanCount = width / imageSize;

        galleryListAdapter = new GalleryListAdapter();
        galleryItemTouchHelperCallback = new GalleryItemTouchHelperCallback(galleryListAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        recyclerView.setAdapter(galleryListAdapter);
//        recyclerView.addItemDecoration(new SpacesItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics())));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(galleryItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
        }
    }
}
