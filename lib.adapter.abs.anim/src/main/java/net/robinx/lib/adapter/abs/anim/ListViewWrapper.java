package net.robinx.lib.adapter.abs.anim;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public interface ListViewWrapper {

    @NonNull
    ViewGroup getListView();

    @Nullable
    View getChildAt(int index);

    int getFirstVisiblePosition();

    int getLastVisiblePosition();

    int getCount();

    int getChildCount();

    int getHeaderViewsCount();

    int getPositionForView(@NonNull View view);

    @Nullable
    ListAdapter getAdapter();

    void smoothScrollBy(int distance, int duration);
}
