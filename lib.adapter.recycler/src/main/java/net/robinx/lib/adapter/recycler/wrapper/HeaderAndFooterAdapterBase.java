package net.robinx.lib.adapter.recycler.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import net.robinx.lib.adapter.recycler.DefaultRecyclerViewHolderCreatorImpl;
import net.robinx.lib.adapter.recycler.RecyclerViewHolderBase;
import net.robinx.lib.adapter.recycler.RecyclerViewHolderCreator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Robin on 2016/7/9 10:19.
 */
public abstract class HeaderAndFooterAdapterBase<ItemDataType> extends RecyclerView.Adapter<RecyclerViewHolderBase<ItemDataType>> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;



    protected SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    protected SparseArrayCompat<Object> mHeaderEnclosingInstances = new SparseArrayCompat<>();
    protected SparseArrayCompat<Class<?>> mHeaderHolderClasses = new SparseArrayCompat<>();
    protected SparseArrayCompat<Object[]> mHeaderArgs = new SparseArrayCompat<>();

    protected SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    protected SparseArrayCompat<Object> mFooterEnclosingInstances = new SparseArrayCompat<>();
    protected SparseArrayCompat<Class<?>> mFooterHolderClasses = new SparseArrayCompat<>();
    protected SparseArrayCompat<Object[]> mFooterArgs = new SparseArrayCompat<>();

    private RecyclerView.Adapter<RecyclerViewHolderBase<ItemDataType>> mInnerAdapter;

    protected List<ItemDataType> mHeaderDataList = new ArrayList<>();
    protected List<ItemDataType> mFooterDataList = new ArrayList<>();

    public HeaderAndFooterAdapterBase(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerViewHolderBase<ItemDataType> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            Object enclosingInstance = mHeaderEnclosingInstances.get(viewType);
            Class<?> holderClass = mHeaderHolderClasses.get(viewType);
            View itemView = mHeaderViews.get(viewType);
            Object[] args = mHeaderArgs.get(viewType);

            RecyclerViewHolderBase<ItemDataType> viewHolderBase = createViewHolder(enclosingInstance, holderClass, itemView, args);
            return viewHolderBase;

        } else if (mFooterViews.get(viewType) != null) {
            Object enclosingInstance = mFooterEnclosingInstances.get(viewType);
            Class<?> holderClass = mFooterHolderClasses.get(viewType);
            View itemView = mFooterViews.get(viewType);
            Object[] args = mFooterArgs.get(viewType);

            RecyclerViewHolderBase<ItemDataType> viewHolderBase = createViewHolder(enclosingInstance, holderClass, itemView, args);
            return viewHolderBase;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    private RecyclerViewHolderBase<ItemDataType> createViewHolder(Object enclosingInstance, Class<?> holderClass, View itemView, Object[] args) {
        int argsLength = 1;
        if (args != null && args.length > 0) {
            argsLength += args.length;
        }
        Object[] resultArgs = new Object[argsLength];
        resultArgs[0] = itemView;
        int copyStart = 1;
        if (args != null && args.length > 0) {
            System.arraycopy(args, 0, resultArgs, copyStart, args.length);
        }
        RecyclerViewHolderCreator<ItemDataType> viewHolderCreator = DefaultRecyclerViewHolderCreatorImpl
                .create(enclosingInstance, holderClass, resultArgs);
        return viewHolderCreator.createViewHolder();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    protected int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderBase<ItemDataType> holder, int position) {
        if (isHeaderViewPos(position)) {
            if (mHeaderDataList.size() > 0) {
                holder.showData(this,position, mHeaderDataList.get(position));
            }
            return;
        }
        if (isFooterViewPos(position)) {
            if (mFooterDataList.size() > 0) {
                holder.showData(this,position- getHeadersCount()-getRealItemCount(), mFooterDataList.get(position- getHeadersCount()-getRealItemCount()));
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperHelper.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperHelper.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolderBase<ItemDataType> holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperHelper.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(Object enclosingInstance, Class<?> holderClass, View view, Object... args) {
        if (enclosingInstance != null) {
            mHeaderEnclosingInstances.put(mHeaderEnclosingInstances.size() + BASE_ITEM_TYPE_HEADER, enclosingInstance);
        }

        if (holderClass != null) {
            mHeaderHolderClasses.put(mHeaderHolderClasses.size() + BASE_ITEM_TYPE_HEADER, holderClass);
        } else {
            throw new IllegalArgumentException("You must add 'holderClass' parameters");
        }
        if (args != null && args.length > 0) {
            mHeaderArgs.put(mHeaderArgs.size() + BASE_ITEM_TYPE_HEADER, args);
        }

        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(Object enclosingInstance, Class<?> holderClass, View view, Object... args) {
        if (enclosingInstance != null) {
            mFooterEnclosingInstances.put(mFooterEnclosingInstances.size() + BASE_ITEM_TYPE_FOOTER, enclosingInstance);
        }
        if (holderClass != null) {
            mFooterHolderClasses.put(mFooterHolderClasses.size() + BASE_ITEM_TYPE_FOOTER, holderClass);
        } else {
            throw new IllegalArgumentException("You must add 'holderClass' parameters");
        }
        if (args != null && args.length > 0) {
            mFooterArgs.put(mFooterArgs.size() + BASE_ITEM_TYPE_FOOTER, args);
        }

        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    //-----------------------------------------------------------------------------
    // RecyclerView methods
    //-----------------------------------------------------------------------------

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mInnerAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mInnerAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        mInnerAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return mInnerAdapter.getItemId(position);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
    }


}