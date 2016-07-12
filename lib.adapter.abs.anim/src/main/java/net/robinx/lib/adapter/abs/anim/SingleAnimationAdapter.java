package net.robinx.lib.adapter.abs.anim;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * An implementation of AnimationAdapter which applies a single Animator to
 * views.
 */
public abstract class SingleAnimationAdapter extends AnimationAdapter {

    protected SingleAnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull final ViewGroup parent, @NonNull final View view) {
        Animator animator = getAnimator(parent, view);
        return new Animator[]{animator};
    }

    /**
     * Get the {@link Animator} to apply to the {@link View}.
     *
     * @param parent the {@link ViewGroup} which is the parent of the View.
     * @param view   the View that will be animated, as retrieved by
     *               {@link #getView(int, View, ViewGroup)}.
     */
    @NonNull
    protected abstract Animator getAnimator(@NonNull ViewGroup parent, @NonNull View view);

}
