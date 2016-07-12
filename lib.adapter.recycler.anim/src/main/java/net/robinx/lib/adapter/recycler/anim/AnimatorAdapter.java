package net.robinx.lib.adapter.recycler.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * The class applies multiple {@link Animator}s at once to views when they are first shown.
 * The Animators applied include the animations specified in {@link #getAnimators(View)}, plus an alpha transition.
 *
 */
public abstract class AnimatorAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private RecyclerView.Adapter<T> mAdapter;

    /**
     * Saved instance state key for the ViewAnimator
     */
    private static final String SAVEDINSTANCESTATE_VIEWANIMATOR = "savedinstancestate_viewanimator";

    /**
     * The ViewAnimator responsible for animating the Views.
     */
    @Nullable
    private ViewAnimator mViewAnimator;

    protected RecyclerView mRecyclerView;

    //-----------------------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------------------

    public AnimatorAdapter(RecyclerView.Adapter<T> adapter, RecyclerView recyclerView) {
        mAdapter = adapter;
        mViewAnimator = new ViewAnimator(recyclerView);
        mRecyclerView = recyclerView;
    }


    //-----------------------------------------------------------------------------
    // Animators methods
    //-----------------------------------------------------------------------------

    /**
     * Returns the Animators to apply to the views.
     *
     * @param view The view that will be animated, as retrieved by onBindViewHolder().
     */
    @NonNull
    public abstract Animator[] getAnimators(@NonNull View view);

    /**
     * Alpha property
     */
    private static final String ALPHA = "alpha";

    /**
     * Animates given View
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    private void animateView(final View view, final int position) {
        assert mViewAnimator != null;
        assert mRecyclerView != null;

        Animator[] animators = getAnimators(view);
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);
        Animator[] concatAnimators = AnimatorUtil.concatAnimators(animators, alphaAnimator);
        mViewAnimator.animateViewIfNecessary(position, view, concatAnimators);
    }

    @Nullable
    public ViewAnimator getViewAnimator() {
        return mViewAnimator;
    }

    //-----------------------------------------------------------------------------
    // SaveInstanceState
    //-----------------------------------------------------------------------------

    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     */
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        if (mViewAnimator != null) {
            bundle.putParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR, mViewAnimator.onSaveInstanceState());
        }

        return bundle;
    }

    /**
     * Restores this AnimationAdapter's state.
     *
     * @param parcelable the Parcelable object previously returned by {@link #onSaveInstanceState()}.
     */
    public void onRestoreInstanceState(@Nullable final Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            if (mViewAnimator != null) {
                mViewAnimator.onRestoreInstanceState(bundle.getParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR));
            }
        }
    }

    //-----------------------------------------------------------------------------
    // RecyclerView methods
    //-----------------------------------------------------------------------------

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        mAdapter.onBindViewHolder(holder, position);
        mViewAnimator.cancelExistingAnimation(holder.itemView);
        animateView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        mAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    @Override
    public void onViewRecycled(T holder) {
        mAdapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(T holder) {
        return mAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(T holder) {
        mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(T holder) {
        mAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mAdapter.onDetachedFromRecyclerView(recyclerView);
    }
}
