package net.robinx.lib.adapter.recycler;

import android.content.Context;
import android.view.View;

/**
 * For RecyclerView multiple view type.
 * 
 * @author Robin
 * @since 2016-03-22 10:29:48
 *
 */
public class RecyclerViewTypeManager<ItemDataType> {

	public RecyclerViewTypeManager() {
	}
	
	public RecyclerViewTypeManager(int[] viewTypes, int[] itemViewIds, Class<?>[] viewHolderClasses) {
		this(viewTypes, itemViewIds, viewHolderClasses, null);
	}
	
	public RecyclerViewTypeManager(int[] viewTypes, int[] itemViewIds, Class<?>[] viewHolderClasses, Object[] enclosingInstances,Object[]...argsArray) {
		this.mViewTypes = viewTypes;
		this.mItemViewIds = itemViewIds;
		this.mViewHolderClasses = viewHolderClasses;
		this.mEnclosingInstances = enclosingInstances;
		this.mArgsArray = argsArray;
	}

	private int[] mViewTypes;

	private Class<?>[] mViewHolderClasses;

	private int[] mItemViewIds;

	private Object[] mEnclosingInstances;
	
	private Object[][] mArgsArray;

	private OnItemViewTypeLogic<ItemDataType> mItemViewTypeLogic;

	public int[] getViewTypes() {
		return mViewTypes;
	}

	public RecyclerViewTypeManager<ItemDataType> viewTypes(int... viewTypes) {
		this.mViewTypes = viewTypes;
		return this;
	}

	public RecyclerViewTypeManager<ItemDataType> viewHolderClasses(Class<?>... viewHolderClasses) {
		this.mViewHolderClasses = viewHolderClasses;
		return this;
	}

	public RecyclerViewTypeManager<ItemDataType> itemViewIds(int... itemViewIds) {
		this.mItemViewIds = itemViewIds;
		return this;
	}

	public RecyclerViewTypeManager<ItemDataType> enclosingInstances(Object... enclosingInstances) {
		this.mEnclosingInstances = enclosingInstances;
		return this;
	}

	public RecyclerViewTypeManager<ItemDataType> argsArray(Object[]... argsArray) {
		this.mArgsArray = argsArray;
		return this;
	}

	public RecyclerViewTypeManager<ItemDataType> itemViewTypeLogic(OnItemViewTypeLogic<ItemDataType> itemViewTypeLogic) {
		this.mItemViewTypeLogic = itemViewTypeLogic;
		return this;
	}

	public int getItemViewType(int position, ItemDataType itemData) {
		// Condition
		if (mItemViewTypeLogic != null) {
			return mItemViewTypeLogic.getItemViewType(position, itemData);
		}

		return getViewTypes()[0];
	}

	public RecyclerViewHolderCreator<ItemDataType> getViewHolderCreator(Context context, int viewType) {
		RecyclerViewHolderCreator<ItemDataType> viewHolderCreator = null;
		for (int i = 0, size = getViewTypes().length; i < size; i++) {
			if (viewType == getViewTypes()[i]) {
				Object enclosingInstance = null;
				if (mEnclosingInstances != null && mEnclosingInstances.length > 0) {
					if (i < mEnclosingInstances.length) {
						enclosingInstance = mEnclosingInstances[i];
					}
				}
				
				Class<?> viewHolderClass = null;
				if (mViewHolderClasses != null && mViewHolderClasses.length >= size ) {
					viewHolderClass = mViewHolderClasses[i];
				}else {
					throw new IllegalArgumentException("You forgot to set the ViewHolderClasses, or ViewHolderClasses length is less than the length of the ViewTypes");
				}
				
				int itemViewId = 0;
				if (mItemViewIds != null && mItemViewIds.length >= size) {
					itemViewId = mItemViewIds[i];
				}else {
					throw new IllegalArgumentException("You forgot to set the ItemViewIds, or ItemViewIds length is less than the length of the ViewTypes");
				}
				
				Object[] args = null;
				if (mArgsArray != null && mArgsArray.length > 0) {
					if (i< mArgsArray.length) {
						args = mArgsArray[i];
						
						Object[] tempArgs = new Object[args.length+1];
						tempArgs[0] = View.inflate(context, itemViewId, null);
						int copyStart = 1;
						System.arraycopy(args, 0, tempArgs, copyStart, args.length);
						viewHolderCreator = DefaultRecyclerViewHolderCreatorImpl.create(enclosingInstance, viewHolderClass, tempArgs);
						break;
					}
				}
				viewHolderCreator = DefaultRecyclerViewHolderCreatorImpl.create(enclosingInstance, viewHolderClass, View.inflate(context, itemViewId, null));
				break;
			}
		}
		return viewHolderCreator;
	}

	public interface OnItemViewTypeLogic<ItemDataType> {
		int getItemViewType(int position, ItemDataType itemData);
	}
}
