package com.asesolution.mobile.lorempixel.gallery.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RvScrollListenerVisibilityToggle extends RecyclerView.OnScrollListener {
    boolean scrollingDown = false;
    boolean isViewVisible = true;
    View view;

    public RvScrollListenerVisibilityToggle(View view) {
        this.view = view;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        scrollingDown = dy > 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            return;
        }

        if (scrollingDown) {
            if (isViewVisible) {
                isViewVisible = false;
                view.animate().scaleX(0f).scaleY(0f).start();
            }
        } else {
            if (!isViewVisible) {
                isViewVisible = true;
                view.animate().scaleX(1f).scaleY(1f).start();
            }
        }
    }
}
