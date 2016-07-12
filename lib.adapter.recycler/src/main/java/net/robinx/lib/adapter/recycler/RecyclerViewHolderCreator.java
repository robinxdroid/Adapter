package net.robinx.lib.adapter.recycler;

/**
 * ViewHolder constructor
 * @author Robin
 * @since 2015-05-24 11:40:21
 */
public interface RecyclerViewHolderCreator<ItemDataType> {
	
    public RecyclerViewHolderBase<ItemDataType> createViewHolder();
    
}