package com.asesolution.mobile.lorempixel.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private int padding;
    private int spanCount;

    public PaddingItemDecoration(int padding, int spanCount) {
        this.padding = padding;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = padding >> 1;
        outRect.bottom = padding >> 1;

        // If there's only one column evenly pad the left/right sides
        if (spanCount == 1) {
            outRect.right = padding;
            outRect.left = padding;
            return;
        }

        if (isFirstColumn(parent.getChildAdapterPosition(view))) {
            outRect.left = padding;
            outRect.right = padding >> 1;
        } else if (isLastColumn(parent.getChildAdapterPosition(view))) {
            outRect.left = padding >> 1;
            outRect.right = padding;
        } else {
            // Somewhere in the middle items are left/right padded by half
            outRect.left = padding >> 1;
            outRect.right = padding >> 1;
        }
    }

    private boolean isFirstColumn(int position) {
        return position % spanCount == 0;
    }

    private boolean isLastColumn(int position) {
        return position % spanCount == spanCount - 1;
    }
}
