package com.asesolution.mobile.lorempixel.favorites.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.asesolution.mobile.lorempixel.FragmentContract;
import com.asesolution.mobile.lorempixel.Injection;
import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.favorites.adapters.FavoritesListAdapter;
import com.asesolution.mobile.lorempixel.favorites.interfaces.FavoritesContract;
import com.asesolution.mobile.lorempixel.favorites.presenters.FavoritesPresenter;
import com.asesolution.mobile.lorempixel.fullscreenimage.activities.FullScreenImageActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment implements FavoritesContract.View, FragmentContract.FragmentView {
    private static final String TAG = "FavoritesFragment";
    @Bind(R.id.favorites_list)
    RecyclerView recyclerView;
    @Bind(R.id.favorites_list_progress)
    ProgressBar progressBar;
    @Bind(R.id.favorites_list_empty)
    ImageView emptyIcon;

    FavoritesPresenter actionListener;

    int imageSize;
    int spanCount;

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

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
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        actionListener = new FavoritesPresenter(Injection.provideFavoritesRepository(), this);
        actionListener.loadFavorites(imageSize);
    }

    @Override
    public void displayProgressIndicator(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
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
    public void showFavorites(ArrayList<String> favorites) {
        if (favorites.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyIcon.setVisibility(View.VISIBLE);
        } else {
            emptyIcon.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            FavoritesListAdapter favoritesListAdapter = new FavoritesListAdapter(actionListener, imageSize, favorites);
            recyclerView.setAdapter(favoritesListAdapter);
        }
    }

    @Override
    public void displayFragment() {
        actionListener.loadFavorites(imageSize);
    }
}
