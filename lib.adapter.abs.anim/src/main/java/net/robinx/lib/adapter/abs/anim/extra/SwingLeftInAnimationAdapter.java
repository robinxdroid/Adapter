package net.robinx.lib.adapter.abs.anim.extra;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.robinx.lib.adapter.abs.anim.SingleAnimationAdapter;

/**
 * An implementation of the AnimationAdapter class which applies a
 * swing-in-from-the-left-animation to views.
 */
public class SwingLeftInAnimationAdapter extends SingleAnimationAdapter {

    private static final String TRANSLATION_X = "translationX";

    public SwingLeftInAnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    @Override
    protected Animator getAnimator(@NonNull final ViewGroup parent, @NonNull final View view) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, 0 - parent.getWidth(), 0);
    }
}
