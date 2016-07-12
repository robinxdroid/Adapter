package net.robinx.lib.adapter.abs;

import android.content.Context;

/**
 * For RecyclerView multiple view type.
 * 
 * @author Robin
 * @since 2016-03-22 10:29:48
 *
 */
public class AbsViewTypeManager<ItemDataType> {

	public AbsViewTypeManager() {
	}
	
	public AbsViewTypeManager(int[] viewTypes, Class<?>[] viewHolderClasses) {
		this(viewTypes, viewHolderClasses, null);
	}

	public AbsViewTypeManager(int[] viewTypes, Class<?>[] viewHolderClasses, Object[] enclosingInstances,Object[]...argsArray) {
		this.mViewTypes = viewTypes;
		this.mViewHolderClasses = viewHolderClasses;
		this.mEnclosingInstances = enclosingInstances;
		this.mArgsArray = argsArray;
	}

	private int[] mViewTypes;

	private Class<?>[] mViewHolderClasses;

	private Object[] mEnclosingInstances;
	
	private Object[][] mArgsArray;

	private OnItemViewTypeLogic<ItemDataType> mItemViewTypeLogic;

	public int[] getViewTypes() {
		return mViewTypes;
	}

	public AbsViewTypeManager<ItemDataType> viewTypes(int... viewTypes) {
		this.mViewTypes = viewTypes;
		return this;
	}

	public AbsViewTypeManager<ItemDataType> viewHolderClasses(Class<?>... viewHolderClasses) {
		this.mViewHolderClasses = viewHolderClasses;
		return this;
	}

	public AbsViewTypeManager<ItemDataType> enclosingInstances(Object... enclosingInstances) {
		this.mEnclosingInstances = enclosingInstances;
		return this;
	}
	
	public AbsViewTypeManager<ItemDataType> argsArray(Object[]... argsArray) {
		this.mArgsArray = argsArray;
		return this;
	}

	public AbsViewTypeManager<ItemDataType> itemViewTypeLogic(OnItemViewTypeLogic<ItemDataType> itemViewTypeLogic) {
		this.mItemViewTypeLogic = itemViewTypeLogic;
		return this;
	}

	public int getItemViewType(int position, ItemDataType itemData) {
		// custom condition
		if (mItemViewTypeLogic != null) {
			return mItemViewTypeLogic.getItemViewType(position, itemData);
		}

		// default condition
		return getViewTypes()[0];
	}

	public ViewHolderCreator<ItemDataType> getViewHolderCreator(Context context, int viewType) {
		ViewHolderCreator<ItemDataType> viewHolderCreator = null;
		for (int i = 0, size = getViewTypes().length; i < size; i++) {
			if (viewType == getViewTypes()[i]) {
				Object enclosingInstance = null;
				if (mEnclosingInstances != null && mEnclosingInstances.length > 0) {
					if (i< mEnclosingInstances.length) {
						enclosingInstance = mEnclosingInstances[i];
					}
				}
				
				Class<?> viewHolderClass = null;
				if (mViewHolderClasses != null && mViewHolderClasses.length >= size ) {
					viewHolderClass = mViewHolderClasses[i];
				}else {
					throw new IllegalArgumentException("You forgot to set the ViewHolderClasses, or ViewHolderClasses length is less than the length of the ViewTypes");
				}
				
				Object[] args = null;
				if (mArgsArray != null && mArgsArray.length > 0) {
					if (i< mArgsArray.length) {
						args = mArgsArray[i];
						viewHolderCreator = DefaultViewHolderCreatorImpl.create(enclosingInstance, viewHolderClass,args);
						break;
					}
				}
				viewHolderCreator = DefaultViewHolderCreatorImpl.create(enclosingInstance, viewHolderClass);
				break;
			}
		}
		return viewHolderCreator;
	}

	public interface OnItemViewTypeLogic<ItemDataType> {
		int getItemViewType(int position, ItemDataType itemData);
	}
}
