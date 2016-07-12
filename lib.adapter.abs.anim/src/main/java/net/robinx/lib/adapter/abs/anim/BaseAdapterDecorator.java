package net.robinx.lib.adapter.abs.anim;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

/**
 * A decorator class that enables decoration of an instance of the {@link BaseAdapter} class.
 * <p/>
 * Classes extending this class can override methods and provide extra functionality before or after calling the super method.
 */
public abstract class BaseAdapterDecorator extends BaseAdapter implements ListViewWrapperSetter {

    /**
     * The {@link BaseAdapter} this {@code BaseAdapterDecorator} decorates.
     */
    @NonNull
    private final BaseAdapter mDecoratedBaseAdapter;

    /**
     * The {@link net.robinx.lib.adapter.abs.anim.ListViewWrapper} containing the ListView this {@code BaseAdapterDecorator} will be bound to.
     */
    @Nullable
    private ListViewWrapper mListViewWrapper;

    /**
     * Create a new {@code BaseAdapterDecorator}, decorating given {@link BaseAdapter}.
     *
     * @param baseAdapter the {@code} BaseAdapter to decorate.
     */
    protected BaseAdapterDecorator(@NonNull final BaseAdapter baseAdapter) {
        mDecoratedBaseAdapter = baseAdapter;
    }

    /**
     * Returns the {@link BaseAdapter} that this {@code BaseAdapterDecorator} decorates.
     */
    @NonNull
    public BaseAdapter getDecoratedBaseAdapter() {
        return mDecoratedBaseAdapter;
    }

    /**
     * Returns the root {@link BaseAdapter} this {@code BaseAdapterDecorator} decorates.
     */
    @NonNull
    protected BaseAdapter getRootAdapter() {
        BaseAdapter adapter = mDecoratedBaseAdapter;
        while (adapter instanceof BaseAdapterDecorator) {
            adapter = ((BaseAdapterDecorator) adapter).getDecoratedBaseAdapter();
        }
        return adapter;
    }

    public void setAbsListView(@NonNull final AbsListView absListView) {
        setListViewWrapper(new AbsListViewWrapper(absListView));
    }

    /**
     * Returns the {@link net.robinx.lib.adapter.abs.anim.ListViewWrapper} containing the ListView this {@code BaseAdapterDecorator} is bound to.
     */
    @Nullable
    public ListViewWrapper getListViewWrapper() {
        return mListViewWrapper;
    }

    /**
     * Alternative to {@link #setAbsListView(AbsListView)}. Sets the {@link net.robinx.lib.adapter.abs.anim.ListViewWrapper} which contains the ListView
     * this adapter will be bound to. Call this method before setting this adapter to the ListView. Also propagates to the decorated {@code BaseAdapter} if applicable.
     */
    @Override
    public void setListViewWrapper(@NonNull final ListViewWrapper listViewWrapper) {
        mListViewWrapper = listViewWrapper;

        if (mDecoratedBaseAdapter instanceof ListViewWrapperSetter) {
            ((ListViewWrapperSetter) mDecoratedBaseAdapter).setListViewWrapper(listViewWrapper);
        }
    }

    @Override
    public int getCount() {
        return mDecoratedBaseAdapter.getCount();
    }

    @Override
    public Object getItem(final int position) {
        return mDecoratedBaseAdapter.getItem(position);
    }

    @Override
    public long getItemId(final int position) {
        return mDecoratedBaseAdapter.getItemId(position);
    }

    @Override
    @NonNull
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        return mDecoratedBaseAdapter.getView(position, convertView, parent);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mDecoratedBaseAdapter.areAllItemsEnabled();
    }

    @Override
    @NonNull
    public View getDropDownView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        return mDecoratedBaseAdapter.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(final int position) {
        return mDecoratedBaseAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return mDecoratedBaseAdapter.getViewTypeCount();
    }

    @Override
    public boolean hasStableIds() {
        return mDecoratedBaseAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mDecoratedBaseAdapter.isEmpty();
    }

    @Override
    public boolean isEnabled(final int position) {
        return mDecoratedBaseAdapter.isEnabled(position);
    }

    @Override
    public void notifyDataSetChanged() {
        if (!(mDecoratedBaseAdapter instanceof ArrayAdapter<?>)) {
            // fix #35 dirty trick !
            // leads to an infinite loop when trying because ArrayAdapter triggers notifyDataSetChanged itself
            // TODO: investigate
            mDecoratedBaseAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Helper function if you want to force notifyDataSetChanged()
     */
    public void notifyDataSetChanged(final boolean force) {
        if (force || !(mDecoratedBaseAdapter instanceof ArrayAdapter<?>)) {
            // leads to an infinite loop when trying because ArrayAdapter triggers notifyDataSetChanged itself
            // TODO: investigate
            mDecoratedBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataSetInvalidated() {
        mDecoratedBaseAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void registerDataSetObserver(@NonNull final DataSetObserver observer) {
        mDecoratedBaseAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(@NonNull final DataSetObserver observer) {
        mDecoratedBaseAdapter.unregisterDataSetObserver(observer);
    }
}