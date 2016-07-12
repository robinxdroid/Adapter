package net.robinx.lib.adapter.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapterBase default implementation,as an common adapter
 * @author Robin
 * @since 2015-07-15 11:33:46
 * @param <ItemDataType>
 */
public class RecyclerListDataAdapter<ItemDataType> extends RecyclerViewAdapterBase<ItemDataType> {


	public RecyclerListDataAdapter() {
	}

	public RecyclerListDataAdapter(Object enclosingInstance, Class<?> cls, int args) {
		super(enclosingInstance, cls, args);
	}

	/**
	 * Dynamically add a data
	 * @param itemDataType entity class object
	 */
	public void append(ItemDataType itemDataType) {
		if (itemDataType != null) {
			mItemDataList.add(itemDataType);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 	Dynamically add a data
	 * 
	 * @param itemDataType entity class object
	 * @param withAnimate Whether show animation
	 * @param position Should be displayed animation insertion position
	 */
	public void append(ItemDataType itemDataType, boolean withAnimate, int position) {
		if (itemDataType != null) {
			if (withAnimate) {
				mItemDataList.add(position,itemDataType);
				notifyItemInserted(position);
				//fix data disorder
				if (position != mItemDataList.size()) {
					notifyItemRangeChanged(position, mItemDataList.size() - position);
				}
			}else {
				mItemDataList.add(position,itemDataType);
				notifyDataSetChanged();
			}
		}
	}

	public void append(ItemDataType itemDataType, boolean withAnimate, int position,int headersCount) {
		if (itemDataType != null) {
			if (withAnimate) {
				mItemDataList.add(position,itemDataType);
				notifyItemInserted(position+headersCount);
				//fix data disorder
				if (position != mItemDataList.size()) {
					notifyItemRangeChanged(position+headersCount, mItemDataList.size() - position+headersCount);
				}
			}else {
				mItemDataList.add(position,itemDataType);
				notifyDataSetChanged();
			}
		}
	}

	/**
	 * Dynamically add a set of data collection
	 * 
	 * @param itemDataTypes Entity class collection
	 */
	public void append(List<ItemDataType> itemDataTypes) {
		if (itemDataTypes.size() > 0) {
			for (ItemDataType itemDataType : itemDataTypes) {
				mItemDataList.add(itemDataType);
			}
			notifyDataSetChanged();
		}
	}

	/**
	 * Replace all data
	 * 
	 * @param itemDataTypes Entity class collection
	 */
	public void replace(List<ItemDataType> itemDataTypes) {
		mItemDataList.clear();
		if (itemDataTypes.size() > 0) {
			mItemDataList.addAll(itemDataTypes);
			notifyDataSetChanged();
		}
	}

	/**
	 * To remove a data set
	 * 
	 * @param position 
	 * @param withAnimate Whether show animation
	 */
	public void remove(int position, boolean withAnimate) {
		mItemDataList.remove(position);
		if (withAnimate) {
			notifyItemRemoved(position);
			//fix data disorder
			if(position != mItemDataList.size()){
				notifyItemRangeChanged(position, mItemDataList.size() - position);
			}
		}else {
			notifyDataSetChanged();
		}
	}

	public void remove(int position,int headersCount, boolean withAnimate) {
		mItemDataList.remove(position);
		if (withAnimate) {
			notifyItemRemoved(position+headersCount);
			//fix data disorder
			if(position != mItemDataList.size()){
				notifyItemRangeChanged(position+headersCount, mItemDataList.size() - (position+headersCount));
			}
		}else {
			notifyDataSetChanged();
		}
	}

	/**
	 * Remove all data
	 */
	public void removeAll() {
		mItemDataList.clear();
		notifyDataSetChanged();
	}

	public ArrayList<ItemDataType> getItemDataList() {
		return mItemDataList;
	}

	public void setItemDataList(ArrayList<ItemDataType> mItemDataList) {
		this.mItemDataList = mItemDataList;
	}

}
