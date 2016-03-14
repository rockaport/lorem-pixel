package com.asesolution.mobile.lorempixel.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.asesolution.mobile.lorempixel.FragmentContract;
import com.asesolution.mobile.lorempixel.Injection;
import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.decorators.PaddingItemDecoration;
import com.asesolution.mobile.lorempixel.fullscreenimage.FullScreenImageActivity;
import com.asesolution.mobile.lorempixel.utils.ViewUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FavoritesFragment extends Fragment implements FavoritesContract.View, FragmentContract.FragmentView {
    @Bind(R.id.favorites_list)
    RecyclerView recyclerView;
    @Bind(R.id.favorites_list_progress)
    ProgressBar progressBar;
    @Bind(R.id.favorites_list_empty)
    ImageView emptyIcon;

    FavoritesPresenter actionListener;

    int imageSize;
    int spanCount;
    int padding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate %s", this);

        // Obtain the image size dimension
        imageSize = getResources().getDimensionPixelSize(R.dimen.gallery_image_size);

        // Calculate the number of spans (columns) and the optimal padding for an evenly spaced grid
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        spanCount = screenWidth / imageSize;
        int extraSpace = screenWidth - spanCount * imageSize;
        padding = extraSpace / (spanCount + 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView %s", this);

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        recyclerView.addItemDecoration(new PaddingItemDecoration(padding, spanCount));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timber.d("onActivityCreated %s", this);

        actionListener = new FavoritesPresenter(this, Injection.provideFavoritesRepository());
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume %s", this);
        actionListener.loadFavorites(imageSize);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Timber.d("onHiddenChanged");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("onStop %s", this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy %s", this);
    }

    @Override
    public void displayProgressIndicator(boolean active) {
        if (active) {
            ViewUtil.animateOut(recyclerView);
            ViewUtil.animateIn(progressBar);
        } else {
            ViewUtil.animateOut(progressBar);
            ViewUtil.animateIn(recyclerView);
        }
    }

    @Override
    public void displayFullScreenImage(@NonNull String url) {
        Intent intent = new Intent(getContext(), FullScreenImageActivity.class);

        intent.putExtra(FullScreenImageActivity.EXTRA_URL, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    @Override
    public void showFavorites(ArrayList<String> favorites) {
        if (favorites.isEmpty()) {
            ViewUtil.animateOut(recyclerView);
            ViewUtil.animateIn(emptyIcon);
        } else {
            recyclerView.setAdapter(new FavoritesListAdapter(actionListener, imageSize, favorites));

            ViewUtil.animateOut(emptyIcon);
            ViewUtil.animateIn(recyclerView);
        }
    }

    @Override
    public void displayFragment() {
        Timber.d("displayFragment %s", this);
        actionListener.loadFavorites(imageSize);
    }
}
