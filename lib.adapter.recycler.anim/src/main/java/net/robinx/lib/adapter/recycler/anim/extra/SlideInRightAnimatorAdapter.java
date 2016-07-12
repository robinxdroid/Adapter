package net.robinx.lib.adapter.recycler.anim.extra;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.robinx.lib.adapter.recycler.anim.AnimatorAdapter;

/**
 * An implementation of the AnimatorAdapter class which applies a
 * swing-in-from-the-right-animation to views.
 */
public class SlideInRightAnimatorAdapter<T extends RecyclerView.ViewHolder> extends AnimatorAdapter<T> {

    private static final String TRANSLATION_X = "translationX";

    public SlideInRightAnimatorAdapter(RecyclerView.Adapter<T> adapter,
                                       RecyclerView recyclerView) {
        super(adapter, recyclerView);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_X, mRecyclerView.getLayoutManager().getWidth(), 0)};
    }
}