package net.robinx.lib.adapter.recycler.anim.extra;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.robinx.lib.adapter.recycler.anim.AnimatorAdapter;

/**
 * An implementation of the AnimatorAdapter class which applies a
 * scale-animation to views.
 */
public class ScaleInAnimatorAdapter<T extends RecyclerView.ViewHolder> extends AnimatorAdapter<T> {

    private static final float DEFAULT_SCALE_FROM = 0.6f;

    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";

    private final float mScaleFrom;

    public ScaleInAnimatorAdapter(@NonNull final RecyclerView.Adapter<T> adapter,
                                  RecyclerView recyclerView) {
        this(adapter, recyclerView, DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimatorAdapter(@NonNull final RecyclerView.Adapter<T> adapter,
                                  RecyclerView recyclerView,
                                  final float scaleFrom) {
        super(adapter, recyclerView);
        mScaleFrom = scaleFrom;
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, SCALE_X, mScaleFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, mScaleFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY};
    }
}