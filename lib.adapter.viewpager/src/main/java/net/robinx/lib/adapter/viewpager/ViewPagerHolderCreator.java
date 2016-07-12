package net.robinx.lib.adapter.viewpager;

/**
 * ViewHolder constructor
 * @author Robin
 * @since 2016-03-21 16:43:09
 */
public interface ViewPagerHolderCreator<ItemDataType> {
	
    public ViewPagerHolderBase<ItemDataType> createViewHolder();
    
}