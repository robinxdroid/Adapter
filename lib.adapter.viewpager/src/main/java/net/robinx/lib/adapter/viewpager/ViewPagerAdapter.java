package net.robinx.lib.adapter.viewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager Adapter
 * @author Robin
 * @since 2015-09-13 14:11:37
 */
public class ViewPagerAdapter<ItemDataType> extends ViewPagerAdapterBase<ItemDataType>{
	/**
	 * 动态增加一条数据
	 * 
	 * @param itemDataType
	 *            数据实体类对象
	 */
	public void append(ItemDataType itemDataType) {
		if (itemDataType != null) {
			mItemDataList.add(itemDataType);
			notifyDataSetChanged();
		}
	}

	/**
	 * 动态增加一组数据集合
	 * 
	 * @param itemDataTypes
	 *            数据实体类集合
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
	 * 替换全部数据
	 * 
	 * @param itemDataTypes
	 *            数据实体类集合
	 */
	public void replace(List<ItemDataType> itemDataTypes) {
		mItemDataList.clear();
		if (itemDataTypes.size() > 0) {
			mItemDataList.addAll(itemDataTypes);
			notifyDataSetChanged();
		}
	}

	/**
	 * 移除一条数据集合
	 * 
	 * @param position
	 */
	public void remove(int position) {
		mItemDataList.remove(position);
		notifyDataSetChanged();
	}

	/**
	 * 移除所有数据
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