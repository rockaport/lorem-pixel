package com.asesolution.mobile.lorempixel.gallery.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.asesolution.mobile.lorempixel.utils.ViewUtil;

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
                ViewUtil.animateOut(view);
            }
        } else {
            if (!isViewVisible) {
                isViewVisible = true;
                ViewUtil.animateIn(view);
            }
        }
    }
}
