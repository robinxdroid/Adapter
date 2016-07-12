package net.robinx.lib.adapter.recycler.anim.extra;


import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.robinx.lib.adapter.recycler.anim.AnimatorAdapter;

public class AlphaAnimatorAdapter<T extends RecyclerView.ViewHolder> extends AnimatorAdapter<T> {

    public AlphaAnimatorAdapter(RecyclerView.Adapter<T> adapter, RecyclerView recyclerView) {
        super(adapter, recyclerView);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        return new Animator[0];
    }
}
