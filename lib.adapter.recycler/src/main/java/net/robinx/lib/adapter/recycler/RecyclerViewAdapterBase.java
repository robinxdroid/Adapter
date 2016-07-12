package net.robinx.lib.adapter.recycler;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView base adapter
 * 
 * @author Robin 
 * @since 2015-04-10 12:33:43
 * @param <ItemDataType>
 */
public abstract class RecyclerViewAdapterBase<ItemDataType>
		extends RecyclerView.Adapter<RecyclerViewHolderBase<ItemDataType>> {

	private static final String TAG = "system.out";

	protected ArrayList<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();

	private Object mEnclosingInstance;
	private Class<?> mCls;
	private int mItemViewResId;
	private Object[] mArgs;

	private RecyclerViewTypeManager<ItemDataType> mViewTypeManager;

	/*
	 * ===============================================================
	 * Constructor
	 * ===============================================================
	 */

	public RecyclerViewAdapterBase() {
	}

	public RecyclerViewAdapterBase(Object enclosingInstance, Class<?> cls, int itemViewResId,Object... args) {
		this.mEnclosingInstance = enclosingInstance;
		this.mCls = cls;
		this.mItemViewResId = itemViewResId;
		this.mArgs = args;
	}

	/*
	 * ===================================================================
	 * Override RecyclerView.Adapter
	 * ===================================================================
	 */
	
	@Override
	public int getItemViewType(int position) {
		if (mViewTypeManager != null) {
			return mViewTypeManager.getItemViewType(position, mItemDataList.get(position));
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getItemCount() {
		return mItemDataList.size();
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolderBase<ItemDataType> viewHolder, int position) {
		viewHolder.showData(this, position, mItemDataList.get(position));
	}

	@Override
	public RecyclerViewHolderBase<ItemDataType> onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		if (mViewTypeManager == null) {
			View itemView = View.inflate(viewGroup.getContext(), mItemViewResId, null);
			
			int argsLength = 1;
			if (mArgs != null && mArgs.length > 0) {
				argsLength += mArgs.length; 
			}
			Object[] args = new Object[argsLength];
			args[0] = itemView;
			int copyStart = 1;
			if (mArgs != null && mArgs.length > 0) {
				System.arraycopy(mArgs, 0, args, copyStart, mArgs.length);
			}
			RecyclerViewHolderCreator<ItemDataType> viewHolderCreator = DefaultRecyclerViewHolderCreatorImpl
					.create(mEnclosingInstance, mCls, args);
			
			if (viewHolderCreator == null) {
				throw new RuntimeException("view holder creator is null");
			} else {
				Log.i(TAG, "create holder");
				RecyclerViewHolderBase<ItemDataType> holder = viewHolderCreator.createViewHolder();
				return holder;
			}
		} else {
			RecyclerViewHolderCreator<ItemDataType> viewHolderCreator = mViewTypeManager.getViewHolderCreator(viewGroup.getContext(),viewType);
			if (viewHolderCreator == null) {
				throw new RuntimeException("view holder creator is null");
			} else {
				Log.i(TAG, "create holder");
				RecyclerViewHolderBase<ItemDataType> holder = viewHolderCreator.createViewHolder();
				return holder;
			}
		}

	}

	/*
	 * =====================================================================
	 * Getter And Setter
	 * =====================================================================
	 */

	public void setViewHolderClass(Class<?> cls, int itemViewResId, Object... args) {
		setViewHolderClass(null,cls,itemViewResId,args);
	}
	
	public void setViewHolderClass(Object enclosingInstance, Class<?> cls, int itemViewResId, Object... args) {
		this.mEnclosingInstance = enclosingInstance;
		this.mCls = cls;
		this.mItemViewResId = itemViewResId;
		this.mArgs = args;
	}
	
	public void setViewTypeManager(RecyclerViewTypeManager<ItemDataType> viewTypeManager){
		this.mViewTypeManager = viewTypeManager;
	}

	/*
	 * =======================================================
	 * Event delivery mechanism
	 * =======================================================
	 */

	@SuppressWarnings("unchecked")
	public <ReturnDataType> ReturnDataType onEvent(Object... params) {
		if (onEventListener != null) {
			return (ReturnDataType) onEventListener.onEvent(params);
		}
		return null;
	}

	private OnEventListener<?> onEventListener;

	public void setOnEventListener(OnEventListener<?> onEventListener) {
		this.onEventListener = onEventListener;
	}

	public interface OnEventListener<ReturnDataType> {
		ReturnDataType onEvent(Object... params);
	}

}
