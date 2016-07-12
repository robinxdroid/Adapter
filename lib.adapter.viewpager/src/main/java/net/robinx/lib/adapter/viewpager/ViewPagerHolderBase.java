package net.robinx.lib.adapter.viewpager;

import android.view.LayoutInflater;
import android.view.View;

/**
 * ViewPager base holder
 * @author Robin
 * @since 2015-09-13 13:24:53
 */
public abstract class ViewPagerHolderBase<ItemDataType>{
	
    public abstract View createView(LayoutInflater layoutInflater);
 


    /**
     * 显示数据
     * @param adapter
     * @param position 位置
     * @param itemData item数据
     */
    public abstract void showData(ViewPagerAdapterBase<ItemDataType> adapter,int position, ItemDataType itemData);

}
