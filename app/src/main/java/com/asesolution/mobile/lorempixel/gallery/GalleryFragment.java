package com.asesolution.mobile.lorempixel.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.asesolution.mobile.lorempixel.FragmentContract;
import com.asesolution.mobile.lorempixel.Injection;
import com.asesolution.mobile.lorempixel.R;
import com.asesolution.mobile.lorempixel.data.LoremPixelRepository;
import com.asesolution.mobile.lorempixel.decorators.PaddingItemDecoration;
import com.asesolution.mobile.lorempixel.fullscreenimage.FullScreenImageActivity;
import com.asesolution.mobile.lorempixel.gallery.listeners.RvScrollListenerVisibilityToggle;
import com.asesolution.mobile.lorempixel.utils.ViewUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class GalleryFragment extends Fragment implements GalleryContract.View, FragmentContract.FragmentView {
    @Bind(R.id.gallery_list)
    RecyclerView recyclerView;
    @Bind(R.id.gallery_list_progress)
    ProgressBar progressBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    GalleryPresenter actionListener;

    int imageSize;
    int spanCount;
    int padding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.d("onCreate");

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
        Timber.d("onCreateView");

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        ButterKnife.bind(this, view);

        fab.setOnClickListener(v -> actionListener.showCategoriesList());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        recyclerView.addItemDecoration(new PaddingItemDecoration(padding, spanCount));
        recyclerView.addOnScrollListener(new RvScrollListenerVisibilityToggle(fab));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Timber.d("onActivityCreated");

        actionListener = new GalleryPresenter(this, Injection.provideImagesRepository(), Injection.provideFavoritesRepository());
    }

    @Override
    public void onStart() {
        super.onStart();

        actionListener.loadGallery(imageSize);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Timber.d("onHiddenChanged");
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
    public void showGallery(ArrayList<String> urls) {
        GalleryListAdapter galleryListAdapter = new GalleryListAdapter(actionListener, urls, Injection.provideFavoritesRepository());
        recyclerView.swapAdapter(galleryListAdapter, false);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new GalleryItemTouchHelperCallback(galleryListAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void displayCategoriesList() {
        PopupMenu popupMenu = new PopupMenu(fab.getContext(), fab);

        popupMenu.getMenu().add("All");
        for (String category : LoremPixelRepository.categories) {
            popupMenu.getMenu().add(category);
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            actionListener.loadGallery(imageSize, menuItem.getTitle().toString());
            return true;
        });

        popupMenu.show();
    }

    @Override
    public void displayFragment() {
        // Do nothing
    }
}
