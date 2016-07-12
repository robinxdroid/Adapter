package net.robinx.lib.adapter.viewpager;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager base adapter
 * 
 * @author Robin
 * @since 2015-09-13 12:24:22
 */
public abstract class ViewPagerAdapterBase<ItemDataType> extends PagerAdapter {

	protected ArrayList<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();
	private ViewPagerHolderCreator<ItemDataType> mViewPagerHolderCreator;
	protected ViewPagerHolderBase<ItemDataType> holder;

	protected boolean isInfinite;

	/*
	 * =============================================================== Override
	 * PagerAdapter
	 * ===============================================================
	 */

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final ItemDataType itemData = getItem(position);
		if (holder == null) {
			holder = createViewHolder();
		}
		View convertView = holder.createView(LayoutInflater.from(container.getContext()));
		holder.showData(this, getPositionForActual(position), itemData);
		container.addView(convertView);
		return convertView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public int getCount() {
		if (mItemDataList == null) {
			return 0;
		}
		return isInfinite ? Integer.MAX_VALUE : mItemDataList.size();
	}

	private ViewPagerHolderBase<ItemDataType> createViewHolder() {
		if (mViewPagerHolderCreator == null) {
			throw new RuntimeException("view holder creator is null");
		}
		if (mViewPagerHolderCreator != null) {
			return mViewPagerHolderCreator.createViewHolder();
		}
		return null;
	}

	/*
	 * =============================================================== Compute
	 * ===============================================================
	 */

	public ItemDataType getItem(int position) {
		if (mItemDataList == null)
			return null;
		return mItemDataList.get(getPositionForActual(position));
	}

	/**
	 * Compute the real position
	 * 
	 * @param position
	 * @return
	 */
	public int getPositionForActual(int position) {
		if (null == mItemDataList || mItemDataList.size() == 0) {
			return 0;
		}
		return position % mItemDataList.size();
	}

	/*
	 * =============================================================== Getter
	 * And Setter
	 * ===============================================================
	 */

	public void setViewHolderClass(final Object enclosingInstance, final Class<?> cls, final Object... args) {
		mViewPagerHolderCreator = DefaultViewPagerHolderCreatorImpl.create(enclosingInstance, cls, args);
	}

	public boolean isInfinite() {
		return isInfinite;
	}

	public void setInfinite(boolean isInfinite) {
		this.isInfinite = isInfinite;
	}

	public int getInfinitePosition() {
		if (mItemDataList.size() <= 0) {
			throw new RuntimeException("Currently mItemData.size() must be > 0");
		}
		int n = Integer.MAX_VALUE / 2 % mItemDataList.size();
		int infinitePosition = Integer.MAX_VALUE / 2 - n;
		return infinitePosition;
	}

	/*
	 * =======================================================
	 * 事件传递机制，用于传递"ViewHolder"事件到"Activity"中
	 * =======================================================
	 */

	/**
	 * 由Holder调用
	 * 
	 * @param params
	 *            需要传递到外部的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <ReturnDataType> ReturnDataType onEvent(Object... params) {
		if (onEventListener != null) {
			return (ReturnDataType) onEventListener.onEvent(params);
		}
		return null;
	}

	private OnEventListener<?> onEventListener;

	/**
	 * Activity等组件可设置此接口，接收holder传递的参数与事件
	 * 
	 * @param onEventListener
	 */
	public void setOnEventListener(OnEventListener<?> onEventListener) {
		this.onEventListener = onEventListener;
	}

	/**
	 * Holder与Activity等组件交互事件与数据接口
	 * 
	 * @author Robin
	 * @since 2015-09-16 19:40:34
	 * @param <ReturnDataType>
	 */
	public interface OnEventListener<ReturnDataType> {
		ReturnDataType onEvent(Object... params);
	}

}
