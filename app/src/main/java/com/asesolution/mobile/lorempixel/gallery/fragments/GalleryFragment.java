package com.asesolution.mobile.lorempixel.gallery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.asesolution.mobile.lorempixel.FragmentContract;
import com.asesolution.mobile.lorempixel.Injection;
import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.fullscreenimage.activities.FullScreenImageActivity;
import com.asesolution.mobile.lorempixel.gallery.adapters.GalleryListAdapter;
import com.asesolution.mobile.lorempixel.gallery.callbacks.GalleryItemTouchHelperCallback;
import com.asesolution.mobile.lorempixel.gallery.interfaces.GalleryContract;
import com.asesolution.mobile.lorempixel.gallery.presenters.GalleryPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment implements GalleryContract.View, FragmentContract.FragmentView {
    @Bind(R.id.gallery_list)
    RecyclerView recyclerView;
    @Bind(R.id.gallery_list_progress)
    ProgressBar progressBar;

    GalleryPresenter actionListener;

    int imageSize;
    int spanCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = getResources().getDisplayMetrics().widthPixels;
        imageSize = getResources().getDimensionPixelSize(R.dimen.gallery_image_size);
        spanCount = width / imageSize;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        actionListener = new GalleryPresenter(Injection.provideImagesRepository(), Injection.provideFavoritesRepository(), this);
        actionListener.loadGallery(imageSize);
    }

    @Override
    public void displayProgressIndicator(boolean active) {
        if (active) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showFullScreenImageUi(@NonNull String url) {
        Intent intent = new Intent(getContext(), FullScreenImageActivity.class);
        intent.putExtra(FullScreenImageActivity.EXTRA_URL, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showGallery(ArrayList<String> urls) {
        GalleryListAdapter galleryListAdapter = new GalleryListAdapter(actionListener, urls, Injection.provideFavoritesRepository().getFavorites());
        recyclerView.setAdapter(galleryListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new GalleryItemTouchHelperCallback(galleryListAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void displayFragment() {
        // Do nothing
    }
}
